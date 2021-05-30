package server.game;

import server.feldobjects.FeldObject;
import server.network.Server;
import server.registercards.Again;
import server.registercards.Move2;
import server.registercards.RegisterCard;

import java.io.IOException;
import java.util.*;

public class Game {

    private final static Game game = new Game();

    int countPlayer; // total count of players
    String mapName; // von players chosen map


    List<String> playerNames; // index of List = index of player = anchor for the game!!!
    List<String> robotFigure; // each player has a robot figure
    List<Integer> priorityEachRound; // e.g. [2,0,1] means player number 2 has first priority in this round

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
    public static HashMap<Integer, Position> positions = new HashMap<>(); // key = clientID, value = where player stands
    public static List<List<List<FeldObject>>> board = new ArrayList<>(); // selected map
    public static HashSet<Integer> clientIDs = new HashSet<>(); // storage the clientIDs
    public static HashMap<Integer, Position> playerPositions = new HashMap<>(); // current position of each player
    public static HashMap<Integer, Integer> restToDrawCardCount = new HashMap<>(); // count for the situation, that undrawnCards not enough
    public static HashMap<Integer, RegisterCard[]> registersAllClients = new HashMap<>(); // registers of all players
    public static List<Integer> selectionFinishList = new ArrayList<>(); // clientID who finished programming


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
        // init registers with 5 slots for all clients
        for(int client : clientIDs){
            RegisterCard[] registers = new RegisterCard[5];
            registersAllClients.put(client, registers);
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
     * invoked from Game: initAndStartGame
     */
    public void drawRegiCards(int PlayerIndex) {
    }

    /**
     * invoked from client side: set robot figure to a client (index as anchor in list)
     */
    public void setPlayerRobots(String clientName) {

        // TODO SEND INFO VIA SERVER TO ALL CLIENTS: who has which robot
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
     * when the client finished programming, will send thd infos of register cards to server
     * then the list of register cards will be set
     *
     * @param clientName
     */
    public void setRegisterCards(String clientName) {

    }

    /**
     * invoked from client by button(only when all finished programming, button can be activated)
     * TODO button from GUI
     */
    public void activatePhase() {

        checkAndSetPriority();

        for (int i = 0; i < playerNames.size(); i++) {
            int playerIndex = priorityEachRound.get(i);
            String clientName = playerNames.get(playerIndex);

            // do RegiCard function

            // do Tile function in board (incl. damage)

            // remove RegiCard from register list

            // TODO SEND INFO VIA SERVER TO EACH CLIENT: the current position of robot
        }

        shootLaser();

        // TODO SEND INFO VIA SERVER TO ALL CLIENTS:
        // send a message to all clients to tell the RegiCard is played, so that one card will disappears in GUI

        checkGameOver();

        if (!isGameOver) {
            for (int i = 0; i < playerNames.size(); i++) {
                drawRegiCards(i);

                // TODO TODO SEND INFO VIA SERVER TO ALL CLIENTS: who has drawn which cards
            }
        }
    }

    /**
     * invoked from Game: activatePhase
     * analog game rules, priority list will be reset
     */
    public void checkAndSetPriority() {

    }

    /**
     * invoked from Game: activatePhase
     */
    public void shootLaser() {

        setRobotLaserLine();

        // check damage for each robot
        for (int i = 0; i < playerNames.size(); i++) {
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
