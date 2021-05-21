package client.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.stage.Stage;
import protocol.Protocol;
import protocol.submessagebody.ReceivedChatBody;
import protocol.submessagebody.SendChatBody;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Client class is responsible for the connection to the server and for storing the properties connected with the GUI.
 * It also holds the main method which starts the Application.
 */
public class Client extends Application {
    private static final Logger logger = Logger.getLogger(Client.class.getName());
    // Socket for the TCP connection
    private volatile Socket socket;
    // Writer for outgoing messages
    private final PrintWriter OUT;
    // Reader for incoming messages
    private final BufferedReader IN;
    // Launcher for new Windows
    private final WindowLauncher LAUNCHER = new WindowLauncher();
    // Name can be chosen in the login process
    private String name;
    // Binding to Chat Window for displaying incoming messages
    private final StringProperty CHATHISTORY = new SimpleStringProperty();


    public Socket getSocket() {
        return socket;
    }

    public BufferedReader getIn() {
        return IN;
    }

    public PrintWriter getOut() {
        return OUT;
    }

    public String getName() {
        return name;
    }

    public StringProperty getChatHistory() {
        return CHATHISTORY;
    }


    /**
     * Constructor establishes the TCP-Connection and initializes the remaining variables
     *
     * @throws IOException
     */
    public Client() throws IOException {

        // Always connect to localhost and fixed port (maybe ask for ip and port?)
        socket = new Socket("127.0.0.1", 5200);

        // Create writer to send messages to server via the TCP-socket
        OUT = new PrintWriter(socket.getOutputStream(), true);

        // Create reader to receive messages from server via the TCP-socket
        IN = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Start with an empty name, will be set during Login
        name = "";

        CHATHISTORY.set("");
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the application on the client side
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // start the login process
        LAUNCHER.launchLogin(this);

        // Open chat and game window after logging in successfully
        CHATHISTORY.set("Welcome " + name + "\n");
        LAUNCHER.launchChat(this);
        new Thread(() -> {
            try {
                String json;
                while (!socket.isClosed()) {
                    // Client socket waits for the input from the server
                    json = IN.readLine();
                    //if(!line.isEmpty()) { // NullPointerException
                    if (json != null) {
                            executeOrder(json);
                    }
                }
                Platform.exit();
            } catch (IOException e) {
                try {
                    socket.close();
                    Platform.exit();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Execute an order from the server by checking the order code and calling the correct method
     * @throws IOException
     */
    public void executeOrder(String json) throws IOException {

        Client client = this;

        String messageType = Protocol.readJsonMessageType(json);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    switch (messageType) {
                        case "Quit": // terminate the connection
                            socket.close();
                            break;
                        case "ReceivedChat":
                            logger.info("received chat printed");
                            String message = Protocol.readJsonReceivedChatBody(json).getMessage();
                            CHATHISTORY.set(CHATHISTORY.get() + message + "\n");
                            break;
                        case "Error":
                            logger.info("error printed");
                            String errorMessage = Protocol.readJsonErrorBody(json).getError();
                            LAUNCHER.launchError(errorMessage);
                            System.out.println(errorMessage);
                            break;
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Method to be called from LoginWindowController to check the entered name.
     *
     * @param temp_name
     */
    public void checkName(String temp_name) {
        try {
            // Send name to Server to check whether it is available
            OUT.println(temp_name);
            String answer = IN.readLine();
            if (answer.equals("user existed!")) {
                // Open error Window, then restart the Login
                LAUNCHER.launchError("The chosen name already exists. Please choose another name:");
                LAUNCHER.launchLogin(this);
                System.out.println("user existed!");
            } else {
                // Store the chosen name
                name = answer;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send a message to only one client.
     *
     * @param name
     * @param message
     */
    public void sendPersonalMessage(String name, String message) throws JsonProcessingException {

        Protocol protocol = new Protocol("SendChat", new SendChatBody(name,message));
        String json = Protocol.writeJson(protocol);
        logger.info(json);
        OUT.println(json);
    }

    /**
     * Send message to the server, quit if logout order is given
     *
     * @param message
     */
    public void sendMessage(String message) throws JsonProcessingException {
        // Check logout condition
        if (message.equals("bye")) {
            Protocol protocol = new Protocol("Quit", null);
            String json = Protocol.writeJson(protocol);
            logger.info(json);
            OUT.println(json);
            // stop the connection
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Confirm logout (currently via terminal)
            System.out.println("You left the room.");

        } else {
            // Send message to server

            Protocol protocol = new Protocol("SendChat", new SendChatBody(null, message));
            String json = Protocol.writeJson(protocol);
            logger.info(json);
            OUT.println(json);
        }
    }
}
