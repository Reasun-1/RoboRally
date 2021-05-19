package server.network;

import protocol.MessageBody;
import protocol.Protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Enumeration;


public class ServerThread implements Runnable {

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
                    executeOrder(json);
                }
            }

        } catch (IOException e) {
            try {
                closeConnect();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void executeOrder(String json) throws IOException {
        switch (Protocol.readJson(json).getMessageType()) {
            case "Quit": // terminate the connection
                closeConnect();
                break;
            case "SendChat": // send private message
                MessageBody messageBody = Protocol.readJson(json).getMessageBody();
                String message = "";
                message = messageBody.getMessage();
                if (messageBody.isPrivate() == true) {
                    String toClient = messageBody.getTo();
                    if(Server.clientList.containsKey(toClient)){
                        // to target client
                        System.out.println(toClient);
                        sendPrivateMessage(toClient, clientName + "[private to you]: " + message);
                        // also to myself as info
                        sendPrivateMessage(clientName, clientName + "[private]: " + message);
                    }else{
                        System.out.println("There is no client with this name!"); // optional in terminal
                        MessageBody mb = new MessageBody();
                        mb.setError("There is no client with this name!");
                        Protocol protocol = new Protocol("Error", mb);
                        String js = Protocol.writeJson(protocol);
                        makeOrder(js);
                    }

                } else {
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

        MessageBody messageBody = new MessageBody();
        messageBody.setPrivate(false);
        messageBody.setMessage(message);
        Protocol protocol = new Protocol("ReceivedChat", messageBody);
        String json = Protocol.writeJson(protocol);

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
