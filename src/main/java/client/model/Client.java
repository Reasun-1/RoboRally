package client.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import protocol.Protocol;
import protocol.submessagebody.*;
import server.feldobjects.FeldObject;
import server.feldobjects.Pit;
import server.game.Direction;
import server.game.Position;
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
    public int registerPointer = 0;
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
    // current positions of all clients
    private final HashMap<Integer, int[]> currentPositions = new HashMap<>();
    // current directions of all clients
    private final HashMap<Integer, Direction> currentDirections = new HashMap<>();
    // 3D-map for GUI
    private List<List<List<FeldObject>>> mapInGUI = new ArrayList<>();
    // store the map name
    private String mapName = null;
    // store the available startPoints for the maps(death trap is different)
    private HashSet<Position> avaibleStartsMaps = new HashSet<>();
    private HashSet<Position> avaibleStartsMapTrap = new HashSet<>();




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
    private final ListProperty<String> MYCARDS = new SimpleListProperty<>(FXCollections.observableArrayList());
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
    // flag property for listner to know that round is over
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
    private StringProperty energyCount = new SimpleStringProperty("5");
    // bind list to comboBox in ChatController
    private final ListProperty<String> MAPS = new SimpleListProperty<>(FXCollections.observableArrayList());
    // bind timer to ChatController
    private StringProperty timerScreen = new SimpleStringProperty();



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

    public ListProperty<String> MYCARDSProperty() {
        return MYCARDS;
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

    public IntegerProperty flagRoundOverProperty() {
        return flagRoundOver;
    }

    public IntegerProperty flagMapUpdateProperty() {
        return flagMapUpdate;
    }

    public List<List<List<FeldObject>>> getMapInGUI() {
        return mapInGUI;
    }

    public IntegerProperty flagMyFigureProperty() {
        return flagMyFigure;
    }

    public IntegerProperty flagPositionsProperty() {
        return flagPositions;
    }

    public IntegerProperty flagDirectionsProperty() {
        return flagDirections;
    }

    public HashMap<Integer, int[]> getCurrentPositions() {
        return currentPositions;
    }

    public HashMap<Integer, Direction> getCurrentDirections() {
        return currentDirections;
    }

    public IntegerProperty flagClearRegistersProperty() { return flagClearRegisters; }

    public IntegerProperty flagTimeOutProperty() { return flagTimeOut; }

    public IntegerProperty flagReplaceRegisterProperty() { return flagReplaceRegister; }

    public StringProperty energyCountProperty() { return energyCount; }

    public ListProperty<String> MAPSProperty() { return MAPS; }

    public ObservableList<String> getMAPS() { return MAPS.get(); }

    public StringProperty timerScreenProperty() { return timerScreen; }




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

        // init MYREGISTER[]
        for (int i = 0; i < 5; i++) {
            MYREGISTER[i] = new SimpleStringProperty("");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        launch(args);
    }

    /**
     * Stops the application on the client side
     */
    public void stop(){
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
                            ReceivedChatBody receivedChatBody = Protocol.readJsonReceivedChatBody(json);
                            String message = receivedChatBody.getMessage();
                            int fromClient = receivedChatBody.getFrom();
                            boolean priv = receivedChatBody.isPrivate();
                            if(priv){
                                CHATHISTORY.set(CHATHISTORY.get() + fromClient + " [private]: " + message + "\n");

                            }else{
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

                            for(String map : availableMaps){
                                MAPS.add(map);
                            }
                            System.out.println("in client "+MAPS);

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
                            } else if (phase == 1) {
                                phaseString = "Upgradephase";
                            } else if (phase == 2) {
                                phaseString = "Programmierphase";
                            } else {
                                phaseString = "Aktivierungsphase";
                                timerScreen.set("OFF");
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
                                }else if(GAMEPHASE.get().equals("Programmierphase")){
                                    INFORMATION.set("");
                                    INFORMATION.set("Begin programming!");
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

                            for (String card : cardsInHand) {
                                MYCARDS.add(card);
                            }

                            INFORMATION.set("");
                            INFORMATION.set("Begin programming!");

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
                            flagTimeOut.set(flagTimeOut.get()+1);
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
                                flagClearRegisters.set(flagClearRegisters.getValue()+1);
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
                            for(int clnNr : WindowLauncher.chatWindowStage.keySet()){
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
                            if(replacedCardClient == clientID){
                                MYREGISTER[replacedRegister].set(replacedCardName);
                                flagReplaceRegister.set(flagReplaceRegister.getValue()+1);
                                if(registerPointer == 0){
                                    registerPointer = 4;
                                }else{
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
                            for(int clientNum : clientNames.keySet()){
                                PLAYERSINSERVER.set(PLAYERSINSERVER.get() + clientNum + "\n");
                            }

                            // update GUI info for client who are ready
                            readyClients.remove(removedClient);
                            PLAYERSWHOAREREADY.set("");
                            for(int clN : readyClients.keySet()){
                                PLAYERSWHOAREREADY.set(PLAYERSWHOAREREADY.get() + clN + "\n");
                            }

                            break;
                        case "Energy":
                            EnergyBody energyBody = Protocol.readJsonEnergy(json);
                            int energyClient = energyBody.getClientID();
                            int addCubes = energyBody.getCount();
                            if(energyClient == clientID){
                                energyCount.set((Integer.valueOf(energyCount.getValue()) + addCubes)+"");
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
                //INFORMATION.set("");
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
            if(mapName.equals("Death Trap")){
                currentDirections.put(client, Direction.LEFT);
            }else{
                currentDirections.put(client, Direction.RIGHT);
            }
        }

        // init timer to off
        timerScreen.set("OFF");

        // inti available StartsPoints
        avaibleStartsMaps.add(new Position(1,1));
        avaibleStartsMaps.add(new Position(0,3));
        avaibleStartsMaps.add(new Position(1,4));
        avaibleStartsMaps.add(new Position(1,5));
        avaibleStartsMaps.add(new Position(0,6));
        avaibleStartsMaps.add(new Position(1,8));
        avaibleStartsMapTrap.add(new Position(11,1));
        avaibleStartsMapTrap.add(new Position(12,3));
        avaibleStartsMapTrap.add(new Position(11,4));
        avaibleStartsMapTrap.add(new Position(11,5));
        avaibleStartsMapTrap.add(new Position(12,6));
        avaibleStartsMapTrap.add(new Position(11,8));
    }

    /**
     * set start point to inform the server
     *
     * @param x
     * @param y
     * @throws JsonProcessingException
     */
    public void setStartPoint(int x, int y) throws IOException {
        boolean isAvailable = checkStartPointAvailable(x, y);
        if(isAvailable){
            Protocol protocol = new Protocol("SetStartingPoint", new SetStartingPointBody(x, y));
            String json = Protocol.writeJson(protocol);
            logger.info(json);
            OUT.println(json);
            // you are not current player any more and disable the set point button
            CANSETSTARTPOINT.set(false);
            ISCURRENTPLAYER.set(false);
            INFORMATION.set("");
        }else{
            LAUNCHER.launchError("Start point is not available, choose another one.");
        }
    }


    /**
     * if the start position is taken, remove it from hashset
     * @param x
     * @param y
     */
    public void removeStartPointsInHashSet(int x, int y){
        HashSet<Position> toRemove = new HashSet<>();
        if(mapName.equals("Death Trap")){
            for(Position p : avaibleStartsMapTrap){
                if(p.getX() == x && p.getY() == y){
                    toRemove.add(p);
                }
            }
            avaibleStartsMapTrap.removeAll(toRemove);
        }else{
            for(Position p : avaibleStartsMaps){
                if(p.getX() == x && p.getY() == y){
                    toRemove.add(p);
                }
            }
            avaibleStartsMaps.removeAll(toRemove);
        }
    }

    /**
     * check if the chosen start point is avaible
     * @param x
     * @param y
     * @return
     */
    public boolean checkStartPointAvailable(int x, int y){
        if(mapName.equals("Death Trap")){
            for(Position p : avaibleStartsMapTrap){
                if(p.getX() == x && p.getY() == y){
                    return true;
                }
            }
        }else{
            for(Position p : avaibleStartsMaps){
                if(p.getX() == x && p.getY() == y){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * client set/clear register slot
     *
     * @param cardName
     * @param registerNum
     * @throws JsonProcessingException
     */
    public void setRegister(String cardName, int registerNum) throws IOException {
        if (cardName != null) {
            if(registerNum == 1 && cardName.equals("Again")){
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
     * @throws JsonProcessingException
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
