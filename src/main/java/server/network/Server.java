package server.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.scene.effect.Bloom;
import protocol.Protocol;
import protocol.submessagebody.*;
import server.game.Game;
import server.game.Register;
import server.registercards.RegisterCard;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.logging.Logger;

public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    private final static Server server = new Server();
    // a map for player index in the game: key = playerIndex, value = clientID
    protected static Hashtable<Integer, Integer> playerList = new Hashtable<>();
    // a map for the accepted ServerThreads: key = clientID, value = ServerThread
    public static final LinkedHashMap<Integer, ServerThread> clientList = new LinkedHashMap<>();
    // for set start point in aufbauPhase
    public static int clientListPointer = 0;
    // the clientsID to distribute, soon in random 100 numbers
    public static final Stack<Integer> clientIDsPool = new Stack<>(){{
        push(42);
        push(25);
        push(15);
        push(77);
        push(85);
        push(100);
        push(33);
    }};

    // map for clientID - clientName : key = clientID, value = clientName: (give new clients infos of previous players)
    public static final HashMap<Integer, String> clientIDUndNames = new HashMap<>();
    // map for clientID - robotFigure : key = clientID, value = robotNum: (give new clients infos of previous players)
    public static final HashMap<Integer, Integer> clientIDUndRobots = new HashMap<>();
    // map for clientID- isReady : key = clientID, value = isReady: (give new clients infos of previous players)
    public static final LinkedHashMap<Integer, Boolean> clientIDUndReady = new LinkedHashMap<>();
    // list for clients who are ready: (give new clients infos for previous players)
    public static List<Integer> playersWhoAreReady = new ArrayList<>();



    /**
     * private constructor for singleton implementation
     */
    private Server() {
    }

    /**
     * Singleton access on server
     * @return server instance
     */
    public static Server getServer() {
        return server;
    }

    /**
     * main thread for server class
     * @param args
     */
    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Start the Server socket and listen for incoming connections
     * @throws IOException
     */
    public void start() throws IOException {
        // create the server and define the port nr.
        ServerSocket server = new ServerSocket(5200);
        boolean flag = true;
        // accept the client request
        while (flag) {
            try {
                // when new client comes, will be put into the thread-set
                // with synchronized, there is only one thread at one time
                Socket clientSocket = server.accept();
                synchronized (clientList) {
                    ServerThread thread = new ServerThread(clientSocket);
                    new Thread(thread).start();
                }// several server threads will respond to the client requests

                //catch the exception
            } catch (Exception e) {
                flag = false;
                e.printStackTrace();
            }
        }
        //close the server
        server.close();
    }


    /**
     * send a message to a particular client
     */
    public void sendTo(int from, String message) {
        //send message to Client clientName
        try {
            Protocol protocol = new Protocol("ReceivedChat", new ReceivedChatBody(message, from, true));
            String json = Protocol.writeJson(protocol);
            new PrintWriter(clientList.get(from).getSocket().getOutputStream(), true).println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * server sends message to all clients
     * @param message
     */
    public void sendMessageToAll(int from, String message) {
        try {
            Protocol protocol = new Protocol("ReceivedChat", new ReceivedChatBody(message, from, false));
            String json = Protocol.writeJson(protocol);
            synchronized (clientList) {
               for(int clientID : clientList.keySet()){
                   ServerThread serverThread = clientList.get(clientID);
                   new PrintWriter(serverThread.getSocket().getOutputStream(), true).println(json);
               }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * send json to all clients
     * @param json
     * @throws IOException
     */
    public void makeOrderToAllClients(String json) throws IOException {
        synchronized (clientList) {
            for(int clientID : clientList.keySet()){
                ServerThread serverThread = clientList.get(clientID);
                new PrintWriter(serverThread.getSocket().getOutputStream(), true).println(json);
            }
        }
    }

    /**
     * send json to one client
     * @param targetClientID
     * @param json
     * @throws IOException
     */
    public void makeOrderToOneClient(int targetClientID, String json) throws IOException {
        new PrintWriter(clientList.get(targetClientID).getSocket().getOutputStream(), true).println(json);
    }

    /**
     * transmit an error to the client
     */
    public void exception(int clientID, String message) throws IOException {
        Protocol protocol = new Protocol("Error", new ErrorBody(message));
        String json = Protocol.writeJson(protocol);
        clientList.get(clientID).makeOrder(json);
    }

    /**
     * inform all the clients that one new player war added
     * @param clientID
     * @param clientName
     * @param robotFigure
     * @throws IOException
     */
    public void handlePlayerAddedToAll(int clientID, String clientName, int robotFigure) throws IOException {
        Protocol protocol = new Protocol("PlayerAdded", new PlayerAddedBody(clientID, clientName, robotFigure));
        String json = Protocol.writeJson(protocol);
        logger.info("server added player");
        makeOrderToAllClients(json);

        Game.clientIDs.add(clientID);
    }

    /**
     * inform the new player about the infos of previous players in server
     * @param targetClient
     * @param clientID
     * @param clientName
     * @param robotFigure
     * @throws IOException
     */
    public void handlePlayerAddedToOne(int targetClient, int clientID, String clientName, int robotFigure) throws IOException {
        Protocol protocol = new Protocol("PlayerAdded", new PlayerAddedBody(clientID, clientName, robotFigure));
        String json = Protocol.writeJson(protocol);
        logger.info("server added player");
        makeOrderToOneClient(targetClient,json);
    }

    /**
     * inform all the clients that someone is ready to play
     * @param clientID
     * @param isReady
     * @throws IOException
     */
    public void handlePlayerStatus(int clientID, boolean isReady) throws IOException {
        Protocol protocol = new Protocol("PlayerStatus", new PlayerStatusBody(clientID, isReady));
        String json = Protocol.writeJson(protocol);
        logger.info("server inform player status");
        makeOrderToAllClients(json);
    }

    /**
     * inform the new player about the status of previous players in server
     * @param targetClient
     * @param clientID
     * @param isReady
     * @throws IOException
     */
    public void handlePlayerStatusToOne(int targetClient, int clientID, boolean isReady) throws IOException {
        Protocol protocol = new Protocol("PlayerStatus", new PlayerStatusBody(clientID, isReady));
        String json = Protocol.writeJson(protocol);
        logger.info("server inform status of previous players");
        makeOrderToOneClient(targetClient,json);
    }

    /**
     * inform the first ready client to select a map
     * @param clientID
     * @throws IOException
     */
    public void handleSelectMap(int clientID) throws IOException {
        ArrayList<String> mapList = new ArrayList<>();
        mapList.add("Dizzy Highway");
        Protocol protocol = new Protocol("SelectMap", new SelectMapBody(mapList));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs first ready player to select a map");
        makeOrderToOneClient(clientID, json);
    }

    /**
     * give the map as 3D-List to all players to rebuild in GUI
     * @param mapName
     * @throws IOException
     */
    public void handleGameStarted(String mapName) throws IOException {
        // dummy map: soon with right maps

        Protocol protocol = new Protocol("GameStarted", new GameStartedBody(Game.board));
        String json = Protocol.writeJson(protocol);
        logger.info("server sends map");
        makeOrderToAllClients(json);
    }

    /**
     * inform all players, which phase is on
     * @param phase
     */
    public void handleActivePhase(int phase) throws IOException {
        Protocol protocol = new Protocol("ActivePhase", new ActivePhaseBody(phase));
        String json = Protocol.writeJson(protocol);
        logger.info("server sends phase info");
        makeOrderToAllClients(json);
    }

    /**
     * inform players who is the current player
     */
    public void handleCurrentPlayer(int currentClientID) throws IOException {
        Protocol protocol = new Protocol("CurrentPlayer", new CurrentPlayerBody(currentClientID));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs current player id");
        makeOrderToAllClients(json);
    }

    /**
     * inform all clients who has chosen which start point
     * @param clientID
     * @param x
     * @param y
     */
    public void handleStartingPointTaken(int clientID, int x, int y) throws IOException {
        Protocol protocol = new Protocol("StartingPointTaken", new StartingPointTakenBody(x, y, clientID));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs set starting point");
        makeOrderToAllClients(json);
    }

    /**
     * distribute cards to each player privately
     */
    public void handleYourCards() throws IOException {
        HashMap<Integer, List<String>> cardsAllClients = Game.getInstance().gameHandleYourCards();
        for(int clientID : cardsAllClients.keySet()){
            Protocol protocol = new Protocol("YourCards", new YourCardsBody(cardsAllClients.get(clientID)));
            String json = Protocol.writeJson(protocol);
            makeOrderToOneClient(clientID, json);
        }
    }

    /**
     * for the situation that drawn cards deck hasn´t enough cards, redraw
     * @param clientID
     * @param newCards
     * @throws IOException
     */
    public void handleYourNewCards(int clientID, List<String> newCards) throws IOException {
        Protocol protocol = new Protocol("YourCards", new YourCardsBody(newCards));
        String json = Protocol.writeJson(protocol);
        logger.info("server inform new cards");
        makeOrderToOneClient(clientID, json);
    }

    /**
     * inform others about how many card you have drawn
     * @param yourID
     */
    public void handleNotYourCards(int yourID, int numberOfCards) throws IOException {

        for(int clientID : clientList.keySet()){
            if(clientID != yourID){
                Protocol protocol = new Protocol("NotYourCards", new NotYourCardsBody(yourID, numberOfCards));
                String json = Protocol.writeJson(protocol);
                makeOrderToOneClient(clientID, json);
            }
        }
    }

    /**
     * if undrawn deck has not enough cards, shuffle the discarded cards deck und draw the rest cards
     * @param clientID
     */
    public void handleShuffleCoding(int clientID) throws IOException {
        Protocol protocol = new Protocol("ShuffleCoding", new ShuffleCodingBody(clientID));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs shuffle");
        makeOrderToAllClients(json);
    }

    /**
     * inform all clients who has set/clear which register
     * @param clientID
     * @param registerNum
     * @param isFilled
     * @throws IOException
     */
    public void handleCardSelected(int clientID, int registerNum, boolean isFilled) throws IOException {
        Protocol protocol = new Protocol("CardSelected", new CardSelectedBody(clientID, registerNum, isFilled));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs card selected");
        makeOrderToAllClients(json);
    }

    /**
     * as first clients finished programming, timer starts
     * @throws IOException
     */
    public void handleTimerStarted() throws IOException {
        Protocol protocol = new Protocol("TimerStarted", null);
        String json = Protocol.writeJson(protocol);
        logger.info("server starts timer");
        makeOrderToAllClients(json);
    }

    /**
     * if some one did not finish when time is out, handle time ended
     * @param clientsWhoNotFinished
     * @throws IOException
     */
    public void handleTimerEnded(List<Integer> clientsWhoNotFinished) throws IOException {
        Protocol protocol = new Protocol("TimerEnded", new TimerEndedBody(clientsWhoNotFinished));
        String json = Protocol.writeJson(protocol);
        logger.info("server inform who did not finish in time");
        makeOrderToAllClients(json);
    }

    /**
     * if some one did not finish in time, will get 5 random cards for register slots
     * @param whoNotFinishedInTime
     * @throws JsonProcessingException
     */
    public void handleCardsYouGotNow(List<Integer> whoNotFinishedInTime) throws JsonProcessingException {
        for(int clientID : whoNotFinishedInTime){
            List<RegisterCard> cards = Game.discardedCards.get(clientID);
            List<RegisterCard> cardsGot = cards.subList(cards.size() - 5, cards.size());
            RegisterCard[] arrCardsGot = new RegisterCard[5];
            for (int i = 0; i < 5; i++) {
               arrCardsGot[i] = cardsGot.get(i);
            }
            Game.registersAllClients.put(clientID, arrCardsGot);
            // convert card list to cardName as string list for json
            List<String> cardStrings = new ArrayList<>();
            for(RegisterCard card : cardsGot){
                cardStrings.add(card.getCardName());
            }
            Protocol protocol = new Protocol("CardsYouGotNow", new CardsYouGotNowBody(cardStrings));
            String json = Protocol.writeJson(protocol);
            logger.info("server inform who got which random cards");
        }
    }

    /**
     * inform about which cards are in current registers of all clients
     */
    public void handleCurrentCards() throws IOException {
        List<Register> list = new ArrayList<>();

        for(int clientID : Game.activePlayersList){
            // if current register slot is not empty
            if(Game.registersAllClients.get(clientID)[Game.registerPointer] != null){
                RegisterCard registerCard = Game.registersAllClients.get(clientID)[Game.registerPointer];
                String cardName = registerCard.getCardName();
                list.add(new Register(clientID, cardName));
            }else{
                list.add(new Register(clientID, null));
            }

        }

        Game.registerPointer++;
        logger.info("Server checked registerPointer " + Game.registerPointer);

        Protocol protocol = new Protocol("CurrentCards", new CurrentCardsBody(list));
        String json = Protocol.writeJson(protocol);
        logger.info("server inform cards of current register");
        makeOrderToAllClients(json);
    }

    /**
     * inform others about which card was played by whom
     * @param clientID
     * @param cardName
     */
    public void handleCardPlayed(int clientID, String cardName) throws IOException {
        Protocol protocol = new Protocol("CardPlayed", new CardPlayedBody(clientID, cardName));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs who played which card");
        makeOrderToAllClients(json);
    }

    /**
     * inform others about reboot client
     * @param clientID
     */
    public void handleReboot(int clientID) throws IOException {
        Protocol protocol = new Protocol("Reboot", new RebootBody(clientID));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs reboot client");
        makeOrderToAllClients(json);
    }


    /**
     * inform all clients game is finished
     * @param clientID
     * @throws IOException
     */
    public void handleGameFinished(int clientID) throws IOException {
        Protocol protocol = new Protocol("GameFinished", new GameFinishedBody(clientID));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs game finished");
        makeOrderToAllClients(json);
    }

    /**
     * inform all clients who has moved to where
     * @param clientID
     * @param toX
     * @param toY
     * @throws IOException
     */
    public void handleMovement(int clientID, int toX, int toY) throws IOException {
        Protocol protocol = new Protocol("Movement", new MovementBody(clientID, toX, toY));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs positions");
        makeOrderToAllClients(json);
    }

    public void handlePlayerTurning(int clientID, String turnDirection) throws IOException {
        Protocol protocol = new Protocol("PlayerTurning", new PlayerTurningBody(clientID, turnDirection));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs turnDirection");
        makeOrderToAllClients(json);
    }
}
