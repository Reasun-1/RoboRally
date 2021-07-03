package server.game;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import protocol.ExecuteOrder;
import server.feldobjects.*;
import server.maps.Board;
import server.network.Server;
import server.registercards.*;
import server.upgradecards.*;

//import javax.print.attribute.IntegerSyntax;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
//import java.util.zip.CheckedInputStream;

public class Game {

    private static final Logger logger = Logger.getLogger(Game.class.getName());
    private final static Game game = new Game();

    int energyBank; // total common energy cubes for the game


    //==========================================================================
    public static HashMap<Integer, List<RegisterCard>> undrawnCards = new HashMap<>(); // key = clientID, value = decks of undrawn cards of all players
    public static HashMap<Integer, List<RegisterCard>> discardedCards = new HashMap<>(); // decks of discarded cards of all players
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
    public static List<List<List<FeldObject>>> board = new ArrayList<>(); // selected map
    public static String mapName = null; // storage for map name
    public static HashSet<Integer> clientIDs = new HashSet<>(); // storage the clientIDs
    public static HashMap<Integer, Position> playerPositions = new HashMap<>(); // current position of each player
    public static HashMap<Integer, Position> playersLastPositions = new HashMap<>(); // store the last position for corner belt
    public static HashMap<Integer, Position> startPositionsAllClients = new HashMap<>(); // storage of all start positions
    public static HashMap<Integer, Integer> restToDrawCardCount = new HashMap<>(); // count for the situation, that undrawnCards not enough
    public static HashMap<Integer, RegisterCard[]> registersAllClients = new HashMap<>(); // registers of all players
    public static List<Integer> selectionFinishList = new ArrayList<>(); // clientID who finished programming
    public static List<Integer> priorityEachTurn = new ArrayList<>(); // e.g. [22,33,11] means clientID 22 has first priority in this round
    public static int registerPointer = 0; // to point the current register
    public static int changePriorityRegPointer = -1; // to point the register which changed priority because of upgrade card Admin
    public static List<Integer> changedPriorityList = new ArrayList<>(); // changed priority due to Admin played
    public static HashMap<Integer, Direction> directionsAllClients = new HashMap<>(); // current directions of all clients: key=clientID, value=Direction
    public static List<Integer> activePlayersList = new ArrayList<>(); // if a player out of board, remove it from this list. For priority calculate
    public static HashMap<Integer, HashSet<Integer>> arrivedCheckpoints = new HashMap<>(); // who has arrived which checkpoints;
    public static int checkPointTotal = 0;
    public static Position rebootPosition = new Position();// storage of the reboot position
    public static Stack<RegisterCard> spamPile = new Stack<>(); // pile for 38 Spam cards
    public static Stack<RegisterCard> trojanHorsePile = new Stack<>(); // pile for 12 TrojanHorse cards
    public static Stack<RegisterCard> wormPile = new Stack<>(); // pile for 6 Worm cards
    public static Stack<RegisterCard> virusPile = new Stack<>(); // pile for 18 Virus cards
    public static boolean hasMap = false; // flag for selected map
    public static HashMap<Integer, Integer> energyCubes = new HashMap<>();// key=clientID, value=energyCount
    public static Position positionAntenna = null;
    public static String directionAntenna = null;
    public static HashMap<Integer, Boolean> clientsOnBoard = new HashMap<>();// key=clientID, value=isOnBoard;
    public static Stack<UpgradeCard> upgradeShop = new Stack<>();
    //key=clientID value=(key=UpgradeCardName value=count of this card)
    public static HashMap<Integer, HashMap<String, Integer>> upgradesCardsAllClients = new HashMap<>();
    public static List<Integer> buyUpgradeCardsFinished = new ArrayList<>(); // clientID who finished buying upgrade cards
    // count of the SpamCards each round; key=cleintID value=count of Spam Cards
    public static HashMap<Integer, Integer> countSpamAllClients = new HashMap<>();
    // storage for moving checkpoints: key=checkpointNum value=[x,y]
    public static HashMap<Integer, int[]> movingCheckpoints = new HashMap<>();

    /**
     * constructor Game:
     *
     * @return only one instance of the Game
     */
    public static Game getInstance() {
        return game;
    }

