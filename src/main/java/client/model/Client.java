package client.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import protocol.Protocol;
import protocol.submessagebody.*;
import server.feldobjects.FeldObject;
import server.game.Direction;
import server.game.Position;
import server.game.Register;

import java.io.*;
import java.net.Socket;
import java.util.*;
//import java.util.logging.Logger;

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
    public int registerPointer = 0;
    // client list with all clientIDs
    private List<Integer> clientsList;

    // map: key = clientID, value = robotfigure;
    public HashMap<Integer, Integer> robotFigureAllClients = new HashMap<>();
    // map : key = clientID, value = name;
    private HashMap<Integer, String> clientNames = new HashMap<>();
    // map : key = clientID, value = isReady
    private LinkedHashMap<Integer, Boolean> readyClients = new LinkedHashMap<>();
    // storage of my start positions for all clients: key=clientID, value = [x,y]
    private final HashMap<Integer, int[]> startPositionsAllClients = new HashMap<>();
    // current positions of all clients
    private final HashMap<Integer, int[]> currentPositions = new HashMap<>();
    // current directions of all clients
    private final HashMap<Integer, Direction> currentDirections = new HashMap<>();
    // 3D-map for GUI
    private List<List<List<FeldObject>>> mapInGUI = new ArrayList<>();

    // store the map name
    public String mapName = null;
    // store the available startPoints for the maps(death trap is different)
    private HashSet<Position> availableStartsMaps = new HashSet<>();
    private HashSet<Position> availableStartsMapTrap = new HashSet<>();

    // key=robotNum, value=robotName
    public HashMap<Integer, String> robotNumAndNames = new HashMap<>();

    // for the listner in update shop
    public List<String> availableUpgradesCards = new ArrayList<>();

    // for the listner in Chat&Game: key=CardName value=count
    public HashMap<String, Integer> myUpgradesCards = new HashMap<>();

    // storage for the cards in memorySwapWindow
    public List<String> cards12ForMemory = new ArrayList<>();

    // storage for moving checkpoints: key=checkpointNr. value=int[x,y]
    public HashMap<Integer, int[]> movingCheckpoints = new HashMap<>();


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
    public final StringProperty GAMEPHASE = new SimpleStringProperty();
    // who is in the first place of ready list, is allowed to select a map
    private final BooleanProperty CANSELECTMAP = new SimpleBooleanProperty(false);
    // player who is in turn
    private final BooleanProperty ISCURRENTPLAYER = new SimpleBooleanProperty(false);

    // player who can set start point, binds with selectStartPoint button in GUI
    public final BooleanProperty CANSETSTARTPOINT = new SimpleBooleanProperty(false);

    // binds with drawnCards in GUI
    public final ListProperty<String> MYCARDS = new SimpleListProperty<>(FXCollections.observableArrayList());
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
    // flag property for listener to know that round is over
    private IntegerProperty flagRoundOver = new SimpleIntegerProperty(0);
    // flag property for map-updating
    private IntegerProperty flagMapUpdate = new SimpleIntegerProperty(0);
    // flag property for myFigure
    private IntegerProperty flagMyFigure = new SimpleIntegerProperty(0);
    // flag property for positionsAllRobots
    private IntegerProperty flagPositions = new SimpleIntegerProperty(0);
    // flag property for directions all robots
    private IntegerProperty flagDirections = new SimpleIntegerProperty(0);
    // flag for clearing registers in GUI
    private IntegerProperty flagClearRegisters = new SimpleIntegerProperty(0);
    // flag for time out for update resgisters
    private IntegerProperty flagTimeOut = new SimpleIntegerProperty(0);
    // flag for replace card
    private IntegerProperty flagReplaceRegister = new SimpleIntegerProperty(0);
    // count of energy cubes
    public StringProperty energyCount = new SimpleStringProperty("5");
    // bind list to comboBox in ChatController
    private final ListProperty<String> MAPS = new SimpleListProperty<>(FXCollections.observableArrayList());
    // bind list to sendTo comboBox in ChatController
    private final ListProperty<String> ROBOTSNAMESFORCHAT = new SimpleListProperty<>(FXCollections.observableArrayList());
    // bind timer to ChatController
    private StringProperty timerScreen = new SimpleStringProperty();
    // bind flag in window upgrade shop
    private IntegerProperty flagRefreshUpdateSop = new SimpleIntegerProperty(0);

    // bind flag in Chat&Game for my upgrade cards
    public IntegerProperty flagMyUpgrades = new SimpleIntegerProperty(0);

    // flag update enable/disable the upgrades button Admin in Chat&Game
    public IntegerProperty flagAdmin = new SimpleIntegerProperty(0);

    // flag update enable/disable the upgrades button Memory in Chat&Game
    public IntegerProperty flagMemory = new SimpleIntegerProperty(0);

    // flag update enable/disable the upgrades button Blocker in Chat&Game
    public IntegerProperty flagBlocker = new SimpleIntegerProperty(0);

    // flag update cards in MemorySwapWindow
    public IntegerProperty flagCardsInMemorySwapWindow = new SimpleIntegerProperty(0);

    // flag update moving checkpoint
    public IntegerProperty flagMovingCheckpoints = new SimpleIntegerProperty(0);

    // Getters

    /**
     * Gets socket.
     *
     * @return the socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * Gets Input.
     *
     * @return the in
     */
    public BufferedReader getIn() {
        return IN;
    }

    /**
     * Gets Output.
     *
     * @return the out
     */
    public PrintWriter getOut() {
        return OUT;
    }

    /**
     * Gets ClientID.
     *
     * @return the client id
     */
    public int getClientID() {
        return clientID;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets chat history.
     *
     * @return the chat history
     */
    public StringProperty getChatHistory() {
        return CHATHISTORY;
    }

    /**
     * Playersinserver property string property.
     *
     * @return the string property
     */
    public StringProperty PLAYERSINSERVERProperty() {
        return PLAYERSINSERVER;
    }

    /**
     * Playerswhoareready property string property.
     *
     * @return the string property
     */
    public StringProperty PLAYERSWHOAREREADYProperty() {
        return PLAYERSWHOAREREADY;
    }

    /**
     * Gets clientidasstringproperty.
     *
     * @return the clientidasstringproperty
     */
    public StringProperty getCLIENTIDASSTRINGPROPERTY() {
        return CLIENTIDASSTRINGPROPERTY;
    }

    /**
     * Gets robot figure all clients.
     *
     * @return the robot figure all clients
     */
    public HashMap<Integer, Integer> getRobotFigureAllClients() {
        return robotFigureAllClients;
    }

    /**
     * Gets client names.
     *
     * @return the client names
     */
    public HashMap<Integer, String> getClientNames() {
        return clientNames;
    }

    /**
     * Canselectmap property boolean property.
     *
     * @return the boolean property
     */
    public BooleanProperty CANSELECTMAPProperty() {
        return CANSELECTMAP;
    }

    /**
     * Information property string property.
     *
     * @return the string property
     */
    public StringProperty INFORMATIONProperty() {
        return INFORMATION;
    }

    /**
     * Iscurrentplayer property boolean property.
     *
     * @return the boolean property
     */
    public BooleanProperty ISCURRENTPLAYERProperty() {
        return ISCURRENTPLAYER;
    }

    /**
     * Gamephase property string property.
     *
     * @return the string property
     */
    public StringProperty GAMEPHASEProperty() {
        return GAMEPHASE;
    }

    /**
     * Cansetstartpoint property boolean property.
     *
     * @return the boolean property
     */
    public BooleanProperty CANSETSTARTPOINTProperty() {
        return CANSETSTARTPOINT;
    }

    /**
     * Mycards property list property.
     *
     * @return the list property
     */
    public ListProperty<String> MYCARDSProperty() {
        return MYCARDS;
    }

    /**
     * Get myregister string property [ ].
     *
     * @return the string property [ ]
     */
    public StringProperty[] getMYREGISTER() {
        return MYREGISTER;
    }

    /**
     * Canclickfinish property boolean property.
     *
     * @return the boolean property
     */
    public BooleanProperty CANCLICKFINISHProperty() {
        return CANCLICKFINISH;
    }

    /**
     * Canplaynextregister property boolean property.
     *
     * @return the boolean property
     */
    public BooleanProperty CANPLAYNEXTREGISTERProperty() {
        return CANPLAYNEXTREGISTER;
    }

    /**
     * Flag round over property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty flagRoundOverProperty() {
        return flagRoundOver;
    }

    /**
     * Flag map update property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty flagMapUpdateProperty() {
        return flagMapUpdate;
    }

    /**
     * Gets map in gui.
     *
     * @return the map in gui
     */
    public List<List<List<FeldObject>>> getMapInGUI() {
        return mapInGUI;
    }

    /**
     * Flag my figure property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty flagMyFigureProperty() {
        return flagMyFigure;
    }

    /**
     * Flag positions property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty flagPositionsProperty() {
        return flagPositions;
    }

    /**
     * Flag directions property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty flagDirectionsProperty() {
        return flagDirections;
    }

    /**
     * Gets current positions.
     *
     * @return the current positions
     */
    public HashMap<Integer, int[]> getCurrentPositions() {
        return currentPositions;
    }

    /**
     * Gets current directions.
     *
     * @return the current directions
     */
    public HashMap<Integer, Direction> getCurrentDirections() {
        return currentDirections;
    }

    /**
     * Flag clear registers property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty flagClearRegistersProperty() {
        return flagClearRegisters;
    }

    /**
     * Flag time out property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty flagTimeOutProperty() {
        return flagTimeOut;
    }

    /**
     * Flag replace register property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty flagReplaceRegisterProperty() {
        return flagReplaceRegister;
    }

    /**
     * Energy count property string property.
     *
     * @return the string property
     */
    public StringProperty energyCountProperty() {
        return energyCount;
    }

    /**
     * Maps property list property.
     *
     * @return the list property
     */
    public ListProperty<String> MAPSProperty() {
        return MAPS;
    }

    /**
     * Gets maps.
     *
     * @return the maps
     */
    public ObservableList<String> getMAPS() {
        return MAPS.get();
    }

    /**
     * Robotsnamesforchat property list property.
     *
     * @return the list property
     */
    public ListProperty<String> ROBOTSNAMESFORCHATProperty() {
        return ROBOTSNAMESFORCHAT;
    }

    /**
     * Gets robotsnamesforchat.
     *
     * @return the robotsnamesforchat
     */
    public ObservableList<String> getROBOTSNAMESFORCHAT() {
        return ROBOTSNAMESFORCHAT.get();
    }

    /**
     * Timer screen property string property.
     *
     * @return the string property
     */
    public StringProperty timerScreenProperty() {
        return timerScreen;
    }

    /**
     * Flag refresh update shop property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty flagRefreshUpdateShopProperty() {
        return flagRefreshUpdateSop;
    }

    /**
     * Flag my upgrades property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty flagMyUpgradesProperty() {
        return flagMyUpgrades;
    }

    /**
     * Flag admin property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty flagAdminProperty() {
        return flagAdmin;
    }

    /**
     * Flag memory property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty flagMemoryProperty() {
        return flagMemory;
    }

    /**
     * Flag blocker property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty flagBlockerProperty() {
        return flagBlocker;
    }

    /**
     * Flag cards in memory swap window property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty flagCardsInMemorySwapWindowProperty() {
        return flagCardsInMemorySwapWindow;
    }

    /**
     * Flag moving checkpoints property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty flagMovingCheckpointsProperty() {
        return flagMovingCheckpoints;
    }


    /**
     * Sets name.
     *
     * @param name the name
     */
// Setters
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets robot figure all clients.
     *
     * @param clientID the client id
     * @param figure   the figure
     */
    public void setRobotFigureAllClients(Integer clientID, Integer figure) {
        robotFigureAllClients.put(clientID, figure);
    }

    /**
     * Sets client names.
     *
     * @param clientID the client id
     * @param name     the name
     */
    public void setClientNames(Integer clientID, String name) {
        clientNames.put(clientID, name);
    }

    /**
     * Constructor establishes the TCP-Connection and initializes the remaining variables
     *
     * @throws IOException the io exception
     */
    public Client() throws IOException {

        // Always connect to localhost and fixed port
        socket = new Socket("127.0.0.1", 5200);
        // test server
        //socket = new Socket("sep21.dbs.ifi.lmu.de", 52018);

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

        // init nums and names of all the robots
        robotNumAndNames.put(1, "Hulk");
        robotNumAndNames.put(2, "Spinbot");
        robotNumAndNames.put(3, "Squashbot");
        robotNumAndNames.put(4, "Trundlebot");
        robotNumAndNames.put(5, "Twitch");
        robotNumAndNames.put(6, "Twonky");


        // init MYREGISTER[]
        for (int i = 0; i < 5; i++) {
            MYREGISTER[i] = new SimpleStringProperty("");
        }
    }

    /*
    public static void main(String[] args) throws InterruptedException {
        launch(args);
    }
     */

    /**
     * Stops the application on the client side
     */
    public void stop() {
        System.out.println("Application stop");
    }

    /**
     * Starts the application on the client side
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        Platform.setImplicitExit(false);

        new Thread(() -> {
            try {
                String json;
                while (!socket.isClosed()) {
                    // Client socket waits for the input from the server
                    json = IN.readLine();
                    //if(!line.isEmpty()) { // NullPointerException
                    if (json != null) {
                        if (Protocol.readJsonMessageType(json).equals("Alive")) {
                            String alive = Protocol.writeJson(new Protocol("Alive", null));
                            OUT.println(alive);
                            logger.info("json from server: " + json + Thread.currentThread().getName());
                            logger.info("==========client " + clientID + " sent alive checked back.===========");
                        } else {
                            executeOrder(json);
                            logger.info("json from server: " + json + Thread.currentThread().getName());
                        }
                    }
                }
                //Platform.exit();
            } catch (IOException e) {
                try {
                    socket.close();
                    //Platform.exit();
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
     * @param json the json
     * @throws IOException the io exception
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
                            ReceivedChatBody receivedChatBody = Protocol.readJsonReceivedChatBody(json);
                            String message = receivedChatBody.getMessage();
                            int fromClient = receivedChatBody.getFrom();
                            boolean priv = receivedChatBody.isPrivate();
                            if (priv) {
                                CHATHISTORY.set(CHATHISTORY.get() + fromClient + " [private]: " + message + "\n");

                            } else {
                                CHATHISTORY.set(CHATHISTORY.get() + fromClient + " [public]: " + message + "\n");
                            }
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
                            Protocol protocol = new Protocol("HelloServer", new HelloServerBody("CC", false, "Version 1.0"));
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
                            INFORMATION.set("");
                            INFORMATION.set("Are you ready to start?");
                            GAMEPHASE.set("Welcomephase");
                            break;
                        /*case "Alive":
                            String alive = Protocol.writeJson(new Protocol("Alive", null));
                            OUT.println(alive);
                            logger.info("==========client " + clientID + " sent alive checked back.===========");
                            break;

                         */
                        case "PlayerAdded":
                            logger.info(json + Thread.currentThread().getName());
                            PlayerAddedBody playerAddedBody = Protocol.readJsonPlayerAdded(json);
                            int clientIDAdded = playerAddedBody.getClientID();
                            int figureAdded = playerAddedBody.getFigure();
                            String nameAdded = playerAddedBody.getName();

                            // not add infos twice
                            if (!clientNames.containsKey(clientIDAdded)) {

                                PLAYERSINSERVER.set(PLAYERSINSERVER.get() + clientIDAdded + "\n");
                                logger.info(clientNames.get(clientIDAdded) + ": " + robotFigureAllClients.get(clientIDAdded));
                                // update clientsFigure list and clients list
                                robotFigureAllClients.put(clientIDAdded, figureAdded);
                                clientNames.put(clientIDAdded, nameAdded);

                                // if the added player is self, then launch the chatAndGame window
                                if (clientIDAdded == clientID) {
                                    logger.info("flag launchen window");
                                    name = nameAdded;

                                    //goToChatGame();
                                    LAUNCHER.launchChat(client);
                                    // update flag for listener
                                    flagMyFigure.set(flagMyFigure.getValue() + 1);
                                }

                                String robotNameAdded = robotNumAndNames.get(figureAdded);
                                ROBOTSNAMESFORCHAT.add(robotNameAdded);
                                System.out.println(ROBOTSNAMESFORCHAT);
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

                            for (String map : availableMaps) {
                                MAPS.add(map);
                            }
                            System.out.println("in client " + MAPS);

                            INFORMATION.set("");
                            INFORMATION.set("Select a map from: " + availableMaps);
                            CANSELECTMAP.set(true);
                            break;
                        case "MapSelected":
                            MapSelectedBody mapSelectedBody = Protocol.readJsonMapSelected(json);
                            String mapString = mapSelectedBody.getMap();
                            mapName = mapString;
                            break;
                        case "GameStarted":
                            GameStartedBody gameStartedBody = Protocol.readJsonGameStarted(json);
                            List<List<List<FeldObject>>> gameMap = gameStartedBody.getGameMap();
                            mapInGUI = gameMap;
                            // update flag for MapUpdate, so that viewController can listen
                            flagMapUpdate.set(flagMapUpdate.getValue() + 1);
                            System.out.println("map size " + gameMap.size() + " : " + gameMap.get(0).size());
                            System.out.println((gameMap.get(0).get(0).get(0)).getClass().getSimpleName() + gameMap.get(0).get(0).get(0).getIsOnBoard() + gameMap.get(0).get(2).get(0).getOrientations());
                            initGameForClients();
                            break;
                        case "ActivePhase":
                            ActivePhaseBody activePhaseBody = Protocol.readJsonActivePhase(json);
                            int phase = activePhaseBody.getPhase();
                            String phaseString = "";
                            if (phase == 0) {
                                phaseString = "Aufbauphase";
                                GAMEPHASE.set(phaseString);
                            } else if (phase == 1) {
                                phaseString = "Upgradephase";
                                GAMEPHASE.set(phaseString);
                            } else if (phase == 2) {
                                phaseString = "Programmierphase";
                                GAMEPHASE.set(phaseString);
                                //active admin button in Chat&Game
                                flagAdmin.set(flagAdmin.get() + 1);
                            } else {
                                phaseString = "Aktivierungsphase";
                                GAMEPHASE.set(phaseString);
                                timerScreen.set("OFF");
                            }

                            //update enable/disable status for buttons upgradeCards in Chat&Game
                            flagBlocker.set(flagBlocker.get() + 1);
                            flagMemory.set(flagMemory.get() + 1);
                            break;
                        case "CurrentPlayer":
                            CurrentPlayerBody currentPlayerBody = Protocol.readJsonCurrentPlayer(json);
                            int currentID = currentPlayerBody.getClientID();
                            if (currentID == clientID) {
                                ISCURRENTPLAYER.set(true);
                                if (GAMEPHASE.get().equals("Aufbauphase")) {
                                    INFORMATION.set("");
                                    INFORMATION.set("You are in turn to set start point (click in map)");
                                    CANSETSTARTPOINT.set(true);
                                } else if (GAMEPHASE.get().equals("Aktivierungsphase")) {
                                    INFORMATION.set("");
                                    INFORMATION.set("You are in turn to play next register card.");
                                    CANPLAYNEXTREGISTER.set(true);

                                } else if (GAMEPHASE.get().equals("Programmierphase")) {
                                    INFORMATION.set("");
                                    INFORMATION.set("Begin programming!");

                                } else if (GAMEPHASE.get().equals("Upgradephase")) {
                                    //===launch upgrade shop window====
                                    LAUNCHER.launchUpgradeShop(client);

                                    INFORMATION.set("");
                                    INFORMATION.set("Now you can purchase upgrade cards!");

                                    //System.out.println(availableUpgradesCards);

                                    flagRefreshUpdateSop.set(flagRefreshUpdateSop.get() + 1);

                                    //===only for test, will be deleted later=====
                                    /*
                                    if(availableUpgradesCards.contains("AdminPrivilege")){
                                        handleBuyUpgrade("AdminPrivilege");
                                    }else if(availableUpgradesCards.contains("MemorySwap")){
                                        handleBuyUpgrade("MemorySwap");
                                    }else if(availableUpgradesCards.contains("RealLaser")){
                                        handleBuyUpgrade("RealLaser");
                                    }else if(availableUpgradesCards.contains("SpamBlocker")){
                                        handleBuyUpgrade("SpamBlocker");
                                    }*/
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

                            // remove the point from hashset
                            removeStartPointsInHashSet(clientX, clientY);

                            // store the start position
                            startPositionsAllClients.get(clientWhoSetPoint)[0] = clientX;
                            startPositionsAllClients.get(clientWhoSetPoint)[1] = clientX;
                            // set current position
                            currentPositions.get(clientWhoSetPoint)[0] = clientX;
                            currentPositions.get(clientWhoSetPoint)[1] = clientY;

                            // update flag current positions
                            flagPositions.set(flagPositions.getValue() + 1);
                            //flagDirections.set(flagDirections.getValue()+1);

                            logger.info("" + currentPositions.get(clientWhoSetPoint)[0]);
                            break;
                        case "YourCards":
                            logger.info("clients your cards");
                            // GUI-Listner binds to flagRoundOver to reset GUI for new round
                            //flagRoundOver.set(flagRoundOver.getValue() + 1);
                            YourCardsBody yourCardsBody = Protocol.readJsonYourCards(json);
                            List<String> cardsInHand = yourCardsBody.getCardsInHand();

                            if (MYCARDS.size() == 9) {

                                for (String cardHad : MYCARDS.get()) {
                                    cards12ForMemory.add(cardHad);
                                }

                                for (String newCard : cardsInHand) {
                                    cards12ForMemory.add(newCard);
                                }

                                flagCardsInMemorySwapWindow.set(flagCardsInMemorySwapWindow.get() + 1);
                            }

                            for (String card : cardsInHand) {
                                MYCARDS.add(card);
                            }
                            System.out.println("print mycards in client: " + MYCARDS);

                            INFORMATION.set("");
                            INFORMATION.set("Begin programming!");

                            // activate buttons in Chat&Game
                            flagMemory.set(flagMemory.get() + 1);
                            flagBlocker.set(flagBlocker.get() + 1);

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
                            timerScreen.set("ON");
                            break;
                        case "TimerEnded":
                            TimerEndedBody timerEndedBody = Protocol.readJsonTimerEnded(json);
                            List<Integer> clientIDs = timerEndedBody.getClientIDs();
                            logger.info(clientIDs + " did not finish.");
                            timerScreen.set("OFF");
                            break;
                        case "CardsYouGotNow":
                            CardsYouGotNowBody cardsYouGotNowBody = Protocol.readJsonCardsYouGotNow(json);
                            List<String> cards = cardsYouGotNowBody.getCards();
                            for (int i = 0; i < 5; i++) {
                                MYREGISTER[i].set(cards.get(i));
                            }
                            if (MYREGISTER[0].equals("Again")) {
                                MYREGISTER[0].set("PowerUp");
                            }
                            flagTimeOut.set(flagTimeOut.get() + 1);
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

                            // clear all my registers if I reboot
                            if (clientReboot == clientID) {
                                // clear register cards in GUI
                                flagClearRegisters.set(flagClearRegisters.getValue() + 1);
                                INFORMATION.set("");
                                INFORMATION.set("You are rebooted, wait for next round.");
                                //handleRebootDirection("right");
                            }
                            logger.info("client reboot to start point");
                            break;
                        case "GameFinished":
                            GameFinishedBody gameFinishedBody = Protocol.readJsonGameFinished(json);
                            int winner = gameFinishedBody.getClientID();
                            INFORMATION.set("");
                            INFORMATION.set("Game finished! The winner is: " + winner);
                            LAUNCHER.launchGameFinished(winner);
                            for (int clnNr : WindowLauncher.chatWindowStage.keySet()) {
                                WindowLauncher.chatWindowStage.get(clnNr).close();
                            }
                            break;
                        case "Movement":
                            MovementBody movementBody = Protocol.readJsonMovement(json);
                            int movedClient = movementBody.getClientID();
                            int toX = movementBody.getX();
                            int toY = movementBody.getY();
                            currentPositions.get(movedClient)[0] = toX;
                            currentPositions.get(movedClient)[1] = toY;
                            flagPositions.set(flagPositionsProperty().getValue() + 1);
                            break;
                        case "PlayerTurning":
                            PlayerTurningBody playerTurningBody = Protocol.readJsonPlayerTurning(json);
                            int turnedClient = playerTurningBody.getClientID();
                            String turnDirection = playerTurningBody.getRotation();

                            // update client direction in client class
                            Direction curDir = currentDirections.get(turnedClient);
                            Direction newDir = null;
                            if (turnDirection.equals("clockwise")) {
                                newDir = Direction.turnClock(curDir);
                            } else if (turnDirection.equals("counterclockwise")) {
                                newDir = Direction.turnCounterClock(curDir);
                            }

                            // tell GUI-Listener about the update
                            currentDirections.put(turnedClient, newDir);
                            flagPositions.set(flagPositions.get() + 1);
                            break;
                        case "DrawDamage":
                            DrawDamageBody drawDamageBody = Protocol.readJsonDrawDamage(json);
                            int damagedClient = drawDamageBody.getClientID();
                            List<String> gottenDamageCards = drawDamageBody.getCards();
                            INFORMATION.set("");
                            INFORMATION.set("client " + damagedClient + " got " + gottenDamageCards);
                            break;
                        case "ReplaceCard":
                            ReplaceCardBody replaceCardBody = Protocol.readJsonReplaceCard(json);
                            int replacedCardClient = replaceCardBody.getClientID();
                            int replacedRegister = replaceCardBody.getRegister();
                            String replacedCardName = replaceCardBody.getNewCard();
                            if (replacedCardClient == clientID) {
                                MYREGISTER[replacedRegister].set(replacedCardName);
                                flagReplaceRegister.set(flagReplaceRegister.getValue() + 1);
                                if (registerPointer == 0) {
                                    registerPointer = 4;
                                } else {
                                    registerPointer--;
                                }

                            }
                            break;
                        case "ConnectionUpdate":
                            ConnectionUpdateBody connectionUpdateBody = Protocol.readJsonConnectionUpdate(json);
                            int removedClient = connectionUpdateBody.getClientID();
                            currentPositions.remove(removedClient);
                            flagPositions.set(flagPositions.get() + 1);

                            // update GUI info for client in server
                            clientNames.remove(removedClient);
                            PLAYERSINSERVER.set("");
                            for (int clientNum : clientNames.keySet()) {
                                PLAYERSINSERVER.set(PLAYERSINSERVER.get() + clientNum + "\n");
                            }

                            // update GUI robot list for Chat
                            int removedRobotNum = robotFigureAllClients.get(removedClient);
                            String removedRobotName = robotNumAndNames.get(removedRobotNum);
                            System.out.println("flag removedRobotName: " + removedRobotName);
                            System.out.println("flag before: " + ROBOTSNAMESFORCHAT);
                            ROBOTSNAMESFORCHAT.remove(removedRobotName);
                            System.out.println("flag after: " + ROBOTSNAMESFORCHAT);

                            // update GUI info for client who are ready
                            readyClients.remove(removedClient);
                            PLAYERSWHOAREREADY.set("");
                            for (int clN : readyClients.keySet()) {
                                PLAYERSWHOAREREADY.set(PLAYERSWHOAREREADY.get() + clN + "\n");
                            }

                            break;
                        case "Energy":
                            EnergyBody energyBody = Protocol.readJsonEnergy(json);
                            int energyClient = energyBody.getClientID();
                            int addCubes = energyBody.getCount();
                            if (energyClient == clientID) {
                                energyCount.set((Integer.valueOf(energyCount.getValue()) + addCubes) + "");
                            }
                            break;
                        case "RefillShop":
                            RefillShopBody refillShopBody = Protocol.readJsonRefillShop(json);
                            List<String> upCards = refillShopBody.getCards();
                            // clear last round cards in list of availableUpgradesCards
                            availableUpgradesCards.clear();
                            for (String upCard : upCards) {
                                availableUpgradesCards.add(upCard);
                            }
                            break;
                        case "UpgradeBought":
                            UpgradeBoughtBody upgradeBoughtBody = Protocol.readJsonUpgradeBought(json);
                            int clientWhoBought = upgradeBoughtBody.getClientID();
                            String upCardBought = upgradeBoughtBody.getCard();

                            if (upCardBought != null) {
                                availableUpgradesCards.remove(upCardBought);
                            }
                            break;
                        case "CheckpointMoved":
                            CheckpointMovedBody checkpointMovedBody = Protocol.readJsonCheckpointMoved(json);
                            int checkpointNr = checkpointMovedBody.getCheckpointID();
                            int locX = checkpointMovedBody.getX();
                            int locY = checkpointMovedBody.getY();
                            int[] location = new int[2];
                            location[0] = locX;
                            location[1] = locY;
                            movingCheckpoints.put(checkpointNr, location);
                            flagMovingCheckpoints.set(flagMovingCheckpoints.get() + 1);
                            break;
                        case "RegisterChosen":
                            RegisterChosenBody registerChosenBody = Protocol.readJsonRegisterChosen(json);
                            int chosenClient = registerChosenBody.getClientID();
                            int chosenBodyRegister = registerChosenBody.getRegister();
                            INFORMATION.set("");
                            INFORMATION.set("client " + chosenClient + " have played AdminPrivilege for Register " + chosenBodyRegister);
                            break;
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * send message to a certain person
     *
     * @param to      the to
     * @param message the message
     * @throws JsonProcessingException the json processing exception
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
     * @param message the message
     * @throws JsonProcessingException the json processing exception
     */
    public void sendMessage(String message) throws JsonProcessingException {
        // Check logout condition
        /*if (message.equals("bye")) {
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
         */
        // Send message to server
        Protocol protocol = new Protocol("SendChat", new SendChatBody(message, -1));
        String json = Protocol.writeJson(protocol);
        logger.info(json);
        OUT.println(json);
        //}
    }

    /**
     * give the client name and robot figure to server
     *
     * @param clientName  the client name
     * @param robotFigure the robot figure
     * @throws JsonProcessingException the json processing exception
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
     * @throws JsonProcessingException the json processing exception
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
     * @throws JsonProcessingException the json processing exception
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
                //INFORMATION.set("");
            }
        }
    }

    /**
     * invoked by client, who selects a map
     *
     * @param mapName the map name
     * @throws JsonProcessingException the json processing exception
     */
    public void handleMapSelected(String mapName) throws JsonProcessingException {
        Protocol protocol = new Protocol("MapSelected", new MapSelectedBody(mapName));
        String json = Protocol.writeJson(protocol);
        logger.info(json);
        OUT.println(json);
        CANSELECTMAP.set(false);
        INFORMATION.set("");
        INFORMATION.set("Not all players are ready, please wait.");
    }

    /**
     * can not init these info with constructor client(), because of the sequence of Application: init -> start -> over
     */
    public void initGameForClients() {

        for (int client : clientNames.keySet()) {

            // init start positions of all clients
            int[] startPos = new int[2];
            startPositionsAllClients.put(client, startPos);

            // init current positions
            int[] currentPo = new int[2];
            currentPositions.put(client, currentPo);

            // init curren directions
            if (mapName.equals("Death Trap")) {
                currentDirections.put(client, Direction.LEFT);
            } else {
                currentDirections.put(client, Direction.RIGHT);
            }
        }

        // init timer to off
        timerScreen.set("OFF");

        // inti available StartsPoints
        availableStartsMaps.add(new Position(1, 1));
        availableStartsMaps.add(new Position(0, 3));
        availableStartsMaps.add(new Position(1, 4));
        availableStartsMaps.add(new Position(1, 5));
        availableStartsMaps.add(new Position(0, 6));
        availableStartsMaps.add(new Position(1, 8));
        availableStartsMapTrap.add(new Position(11, 1));
        availableStartsMapTrap.add(new Position(12, 3));
        availableStartsMapTrap.add(new Position(11, 4));
        availableStartsMapTrap.add(new Position(11, 5));
        availableStartsMapTrap.add(new Position(12, 6));
        availableStartsMapTrap.add(new Position(11, 8));

        // init my upgrade card
        myUpgradesCards.put("AdminPrivilege", 0);
        myUpgradesCards.put("RealLaser", 0);
        myUpgradesCards.put("MemorySwap", 0);
        myUpgradesCards.put("SpamBlocker", 0);
    }

    /**
     * set start point to inform the server
     *
     * @param x the x
     * @param y the y
     * @throws IOException the io exception
     */
    public void setStartPoint(int x, int y) throws IOException {
        boolean isAvailable = checkStartPointAvailable(x, y);
        if (isAvailable) {
            Protocol protocol = new Protocol("SetStartingPoint", new SetStartingPointBody(x, y));
            String json = Protocol.writeJson(protocol);
            logger.info(json);
            OUT.println(json);
            // you are not current player any more and disable the set point button
            CANSETSTARTPOINT.set(false);
            ISCURRENTPLAYER.set(false);
            INFORMATION.set("");
        } else {
            LAUNCHER.launchError("Start point is not available, choose another one.");
        }
    }


    /**
     * if the start position is taken, remove it from hashset
     *
     * @param x the x
     * @param y the y
     */
    public void removeStartPointsInHashSet(int x, int y) {
        HashSet<Position> toRemove = new HashSet<>();
        if (mapName.equals("Death Trap")) {
            for (Position p : availableStartsMapTrap) {
                if (p.getX() == x && p.getY() == y) {
                    toRemove.add(p);
                }
            }
            availableStartsMapTrap.removeAll(toRemove);
        } else {
            for (Position p : availableStartsMaps) {
                if (p.getX() == x && p.getY() == y) {
                    toRemove.add(p);
                }
            }
            availableStartsMaps.removeAll(toRemove);
        }
    }

    /**
     * check if the chosen start point is available
     *
     * @param x the x
     * @param y the y
     * @return boolean
     */
    public boolean checkStartPointAvailable(int x, int y) {
        if (mapName.equals("Death Trap")) {
            for (Position p : availableStartsMapTrap) {
                if (p.getX() == x && p.getY() == y) {
                    return true;
                }
            }
        } else {
            for (Position p : availableStartsMaps) {
                if (p.getX() == x && p.getY() == y) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * client set/clear register slot
     *
     * @param cardName    the card name
     * @param registerNum the register num
     * @throws IOException the io exception
     */
    public void setRegister(String cardName, int registerNum) throws IOException {
        if (cardName != null) {
            if (registerNum == 1 && cardName.equals("Again")) {
                LAUNCHER.launchError("Card Again can not be set in the first Register. Choose another card.");
            }
        }

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
     * @throws JsonProcessingException the json processing exception
     */
    public void selectFinish() throws JsonProcessingException {
        Protocol protocol = new Protocol("SelectionFinished", new SelectionFinishedBody(clientID));
        String json = Protocol.writeJson(protocol);
        logger.info(json);
        CANCLICKFINISH.set(false);
        timerScreen.set("OFF");
        OUT.println(json);
    }

    /**
     * client plays one card in the current register
     *
     * @param cardName the card name
     * @throws JsonProcessingException the json processing exception
     */
    public void playNextRegister(String cardName) throws JsonProcessingException {
        Protocol protocol = new Protocol("PlayCard", new PlayCardBody(cardName));
        String json = Protocol.writeJson(protocol);
        logger.info(json);
        OUT.println(json);
        CANPLAYNEXTREGISTER.set(false);
    }

    /**
     * client plays upgrade card
     *
     * @param cardName the card name
     * @throws JsonProcessingException the json processing exception
     */
    public void playUpgrade(String cardName) throws JsonProcessingException {
        Protocol protocol = new Protocol("PlayCard", new PlayCardBody(cardName));
        String json = Protocol.writeJson(protocol);
        logger.info(json);
        OUT.println(json);
    }

    /**
     * Handle reboot direction.
     *
     * @param direction the direction
     * @throws JsonProcessingException the json processing exception
     */
    public void handleRebootDirection(String direction) throws JsonProcessingException {
        Protocol protocol = new Protocol("RebootDirection", new RebootDirectionBody(direction));
        String json = Protocol.writeJson(protocol);
        logger.info(json);
        OUT.println(json);
    }

    /**
     * client buys upgrade card
     *
     * @param upCardName the up card name
     * @throws JsonProcessingException the json processing exception
     */
    public void handleBuyUpgrade(String upCardName) throws JsonProcessingException {
        if (upCardName == null) {
            Protocol protocol = new Protocol("BuyUpgrade", new BuyUpgradeBody(false, null));
            String json = Protocol.writeJson(protocol);
            logger.info(json);
            OUT.println(json);

        } else {
            Protocol protocol = new Protocol("BuyUpgrade", new BuyUpgradeBody(true, upCardName));
            String json = Protocol.writeJson(protocol);
            logger.info(json);
            OUT.println(json);

            // update my upgrade cards in Chat&Game
            int curCount = myUpgradesCards.get(upCardName);
            myUpgradesCards.put(upCardName, curCount + 1);

            // update my energy cubes
            if (upCardName.equals("AdminPrivilege")) {
                energyCount.set((Integer.valueOf(energyCount.get()) - 3) + "");
            } else if (upCardName.equals("RealLaser")) {
                energyCount.set((Integer.valueOf(energyCount.get()) - 2) + "");
            } else if (upCardName.equals("MemorySwap")) {
                energyCount.set((Integer.valueOf(energyCount.get()) - 1) + "");
            } else if (upCardName.equals("SpamBlocker")) {
                energyCount.set((Integer.valueOf(energyCount.get()) - 3) + "");
            }

            // buy max.3 cards each type
            if (myUpgradesCards.get("AdminPrivilege") + myUpgradesCards.get("RealLaser") > 3) {
                if (myUpgradesCards.get("AdminPrivilege") > 0) {
                    myUpgradesCards.put("AdminPrivilege", myUpgradesCards.get("AdminPrivilege") - 1);
                } else if (myUpgradesCards.get("RealLaser") > 0) {
                    myUpgradesCards.put("RealLaser", myUpgradesCards.get("RealLaser") - 1);
                }
            } else if (myUpgradesCards.get("MemorySwap") + myUpgradesCards.get("SpamBlocker") > 3) {
                if (myUpgradesCards.get("SpamBlocker") > 0) {
                    myUpgradesCards.put("SpamBlocker", myUpgradesCards.get("SpamBlocker") - 1);
                } else if (myUpgradesCards.get("MemorySwap") > 0) {
                    myUpgradesCards.put("MemorySwap", myUpgradesCards.get("MemorySwap") - 1);
                }
            }


            flagMyUpgrades.set(flagMyUpgrades.get() + 1);
        }
    }

    /**
     * choose register for playing adminPrivilage
     *
     * @param registerNummer
     */
    public void handleChooseRegister(int registerNummer) throws JsonProcessingException {
        Protocol protocol = new Protocol("ChooseRegister", new ChooseRegisterBody(registerNummer));
        String json = Protocol.writeJson(protocol);
        logger.info(json);
        OUT.println(json);
    }
}
