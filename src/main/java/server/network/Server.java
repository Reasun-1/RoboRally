package server.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import protocol.Protocol;
import protocol.submessagebody.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Stack;
import java.util.logging.Logger;

public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    private final static Server server = new Server();
    // a set for the accepted ServerThreads
    protected static Hashtable<Integer, String> playerList = new Hashtable<>();
    // a set for checking clientIDs
    public static final Hashtable<Integer, ServerThread> clientList = new Hashtable<>();
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
    // all the figure numbers chosen from clients
    public static final HashSet<Integer> figuresPoll = new HashSet<>();


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
     *
     * @param message
     */
    public void sendMessageToAll(int from, String message) {
        try {
            Protocol protocol = new Protocol("ReceivedChat", new ReceivedChatBody(message, from, false));
            String json = Protocol.writeJson(protocol);
            synchronized (clientList) {
                for (Enumeration<ServerThread> e = clientList.elements(); e.hasMoreElements(); ) {
                    new PrintWriter(e.nextElement().getSocket().getOutputStream(), true).println(json);
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
            for (Enumeration<ServerThread> e = clientList.elements(); e.hasMoreElements(); ) {
                new PrintWriter(e.nextElement().getSocket().getOutputStream(), true).println(json);
            }
        }
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
    public void handlePlayerAdded(int clientID, String clientName, int robotFigure) throws IOException {
        Protocol protocol = new Protocol("PlayerAdded", new PlayerAddedBody(clientID, clientName, robotFigure));
        String json = Protocol.writeJson(protocol);
        logger.info("server added player");
        makeOrderToAllClients(json);
    }
}
