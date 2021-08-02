package ai;

import ai.database.Simulator;
import ai.database.fieldelements.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import protocol.Protocol;
import protocol.submessagebody.*;
import server.game.Direction;
import server.game.Position;
import server.game.Register;
import server.network.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class AIWithDatabase extends Application {

    private static final Logger logger = Logger.getLogger(AILow.class.getName());
    // Launcher for new Windows
    private final WindowLauncherAI LAUNCHER = new WindowLauncherAI();
    // Socket for the TCP connection
    public volatile Socket socket;
    // Writer for outgoing messages
    private final PrintWriter OUT;
    // Reader for incoming messages
    private final BufferedReader IN;
    // Name can be chosen in the login process
    private String name;
    // unique client ID given from server, key between Game, Server, Client and GUI!!
    public int clientID;
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
    public final HashMap<Integer, int[]> currentPositions = new HashMap<>();
    // current directions of all clients
    public final HashMap<Integer, Direction> currentDirections = new HashMap<>();
    // 3D-map for GUI
    private List<List<List<FeldObject>>> mapInGUI = new ArrayList<>();
    // store the map name
    private String mapName = null;
    // for the listner in update shop
    public List<String> availableUpgradesCards = new ArrayList<>();
    // store the available startPoints for the maps(death trap is different)
    private HashSet<Position> availableStartsMaps = new HashSet<>();
    private HashSet<Position> availableStartsMapTrap = new HashSet<>();

    // storage for moving checkpoints: key=checkpointNr. value=int[x,y]
    public HashMap<Integer, int[]> movingCheckpoints = new HashMap<>();

    public List<String> myCards = new ArrayList<>();
    private String[] myRegisters = new String[5];
    private String activePhase = null;
    private int numRobot = 1;
    private int energyCount = 12;

    // check for game already running
    public boolean isGameOn = false;

    // protocol version for login
    public String versionProtocol = "";

    /**
     * Sets active phase.
     *
     * @param activePhase the active phase
     */
    public void setActivePhase(String activePhase) {
        this.activePhase = activePhase;
    }

    /**
     * Instantiates a new AI.
     *
     * @throws IOException the io exception
     */
// constructor for initializing KI
    public AIWithDatabase() throws Exception {

        // Always connect to localhost and fixed port
        //socket = new Socket("127.0.0.1", 5200);
        // test server
        //socket = new Socket("sep21.dbs.ifi.lmu.de", 52021);

        LAUNCHER.launchSocketAI(this);

        // Create writer to send messages to server via the TCP-socket
        OUT = new PrintWriter(socket.getOutputStream(), true);

        // Create reader to receive messages from server via the TCP-socket
        IN = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Start with an empty name, will be set during Login
        name = "";
        clientNames.clear();
        robotFigureAllClients.clear();
        readyClients.clear();
    }


    @Override
    public void start(Stage stage) {
        try {
            String json;
            while (!socket.isClosed()) {
                // Client socket waits for the input from the server
                json = IN.readLine();
                //if(!line.isEmpty()) { // NullPointerException
                if (json != null) {

                    if (Protocol.readJsonMessageType(json).equals("GameOn")) {
                        logger.info("game on from server thread : " + json);
                        isGameOn = true;
                        socket.close();
                    } else {
                        executeOrder(json);
                        if (!Protocol.readJsonMessageType(json).equals("Alive")) {
                            logger.info("json from server: " + json + Thread.currentThread().getName());
                        }
                    }
                }
            }
            Platform.exit();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            try {
                socket.close();
                Platform.exit();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Execute an order from the server by checking the order code and calling the correct method
     *
     * @param json the json
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     * @throws InterruptedException   the interrupted exception
     */
    public void executeOrder(String json) throws IOException, ClassNotFoundException, InterruptedException {

        String messageType = Protocol.readJsonMessageType(json);


        switch (messageType) {
            case "Quit": // terminate the connection
                socket.close();
                break;
            case "ReceivedChat":
                logger.info("received chat printed");
                ReceivedChatBody receivedChatBody = Protocol.readJsonReceivedChatBody(json);
                String message = receivedChatBody.getMessage();
                int fromClient = receivedChatBody.getFrom();
                if (fromClient != clientID) {
                    sendPersonalMessage(fromClient, "I am an AI, can not talk yet.");
                }
                break;
            case "Error":
                logger.info("error printed");
                String errorMessage = Protocol.readJsonErrorBody(json).getError();
                if (errorMessage.equals("Version wrong! disconnected.")) {
                    socket.close();
                }
                if (errorMessage.equals("Figure is already taken")) {
                    numRobot++;
                    setPlayerValues("AI", numRobot);
                }
                break;
            case "HelloClient":
                Protocol protocol = new Protocol("HelloServer", new HelloServerBody("CC", true, versionProtocol));
                String js = Protocol.writeJson(protocol);
                logger.info("protocol from Server: \n" + js);
                if (!isGameOn) {
                    OUT.println(js);
                }
                break;
            case "Welcome":
                logger.info(json + Thread.currentThread().getName());
                int clientIDfromServer = Protocol.readJsonWelcomeBody(json).getClientID();
                clientID = clientIDfromServer;
                System.out.println("check server robot list " + Server.clientIDUndRobots.size());
                System.out.println("check server has robot " + Server.clientIDUndRobots.containsValue(1));
                // set a available robot to AI
                setPlayerValues("AI", numRobot);
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

                    logger.info(clientNames.get(clientIDAdded) + ": " + robotFigureAllClients.get(clientIDAdded));
                    // update clientsFigure list and clients list
                    robotFigureAllClients.put(clientIDAdded, figureAdded);
                    clientNames.put(clientIDAdded, nameAdded);

                    // if the added player is self, then launch the chatAndGame window
                    if (clientIDAdded == clientID) {
                        name = nameAdded;
                        setReady();
                    }
                }
                break;
            case "PlayerStatus":
                logger.info(json);
                PlayerStatusBody playerStatusBody = Protocol.readJsonPlayerStatus(json);
                int readyClientID = playerStatusBody.getClientID();
                boolean isReady = playerStatusBody.isReady();
                readyClients.put(readyClientID, isReady);
                break;
            case "SelectMap":
                logger.info(json);
                SelectMapBody selectMapBody = Protocol.readJsonSelectMap(json);
                List<String> availableMaps = selectMapBody.getAvailableMaps();
                handleMapSelected("Dizzy Highway");
                break;
            case "MapSelected":
                MapSelectedBody mapSelectedBody = Protocol.readJsonMapSelected(json);
                String mapString = mapSelectedBody.getMap();
                mapName = mapString;
                break;
            case "GameStarted":
                GameStartedBody gameStartedBody = Protocol.readJsonGameStarted(json);
                List<List<List<server.feldobjects.FeldObject>>> gameMap = gameStartedBody.getGameMap();

                Simulator.board = changeFeldObjectMapToAIMap(gameMap);
                logger.info("new board deep copy: " + Simulator.board);

                Simulator.getInstance().findCheckpoint();
                Simulator.getInstance().findPit();

                // update flag for MapUpdate, so that viewController can listen
                logger.info("map size " + gameMap.size() + " : " + gameMap.get(0).size());
                logger.info((gameMap.get(0).get(0).get(0)).getClass().getSimpleName() + gameMap.get(0).get(0).get(0).getIsOnBoard() + gameMap.get(0).get(2).get(0).getOrientations());
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
                setActivePhase(phaseString);
                break;
            case "CurrentPlayer":
                CurrentPlayerBody currentPlayerBody = Protocol.readJsonCurrentPlayer(json);
                int currentID = currentPlayerBody.getClientID();
                if (currentID == clientID) {
                    if (activePhase.equals("Aufbauphase")) {
                        if (mapName.equals("Death Trap")) {
                            setStartPoint(11, 8);
                        } else {
                            setStartPoint(1, 8);
                        }

                    } else if (activePhase.equals("Aktivierungsphase")) {
                        String cardName = myRegisters[registerPointer];
                        playNextRegister(cardName);
                        registerPointer++;
                        logger.info("registerpointer " + registerPointer);
                        // if round over, reset register pointer to 0 for next round
                        if (registerPointer == 5) {
                            myCards.clear();
                            for (int i = 0; i < 5; i++) {
                                myRegisters[i] = "";
                            }
                            logger.info("round over");
                            registerPointer = 0;
                        }
                    } else if (activePhase.equals("Upgradephase")) {
                        handleBuyUpgrade();
                    }

                } else {

                }
                break;
            case "StartingPointTaken":
                StartingPointTakenBody startingPointTakenBody = Protocol.readJsonStartingPointTaken(json);
                int clientWhoSetPoint = startingPointTakenBody.getClientID();
                int clientX = startingPointTakenBody.getX();
                int clientY = startingPointTakenBody.getY();

                removeStartPointsInHashSet(clientX, clientY);

                // store the start position
                startPositionsAllClients.get(clientWhoSetPoint)[0] = clientX;
                startPositionsAllClients.get(clientWhoSetPoint)[1] = clientX;
                // set current position
                currentPositions.get(clientWhoSetPoint)[0] = clientX;
                currentPositions.get(clientWhoSetPoint)[1] = clientY;

                logger.info("" + currentPositions.get(clientWhoSetPoint)[0]);
                break;
            case "YourCards":
                logger.info("clients your cards");
                YourCardsBody yourCardsBody = Protocol.readJsonYourCards(json);
                List<String> cardsInHand = yourCardsBody.getCardsInHand();
                // storage the cards in myCards list
                for (String card : cardsInHand) {
                    myCards.add(card);
                }
                selectAndSetRegisterFinish();
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
                logger.info(clientSelectedCard + " has for register " + registerSelected + filled);
                break;
            case "TimerStarted":
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
                    myRegisters[i] = cards.get(i);
                }
                break;
            case "CurrentCards":
                CurrentCardsBody currentCardsBody = Protocol.readJsonCurrentCards(json);
                List<Register> currentRegistersAllClients = currentCardsBody.getCurrentRegistersAllClients();
                /*for (Register rg : currentRegistersAllClients) {
                    logger.info(rg.getClientID() + " has for current register: " + rg.getCardName());
                }

                 */
                break;
            case "CardPlayed":
                CardPlayedBody cardPlayedBody = Protocol.readJsonCardPlayed(json);
                int clientWhoPlayed = cardPlayedBody.getClientID();
                String cardNamePlayed = cardPlayedBody.getCard();
                logger.info("client " + clientWhoPlayed + " has played " + cardNamePlayed);
                break;
            case "Reboot":
                RebootBody rebootBody = Protocol.readJsonReboot(json);
                int clientReboot = rebootBody.getClientID();

                // clear all my registers if I reboot
                if (clientReboot == clientID) {
                    myCards.clear();
                    for (int i = 0; i < 5; i++) {
                        myRegisters[i] = "";
                    }
                    registerPointer = 0;
                }
                logger.info("client reboot to start point");
                break;
            case "GameFinished":
                GameFinishedBody gameFinishedBody = Protocol.readJsonGameFinished(json);
                int winner = gameFinishedBody.getClientID();
                logger.info("Game is finished, winner is: " + winner + ".");
                break;
            case "Movement":
                MovementBody movementBody = Protocol.readJsonMovement(json);
                int movedClient = movementBody.getClientID();
                int toX = movementBody.getX();
                int toY = movementBody.getY();
                currentPositions.get(movedClient)[0] = toX;
                currentPositions.get(movedClient)[1] = toY;

                if(movedClient == clientID){
                    Simulator.curPosition = new Position(toX, toY);
                }
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
                currentDirections.put(turnedClient, newDir);

                if(turnedClient == clientID){
                    Simulator.curDirection = newDir;
                }
                break;
            case "ReplaceCard":
                ReplaceCardBody replaceCardBody = Protocol.readJsonReplaceCard(json);
                int replacedCardClient = replaceCardBody.getClientID();
                int replacedRegister = replaceCardBody.getRegister();
                String replacedCardName = replaceCardBody.getNewCard();
                if (replacedCardClient == clientID) {
                    myRegisters[replacedRegister] = replacedCardName;
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

                clientNames.remove(removedClient);
                readyClients.remove(removedClient);
                break;
            case "Energy":
                EnergyBody energyBody = Protocol.readJsonEnergy(json);
                int energyClient = energyBody.getClientID();
                int addCubes = energyBody.getCount();
                if (energyClient == clientID) {
                    energyCount = energyCount + addCubes;
                }
                break;
            case "RefillShop":
                RefillShopBody refillShopBody = Protocol.readJsonRefillShop(json);
                List<String> upCards = refillShopBody.getCards();
                availableUpgradesCards.clear();
                for (String upCard : upCards) {
                    availableUpgradesCards.add(upCard);
                }
                break;
            case "ExchangeShop":
                RefillShopBody exchangeShopBody = Protocol.readJsonRefillShop(json);
                List<String> upgradesCards = exchangeShopBody.getCards();
                availableUpgradesCards.clear();
                for (String upCard : upgradesCards) {
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
                break;
        }
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
        Protocol protocol = null;
        if (isAvailable) {
            protocol = new Protocol("SetStartingPoint", new SetStartingPointBody(x, y));
        } else {
            int newX = 0;
            int newY = 0;
            if (mapName.equals("Death Trap")) {
                for (Position pos : availableStartsMapTrap) {
                    newX = pos.getX();
                    newY = pos.getY();
                }
            } else {
                for (Position po : availableStartsMaps) {
                    newX = po.getX();
                    newY = po.getY();
                }
            }
            protocol = new Protocol("SetStartingPoint", new SetStartingPointBody(newX, newY));
        }
        String json = Protocol.writeJson(protocol);
        logger.info(json);
        OUT.println(json);
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
     * @throws JsonProcessingException the json processing exception
     */
    public void setRegister(String cardName, int registerNum) throws JsonProcessingException {

        if (cardName != null) {
            Protocol protocol = new Protocol("SelectedCard", new SelectedCardBody(cardName, registerNum));
            String json = Protocol.writeJson(protocol);
            myRegisters[registerNum - 1] = cardName;
            logger.info(json);
            OUT.println(json);

        } else {
            Protocol protocol = new Protocol("SelectedCard", new SelectedCardBody(null, registerNum));
            String json = Protocol.writeJson(protocol);
            myRegisters[registerNum - 1] = "";
            logger.info(json);
            OUT.println(json);
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
     * Select and set register finish.
     *
     * @throws JsonProcessingException the json processing exception
     */
    public void selectAndSetRegisterFinish() throws JsonProcessingException {

        if (myCards.size() == 9) {
            // random first 5 cards for registers
            /*for (int i = 0; i < 5; i++) {
                // first register card can not be Again
                if (i == 0 && myCards.get(0).equals("Again")) {
                    setRegister(myCards.get(6), 1);
                } else {
                    setRegister(myCards.get(i), i + 1);
                }
            }

             */


            List<String> best5Cards = Simulator.getInstance().findBest5Cards(myCards, this);

            for (int i = 0; i < 5; i++) {
                setRegister(best5Cards.get(i), i + 1);
            }

            logger.info("AI best 5 cards are : " + best5Cards);
            selectFinish();
        }
    }

    /**
     * ai buys no upgrade card
     *
     * @throws JsonProcessingException the json processing exception
     */
    public void handleBuyUpgrade() throws JsonProcessingException {

        Protocol protocol = new Protocol("BuyUpgrade", new BuyUpgradeBody(false, null));
        String json = Protocol.writeJson(protocol);
        logger.info(json);
        OUT.println(json);

    }

    public List<List<List<ai.database.fieldelements.FeldObject>>> changeFeldObjectMapToAIMap(List<List<List<server.feldobjects.FeldObject>>> oriMap){

        List<List<List<ai.database.fieldelements.FeldObject>>> aiBoard = new ArrayList<>();

        int rowSize = oriMap.size();
        int columnSize = oriMap.get(0).size();

        for (int i = 0; i < rowSize; i++) {

            List<List<ai.database.fieldelements.FeldObject>> newcolumns = new ArrayList<>();

            for (int j = 0; j < columnSize; j++) {

                List<ai.database.fieldelements.FeldObject> newObjs = new ArrayList<>();

                List<server.feldobjects.FeldObject> feldObjects = oriMap.get(i).get(j);
                for(server.feldobjects.FeldObject object : feldObjects){

                    ai.database.fieldelements.FeldObject newObj = null;

                    switch (object.getClass().getSimpleName()){
                        case "Antenna":
                            newObj = new Antenna(object.getIsOnBoard(), object.getOrientations());
                            break;
                        case "CheckPoint":
                            newObj = new CheckPoint(object.getIsOnBoard(), object.getCount());
                            break;
                        case "ConveyorBelt":
                            newObj = new ConveyorBelt(object.getIsOnBoard(), object.getSpeed(), object.getOrientations());
                            break;
                        case "Empty":
                            newObj = new Empty(object.getIsOnBoard());
                            break;
                        case "EnergySpace":
                            newObj = new EnergySpace(object.getIsOnBoard(), object.getCount());
                            break;
                        case "Gear":
                            newObj = new Gear(object.getIsOnBoard(), object.getOrientations());
                            break;
                        case "Laser":
                            newObj = new Laser(object.getIsOnBoard(), object.getOrientations(), object.getCount());
                            break;
                        case "Pit":
                            newObj = new Pit(object.getIsOnBoard());
                            break;
                        case "PushPanel":
                            newObj = new PushPanel(object.getIsOnBoard(), object.getOrientations(), object.getRegisters());
                            break;
                        case "RestartPoint":
                            newObj = new RestartPoint(object.getIsOnBoard(), object.getOrientations());
                            break;
                        case "StartPoint":
                            newObj = new StartPoint(object.getIsOnBoard());
                            break;
                        case "Wall":
                            newObj = new Wall(object.getIsOnBoard(), object.getOrientations());
                            break;
                    }
                    newObjs.add(newObj);
                }

                newcolumns.add(newObjs);
            }

            aiBoard.add(newcolumns);
        }

        return aiBoard;
    }

}
