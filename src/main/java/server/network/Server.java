package server.network;

import ai.database.Simulator;
import org.apache.log4j.Logger;
import protocol.Protocol;
import protocol.submessagebody.*;
import server.game.Game;
import server.game.Register;
import server.registercards.PowerUp;
import server.registercards.RegisterCard;
import server.upgradecards.UpgradeCard;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


/**
 * The type Server.
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    private final static Server server = new Server();
    /**
     * The constant playerList.
     */
// a map for player index in the game: key = playerIndex, value = clientID
    protected static Hashtable<Integer, Integer> playerList = new Hashtable<>();
    /**
     * The constant clientList.
     */
// a map for the accepted ServerThreads: key = clientID, value = ServerThread
    public static final LinkedHashMap<Integer, ServerThread> clientList = new LinkedHashMap<>();
    /**
     * The constant clientListPointer.
     */
// for set start point in aufbauPhase
    public static int clientListPointer = 0;
    /**
     * The constant clientIDsPool.
     */
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

    /**
     * The constant clientIDUndNames.
     */
// map for clientID - clientName : key = clientID, value = clientName: (give new clients infos of previous players)
    public static final HashMap<Integer, String> clientIDUndNames = new HashMap<>();
    /**
     * The constant clientIDUndRobots.
     */
// map for clientID - robotFigure : key = clientID, value = robotNum: (give new clients infos of previous players)
    public static final HashMap<Integer, Integer> clientIDUndRobots = new HashMap<>();
    /**
     * The constant clientIDUndReady.
     */
// map for clientID- isReady : key = clientID, value = isReady: (give new clients infos of previous players)
    public static final LinkedHashMap<Integer, Boolean> clientIDUndReady = new LinkedHashMap<>();
    /**
     * The constant playersWhoAreReady.
     */
