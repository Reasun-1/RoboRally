package server.network;

import protocol.Protocol;
import protocol.submessagebody.ErrorBody;
import protocol.submessagebody.ReceivedChatBody;
import protocol.submessagebody.SendChatBody;

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
    private String clientName;


    /**
     * Constructor combines the client socket with the ServerThread socket
     *
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

            // Receive the name from the client and check it with the client list
            clientName = "";
            while (clientName.isEmpty()) {
                String temp_name = in.readLine();
                if (Server.clientList.containsKey(temp_name)) {
                    out.println("user existed!");
                } else {
                    // introduce the new client
                    sendMessage(temp_name + " has joined the chat.");
                    // Store the client name and add it to the name list
                    clientName = temp_name;
                    Server.clientList.put(clientName, this);
                    // send client name as a confirmation
                    out.println(clientName);
                }
            }


            // wait for messages from the client
            boolean flag = true;
            while (flag) {
                //wait for the output Stream from Client
                String json = in.readLine();
                // if no message from the client, then wait
                if (json != null) {
                    logger.info("excuteOrder by server " + json);
                    executeOrder(json);
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            try {
                closeConnect();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void executeOrder(String json) throws IOException, ClassNotFoundException {

        String messageType = Protocol.readJsonMessageType(json);
        switch (messageType) {
            case "Quit": // terminate the connection
                logger.info(clientName + " quit.");
                closeConnect();
                break;
            case "SendChat": // send private message
                logger.info("message arrived server");
                SendChatBody sendChatBody = null;
                try {
                    sendChatBody = Protocol.readJsonSendChatBody(json);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                String message = "";
                message = sendChatBody.getMessage();
                if (sendChatBody.getTo() != null) {
                    String toClient = sendChatBody.getTo();
                    if(Server.clientList.containsKey(toClient)){
                        // to target client
                        System.out.println(toClient);
                        sendPrivateMessage(toClient, clientName + "[private to you]: " + message);
                        // also to myself as info
                        sendPrivateMessage(clientName, clientName + "[private]: " + message);
                    }else{
                        System.out.println("There is no client with this name!"); // optional in terminal
                        Protocol protocol = new Protocol("Error", new ErrorBody("There is no client with this name!"));
                        String js = Protocol.writeJson(protocol);
                        makeOrder(js);
                    }

                } else {
                    logger.info("send message to all");
                    sendMessage(clientName + ": " + message);
                }
                break;
        }
    }

    /**
     * send a message to all clients
     *
     * @param message
     * @throws IOException
     */
    private void sendMessage(String message) throws IOException {
        //optional, for server terminal print
        System.out.println(message);
        Protocol protocol = new Protocol("ReceivedChat", new ReceivedChatBody(message));
        String json = Protocol.writeJson(protocol);
        logger.info(json);
        synchronized (Server.clientList) {
            for (Enumeration<ServerThread> e = Server.clientList.elements(); e.hasMoreElements(); ) {
                new PrintWriter(e.nextElement().getSocket().getOutputStream(), true).println(json);
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
            Server.clientList.remove(clientName);
        }

        // inform the other clients
        //sendMessage(clientName + " has left the room.");
        SOCKET.close();
    }

    /**
     * send a message only to one client
     *
     * @param name
     * @param message
     */
    public void sendPrivateMessage(String name, String message) {
        Server.getServer().sendTo(name, message);
    }

}