    /**
     * set the parameters of the game
     */
    public void initGame() {

        for (int client : clientIDs) {
            // init registers with 5 slots for all clients
            RegisterCard[] registers = new RegisterCard[5];
            registersAllClients.put(client, registers);

            // init directions of all clients
            if (mapName.equals("Death Trap")) {
                directionsAllClients.put(client, Direction.LEFT);
            } else {
                directionsAllClients.put(client, Direction.RIGHT);
            }

            // init moving checkpoints for map twister
            if (mapName.equals("Twister")) {
                int[] location1 = new int[2];
                location1[0] = 9;
                location1[1] = 1;
                movingCheckpoints.put(1, location1);

                int[] location2 = new int[2];
                location2[0] = 6;
                location2[1] = 6;
                movingCheckpoints.put(2, location2);

                int[] location3 = new int[2];
                location3[0] = 4;
                location3[1] = 1;
                movingCheckpoints.put(3, location3);

                int[] location4 = new int[2];
                location4[0] = 9;
                location4[1] = 8;
                movingCheckpoints.put(4, location4);
            }

            // init activePlayerList with all clients
            activePlayersList.add(client);

            // init arrivedCheckpoints list
            arrivedCheckpoints.put(client, new HashSet<>());

            // init energy cubes for each client
            energyCubes.put(client, 5);

            clientsOnBoard.put(client, true);

            // init upgrade cards for each client(null cards at the beginning)
            HashMap<String, Integer> initUpgrades = new HashMap<>();
            initUpgrades.put(new MemorySwap().getCardName(), 0);
            initUpgrades.put(new RealLaser().getCardName(), 0);
            initUpgrades.put(new AdminPrivilege().getCardName(), 0);
            initUpgrades.put(new SpamBlocker().getCardName(), 0);
            upgradesCardsAllClients.put(client, initUpgrades);

            // inti count of spam cards for each cleint
            countSpamAllClients.put(client, 0);

        }

        // init undrawn and discarded cards deck for each player
        for (int client : clientIDs) {
            // for undrawn cards
            List<RegisterCard> cards = new ArrayList<>();

            for (int i = 0; i < MoveIII.cardCount; i++) {
                cards.add(new MoveIII());
            }
            for (int i = 0; i < PowerUp.cardCount; i++) {
                cards.add(new PowerUp());
            }
            for (int i = 0; i < MoveI.cardCount; i++) {
                cards.add(new MoveI());
            }
            for (int i = 0; i < BackUp.cardCount; i++) {
                cards.add(new BackUp());
            }
            for (int i = 0; i < TurnLeft.cardCount; i++) {
                cards.add(new TurnLeft());
            }

            for (int i = 0; i < UTurn.cardCount; i++) {
                cards.add(new UTurn());
            }
            for (int i = 0; i < TurnRight.cardCount; i++) {
                cards.add(new TurnRight());
            }
            for (int i = 0; i < Again.cardCount; i++) {
                cards.add(new Again());
            }

            for (int i = 0; i < MoveII.cardCount; i++) {
                cards.add(new MoveII());
            }

            List<RegisterCard> shuffledDeck = shuffleUndrawnDeck(cards);

            undrawnCards.put(client, shuffledDeck);

            // init for discarded cards
            List<RegisterCard> discards = new ArrayList<>();
            discardedCards.put(client, discards);

            // init for damage card piles
            for (int i = 0; i < Spam.cardCount; i++) {
                spamPile.push(new Spam());
            }
            for (int i = 0; i < Trojan.cardCount; i++) {
                trojanHorsePile.push(new Trojan());
            }
            for (int i = 0; i < Worm.cardCount; i++) {
                wormPile.push(new Worm());
            }
            for (int i = 0; i < Virus.cardCount; i++) {
                virusPile.push(new Virus());
            }

            // init upgrade shop
            Stack<UpgradeCard> newStackOfUpgrades = getAndShuffleUndrawnDeck();
            upgradeShop = newStackOfUpgrades;
        }
    }

