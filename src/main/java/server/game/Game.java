package server.game;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import server.feldobjects.FeldObject;
import server.feldobjects.Pit;
import server.maps.Board;
import server.network.Server;
import server.registercards.*;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class Game {

    private static final Logger logger = Logger.getLogger(Game.class.getName());
    private final static Game game = new Game();

    List<List<UpgradeCard>> upgradeCards; // deck of upgrade cards of all players
    List<UpgradeCard> upgradeShop; // total common deck of all upgrade cards
    List<Integer> energyCubes; // who has how many cubes
    int energyBank; // total common energy cubes for the game
    boolean isGameOver; // true for game over


    //==========================================================================
    public static HashMap<Integer, List<RegisterCard>> undrawnCards = new HashMap<>(); // key = clientID, value = decks of undrawn cards of all players
    public static HashMap<Integer, List<RegisterCard>> discardedCards = new HashMap<>(); // decks of discarded cards of all players
    @JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
    public static List<List<List<FeldObject>>> board = new ArrayList<>(); // selected map
    public static String mapName = null; // storage for map name
    public static HashSet<Integer> clientIDs = new HashSet<>(); // storage the clientIDs
    public static HashMap<Integer, Position> playerPositions = new HashMap<>(); // current position of each player
    public static HashMap<Integer, Position> startPositionsAllClients = new HashMap<>(); // storage of all start positions
    public static HashMap<Integer, Integer> restToDrawCardCount = new HashMap<>(); // count for the situation, that undrawnCards not enough
    public static HashMap<Integer, RegisterCard[]> registersAllClients = new HashMap<>(); // registers of all players
    public static List<Integer> selectionFinishList = new ArrayList<>(); // clientID who finished programming
    public static List<Integer> priorityEachTurn = new ArrayList<>(); // e.g. [22,33,11] means clientID 22 has first priority in this round
    public static int registerPointer = 0; // to point the current register
    public static HashMap<Integer, Direction> directionsAllClients = new HashMap<>(); // current directions of all clients: key=clientID, value=Direction
    public static List<Integer> activePlayersList = new ArrayList<>(); // if a player out of board, remove it from this list. For priority calculate
    public static HashMap<Integer, HashSet<Integer>> arrivedCheckpoints = new HashMap<>(); // who has arrived which checkpoints;

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

        for(int client : clientIDs){
            // init registers with 5 slots for all clients
            RegisterCard[] registers = new RegisterCard[5];
            registersAllClients.put(client, registers);

            // init directions of all clients to RIGHT
            directionsAllClients.put(client, Direction.RIGHT);

            // init activePlayerList with all clients
            activePlayersList.add(client);

            // init arrivedCheckpoints list
            arrivedCheckpoints.put(client, new HashSet<>());
        }

        // init undrawn and discarded cards deck for each player
        for(int client : clientIDs){
            // for undrawn cards
            List<RegisterCard> cards = new ArrayList<>();
            for (int i = 0; i < Again.cardCount; i++) {
                cards.add(new Again());
            }
            for (int i = 0; i < UTurn.cardCount; i++) {
                cards.add(new UTurn());
            }
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
            for (int i = 0; i < TurnRight.cardCount; i++) {
                cards.add(new TurnRight());
            }

            for (int i = 0; i < MoveII.cardCount; i++) {
                cards.add(new MoveII());
            }

            undrawnCards.put(client, cards);

            // init for discarded cards
            List<RegisterCard> discards = new ArrayList<>();
            discardedCards.put(client, discards);

        }
    }

    /**
     * init Board
     */
    public void initBoard(){
        // init board (start board + game board)
        for (int i = 0; i < 10; i++) {
            List<List<FeldObject>> row = new ArrayList<>();
            for (int j = 0; j < 13; j++) {
                List<FeldObject> zelle = new ArrayList<>();
                row.add(zelle);
            }
            board.add(row);
        }
    }

    /**
     * invoked fom "MapSelected" in ExecuteOrder
     * @param mapName
     */
    public void setMap3DList(String mapName) {
        logger.info("Game sets map.");
        switch (mapName){
            case "Dizzy Highway":
                Board.buildDizzyHighway();
                break;
        }
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
                    // add the drawn cards to discarded card deck
                    discardedCards.get(clientID).add(card);
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
                System.out.println(discardedCards.get(clientID) + " : "+ discardedCards.get(clientID).size());
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
            }
            drawCardsAllClients.put(clientID, list);
            // only for test
            for(int clientNum : clientIDs){
                System.out.println(clientNum + " undrawncardsNum: " + undrawnCards.get(clientNum).size());
                System.out.println(clientNum + " discardedNum: " +  discardedCards.get(clientNum).size());
            }
        }
        return drawCardsAllClients;
    }

    /**
     * invoke method in class Timer(in another thread)
     */
    public void startTimer(){
        Thread thread = new Thread(Timer.timer);
        thread.start();
        logger.info("game starts timer");
    }

    /**
     * if all players finished programming within 30 seconds, timer stops
     */
    public void stopTimer(){
        Timer.flag = false;
        logger.info("game stops timer");
    }

    /**
     * invoked from ExecuteOrder seletionFinished
     * analog game rules, priority list will be reset
     */
    public void checkAndSetPriority() {
        // soon: calculate distance to set priority
        // here only for test
        for (int clientID : activePlayersList){
            priorityEachTurn.add(clientID);
        }
    }

    // help function to remove one client from a list
    public static List<Integer> removeOneClientFromList(List<Integer> list, int clientID){
        Iterator iterator = list.iterator();
        while (iterator.hasNext()){
            Object cur = iterator.next();
            if(cur.equals(clientID)){
                iterator.remove();
            }
        }
        return list;
    }

    /**
     * execute the logical functions for the played card
     * @param clientID
     * @param card
     */
    public void playCard(int clientID, RegisterCard card) throws IOException {
        // if no card played, skip
        // if card played, do card function
        if(card != null){
            card.doCardFunction(clientID);
        }
    }

    /**
     * check if a client´s position out of board
     * @param clientID
     * @param position
     * @return
     */
    public boolean checkOnBoard(int clientID, Position position) throws IOException {
        System.out.println("Game checks onBoard.");
        System.out.println("new position: " + position.getX() + "-" + position.getY());
        // if out of board, reboot and clear the registers and remove from priorityList
        if(position.getX() < 0 || position.getX() > 12 || position.getY() < 0 || position.getY() > 9){
            System.out.println("not on board anymore");
            if(position.getX() >= 3){
                reboot(clientID, new Position(7,3));
            }else if(position.getX() < 3){
                System.out.println("x < 0");
                reboot(clientID, startPositionsAllClients.get(clientID));
            }
            return false;
        }
        return true;
    }

    /**
     * if one player out of board, reboot
     * @param clientID
     */
    public void reboot(int clientID, Position position) throws IOException {
        // set robots position to start point

        playerPositions.get(clientID).setX(position.getX());
        playerPositions.get(clientID).setY(position.getY());

        // clear client´s registers
        registersAllClients.put(clientID, new RegisterCard[5]);

        // remove client from activePlayersList and current playersInTurn list
        removeOneClientFromList(activePlayersList, clientID);
        removeOneClientFromList(priorityEachTurn, clientID);

        // inform others about reboot client
        Server.getServer().handleReboot(clientID);
        // inform all the position of reboot
        Server.getServer().handleMovement(clientID, position.getX(), position.getY());
    }

    /**
     * if the priority list is empty, there is no more client to play in this turn
     * then this turn is over, reset priority
     * @return
     */
    public boolean checkTurnOver() throws IOException {
        logger.info("Game checks turn over");
        System.out.println("game turn over: " + registerPointer);
        System.out.println("turn over prioritylist: " + priorityEachTurn);
        if(priorityEachTurn.size() == 0){
            System.out.println("active players: " + activePlayersList);
            checkAndSetPriority();
            registerPointer++;
            activeBoardElements();
            return true;
        }else{
            return false;
        }
    }

    /**
     * soon: with robot laser also
     */
    public void activeBoardElements() throws IOException {
        for(int client : activePlayersList){
            Position position = playerPositions.get(client);
            int row = position.getY();
            int column = position.getX();

            List<FeldObject> feldObjects = board.get(row).get(column);
            for(FeldObject obj : feldObjects){
                if(!obj.getClass().getSimpleName().equals("Empty")){
                    obj.doBoardFunction(client, obj);
                }
            }
        }
    }

    /**
     * check if all the register slots have been played
     * @return
     */
    public boolean checkRoundOver(){
        logger.info("Game checks round over" + registerPointer);
        System.out.println("game round over : " + registerPointer);
        if(registerPointer == 5){
            registerPointer = 0;
            activePlayersList.clear();
            priorityEachTurn.clear();
            for(int clientID : clientIDs){
                // set all clients active
                activePlayersList.add(clientID);
                // reset all the register slots with no cards in game
                RegisterCard[] registers = new RegisterCard[5];
                registersAllClients.put(clientID, registers);
            }
            // reset selection finish list to null for the next round selection
            selectionFinishList.clear();
            System.out.println("priority list: " + priorityEachTurn);
            return true;
        }else{
            return false;
        }
    }


    /**
     * for Dizzy Highway, if someone arrived at checkpoint, game is finished
     * soon: adjust for other game maps
     * @return
     * @throws IOException
     */
    public boolean checkGameOver() throws IOException {
        logger.info("Game checks game over.");
        for(int client : clientIDs){
            // soon size() to number of checkpoints
            if(arrivedCheckpoints.get(client).size() == 1){
                Server.getServer().handleGameFinished(client);
                return true;
            }
        }
        return false;
    }

    /**
     * check if one field hat a wall, if has, return the orientation of the wall
     * @return
     */
    public String checkWall(int row, int column){
        logger.info("Game checks wall on board for move");
        String wallOrientation = "";
        List<FeldObject> feldObjects = board.get(row).get(column);
        for(FeldObject obj : feldObjects){
            if(obj.getClass().getSimpleName().equals("Wall")){
                wallOrientation = obj.getOrientations().get(0);
            }
        }
        return wallOrientation;
    }

    /**
     * if the client is offline, should be removed from game
     */
    public void removePlayer(String clientName) {


        // TODO SEND INFO VIA SERVER TO ALL CLIENTS: who was removed
    }






    /**
     * invoked from Game: activatePhase
     */
    public void shootLaser() {

        setRobotLaserLine();

        // check damage for each robot
        for (int i = 0; i < clientIDs.size(); i++) {
        }

        clearRobotLaseLine();
    }


    /**
     * invoked from Game: shootLaser
     * calculate the line of robot laser, set the line into board list (3D)
     */
    public void setRobotLaserLine() {
    }

    /**
     * invoked from Game: shootLaser
     * erase the line of robot laser from board list (3D), ready for next turn
     */
    public void clearRobotLaseLine() {
    }

    // only for test:

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


}
