package client.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.stage.Stage;
import protocol.Protocol;
import protocol.submessagebody.*;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    // unique client ID given from server, key between Game, Server, Client and GUI!!
    private int clientID;
    // to remember which register war the last one
    private int registerPointer;
    // client list with all clientIDs
    private List<Integer> clientsList;
    // map: key = clientID, value = robotfigure;
    private HashMap<Integer, Integer> robotFigureAllClients= new HashMap<>();
    // map : key = clientID, value = name;
    private HashMap<Integer, String> clientNames = new HashMap<>();


    // clientID als StringProperty to bind with Controller
    private final StringProperty CLIENTIDASSTRINGPROPERTY = new SimpleStringProperty();
    // client name set by client
    private final StringProperty CLIENTNAME = new SimpleStringProperty();
    // Binding to Chat Window for displaying incoming messages
    private final StringProperty CHATHISTORY = new SimpleStringProperty();
    // Binding to ChatAndGame for moving the robots
    // max.6 players and 4 infos: e.g. [alice,2,5,UP]
    private final StringProperty[][] PLAYERPOSITIONS = new SimpleStringProperty[6][4];
    // Bindings enables/disables button "play card" (after played card, send message to next one to play)
    private final BooleanProperty ISINTURN = new SimpleBooleanProperty(false);


    // Getters
    public Socket getSocket() {
        return socket;
    }

    public BufferedReader getIn() {
        return IN;
    }

    public PrintWriter getOut() {
        return OUT;
    }

    public int getClientID() { return clientID; }

    public String getName() {
        return name;
    }

    public StringProperty getChatHistory() {
        return CHATHISTORY;
    }

    public StringProperty getCLIENTIDASSTRINGPROPERTY() { return CLIENTIDASSTRINGPROPERTY; }

    public HashMap<Integer, Integer> getRobotFigureAllClients() {
        return robotFigureAllClients;
    }

    public HashMap<Integer, String> getClientNames() {
        return clientNames;
    }




    // Setters
    public void setName(String name) { this.name = name; }

    public void setRobotFigureAllClients(Integer clientID, Integer figure) {
        robotFigureAllClients.put(clientID, figure);
    }

    public void setClientNames(Integer clientID, String name) {
        clientNames.put(clientID, name);
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



        // Open chat and game window after logging in successfully
        //CHATHISTORY.set("Welcome " + name + "\n");
        //LAUNCHER.launchChat(this);
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

        // start the login process
        LAUNCHER.launchLogin(this);

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
                            if(errorMessage.equals("This figure exists already, choose again.")){
                                reLoggin();
                            }
                            break;
                        case "HelloClient":
                            Protocol protocol = new Protocol("HelloServer", new HelloServerBody("CC",false, "Version 0.1"));
                            String js = Protocol.writeJson(protocol);
                            logger.info("protocol from Server: \n" + js );
                            OUT.println(js);
                            break;
                        case "Welcome":
                            logger.info(json);
                            int clientIDfromServer = Protocol.readJsonWelcomeBody(json).getClientID();
                            clientID = clientIDfromServer;
                            CLIENTIDASSTRINGPROPERTY.set(""+clientID);
                            logger.info(CLIENTIDASSTRINGPROPERTY.get());
                            logger.info("your clientID is: " + clientID);
                            break;
                        case "Alive":
                            String alive =Protocol.writeJson(new Protocol("Alive",null));
                            OUT.println(alive);
                            break;
                        case "PlayerAdded":
                            logger.info(json);
                            PlayerAddedBody playerAddedBody = Protocol.readJsonPlayerAdded(json);
                            int clientIDAdded = playerAddedBody.getClientID();
                            int figureAdded = playerAddedBody.getFigure();
                            String nameAdded = playerAddedBody.getName();
                            robotFigureAllClients.put(clientIDAdded, figureAdded);
                            clientNames.put(clientIDAdded, nameAdded);
                            logger.info(clientNames.get(clientIDAdded) + ": " + robotFigureAllClients.get(clientIDAdded));
                            if(clientIDAdded == clientID){
                                name = nameAdded;
                                goToChatGame();
                            }
                            break;
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * launch loggin as separate methode because of thread problem
     * @throws IOException
     */
    public void reLoggin() throws IOException {
        LAUNCHER.launchLogin(this);
    }

    /**
     * launch ChatAndGame as separate methode because of thread problem
     * @throws IOException
     */
    public void goToChatGame() throws IOException {
        LAUNCHER.launchChat(this);
    }

    /**
     * Method to be called from LoginWindowController to check the entered name.
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


    public void sendPersonalMessage(int to, String message) throws JsonProcessingException {

        Protocol protocol = new Protocol("SendChat", new SendChatBody(message, to));
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

            Protocol protocol = new Protocol("SendChat", new SendChatBody(message, -1));
            String json = Protocol.writeJson(protocol);
            logger.info(json);
            OUT.println(json);
        }
    }

    /**
     * give the client name and robot figure to server
     */
    public void setPlayerValues(String clientName, int robotFigure) throws JsonProcessingException {
        Protocol protocol = new Protocol("PlayerValues", new PlayerValuesBody(clientName, robotFigure));
        String JsonPlayerValues = Protocol.writeJson(protocol);
        logger.info(JsonPlayerValues);
        OUT.println(JsonPlayerValues);
    }
}