// list for clients who are ready: (give new clients infos for previous players)
    public static List<Integer> playersWhoAreReady = new ArrayList<>();


    /**
     * private constructor for singleton implementation
     */
    protected Server() {
    }

    /**
     * Singleton access on server
     *
     * @return server instance
     */
    public static Server getServer() {
        return server;
    }


    /**
     * main thread for server class
     *
     * @param args the input arguments
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
     *
     * @throws IOException the io exception
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
     *
     * @param to      the to
     * @param from    the from
     * @param message the message
     */
    public void sendTo(int to,int from, String message) {
        //send message to Client clientName
        try {
            Protocol protocol = new Protocol("ReceivedChat", new ReceivedChatBody(message, from, true));
            String json = Protocol.writeJson(protocol);
            logger.info(json);
            new PrintWriter(clientList.get(to).getSocket().getOutputStream(), true).println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * server sends message to all clients
     *
     * @param from    the from
     * @param message the message
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
     *
     * @param json the json
     * @throws IOException the io exception
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
     *
     * @param targetClientID the target client id
     * @param json           the json
     * @throws IOException the io exception
     */
    public void makeOrderToOneClient(int targetClientID, String json) throws IOException {
        if(clientList.containsKey(targetClientID)){
            new PrintWriter(clientList.get(targetClientID).getSocket().getOutputStream(), true).println(json);
        }
    }

    /**
     * transmit an error to the client
     *
     * @param clientID the client id
     * @param message  the message
     * @throws IOException the io exception
     */
    public void exception(int clientID, String message) throws IOException {
        Protocol protocol = new Protocol("Error", new ErrorBody(message));
        String json = Protocol.writeJson(protocol);
        clientList.get(clientID).makeOrder(json);
    }

    /**
     * check all players online
     *
     * @param client the client
     * @throws IOException the io exception
     */
    public void handleAlive(int client) throws IOException {
        Protocol protocol = new Protocol("Alive", null);
        String json = Protocol.writeJson(protocol);
        logger.info("Server sends Alive check for client " + client);
        makeOrderToOneClient(client, json);
    }

    /**
     * inform all the clients that one new player war added
     *
     * @param clientID    the client id
     * @param clientName  the client name
     * @param robotFigure the robot figure
     * @throws IOException the io exception
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
     *
     * @param targetClient the target client
     * @param clientID     the client id
     * @param clientName   the client name
     * @param robotFigure  the robot figure
     * @throws IOException the io exception
     */
    public void handlePlayerAddedToOne(int targetClient, int clientID, String clientName, int robotFigure) throws IOException {
        Protocol protocol = new Protocol("PlayerAdded", new PlayerAddedBody(clientID, clientName, robotFigure));
        String json = Protocol.writeJson(protocol);
        logger.info("server added player");
        makeOrderToOneClient(targetClient,json);
    }

    /**
     * inform all the clients that someone is ready to play
     *
     * @param clientID the client id
     * @param isReady  the is ready
     * @throws IOException the io exception
     */
    public void handlePlayerStatus(int clientID, boolean isReady) throws IOException {
        Protocol protocol = new Protocol("PlayerStatus", new PlayerStatusBody(clientID, isReady));
        String json = Protocol.writeJson(protocol);
        logger.info("server inform player status");
        makeOrderToAllClients(json);
    }

    /**
     * inform the new player about the status of previous players in server
     *
     * @param targetClient the target client
     * @param clientID     the client id
     * @param isReady      the is ready
     * @throws IOException the io exception
     */
    public void handlePlayerStatusToOne(int targetClient, int clientID, boolean isReady) throws IOException {
        Protocol protocol = new Protocol("PlayerStatus", new PlayerStatusBody(clientID, isReady));
        String json = Protocol.writeJson(protocol);
        logger.info("server inform status of previous players");
        makeOrderToOneClient(targetClient,json);
    }

    /**
     * inform the first ready client to select a map
     *
     * @param clientID the client id
     * @throws IOException the io exception
     */
    public void handleSelectMap(int clientID) throws IOException {
        ArrayList<String> mapList = new ArrayList<>();
        mapList.add("Dizzy Highway");
        mapList.add("Lost Bearings");
        mapList.add("Death Trap");
        mapList.add("Extra Crispy");
        mapList.add("Twister");
        Protocol protocol = new Protocol("SelectMap", new SelectMapBody(mapList));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs first ready player to select a map");
        makeOrderToOneClient(clientID, json);
    }

    /**
     * give the map as 3D-List to all players to rebuild in GUI
     *
     * @param mapName the map name
     * @throws IOException the io exception
     */
    public void handleGameStarted(String mapName) throws IOException {
        // dummy map: soon with right maps

        Protocol protocol = new Protocol("GameStarted", new GameStartedBody(null));
        String json = Protocol.writeJson(protocol);
        logger.info("server sends map");
        makeOrderToAllClients(json);
    }

    /**
     * for map twister, send the locations of all 4 checkpoints
     *
     * @throws IOException the io exception
     */
    public void handleCheckpointsLocations() throws IOException {
        for (int i = 0; i < 4; i++) {
            int[] location = Game.movingCheckpoints.get(i + 1);
            int x = location[0];
            int y = location[1];

            Protocol protocol = new Protocol("CheckpointMoved", new CheckpointMovedBody(i + 1, x, y));
            String json = Protocol.writeJson(protocol);
            logger.info(json);
            makeOrderToAllClients(json);
        }
    }

    /**
     * inform all players, which phase is on
     *
     * @param phase the phase
     * @throws IOException the io exception
     */
    public void handleActivePhase(int phase) throws IOException {
        Protocol protocol = new Protocol("ActivePhase", new ActivePhaseBody(phase));
        String json = Protocol.writeJson(protocol);
        logger.info("server sends phase info");
        makeOrderToAllClients(json);
    }

    /**
     * inform players who is the current player
     *
     * @param currentClientID the current client id
     * @throws IOException the io exception
     */
    public void handleCurrentPlayer(int currentClientID) throws IOException {
        Protocol protocol = new Protocol("CurrentPlayer", new CurrentPlayerBody(currentClientID));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs current player id");
        makeOrderToAllClients(json);
    }

    /**
     * inform all clients who has chosen which start point
     *
     * @param clientID the client id
     * @param x        the x
     * @param y        the y
     * @throws IOException the io exception
     */
    public void handleStartingPointTaken(int clientID, int x, int y) throws IOException {
        Protocol protocol = new Protocol("StartingPointTaken", new StartingPointTakenBody(x, y, clientID));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs set starting point");
        makeOrderToAllClients(json);
    }

    /**
     * distribute cards to each player privately
     *
     * @throws IOException the io exception
     */
    public void handleYourCards() throws IOException {
        HashMap<Integer, List<String>> cardsAllClients = Game.getInstance().gameHandleYourCards();
        for(int clientID : cardsAllClients.keySet()){
            Protocol protocol = new Protocol("YourCards", new YourCardsBody(cardsAllClients.get(clientID)));
            String json = Protocol.writeJson(protocol);
            logger.info("server informs your cards.");
            makeOrderToOneClient(clientID, json);
        }
    }

    /**
     * server handles new cards instead of damage cards for spamBlocker
     *
     * @param clientID the client id
     * @param newCards the new cards
     * @throws IOException the io exception
     */
    public void handleSpamBlocker(int clientID, List<String> newCards) throws IOException {
        Protocol protocol = new Protocol("YourCards", new YourCardsBody(newCards));
        String json = Protocol.writeJson(protocol);
        logger.info("server sends new cards for spam blocker.");
        makeOrderToOneClient(clientID, json);
    }

    /**
     * server gives three cards to the target client
     *
     * @param clientID the client id
     * @param newCards the new cards
     * @throws IOException the io exception
     */
    public void handleMemorySwap(int clientID, List<String> newCards) throws IOException {
        Protocol protocol = new Protocol("YourCards", new YourCardsBody(newCards));
        String json = Protocol.writeJson(protocol);
        logger.info("server sends new cards for spam blocker.");
        makeOrderToOneClient(clientID, json);
    }

    /**
     * for the situation that drawn cards deck hasn??t enough cards, redraw
     *
     * @param clientID the client id
     * @param newCards the new cards
     * @throws IOException the io exception
     */
    public void handleYourNewCards(int clientID, List<String> newCards) throws IOException {
        Protocol protocol = new Protocol("YourCards", new YourCardsBody(newCards));
        String json = Protocol.writeJson(protocol);
        logger.info("server inform new cards");
        makeOrderToOneClient(clientID, json);
    }

    /**
     * inform others about how many card you have drawn
     *
     * @param yourID        the your id
     * @param numberOfCards the number of cards
     * @throws IOException the io exception
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
     *
     * @param clientID the client id
     * @throws IOException the io exception
     */
    public void handleShuffleCoding(int clientID) throws IOException {
        Protocol protocol = new Protocol("ShuffleCoding", new ShuffleCodingBody(clientID));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs shuffle");
        makeOrderToAllClients(json);
    }

    /**
     * inform all clients who has set/clear which register
     *
     * @param clientID    the client id
     * @param registerNum the register num
     * @param isFilled    the is filled
     * @throws IOException the io exception
     */
    public void handleCardSelected(int clientID, int registerNum, boolean isFilled) throws IOException {
        Protocol protocol = new Protocol("CardSelected", new CardSelectedBody(clientID, registerNum, isFilled));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs card selected");
        makeOrderToAllClients(json);
    }

    /**
     * as first clients finished programming, timer starts
     *
     * @throws IOException the io exception
     */
    public void handleTimerStarted() throws IOException {
        Protocol protocol = new Protocol("TimerStarted", null);
        String json = Protocol.writeJson(protocol);
        logger.info("server starts timer");
        makeOrderToAllClients(json);
    }

    /**
     * if some one did not finish when time is out, handle time ended
     *
     * @param clientsWhoNotFinished the clients who not finished
     * @throws IOException the io exception
     */
    public void handleTimerEnded(List<Integer> clientsWhoNotFinished) throws IOException {
        Protocol protocol = new Protocol("TimerEnded", new TimerEndedBody(clientsWhoNotFinished));
        String json = Protocol.writeJson(protocol);
        logger.info("server inform who did not finish in time");
        makeOrderToAllClients(json);
    }

    /**
     * if some one did not finish in time, will get 5 random cards for register slots
     *
     * @param whoNotFinishedInTime the who not finished in time
     * @throws IOException the io exception
     */
    public void handleCardsYouGotNow(List<Integer> whoNotFinishedInTime) throws IOException {
        for(int clientID : whoNotFinishedInTime){
            List<RegisterCard> cards = Game.discardedCards.get(clientID);
            List<RegisterCard> cardsGot = cards.subList(cards.size() - 5, cards.size());
            RegisterCard[] arrCardsGot = new RegisterCard[5];
            for (int i = 0; i < 5; i++) {
               arrCardsGot[i] = cardsGot.get(i);
            }
            // for the exception 1.Reg not Again
            if(arrCardsGot[0].getCardName().equals("Again")){
                PowerUp powerUp = new PowerUp();
                arrCardsGot[0] = powerUp;
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
            makeOrderToOneClient(clientID,json);
        }
    }

    /**
     * inform about which cards are in current registers of all clients
     *
     * @throws IOException the io exception
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

        logger.info("Server checked registerPointer " + Game.registerPointer);

        Protocol protocol = new Protocol("CurrentCards", new CurrentCardsBody(list));
        String json = Protocol.writeJson(protocol);
        logger.info("server inform cards of current register");
        makeOrderToAllClients(json);
    }

    /**
     * inform others about which card was played by whom
     *
     * @param clientID the client id
     * @param cardName the card name
     * @throws IOException the io exception
     */
    public void handleCardPlayed(int clientID, String cardName) throws IOException {
        Protocol protocol = new Protocol("CardPlayed", new CardPlayedBody(clientID, cardName));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs who played which card");
        makeOrderToAllClients(json);
    }

    /**
     * inform others about reboot client
     *
     * @param clientID the client id
     * @throws IOException the io exception
     */
    public void handleReboot(int clientID) throws IOException {
        Protocol protocol = new Protocol("Reboot", new RebootBody(clientID));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs reboot client");
        System.out.println("flag reboot from server");
        makeOrderToAllClients(json);
    }


    /**
     * inform all clients game is finished
     *
     * @param clientID the client id
     * @throws IOException the io exception
     */
    public void handleGameFinished(int clientID) throws IOException {
        Protocol protocol = new Protocol("GameFinished", new GameFinishedBody(clientID));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs game finished");
        System.out.println(json);
        makeOrderToAllClients(json);
    }

    /**
     * inform all clients who has moved to where
     *
     * @param clientID the client id
     * @param toX      the to x
     * @param toY      the to y
     * @throws IOException the io exception
     */
    public void handleMovement(int clientID, int toX, int toY) throws IOException {
        Protocol protocol = new Protocol("Movement", new MovementBody(clientID, toX, toY));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs positions");
        logger.info(json);
        makeOrderToAllClients(json);
    }

    /**
     * inform all clients which player turns to which direction
     *
     * @param clientID      the client id
     * @param turnDirection the turn direction
     * @throws IOException the io exception
     */
    public void handlePlayerTurning(int clientID, String turnDirection) throws IOException {
        Protocol protocol = new Protocol("PlayerTurning", new PlayerTurningBody(clientID, turnDirection));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs turnDirection");
        makeOrderToAllClients(json);
    }

    /**
     * inform all clients who has drawn which damage cards
     *
     * @param clientID         the client id
     * @param drawnDamageCards the drawn damage cards
     * @throws IOException the io exception
     */
    public void handleDrawDamage(int clientID, List<String> drawnDamageCards) throws IOException {

        Protocol protocol = new Protocol("DrawDamage", new DrawDamageBody(clientID, drawnDamageCards));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs drawn damage cards");
        makeOrderToAllClients(json);

    }

    /**
     * if damage card played, inform all clients which random card has be drawn
     *
     * @param register the register
     * @param client   the client
     * @param cardName the card name
     * @throws IOException the io exception
     */
    public void handleReplaceCard(int register, int client, String cardName) throws IOException {
        Protocol protocol = new Protocol("ReplaceCard", new ReplaceCardBody(register, cardName, client));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs replace card");
        makeOrderToAllClients(json);
    }

    /**
     * if connection loses, inform all clients
     *
     * @param clientID the client id
     * @throws IOException the io exception
     */
    public void handleConnectionUpdate(int clientID) throws IOException {
        Game.getInstance().removePlayer(clientID);
        // if only one player left, can not continue
        if(clientList.size() == 1){
            for(int remainedClient : clientList.keySet()){
                exception(remainedClient, "Only you left in game, please quit the game.");
            }
        }
        // reinform the next player
        if(Game.priorityEachTurn.size() != 0){
            int curClient = Game.priorityEachTurn.get(0);
            handleCurrentPlayer(curClient);
        }

        Protocol protocol = new Protocol("ConnectionUpdate", new ConnectionUpdateBody(clientID, false, "remove"));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs connection update");
        makeOrderToAllClients(json);
    }

    /**
     * inform all clients who has got how many cubes
     *
     * @param client the client
     * @param addNum the add num
     * @param source the source
     * @throws IOException the io exception
     */
    public void handleEnergy(int client, int addNum, String source) throws IOException {
        Protocol protocol = new Protocol("Energy", new EnergyBody(client, addNum, source));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs energy got.");
        makeOrderToAllClients(json);
    }

    /**
     * inform all players which map was selected
     *
     * @param mapName the map name
     * @throws IOException the io exception
     */
    public void handleMapSelected(String mapName) throws IOException {
        Protocol protocol = new Protocol("MapSelected", new MapSelectedBody(mapName));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs which map selected");
        makeOrderToAllClients(json);
    }

    /**
     * after start point setted,
     *
     * @throws IOException the io exception
     */
    public void handleRefillShop() throws IOException {
        List<String> upgradeCardsString = new ArrayList<>();
        for (int i = 0; i < Game.clientIDs.size(); i++) {
            UpgradeCard upgradeCard = Game.upgradeShop.pop();
            String upCardName = upgradeCard.getCardName();
            upgradeCardsString.add(upCardName);
        }
        Protocol protocol = new Protocol("RefillShop", new RefillShopBody(upgradeCardsString));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs Refill upgrade shop.");
        makeOrderToAllClients(json);
    }

    /**
     * inform all clients who bought which upgrade
     *
     * @param client     the client
     * @param boughtCard the bought card
     * @throws IOException the io exception
     */
    public void handleUpgradeBought(int client, String boughtCard) throws IOException {
        Protocol protocol = new Protocol("UpgradeBought", new UpgradeBoughtBody(client, boughtCard));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs who bought which upgrade.");
        makeOrderToAllClients(json);
    }

    /**
     * inform all clients who chose which register for AdminPrivilage
     *
     * @param client
     * @param regNr
     * @throws IOException
     */
    public void handleRegisterChosen(int client, int regNr) throws IOException {
        Protocol protocol = new Protocol("RegisterChosen", new RegisterChosenBody(client, regNr));
        String json = Protocol.writeJson(protocol);
        logger.info("server informs who chose which reg. for AdminPrivilage");
        makeOrderToAllClients(json);
    }
}
