package server.game;

import server.feldobjects.FeldObject;
import server.network.Server;
import server.registercards.Again;
import server.registercards.Move2;
import server.registercards.RegisterCard;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class Game {

    private static final Logger logger = Logger.getLogger(Game.class.getName());

    private final static Game game = new Game();

    int countPlayer; // total count of players
    String mapName; // von players chosen map




    List<List<UpgradeCard>> upgradeCards; // deck of upgrade cards of all players

    List<UpgradeCard> upgradeShop; // total common deck of all upgrade cards
    List<Integer> energyCubes; // who has how many cubes
    int energyBank; // total common energy cubes for the game
    List<HashSet<Integer>> arrivedCheckpoints; // who has arrived which checkpoints;

    List<String> playerStatus; // OUTOFBOARD INPLAY


    boolean isGameOver; // true for game over


    //==========================================================================
    public static HashMap<Integer, List<RegisterCard>> undrawnCards = new HashMap<>(); // key = clientID, value = decks of undrawn cards of all players
    public static HashMap<Integer, List<RegisterCard>> discardedCards = new HashMap<>(); // decks of discarded cards of all players
    public static List<List<List<FeldObject>>> board = new ArrayList<>(); // selected map
    public static HashSet<Integer> clientIDs = new HashSet<>(); // storage the clientIDs
    public static HashMap<Integer, Position> playerPositions = new HashMap<>(); // current position of each player
    public static HashMap<Integer, Position> startPositionsAllClients = new HashMap<>(); // storage of all start positions
    public static HashMap<Integer, Integer> restToDrawCardCount = new HashMap<>(); // count for the situation, that undrawnCards not enough
    public static HashMap<Integer, RegisterCard[]> registersAllClients = new HashMap<>(); // registers of all players
    public static List<Integer> selectionFinishList = new ArrayList<>(); // clientID who finished programming
    public static List<Integer> priorityEachTurn = new ArrayList<>(); // e.g. [22,33,11] means clientID 22 has first priority in this round
    public static int registerPointer = 0; // to point the current register
    public static HashMap<Integer, Position> positionsAllClients = new HashMap<>(); // current positions of all clients: key=clientID, value=Position
    public static HashMap<Integer, Direction> directionsAllClients = new HashMap<>(); // current directions of all clients: key=clientID, value=Direction
    public static List<Integer> activePlayersList = new ArrayList<>(); // if a player out of board, remove it from this list. For priority calculate

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
        }




        // only for test!!
        // soon: shuffle card with correct count of cards
        List<RegisterCard> list = new ArrayList<>();
        list.add(new Again("PROGRAMME", "again"));
        list.add(new Again("PROGRAMME", "again"));
        list.add(new Again("PROGRAMME", "again"));
        list.add(new Again("PROGRAMME", "again"));
        list.add(new Move2("PROGRAMME", "move2"));
        list.add(new Again("PROGRAMME", "again"));
        list.add(new Again("PROGRAMME", "again"));
        list.add(new Move2("PROGRAMME", "move2"));
        list.add(new Again("PROGRAMME", "again"));
        list.add(new Again("PROGRAMME", "again"));

        List<RegisterCard> list2 = new ArrayList<>();
        list2.add(new Again("PROGRAMME", "again"));
        list2.add(new Again("PROGRAMME", "again"));
        list2.add(new Again("PROGRAMME", "again"));

        List<List<RegisterCard>> ll = new ArrayList<>();
        ll.add(list);
        ll.add(list2);
        int num = 0;
        for (int clientID : clientIDs) {
            undrawnCards.put(clientID, ll.get(num++));
        }

        // only for test
        List<RegisterCard> dis1 = new ArrayList<>();
        dis1.add(new Move2("PROGRAMME", "move2"));
        dis1.add(new Move2("PROGRAMME", "move2"));
        dis1.add(new Move2("PROGRAMME", "move2"));
        dis1.add(new Move2("PROGRAMME", "move2"));
        List<RegisterCard> dis2 = new ArrayList<>();
        dis2.add(new Again("PROGRAMME", "again"));
        dis2.add(new Again("PROGRAMME", "again"));
        dis2.add(new Again("PROGRAMME", "again"));
        dis2.add(new Again("PROGRAMME", "again"));
        List<List<RegisterCard>> ww = new ArrayList<>();
        ww.add(dis1);
        ww.add(dis2);
        int num1 = 0;
        for (int clientID : clientIDs) {
            discardedCards.put(clientID, ww.get(num1++));
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

        HashMap<Integer, List<String>> drawCardsAllClients = new HashMap<>();

        for (int clientID : undrawnCards.keySet()) {
            List<String> list = new ArrayList<>();
            int cardCount = undrawnCards.get(clientID).size();

            // if undrawnCards more than 5, draw 5; otherwise draw all the cars
            if (cardCount >= 5) {
                for (int i = 0; i < 5; i++) {
                    RegisterCard card = undrawnCards.get(clientID).get(i);
                    String cardName = card.getCardName();
                    list.add(cardName);
                    // add the drawn cards to discarded card deck
                    discardedCards.get(clientID).add(card);
                }
                // the first 5 cards have been drawn, remove them from deck
                for (int i = 0; i < 5; i++) {
                    undrawnCards.get(clientID).remove(0);
                }
                Server.getServer().handleNotYourCards(clientID, 5);

            } else { // if undrawnCards less than 5, draw all

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
                int restToDrawCardNum = 5 - cardCount;
                restToDrawCardCount.put(clientID, restToDrawCardNum);

                Server.getServer().handleNotYourCards(clientID, cardCount);
                Server.getServer().handleShuffleCoding(clientID);

                // put discarded cards deck into undrawn deck
                undrawnCards.get(clientID).addAll(discardedCards.get(clientID));
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
        card.doCardFunction(clientID);
    }

    /**
     * check if a client´s position out of board
     * @param clientID
     * @param position
     * @return
     */
    public boolean checkOnBoard(int clientID, Position position) throws IOException {
        // if out of board, reboot and clear the registers and remove from priorityList
        if(position.getX() < 0 || position.getX() > 12 || position.getY() < 0 || position.getY() > 9){
            reboot(clientID);
            return false;
        }
        return true;
    }

    /**
     * if one player out of board, reboot
     * @param clientID
     */
    public void reboot(int clientID) throws IOException {
        // set robots position to start point
        Position startPosition = startPositionsAllClients.get(clientID);
        int startX = startPosition.getX();
        int startY = startPosition.getY();
        playerPositions.get(clientID).setX(startX);
        playerPositions.get(clientID).setY(startY);

        // set robots direction to right
        directionsAllClients.put(clientID, Direction.RIGHT);

        // clear client´s registers
        registersAllClients.put(clientID, new RegisterCard[5]);

        // remove client from activePlayersList
        removeOneClientFromList(activePlayersList, clientID);

        // inform others about reboot client
        Server.getServer().handleReboot(clientID);

    }


    public void setMap3DList(String mapName) {

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

    /**
     * if all checkpoints are reached, game is over
     */
    public void checkGameOver() {
        // TODO SEND INFO VIA SERVER TO EACH CLIENT: game is over and who is the winner
    }
}