    /**
     * init Board
     */
    public void initBoard() {
        // init board (start board + game board)
        for (int i = 0; i < 13; i++) {
            List<List<FeldObject>> row = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                List<FeldObject> zelle = new ArrayList<>();
                row.add(zelle);
            }
            board.add(row);
        }
    }

    /**
     * invoked fom "MapSelected" in ExecuteOrder
     *
     * @param mapName
     */
    public void setMap3DList(String mapName) {
        logger.info("Game sets map.");
        switch (mapName) {
            case "Dizzy Highway":
                Board.buildDizzyHighway();
                break;
            case "Lost Bearings":
                Board.buildLostBearings();
                break;
            case "Death Trap":
                Board.buildDeathTrap();
                break;
            case "Extra Crispy":
                Board.buildExtraCrispy();
                break;
            case "Twister":
                Board.buildTwister();
                break;
        }
        // count the total num of checkpoints
        findCheckpointTotal();
        // store the position of reboot
        findRebootPosition();
        // store the position and direction of antenna
        findAntenna();
    }


    /**
     * key = clientID, value = list of undrawn cards
     * convert undrawn cards from object to Strings for server
     *
     * @return
     */
    public HashMap<Integer, List<String>> gameHandleYourCards() throws IOException {
        // before new storage for count, clear the count
        restToDrawCardCount.clear();
        // temp list of total drawn cards for all players
        HashMap<Integer, List<String>> drawCardsAllClients = new HashMap<>();

        for (int clientID : undrawnCards.keySet()) {
            // list of drawn cars for each player
            List<String> list = new ArrayList<>();

            int cardCount = undrawnCards.get(clientID).size();
            System.out.println(cardCount);

            // if undrawnCards more than 9, draw 9; otherwise draw all the cars
            if (cardCount >= 9) {
                for (int i = 0; i < 9; i++) {
                    RegisterCard card = undrawnCards.get(clientID).get(i);
                    String cardName = card.getCardName();
                    list.add(cardName);
                    // add the drawn cards to discarded card deck or damage card piles
                    if (card.getCardType().equals("PROGRAMME")) {
                        discardedCards.get(clientID).add(card);
                    } else { // if this is a damage card, put it into damage card piles
                        int curCountSpam = countSpamAllClients.get(clientID);
                        countSpamAllClients.put(clientID, curCountSpam + 1);

                        if (card.getCardName().equals("Spam")) {
                            spamPile.push(card);
                        } else if (card.getCardName().equals("Trojan")) {
                            trojanHorsePile.push(card);
                        } else if (card.getCardName().equals("Virus")) {
                            virusPile.push(card);
                        } else if (card.getCardName().equals("Worm")) {
                            wormPile.push(card);
                        }
                    }
                }
                // the first 9 cards have been drawn, remove them from deck
                for (int i = 0; i < 9; i++) {
                    undrawnCards.get(clientID).remove(0);
                }
                Server.getServer().handleNotYourCards(clientID, 9);

            } else { // if undrawnCards less than 9, draw all
                System.out.println("game for undrawn cards less 9");
                for (int i = 0; i < cardCount; i++) {
                    RegisterCard cd = undrawnCards.get(clientID).get(i);
                    String cardName = cd.getCardName();
                    list.add(cardName);
                }
                // temporary list for storage the drawn cards
                List<RegisterCard> tempListForDrawnCards = new ArrayList<>();
                tempListForDrawnCards.addAll(undrawnCards.get(clientID));

                // all the cards have been drawn, clear deck
                undrawnCards.get(clientID).clear();
                int restToDrawCardNum = 9 - cardCount;
                restToDrawCardCount.put(clientID, restToDrawCardNum);

                Server.getServer().handleNotYourCards(clientID, cardCount);
                Server.getServer().handleShuffleCoding(clientID);

                // put discarded cards deck into undrawn deck
                undrawnCards.get(clientID).addAll(discardedCards.get(clientID));
                System.out.println(discardedCards.get(clientID) + " : " + discardedCards.get(clientID).size());
                // put drawn cards in this round into discarded deck
                discardedCards.get(clientID).clear();
                discardedCards.get(clientID).addAll(tempListForDrawnCards);

                // give the rest cars to the client
                List<String> newCards = new ArrayList<>();
                for (int i = 0; i < restToDrawCardNum; i++) {
                    RegisterCard card = undrawnCards.get(clientID).get(i);
                    String cardName = card.getCardName();
                    newCards.add(cardName);
                }
                Server.getServer().handleYourNewCards(clientID, newCards);
                Server.getServer().handleNotYourCards(clientID, restToDrawCardNum);

                // remove the new drawn cards and put them in discarded deck
                for (int i = 0; i < restToDrawCardNum; i++) {
                    discardedCards.get(clientID).add(undrawnCards.get(clientID).get(0));
                    undrawnCards.get(clientID).remove(0);
                }

                // filter the discardedCardDeck for damage cards
                List<RegisterCard> tempToRemove = new ArrayList<>();
                for (RegisterCard card : discardedCards.get(clientID)) {
                    if (!card.getCardType().equals("PROGRAMME")) {
                        //discardedCards.get(clientID).remove(card);
                        tempToRemove.add(card);

                        if (card.getCardName().equals("Spam")) {
                            spamPile.push(card);
                        } else if (card.getCardName().equals("Trojan")) {
                            trojanHorsePile.push(card);
                        } else if (card.getCardName().equals("Virus")) {
                            virusPile.push(card);
                        } else if (card.getCardName().equals("Worm")) {
                            wormPile.push(card);
                        }

                        int curCountSpam = countSpamAllClients.get(clientID);
                        countSpamAllClients.put(clientID, curCountSpam + 1);

                    }
                }
                discardedCards.get(clientID).removeAll(tempToRemove);
            }
            drawCardsAllClients.put(clientID, list);
            // only for test
            for (int clientNum : clientIDs) {
                System.out.println(clientNum + " undrawncardsNum: " + undrawnCards.get(clientNum).size());
                System.out.println(clientNum + " discardedNum: " + discardedCards.get(clientNum).size());
            }
        }
        return drawCardsAllClients;
    }

    /**
     * invoke method in class Timer(in another thread)
     */
    public void startTimer() {
        Thread thread = new Thread(Timer.timer);
        thread.start();
        logger.info("game starts timer");
    }

    /**
     * if all players finished programming within 30 seconds, timer stops
     */
    public void stopTimer() {
        Timer.flag = false;
        logger.info("game stops timer");
    }

    /**
     * shuffle undrawn deck
     *
     * @param cards
     * @return
     */
    public List<RegisterCard> shuffleUndrawnDeck(List<RegisterCard> cards) {

        java.util.Random random = new Random();

        // randomly change two cards for 50 times
        for (int i = 0; i < 50; i++) {
            int indexCard1 = random.nextInt(20);
            int indexCard2 = random.nextInt(20);
            RegisterCard card = cards.get(indexCard1);
            cards.set(indexCard1, cards.get(indexCard2));
            cards.set(indexCard2, card);
        }
        return cards;
    }

    /**
     * creart and shuffle the deck of upgrade cards
     *
     * @return
     */
    public Stack<UpgradeCard> getAndShuffleUndrawnDeck() {

        java.util.Random random = new Random();

        Stack<UpgradeCard> shuffledDeck = new Stack<>();

        List<UpgradeCard> cards = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            cards.add(new AdminPrivilege());
            cards.add(new RealLaser());
            cards.add(new SpamBlocker());
            cards.add(new MemorySwap());
        }

        // randomly change two cards for 50 times
        for (int i = 0; i < 50; i++) {
            int indexCard1 = random.nextInt(40);
            int indexCard2 = random.nextInt(40);
            UpgradeCard card = cards.get(indexCard1);
            cards.set(indexCard1, cards.get(indexCard2));
            cards.set(indexCard2, card);
        }

        for (int i = 0; i < 40; i++) {
            shuffledDeck.push(cards.get(i));
        }
        return shuffledDeck;
    }

    /**
     * invoked from ExecuteOrder seletionFinished
     * analog game rules, priority list will be reset
     */
    public void checkAndSetPriority() {

        Antenna.calculateDistances();
    }

    // help function to remove one client from a list
    public static List<Integer> removeOneClientFromList(List<Integer> list, int clientID) {
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Object cur = iterator.next();
            if (cur.equals(clientID)) {
                iterator.remove();
            }
        }
        return list;
    }

    /**
     * execute the logical functions for the played card
     *
     * @param clientID
     * @param card
     */
    public void playCard(int clientID, RegisterCard card) throws IOException {
        // if no card played, skip
        // if card played, do card function
        if (card != null) {
            card.doCardFunction(clientID);
        }
    }

    /**
     * execute the logical functions for the upgrade cards
     *
     * @param clientID
     * @param card
     */
    public void playUpgradeCard(int clientID, UpgradeCard card) throws IOException {
        card.doCardFunction(clientID);
    }

    /**
     * check if a client´s position out of board
     *
     * @param clientID
     * @param position
     * @return
     */
    public boolean checkOnBoard(int clientID, Position position) throws IOException {
        System.out.println("Game checks onBoard.");
        System.out.println("new position: " + position.getX() + "-" + position.getY());
        // if out of board, reboot and clear the registers and remove from priorityList
        if (position.getX() < 0 || position.getX() > 12 || position.getY() < 0 || position.getY() > 9) {
            System.out.println("not on board anymore");
            clientsOnBoard.put(clientID, false);
            if (position.getX() >= 3) {
                reboot(clientID, new Position(rebootPosition.getX(), rebootPosition.getY()), false);
            } else if (position.getX() < 3) {
                System.out.println("x < 0");
                reboot(clientID, startPositionsAllClients.get(clientID), false);
            }
            return false;
        }
        return true;
    }

    public boolean checkOnBoardFromBelt(int clientID, Position position) throws IOException {
        System.out.println("Game checks onBoard.");
        System.out.println("new position: " + position.getX() + "-" + position.getY());
        // if out of board, reboot and clear the registers and remove from priorityList
        if (position.getX() < 0 || position.getX() > 12 || position.getY() < 0 || position.getY() > 9) {
            System.out.println("not on board anymore");
            clientsOnBoard.put(clientID, false);
            if (position.getX() >= 3) {
                reboot(clientID, new Position(rebootPosition.getX(), rebootPosition.getY()), true);
            } else if (position.getX() < 3) {
                System.out.println("x < 0");
                reboot(clientID, startPositionsAllClients.get(clientID), true);
            }
            return false;
        }
        return true;
    }

    /**
     * if one player out of board, reboot
     *
     * @param clientID
     */
    public void reboot(int clientID, Position position, boolean fromPit) throws IOException {
        // got a spam card
        String cardName = Laser.drawOneDamageCard(clientID);
        List<String> damageCards = new ArrayList<>();
        damageCards.add(cardName);
        System.out.println("got damage card because of reboot");
        Server.getServer().handleDrawDamage(clientID, damageCards);

        // set robots position to start point
        playerPositions.get(clientID).setX(position.getX());
        playerPositions.get(clientID).setY(position.getY());

        // clear client´s registers
        registersAllClients.put(clientID, new RegisterCard[5]);

        // remove client from activePlayersList and current playersInTurn list
        if (!fromPit) {
            removeOneClientFromList(activePlayersList, clientID);
        }

        System.out.println("game reboots: actList: " + activePlayersList);

        // inform others about reboot client
        Server.getServer().handleReboot(clientID);
        // inform all the position of reboot
        Server.getServer().handleMovement(clientID, position.getX(), position.getY());
        setRebootDirectonToNord(clientID);
        directionsAllClients.put(clientID, Direction.UP);


        // if all players rebooted, start a new round
        if (activePlayersList.size() == 0) {

            System.out.println("game rebooted all players");
            ExecuteOrder.allplayersRebooted = true;
            activePlayersList.clear();
            priorityEachTurn.clear();
            registerPointer = 0;

            for (int cltID : clientIDs) {
                // set all clients active
                activePlayersList.add(cltID);

                // reset all the register slots with no cards in game
                RegisterCard[] registers = new RegisterCard[5];
                registersAllClients.put(cltID, registers);
            }
            // reset selection finish list to null for the next round selection
            selectionFinishList.clear();

            Server.getServer().handleYourCards();
            // inform all players: programming phase begins
            Server.getServer().handleActivePhase(2);
            ExecuteOrder.activePhase = 2;
        }

    }

    /**
     * set reboot direction to nord
     *
     * @param clientID
     * @throws IOException
     */
    public void setRebootDirectonToNord(int clientID) throws IOException {
        Direction direction = directionsAllClients.get(clientID);
        switch (direction) {
            case UP:
                break;
            case RIGHT:
                Server.getServer().handlePlayerTurning(clientID, "counterclockwise");
                break;
            case DOWN:
                Server.getServer().handlePlayerTurning(clientID, "counterclockwise");
                Server.getServer().handlePlayerTurning(clientID, "counterclockwise");
                break;
            case LEFT:
                Server.getServer().handlePlayerTurning(clientID, "clockwise");
                break;
        }
    }

    /**
     * if the priority list is empty, there is no more client to play in this turn
     * then this turn is over, reset priority
     *
     * @return
     */
    public boolean checkTurnOver() throws IOException {
        logger.info("Game checks turn over");
        System.out.println("game reg Pointer: " + registerPointer);
        System.out.println("game checkturnover prints prioritylist: " + priorityEachTurn);
        if (priorityEachTurn.size() == 0) {
            activeBoardElements();
            robotShoot();
            System.out.println("active players: " + activePlayersList);
            checkAndSetPriority();
            registerPointer++;
            return true;
        } else {
            return false;
        }
    }

    /**
     * soon: with robot laser also
     */
    public void activeBoardElements() throws IOException {

        for (Iterator<Integer> iterator = activePlayersList.iterator(); iterator.hasNext(); ) {
            int client = iterator.next();
            Position position = playerPositions.get(client);
            int x = position.getX();
            int y = position.getY();

            List<FeldObject> feldObjects = board.get(x).get(y);
            for (FeldObject obj : feldObjects) {
                if (obj.getClass().getSimpleName().equals("Pit")) {
                    iterator.remove();
                    reboot(client, new Position(Game.rebootPosition.getX(), Game.rebootPosition.getY()), true);
                }
                if (obj.getClass().getSimpleName().equals("ConveyorBelt")) {
                    obj.doBoardFunction(client, obj);
                    if (clientsOnBoard.get(client) == false) {
                        iterator.remove();
                    }
                }
                if (!obj.getClass().getSimpleName().equals("Empty") && !obj.getClass().getSimpleName().equals("Pit") && !obj.getClass().getSimpleName().equals("ConveyorBelt")) {
                    obj.doBoardFunction(client, obj);
                }
            }
        }

        if(mapName.equals("Twister")){
            for(int checkpoint : movingCheckpoints.keySet()){
                int[] location = movingCheckpoints.get(checkpoint);
                int curX = location[0];
                int curY = location[1];
                FeldObject feldObject = board.get(curX).get(curY).get(0);
                ConveyorBelt belt = (ConveyorBelt)feldObject;
                belt.moveCheckpoints(checkpoint, belt);
            }
            Server.getServer().handleCheckpointsLocations();
        }
    }

    /**
     * check if all the register slots have been played
     *
     * @return
     */
    public boolean checkRoundOver() {
        logger.info("Game checks round over" + registerPointer);
        System.out.println("game round over : " + registerPointer);
        if (registerPointer == 5) {
            registerPointer = 0;
            activePlayersList.clear();

            //update for upgrade cards
            buyUpgradeCardsFinished.clear();

            priorityEachTurn.clear();
            for (int clientID : clientIDs) {
                // set all clients active
                activePlayersList.add(clientID);

                // reset all the register slots with no cards in game
                RegisterCard[] registers = new RegisterCard[5];
                registersAllClients.put(clientID, registers);

                clientsOnBoard.put(clientID, true);

                // reset the count of damage cards for all clients
                countSpamAllClients.put(clientID, 0);
            }
            // reset selection finish list to null for the next round selection
            selectionFinishList.clear();

            // reset changedRegPointer to -1 for next round
            changePriorityRegPointer = -1;

            System.out.println("priority list: " + priorityEachTurn);
            return true;
        } else {
            return false;
        }
    }


    /**
     * for Dizzy Highway, if someone arrived at checkpoint, game is finished
     * soon: adjust for other game maps
     *
     * @return
     * @throws IOException
     */
    public boolean checkGameOver() throws IOException {
        logger.info("Game checks game over.");
        for (int client : clientIDs) {
            // soon size() to number of checkpoints
            if (arrivedCheckpoints.get(client).size() == checkPointTotal) {
                System.out.println("print how many checkpoints: " + checkPointTotal);
                Server.getServer().handleGameFinished(client);
                return true;
            }
        }
        return false;
    }

    /**
     * check if one field hat a wall, if has, return the orientation of the wall
     *
     * @return
     */
    public String checkWall(int row, int column) {
        logger.info("Game checks wall on board for move");
        String wallOrientation = "";
        List<FeldObject> feldObjects = board.get(row).get(column);
        for (FeldObject obj : feldObjects) {
            if (obj.getClass().getSimpleName().equals("Wall")) {
                wallOrientation = obj.getOrientations().get(0);
            }
        }
        return wallOrientation;
    }

    /**
     * check if there are other robots who stand in the way
     *
     * @param x
     * @param y
     * @return
     */
    public int checkOtherRobot(int whoChecks, int x, int y) {
        for (int client : playerPositions.keySet()) {
            if (client != whoChecks) {
                if (playerPositions.get(client).getX() == x && playerPositions.get(client).getY() == y) {
                    return client;
                }
            }
        }
        return 0;
    }

    /**
     * for the robot, which is pushed
     *
     * @param client
     * @param pushedPo
     * @throws IOException
     */
    public void checkAndSetPushedPosition(int client, Position pushedPo) throws IOException {
        // check if robot is still on board
        boolean isOnBoard = Game.getInstance().checkOnBoard(client, pushedPo);
        if (isOnBoard) {
            // set new Position in Game
            Game.playerPositions.put(client, pushedPo);
            // transport new Position to client
            Server.getServer().handleMovement(client, pushedPo.getX(), pushedPo.getY());
        }
    }

    /**
     * if the client is offline, should be removed from game
     */
    public void removePlayer(int clientId) {
        for (int i = 0; i < activePlayersList.size(); i++) {
            if (activePlayersList.get(i) == clientId) {
                activePlayersList.remove(i);

            }
        }
        for (int i = 0; i < priorityEachTurn.size(); i++) {
            if (priorityEachTurn.get(i) == clientId) {
                priorityEachTurn.remove(i);
            }
        }
        clientIDs.remove(clientId);
    }

    /**
     * store the total num of checkpoints
     */
    public void findCheckpointTotal() {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 10; j++) {
                if (board.get(i).get(j).size() == 2) {
                    if (board.get(i).get(j).get(1).getClass().getSimpleName().equals("CheckPoint")) {
                        checkPointTotal++;
                    }
                }
            }
        }
        System.out.println("findCheckPointTotal: " + checkPointTotal);
    }

    /**
     * find the reboot position and direction in map
     */
    public void findRebootPosition() {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 10; j++) {
                if (board.get(i).get(j).size() == 2) {
                    if (board.get(i).get(j).get(1).getClass().getSimpleName().equals("RestartPoint")) {
                        RestartPoint restartPoint = (RestartPoint) (board.get(i).get(j).get(1));
                        String restartDirection = restartPoint.getOrientations().get(0);
                        rebootPosition = new Position(i, j);
                    }
                }
            }

        }
    }

    /**
     * store the position and direction of antenna
     */
    public void findAntenna() {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 10; j++) {
                if (board.get(i).get(j).get(0).getClass().getSimpleName().equals("Antenna")) {
                    Antenna antenna = (Antenna) (board.get(i).get(j).get(0));
                    String dirAntenna = antenna.getOrientations().get(0);
                    positionAntenna = new Position(i, j);
                    directionAntenna = dirAntenna;
                }

            }

        }

    }

    /**
     * calculate the shooting line and check if there are other robots in the line
     *
     * @throws IOException
     */
    public void robotShoot() throws IOException {
        for (int bot : activePlayersList) {
            Direction direction = directionsAllClients.get(bot);
            int botX = playerPositions.get(bot).getX();
            int botY = playerPositions.get(bot).getY();

            switch (direction) {
                case UP:
                    for (int i = 0; i < botY; i++) {
                        checkHit(botX, i);
                    }
                    break;
                case DOWN:
                    for (int i = botY + 1; i < 10; i++) {
                        checkHit(botX, i);
                    }
                    break;
                case RIGHT:
                    for (int i = botX + 1; i < 13; i++) {
                        checkHit(i, botY);
                    }
                    break;
                case LEFT:
                    for (int i = 0; i < botX; i++) {
                        checkHit(i, botY);
                    }
                    break;
            }
        }
    }

    /**
     * check if there are other robots in the shooting line
     *
     * @param x
     * @param y
     * @throws IOException
     */
    public void checkHit(int x, int y) throws IOException {
        for (int clt : activePlayersList) {
            if (x == playerPositions.get(clt).getX() && y == playerPositions.get(clt).getY()) {

                List<String> damageCards = new ArrayList<>();
                String cardName = Laser.drawOneDamageCard(clt);
                damageCards.add(cardName);

                System.out.println("got damages by bot shooting : " + damageCards);
                Server.getServer().handleDrawDamage(clt, damageCards);
            }
        }
    }

    // only for test:
    /*
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            List<List<FeldObject>> row = new ArrayList<>();
            for (int j = 0; j < 13; j++) {
                List<FeldObject> zelle = new ArrayList<>();
                row.add(zelle);
            }
            board.add(row);
        }

        board.get(1).get(1).add(new Pit("5B"));
        System.out.println(board.size());
        System.out.println(board.get(0).size());
        System.out.println(board.get(0).get(0));
        System.out.println("================");

        //System.out.println(board.get(0).get(5).get(0).getType());
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(0).size(); j++) {
                System.out.print(board.get(i).get(j) + " ");
            }
            System.out.println("");
        }
    }
     */


}
