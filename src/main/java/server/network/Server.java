package server.network;

import protocol.Protocol;
import protocol.submessagebody.ErrorBody;
import protocol.submessagebody.ReceivedChatBody;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Logger;

public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    private final static Server server = new Server();

    // a set for the accepted ServerThreads
    protected static Hashtable<Integer, String> playerList = new Hashtable<>();

    // a set for checking clients´ names
    public static final Hashtable<String, ServerThread> clientList = new Hashtable<>();

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
     *
     * @param clientName
     * @param message
     */
    public void sendTo(String clientName, String message) {
        //send message to Client clientName
        try {
            Protocol protocol = new Protocol("ReceivedChat", new ReceivedChatBody(message));
            String json = Protocol.writeJson(protocol);
            new PrintWriter(clientList.get(clientName).getSocket().getOutputStream(), true).println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * server sends message to all clients
     *
     * @param message
     */
    public void sendMessageToAll(String message) {
        try {
            Protocol protocol = new Protocol("ReceivedChat", new ReceivedChatBody(message));
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
     * transmit an error to the client
     *
     * @param name    Name of the client
     * @param message Error message
     * @throws IOException
     */
    public void exception(String name, String message) throws IOException {

        Protocol protocol = new Protocol("Error", new ErrorBody(message));
        String json = Protocol.writeJson(protocol);
        clientList.get(name).makeOrder(json);
    }

}
