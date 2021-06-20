package server.network;

import protocol.ExecuteOrder;
import protocol.Protocol;
import protocol.submessagebody.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Enumeration;
import java.util.logging.Logger;


public class ServerThread implements Runnable {

    private static final Logger logger = Logger.getLogger(ServerThread.class.getName());

    private final Socket SOCKET;
    private int clientID;

    public boolean flagIsAI = false;


    /**
     * Constructor combines the client socket with the ServerThread socket
     * @param socket
     */
    public ServerThread(Socket socket) {
        SOCKET = socket;
    }

    public Socket getSocket() {
        return SOCKET;
    }

    @Override
    public void run() {
        try {
            // Create reader for messages from the client
            BufferedReader in = new BufferedReader(new InputStreamReader(SOCKET.getInputStream()));
            // Create writer for messages to the client
            PrintWriter out = new PrintWriter(SOCKET.getOutputStream(), true);

            String js = Protocol.writeJson(new Protocol("HelloClient", new HelloClientBody("Version 1.0")));
            logger.info("protocol to client: \n" + js);
            out.println(js);

            //check the protocol version
            boolean versionProofed = false;

            while(!versionProofed){
                String json = in.readLine();
                if(json != null){
                    HelloServerBody helloServerBody = Protocol.readJsonHelloServerBody(json);
                    String protocolVersion = helloServerBody.getProtocol();
                    // check if the client is AI
                    System.out.println(json);
                    System.out.println(helloServerBody.isAI());
                    if(helloServerBody.isAI()){
                        flagIsAI = true;
                    }
                    if(!"Version 1.0".equals(protocolVersion)){
                        Protocol protocol = new Protocol("Error", new ErrorBody("Version wrong! disconnected."));
                        String jss = Protocol.writeJson(protocol);
                        out.println(jss);
                        closeConnect();
                    }
                    versionProofed = true;
                }
            }

            // generate a clientID from server
            clientID = Server.clientIDsPool.pop();
            Server.clientList.put(clientID,this);

            System.out.println("print aiFlag in ServerThread: " + flagIsAI);

            // if the client is AI, remember the clientID of AI
            if(flagIsAI){
                ExecuteOrder.clientIDOfAI = clientID;
            }

            System.out.println("print aiNum in ExecuteOrder: " + ExecuteOrder.clientIDOfAI);

            String string = Protocol.writeJson(new Protocol("Welcome", new WelcomeBody(clientID)));
            logger.info("welcome info printed");
            out.println(string);

            // wait for messages from the client
            boolean flag = true;
            while (flag) {
                //wait for the output Stream from Client
                String json = in.readLine();
                // if no message from the client, then wait
                if (json != null) {
                    logger.info("excuteOrder by server " + json);
                    ExecuteOrder.executeOrder(clientID,json); // invoke method in extern class ExcuteOrder
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            try {
                closeConnect();
                Server.getServer().handleConnectionUpdate(clientID);
                ExecuteOrder.aliveCheckList.get(clientID).flagAliveCheck = false;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * send a message to all clients
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(int from, String message) throws IOException {
        //optional, for server terminal print
        System.out.println(message);
        Protocol protocol = new Protocol("ReceivedChat", new ReceivedChatBody(message, from, false));
        String json = Protocol.writeJson(protocol);
        logger.info(json);
        synchronized (Server.clientList) {
            for(int clientID : Server.clientList.keySet()){
                ServerThread serverThread = Server.clientList.get(clientID);
                new PrintWriter(serverThread.getSocket().getOutputStream(), true).println(json);
            }
        }
    }


    /**
     * transmit an order from the server to the client
     *
     * @param json
     * @throws IOException
     */
    public void makeOrder(String json) throws IOException {
        new PrintWriter(SOCKET.getOutputStream(), true).println(json);
    }

    /**
     * terminate the connection to the client
     *
     * @throws IOException
     */
    public void closeConnect() throws IOException {
        //remove the socket from the set
        synchronized (Server.clientList) {
            Server.clientList.remove(clientID);
        }
        //sendMessage(clientName + " has left the room.");
        SOCKET.close();
    }

    /**
     * send a message only to one client
     */
    public void sendPrivateMessage(int toClient,int fromClient, String message) {
        Server.getServer().sendTo(toClient,fromClient, message);
    }



}
