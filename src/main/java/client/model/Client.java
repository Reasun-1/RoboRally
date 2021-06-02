package client.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.stage.Stage;
import protocol.Protocol;
import protocol.submessagebody.*;
import server.feldobjects.FeldObject;
import server.feldobjects.Pit;
import server.game.Register;

import java.io.*;
import java.net.Socket;
import java.util.*;
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
    private HashMap<Integer, Integer> robotFigureAllClients = new HashMap<>();
    // map : key = clientID, value = name;
    private HashMap<Integer, String> clientNames = new HashMap<>();
    // map : key = clientID, value = isReady
    private LinkedHashMap<Integer, Boolean> readyClients = new LinkedHashMap<>();
    // storage of my start positions for all clients: key=clientID, value = [x,y]
    private final HashMap<Integer, int[]> startPositionsAllClients = new HashMap<>();


    //=================================Properties================================
    // clientID als StringProperty to bind with Controller
    private final StringProperty CLIENTIDASSTRINGPROPERTY = new SimpleStringProperty();
    // client name set by client
    private final StringProperty CLIENTNAME = new SimpleStringProperty();
    // Binding to Chat Window for displaying incoming messages
    private final StringProperty CHATHISTORY = new SimpleStringProperty();
    // Information Window: inform what to do next!
    private final StringProperty INFORMATION = new SimpleStringProperty();
    // players who are in server (invoked by PlayerAdded)
    private final StringProperty PLAYERSINSERVER = new SimpleStringProperty();
    // players who are ready to play
    private final StringProperty PLAYERSWHOAREREADY = new SimpleStringProperty();
    // show the info about game phase
    private final StringProperty GAMEPHASE = new SimpleStringProperty();
    // who is in the first place of ready list, is allowed to select a map
    private final BooleanProperty CANSELECTMAP = new SimpleBooleanProperty(false);
    // player who is in turn
    private final BooleanProperty ISCURRENTPLAYER = new SimpleBooleanProperty(false);
    // player who can set start point, binds with selectStartPoint button in GUI
    private final BooleanProperty CANSETSTARTPOINT = new SimpleBooleanProperty(false);
    // binds with drawnCards in GUI
    private final List<StringProperty> MYDRAWNCARDS = new ArrayList<>();
    // binds myRegister slots in GUI
    private final StringProperty[] MYREGISTER = new StringProperty[5];
    // bind button finish in GUI
    private final BooleanProperty CANCLICKFINISH = new SimpleBooleanProperty(false);
    // shows whether the timer is on
    private final BooleanProperty ISTIMERON = new SimpleBooleanProperty(false);
    // bind button canPlayNextRegister in GUI
    private final BooleanProperty CANPLAYNEXTREGISTER = new SimpleBooleanProperty(false);
    // binding current positions of all clients
    private final HashMap<Integer, IntegerProperty[]> CURRENTPOSITIONS = new HashMap<>();
    // to bind..
    private final HashMap<Integer, String> currentDirection = new HashMap<>();


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

    public int getClientID() {
        return clientID;
    }

    public String getName() {
        return name;
    }

    public StringProperty getChatHistory() {
        return CHATHISTORY;
    }

    public StringProperty PLAYERSINSERVERProperty() {
        return PLAYERSINSERVER;
    }

    public StringProperty PLAYERSWHOAREREADYProperty() {
        return PLAYERSWHOAREREADY;
    }

    public StringProperty getCLIENTIDASSTRINGPROPERTY() {
        return CLIENTIDASSTRINGPROPERTY;
    }

    public HashMap<Integer, Integer> getRobotFigureAllClients() {
        return robotFigureAllClients;
    }

    public HashMap<Integer, String> getClientNames() {
        return clientNames;
    }

    public BooleanProperty CANSELECTMAPProperty() {
        return CANSELECTMAP;
    }

    public StringProperty INFORMATIONProperty() {
        return INFORMATION;
    }

    public BooleanProperty ISCURRENTPLAYERProperty() {
        return ISCURRENTPLAYER;
    }

    public StringProperty GAMEPHASEProperty() {
        return GAMEPHASE;
    }

    public BooleanProperty CANSETSTARTPOINTProperty() {
        return CANSETSTARTPOINT;
    }

    public List<StringProperty> getMYDRAWNCARDS() {
        return MYDRAWNCARDS;
    }

    public StringProperty[] getMYREGISTER() {
        return MYREGISTER;
    }

    public BooleanProperty CANCLICKFINISHProperty() {
        return CANCLICKFINISH;
    }

    public BooleanProperty CANPLAYNEXTREGISTERProperty() {
        return CANPLAYNEXTREGISTER;
    }


    // Setters
    public void setName(String name) {
        this.name = name;
    }

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

        // Always connect to localhost and fixed port
        //socket = new Socket("127.0.0.1", 5200);
        // test server
        socket = new Socket("sep21.dbs.ifi.lmu.de", 52018);

        // Create writer to send messages to server via the TCP-socket
        OUT = new PrintWriter(socket.getOutputStream(), true);

        // Create reader to receive messages from server via the TCP-socket
        IN = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Start with an empty name, will be set during Login
        name = "";
        clientNames.clear();
        robotFigureAllClients.clear();
        readyClients.clear();

        CHATHISTORY.set("");
        INFORMATION.set("");
        PLAYERSINSERVER.set("");
        PLAYERSWHOAREREADY.set("");

        // init MYREGISTER[]
        for (int i = 0; i < 5; i++) {
            MYREGISTER[i] = new SimpleStringProperty();
        }

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


        new Thread(() -> {
            try {
                String json;
                while (!socket.isClosed()) {
                    // Client socket waits for the input from the server
                    json = IN.readLine();
                    //if(!line.isEmpty()) { // NullPointerException
                    if (json != null) {
                        executeOrder(json);
                        logger.info("json from server: " + json + Thread.currentThread().getName());
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

        // IMPORTANT! ImplicitExit = false: when no GUI-Windows on, GUI-Thread will not exit.
        Platform.setImplicitExit(false);
        // start the login process
        LAUNCHER.launchLogin(this);

    }

    /**
     * Execute an order from the server by checking the order code and calling the correct method
     *
     * @throws IOException
     */
    public void executeOrder(String json) throws IOException {

        logger.info("by executeOrder " + Thread.currentThread().getName());
        Client client = this;
        String messageType = Protocol.readJsonMessageType(json);

        // IMPORTANT! ImplicitExit = false: when no GUI-Windows on, GUI-Thread will not exit.
        Platform.setImplicitExit(false);
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
                            if (errorMessage.equals("Version wrong! disconnected.")) {
                                socket.close();
                            }
                            if (errorMessage.equals("This figure exists already, choose again.")) {
                                //reLoggin();
                                LAUNCHER.launchLogin(client);
                            }
                            break;
                        case "HelloClient":
                            Protocol protocol = new Protocol("HelloServer", new HelloServerBody("CC", false, "Version 0.1"));
                            String js = Protocol.writeJson(protocol);
                            logger.info("protocol from Server: \n" + js);
                            OUT.println(js);
                            break;
                        case "Welcome":
                            logger.info(json + Thread.currentThread().getName());
                            int clientIDfromServer = Protocol.readJsonWelcomeBody(json).getClientID();
                            clientID = clientIDfromServer;
                            CLIENTIDASSTRINGPROPERTY.set("" + clientID);
                            logger.info(CLIENTIDASSTRINGPROPERTY.get());
                            logger.info("your clientID is: " + clientID);
                            break;
                        case "Alive":
                            String alive = Protocol.writeJson(new Protocol("Alive", null));
                            OUT.println(alive);
                            break;
                        case "PlayerAdded":
                            logger.info(json + Thread.currentThread().getName());
                            PlayerAddedBody playerAddedBody = Protocol.readJsonPlayerAdded(json);
                            int clientIDAdded = playerAddedBody.getClientID();
                            int figureAdded = playerAddedBody.getFigure();
                            String nameAdded = playerAddedBody.getName();

                            // not add infos twice
                            if (!clientNames.containsKey(clientIDAdded)) {

                                PLAYERSINSERVER.set(PLAYERSINSERVER.get() + clientIDAdded + "\n");
                                robotFigureAllClients.put(clientIDAdded, figureAdded);
                                clientNames.put(clientIDAdded, nameAdded);
                                logger.info(clientNames.get(clientIDAdded) + ": " + robotFigureAllClients.get(clientIDAdded));

                                // if the added player is self, then launch the chatAndGame window
                                if (clientIDAdded == clientID) {
                                    logger.info("flag launchen window");
                                    name = nameAdded;
                                    //goToChatGame();
                                    LAUNCHER.launchChat(client);
                                }
                            }
                            break;
                        case "PlayerStatus":
                            logger.info(json);
                            PlayerStatusBody playerStatusBody = Protocol.readJsonPlayerStatus(json);
                            int readyClientID = playerStatusBody.getClientID();
                            boolean isReady = playerStatusBody.isReady();
                            readyClients.put(readyClientID, isReady);
                            updateReadyStringProperty();
                            break;
                        case "SelectMap":
                            logger.info(json);
                            SelectMapBody selectMapBody = Protocol.readJsonSelectMap(json);
                            List<String> availableMaps = selectMapBody.getAvailableMaps();
                            INFORMATION.set("");
                            INFORMATION.set("Select a map from: " + availableMaps);
                            CANSELECTMAP.set(true);
                            break;
                        case "GameStarted":
                            GameStartedBody gameStartedBody = Protocol.readJsonGameStarted(json);
                            List<List<List<FeldObject>>> gameMap = gameStartedBody.getGameMap();
                            System.out.println("map size " + gameMap.size() + " : " + gameMap.get(0).size());
                            System.out.println((gameMap.get(0).get(0).get(0)).getIsOnBoard() + gameMap.get(0).get(0).get(0).getIsOnBoard() +gameMap.get(0).get(2).get(0).getOrientations());
                            initGameForClients();
                            break;
                        case "ActivePhase":
                            ActivePhaseBody activePhaseBody = Protocol.readJsonActivePhase(json);
                            int phase = activePhaseBody.getPhase();
                            String phaseString = "";
                            if (phase == 0) {
                                phaseString = "Aufbauphase";
                            } else if (phase == 1) {
                                phaseString = "Upgradephase";
                            } else if (phase == 2) {
                                phaseString = "Programmierphase";
                            } else {
                                phaseString = "Aktivierungsphase";
                            }
                            GAMEPHASE.set(phaseString);
                            break;
                        case "CurrentPlayer":
                            CurrentPlayerBody currentPlayerBody = Protocol.readJsonCurrentPlayer(json);
                            int currentID = currentPlayerBody.getClientID();
                            if (currentID == clientID) {
                                ISCURRENTPLAYER.set(true);
                                if (GAMEPHASE.get().equals("Aufbauphase")) {
                                    INFORMATION.set("");
                                    INFORMATION.set("You are in turn to set start point");
                                    CANSETSTARTPOINT.set(true);
                                } else if (GAMEPHASE.get().equals("Aktivierungsphase")) {
                                    INFORMATION.set("");
                                    INFORMATION.set("You are in turn to play next register card.");
                                    CANPLAYNEXTREGISTER.set(true);
                                }

                            } else {
                                INFORMATION.set("");
                                INFORMATION.set(currentID + " is in turn. Please wait.");
                            }
                            break;
                        case "StartingPointTaken":
                            StartingPointTakenBody startingPointTakenBody = Protocol.readJsonStartingPointTaken(json);
                            int clientWhoSetPoint = startingPointTakenBody.getClientID();
                            int clientX = startingPointTakenBody.getX();
                            int clientY = startingPointTakenBody.getY();

                            // store the start position
                            startPositionsAllClients.get(clientWhoSetPoint)[0] = clientX;
                            startPositionsAllClients.get(clientWhoSetPoint)[1] = clientX;

                            // set current position
                            CURRENTPOSITIONS.get(clientWhoSetPoint)[0].set(clientX);
                            CURRENTPOSITIONS.get(clientWhoSetPoint)[1].set(clientY);

                            logger.info("" + CURRENTPOSITIONS.get(clientWhoSetPoint));
                            break;
                        case "YourCards":
                            YourCardsBody yourCardsBody = Protocol.readJsonYourCards(json);
                            List<String> cardsInHand = yourCardsBody.getCardsInHand();
                            for (String card : cardsInHand) {
                                MYDRAWNCARDS.add(new SimpleStringProperty(card));
                            }
                            INFORMATION.set("");
                            INFORMATION.set("Begin programming!");
                            logger.info(MYDRAWNCARDS.toString());
                            break;
                        case "NotYourCards":
                            NotYourCardsBody notYourCardsBody = Protocol.readJsonNotYourCards(json);
                            int client = notYourCardsBody.getClientID();
                            int cardCount = notYourCardsBody.getCardsInHand();
                            logger.info(client + "have got " + cardCount + " cards");
                            break;
                        case "ShuffleCoding":
                            ShuffleCodingBody shuffleCodingBody = Protocol.readJsonShuffleCoding(json);
                            int clientShuffle = shuffleCodingBody.getClientID();
                            logger.info(clientShuffle + " is shuffling");
                            break;
                        case "CardSelected":
                            CardSelectedBody cardSelectedBody = Protocol.readJsonCardSelected(json);
                            int clientSelectedCard = cardSelectedBody.getClientID();
                            int registerSelected = cardSelectedBody.getRegister();
                            boolean filled = cardSelectedBody.isFilled();
                            // optional soon: in GUI verbinden
                            logger.info(clientSelectedCard + " has for register " + registerSelected + filled);
                            break;
                        case "TimerStarted":
                            ISTIMERON.set(true);
                            logger.info("timer is on");
                            break;
                        case "TimerEnded":
                            TimerEndedBody timerEndedBody = Protocol.readJsonTimerEnded(json);
                            List<Integer> clientIDs = timerEndedBody.getClientIDs();
                            logger.info(clientIDs + " did not finish.");
                            break;
                        case "CardsYouGotNow":
                            CardsYouGotNowBody cardsYouGotNowBody = Protocol.readJsonCardsYouGotNow(json);
                            List<String> cards = cardsYouGotNowBody.getCards();
                            for (int i = 0; i < 5; i++) {
                                MYREGISTER[i].set(cards.get(i));
                            }
                            break;
                        case "CurrentCards":
                            CurrentCardsBody currentCardsBody = Protocol.readJsonCurrentCards(json);
                            List<Register> currentRegistersAllClients = currentCardsBody.getCurrentRegistersAllClients();
                            for (Register rg : currentRegistersAllClients) {
                                logger.info(rg.getClientID() + " has for current register: " + rg.getCardName());
                            }
                            break;
                        case "CardPlayed":
                            CardPlayedBody cardPlayedBody = Protocol.readJsonCardPlayed(json);
                            int clientWhoPlayed = cardPlayedBody.getClientID();
                            String cardNamePlayed = cardPlayedBody.getCard();
                            logger.info("client " + clientWhoPlayed + " has played " + cardNamePlayed);
                            INFORMATION.set("");
                            INFORMATION.set("client " + clientWhoPlayed + " played " + cardNamePlayed);
                            break;
                        case "Reboot":
                            RebootBody rebootBody = Protocol.readJsonReboot(json);
                            int clientReboot = rebootBody.getClientID();
                            // set client to start point
                            int startX = startPositionsAllClients.get(clientReboot)[0];
                            int startY = startPositionsAllClients.get(clientReboot)[1];
                            CURRENTPOSITIONS.get(clientReboot)[0].set(startX);
                            CURRENTPOSITIONS.get(clientReboot)[1].set(startY);


                            // clear all my registers if I reboot
                            if (clientReboot == clientID) {
                                MYDRAWNCARDS.clear();
                                // soon in GUI
                                handleRebootDirection("right");
                            }
                            logger.info("client reboot to start point");
                            break;
                        case "GameFinished":
                            GameFinishedBody gameFinishedBody = Protocol.readJsonGameFinished(json);
                            int winner = gameFinishedBody.getClientID();
                            INFORMATION.set("");
                            INFORMATION.set("Game finished! The winner is: " + winner);
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
     * send message to a certain person
     *
     * @param to
     * @param message
     * @throws JsonProcessingException
     */
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

    /**
     * client set status, update HashMap readyClients in client- and server- class
     *
     * @throws JsonProcessingException
     */
    public void setReady() throws JsonProcessingException {
        readyClients.put(clientID, true);
        Protocol protocol = new Protocol("SetStatus", new SetStatusBody(true));
        String json = Protocol.writeJson(protocol);
        logger.info(json);
        OUT.println(json);
    }

    /**
     * client set status unready, HashMap update readyClients in client- and server- class
     *
     * @throws JsonProcessingException
     */
    public void setUnready() throws JsonProcessingException {
        readyClients.put(clientID, false);
        Protocol protocol = new Protocol("SetStatus", new SetStatusBody(false));
        String json = Protocol.writeJson(protocol);
        logger.info(json);
        OUT.println(json);
    }

    /**
     * update StringProperty playerWhoAreReady, which binds the Textarea in GUI
     */
    public void updateReadyStringProperty() {
        PLAYERSWHOAREREADY.set("");
        for (int clientIDEach : readyClients.keySet()) {
            if (readyClients.get(clientIDEach) == true) {
                PLAYERSWHOAREREADY.set(PLAYERSWHOAREREADY.get() + clientIDEach + "\n");
                // reset boolean canSelectMap to all false
                CANSELECTMAP.set(false);
                INFORMATION.set("");
            }
        }
    }

    /**
     * invoked by client, who selects a map
     *
     * @param mapName
     * @throws JsonProcessingException
     */
    public void handleMapSelected(String mapName) throws JsonProcessingException {
        Protocol protocol = new Protocol("MapSelected", new MapSelectedBody(mapName));
        String json = Protocol.writeJson(protocol);
        logger.info(json);
        OUT.println(json);
        CANSELECTMAP.set(false);
    }

    /**
     * can not init these info with constructor client(), because of the sequence of Application: init -> start -> over
     */
    public void initGameForClients() {

        for (int client : clientNames.keySet()) {
            // init CURRENTPOSITIONS
            SimpleIntegerProperty x = new SimpleIntegerProperty();
            SimpleIntegerProperty y = new SimpleIntegerProperty();
            IntegerProperty[] position = new IntegerProperty[2];
            position[0] = x;
            position[1] = y;
            CURRENTPOSITIONS.put(client, position);

            // init start positions of all clients
            int[] startPos = new int[2];
            startPositionsAllClients.put(client, startPos);
        }
    }

    public void rebuildMap() {
        //TODO: 3D-Boardelement-list convert to 2D-IntegerProperty-list
    }

    /**
     * set start point to inform the server
     *
     * @param x
     * @param y
     * @throws JsonProcessingException
     */
    public void setStartPoint(int x, int y) throws JsonProcessingException {
        Protocol protocol = new Protocol("SetStartingPoint", new SetStartingPointBody(x, y));
        String json = Protocol.writeJson(protocol);
        logger.info(json);
        OUT.println(json);
        // you are not current player any more and disable the set point button
        CANSETSTARTPOINT.set(false);
        ISCURRENTPLAYER.set(false);
        INFORMATION.set("");
    }

    /**
     * client set/clear register slot
     *
     * @param cardName
     * @param registerNum
     * @throws JsonProcessingException
     */
    public void setRegister(String cardName, int registerNum) throws JsonProcessingException {
        if (cardName != null) {
            Protocol protocol = new Protocol("SelectedCard", new SelectedCardBody(cardName, registerNum));
            String json = Protocol.writeJson(protocol);
            MYREGISTER[registerNum - 1].set(cardName);
            logger.info(json);
            OUT.println(json);
            CANCLICKFINISH.set(true);
        } else {
            Protocol protocol = new Protocol("SelectedCard", new SelectedCardBody(null, registerNum));
            String json = Protocol.writeJson(protocol);
            MYREGISTER[registerNum - 1].set("");
            logger.info(json);
            OUT.println(json);
            CANCLICKFINISH.set(true);
        }

    }

    /**
     * when one client finished selecting card for one register, tell is to server
     *
     * @throws JsonProcessingException
     */
    public void selectFinish() throws JsonProcessingException {
        Protocol protocol = new Protocol("SelectionFinished", new SelectionFinishedBody(clientID));
        String json = Protocol.writeJson(protocol);
        logger.info(json);
        CANCLICKFINISH.set(false);
        OUT.println(json);
    }

    /**
     * client plays one card in the current register
     *
     * @param cardName
     */
    public void playNextRegister(String cardName) throws JsonProcessingException {
        Protocol protocol = new Protocol("PlayCard", new PlayCardBody(cardName));
        String json = Protocol.writeJson(protocol);
        logger.info(json);
        OUT.println(json);
        CANPLAYNEXTREGISTER.set(false);
    }

    public void handleRebootDirection(String direction) throws JsonProcessingException {
        Protocol protocol = new Protocol("RebootDirection", new RebootDirectionBody(direction));
        String json = Protocol.writeJson(protocol);
        logger.info(json);
        OUT.println(json);
    }
}
