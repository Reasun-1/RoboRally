package server.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import protocol.Protocol;
import protocol.submessagebody.*;
import server.feldobjects.FeldObject;
import server.feldobjects.Pit;
import server.feldobjects.PushPanel;

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
     * inform all players about the selected map
     * @param mapName
     * @throws IOException
     */
    public void handleMapSelected(String mapName) throws IOException {
        Protocol protocol = new Protocol("MapSelected", new MapSelectedBody(mapName));
        String json = Protocol.writeJson(protocol);
        logger.info("server inform all players about selected map");
        makeOrderToAllClients(json);
    }

    /**
     * give the map as 3D-List to all players to rebuild in GUI
     * @param mapName
     * @throws IOException
     */
    public void handleGameStarted(String mapName) throws IOException {
        // dummy map: soon with right maps
        List<List<List<FeldObject>>> threeDListAsMap = Arrays.asList(Arrays.asList(Arrays.asList(new Pit(1,"rr"))));

        Protocol protocol = new Protocol("GameStarted", new GameStartedBody(threeDListAsMap));
        String json = Protocol.writeJson(protocol);
        logger.info("server sends map");
        makeOrderToAllClients(json);
    }
}
