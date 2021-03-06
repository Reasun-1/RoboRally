package protocol;

import org.apache.log4j.Logger;
import protocol.submessagebody.*;
import server.game.Direction;
import server.game.Game;
import server.game.Position;
import server.network.AliveCheck;
import server.network.Connected;
import server.network.Server;
import server.registercards.*;
import server.upgradecards.*;

import java.io.IOException;
import java.util.HashMap;


/**
 * this class is specially for decoding json message and execute the order in json
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
public class ExecuteOrder {

    private static final Logger logger = Logger.getLogger(ExecuteOrder.class.getName());
    /**
     * The constant connectList.
     */
    public static HashMap<Integer, Connected> connectList = new HashMap<>();
    /**
     * The constant aliveCheckList.
     */
    public static HashMap<Integer, AliveCheck> aliveCheckList = new HashMap<>();
    /**
     * The constant clientIDOfAI.
     */
    public static int clientIDOfAI = 0;
    /**
     * The constant activePhase.
     */
    public static int activePhase = 0;
    /**
     * The constant allplayersRebooted.
     */
    public static boolean allplayersRebooted = false;
    /**
     * Check whether game is on
     */
    public static boolean isGameOn = false;


    /**
     * Execute order.
     *
     * @param clientID the client id
     * @param json     the json
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    public static void executeOrder(int clientID, String json) throws IOException, ClassNotFoundException {

        String messageType = Protocol.readJsonMessageType(json);
        switch (messageType) {
            case "Quit": // terminate the connection
                logger.info(clientID + " quit.");
                Server.clientList.get(clientID).closeConnect();
                break;

            case "PlayerValues":
                logger.info("check player figures");
                PlayerValuesBody playerValuesBody = Protocol.readJsonPlayerValues(json);
                int figureNumber = playerValuesBody.getFigure();
                String clientName = playerValuesBody.getName();

                if (Server.clientIDUndRobots.containsValue(figureNumber)) {
                    Server.getServer().exception(clientID, "This figure exists already, choose again.");
                } else {
                    logger.info("addPlayer");
                    Server.clientIDUndRobots.put(clientID, figureNumber);
                    Server.clientIDUndNames.put(clientID, clientName);
                    // inform all existed players that one new play war added
                    Server.getServer().handlePlayerAddedToAll(clientID, clientName, figureNumber);


                    if (Server.clientIDUndNames.size() != 1) {
                        // send infos of previous players also to current player
                        for (int clientIDEach : Server.clientIDUndNames.keySet()) {
                            String nameEach = Server.clientIDUndNames.get(clientIDEach);
                            int figureEach = Server.clientIDUndRobots.get(clientIDEach);
                            Server.getServer().handlePlayerAddedToOne(clientID, clientIDEach, nameEach, figureEach);
                        }
                    }
                    // send status of previous players to current player
                    if (!Server.clientIDUndReady.isEmpty()) {
                        for (int clientIDEach : Server.clientIDUndReady.keySet()) {
                            Boolean isReadyEach = Server.clientIDUndReady.get(clientIDEach);
                            Server.getServer().handlePlayerStatusToOne(clientID, clientIDEach, isReadyEach);
                        }
                    }

                    if(Game.mapName != null){// if map has set before logged in, client count must be adjusted once again
                        Server.getServer().handleMapSelected(Game.mapName);
                        Game.getInstance().initGame();
                    }

                }
                break;
            case "Alive":
                logger.info("received alive backinfo, before setFalse " + clientID + ": " + connectList.get(clientID).flagConnect);
                connectList.get(clientID).flagConnect = false;
                logger.info("received alive backinfo, after setFalse " + clientID + ": " + connectList.get(clientID).flagConnect);
                break;
            case "SetStatus":
                logger.info("set Status in ExecuteOrder");
                SetStatusBody setStatusBody = Protocol.readJsonSetStatus(json);
                boolean isReady = setStatusBody.isReady();
                Server.clientIDUndReady.put(clientID, isReady);
                Server.getServer().handlePlayerStatus(clientID, isReady);

                // first client who ist ready can select a map
                for (int clientIDEach : Server.clientIDUndReady.keySet()) {
                    System.out.println("print the AI ID: " + clientIDOfAI);
                    if (Server.clientIDUndReady.get(clientIDEach) == true && clientIDEach != clientIDOfAI) {
                        Server.getServer().handleSelectMap(clientIDEach);
                        break;
                    }
                }
                // if there are more than 2 clients and all clients are ready and map is selected, start the game
                checkAndStartGame();

                // start connect check for each player
                Connected connected = new Connected(clientID);
                Thread threadConnect = new Thread(connected);
                connectList.put(clientID, connected);
                //System.out.println("connected of client " + clientID + " isconneced" + connectList.get(clientID).flagConnect);
                //System.out.println(connectList.keySet());
                threadConnect.start();

                // start alive check for each player
                AliveCheck aliveCheck = new AliveCheck(clientID);
                Thread threadAliveCheck = new Thread(aliveCheck);
                aliveCheckList.put(clientID, aliveCheck);
                //System.out.println("alivecheck of all clients " + aliveCheckList.keySet());
                threadAliveCheck.start();

                break;
            case "MapSelected":
                logger.info("set Map in ExecuteOrder");

                MapSelectedBody mapSelectedBody = Protocol.readJsonMapSelected(json);
                String mapName = mapSelectedBody.getMap();

                Server.getServer().handleMapSelected(mapName);

                Game.mapName = mapName;
                Game.getInstance().initGame();
                Game.getInstance().initBoard();
                Game.getInstance().setMap3DList(mapName);
                Game.hasMap = true;

                // if there are more than 2 clients and all clients are ready and map is selected, start the game
                checkAndStartGame();

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
                // private message
                if (sendChatBody.getTo() != -1) {
                    int toClient = sendChatBody.getTo();
                    if (Server.clientList.containsKey(toClient)) {
                        // to target client
                        System.out.println(toClient);

                        Server.clientList.get(clientID).sendPrivateMessage(toClient, clientID, message);
                        // also to myself as info
                        Server.clientList.get(clientID).sendPrivateMessage(clientID, clientID, message);
                    } else {
                        System.out.println("There is no client with this name!"); // optional in terminal
                        Protocol protocol = new Protocol("Error", new ErrorBody("There is no client with this name!"));
                        String js = Protocol.writeJson(protocol);
                        Server.clientList.get(clientID).makeOrder(js);
                    }
                    // public message
                } else {
                    logger.info("send message to all");
                    Server.clientList.get(clientID).sendMessage(clientID, message);
                }
                break;
            case "SetStartingPoint":
                SetStartingPointBody setStartingPointBody = Protocol.readJsonSetStartingPoint(json);
                int x = setStartingPointBody.getX();
                int y = setStartingPointBody.getY();

                // set client??s position in Game
                Game.playerPositions.put(clientID, new Position(x, y));
                // storage all clients?? start positions
                Game.startPositionsAllClients.put(clientID, new Position(x, y));
                // inform others about the client??s position
                Server.getServer().handleStartingPointTaken(clientID, x, y);

                // if next client exists, then it??s his/her turn
                if (Server.clientListPointer < Server.clientList.size()) {
                    int clientCurren = (Integer) Server.clientList.keySet().toArray()[Server.clientListPointer];
                    Server.clientListPointer++;
                    Server.getServer().handleCurrentPlayer(clientCurren);
                    // if all players haven chosen start points, go to next phase
                } else if (Server.clientListPointer == Server.clientList.size()) {
                    Server.getServer().handleActivePhase(1);
                    activePhase = 1;

                    // send available upgrade cards info to client
                    Server.getServer().handleRefillShop();
                    // set priority for this turn
                    Game.getInstance().checkAndSetPriority();
                    logger.info("after setting startPoint, priority list: " + Game.priorityEachTurn);
                    // set player in turn
                    int curClient = Game.priorityEachTurn.get(0);
                    Server.getServer().handleCurrentPlayer(curClient);
                    Game.priorityEachTurn.remove(0);

                }
                break;
            case "SelectedCard":
                SelectedCardBody selectedCardBody = Protocol.readJsonSelectedCard(json);
                int registerNum = selectedCardBody.getRegister();

                // if register slot with card
                if (selectedCardBody.getCard() != null) {
                    String cardName = selectedCardBody.getCard();
                    RegisterCard card = convertCardToObject(cardName);
                    Game.registersAllClients.get(clientID)[registerNum - 1] = card;
                    logger.info(card.getCardName());
                    Server.getServer().handleCardSelected(clientID, registerNum, true);
                } else { // if register slot without card
                    Game.registersAllClients.get(clientID)[registerNum - 1] = null;
                    Server.getServer().handleCardSelected(clientID, registerNum, false);
                }
                break;
            case "SelectionFinished":
                SelectionFinishedBody selectionFinishedBody = Protocol.readJsonSelectionFinished(json);
                int clientFinished = selectionFinishedBody.getClientID();
                Game.selectionFinishList.add(clientFinished);
                logger.info("executeOrder: selectionFinished : " + Game.selectionFinishList);

                // if only one client(not AI) finished programming, timer starts
                if (Game.selectionFinishList.size() == 1 && clientFinished != clientIDOfAI) {
                    Game.getInstance().startTimer();
                    Server.getServer().handleTimerStarted();
                    // if one client finished and AI also finished, start timer
                } else if (Game.clientIDs.size() > 2 && Game.selectionFinishList.size() == 2 && Game.selectionFinishList.contains(clientIDOfAI)) {
                    Game.getInstance().startTimer();
                    Server.getServer().handleTimerStarted();
                } else if (Game.selectionFinishList.size() == Game.clientIDs.size()) {// if all clients finished programming, next phase begins
                    Game.getInstance().stopTimer();
                    System.out.println("flag executeOrder: all players finished programmed.");
                    logger.info("executeOrder all clients finished programming in time");

                    // Aktivierungsphase beginns
                    Server.getServer().handleActivePhase(3);
                    activePhase = 3;
                    // inform all clients about current register cards of all
                    Server.getServer().handleCurrentCards();
                    // set priority for this turn
                    Game.priorityEachTurn.clear();
                    Game.getInstance().checkAndSetPriority();
                    // set player in turn
                    int curClient = Game.priorityEachTurn.get(0);
                    Server.getServer().handleCurrentPlayer(curClient);
                }
                break;
            case "PlayCard":
                logger.info("executeOrder playCard");

                PlayCardBody playCardBody = Protocol.readJsonPlayCard(json);
                String cardName = playCardBody.getCard();
                // server inform others which card was by whom played
                Server.getServer().handleCardPlayed(clientID, cardName);

                // when upgrade cards played
                if(cardName.equals("AdminPrivilege") || cardName.equals("RealLaser") || cardName.equals("MemorySwap") || cardName.equals("SpamBlocker")){

                    System.out.println("************UPGRADE played!!*********");

                    // update Game info of upgrade cards
                    int countCard = Game.upgradesCardsAllClients.get(clientID).get(cardName);
                    Game.upgradesCardsAllClients.get(clientID).put(cardName, countCard-1);

                    // do the function of upgrade card
                    UpgradeCard upgradeCard = convertStringUpdateToObject(cardName);
                    Game.getInstance().playUpgradeCard(clientID, upgradeCard);

                }else {// when registerCards played

                    allplayersRebooted = false;

                    // logic function in Game: move or turn
                    RegisterCard card = convertCardToObject(cardName);
                    Game.getInstance().playCard(clientID, card);



                    // if not all players rebooted, check turn over, otherwise break
                    if (!allplayersRebooted && activePhase != 2) {

                        // if damage card played, must be replaced and play again
                        if ((card.getCardType().equals("PROGRAMME") || card.getCardName().equals("Worm")) && !Game.priorityEachTurn.isEmpty()) {
                            Game.priorityEachTurn.remove(0);
                            logger.info("not damage card played " + Game.priorityEachTurn);
                        }

                        // check turnOver
                        boolean isTurnOver = Game.getInstance().checkTurnOver();
                        if (isTurnOver) {
                            logger.info("ExecuteOrder: turn is over!");
                            // if turn is over, check if round is over
                            boolean isRoundOver = Game.getInstance().checkRoundOver();
                            if (isRoundOver) {

                                logger.info("ExecuteOrder: round is over!");

                                if(Game.isGameOver == false){
                                    Server.getServer().handleActivePhase(1);
                                    activePhase = 1;
                                }

                                // send available upgrade cards info to client
                                Server.getServer().handleRefillShop();
                                // set priority for this turn
                                Game.getInstance().checkAndSetPriority();
                                // set player in turn
                                int curClient = Game.priorityEachTurn.get(0);
                                Server.getServer().handleCurrentPlayer(curClient);
                                Game.priorityEachTurn.remove(0);

                                //Server.getServer().handleYourCards();
                                // inform all players: programming phase begins
                                //Server.getServer().handleActivePhase(2);
                                //activePhase = 2;
                                break;
                            }
                        }

                        // if turn is not over inform next player to play
                        int curClient = Game.priorityEachTurn.get(0);
                        Server.getServer().handleCurrentPlayer(curClient);
                    }
                }

                break;
            case "ChooseRegister":
                System.out.println("************AdminPrivilege played!!*********");

                ChooseRegisterBody chooseRegisterBody = Protocol.readJsonChooseRegister(json);
                int chosenRegNr = chooseRegisterBody.getRegister();

                // inform others who chose which register por adminPrivilage
                Server.getServer().handleRegisterChosen(clientID, chosenRegNr);

                // update Game info of upgrade cards
                int countCard = Game.upgradesCardsAllClients.get(clientID).get("AdminPrivilege");
                Game.upgradesCardsAllClients.get(clientID).put("AdminPrivilege", countCard-1);

                // do the function of upgrade card
                UpgradeCard upgradeCard = convertStringUpdateToObject("AdminPrivilege");
                Game.getInstance().playAdminPriv(clientID, chosenRegNr, upgradeCard);
                //Game.getInstance().playUpgradeCard(clientID, upgradeCard);
                break;
            case "RebootDirection":
                RebootDirectionBody rebootDirectionBody = Protocol.readJsonRebootDirection(json);
                String reDirection = rebootDirectionBody.getDirection();
                Direction direction = Direction.convertStringToDirection(reDirection);

                Game.directionsAllClients.put(clientID, direction);
                // inform all players about the reboot direction
                switch (reDirection){
                    case "up":
                        break;
                    case "down":
                        Server.getServer().handlePlayerTurning(clientID, "clockwise");
                        Server.getServer().handlePlayerTurning(clientID, "clockwise");
                        break;
                    case "left":
                        Server.getServer().handlePlayerTurning(clientID, "counterclockwise");
                        break;
                    case "right":
                        Server.getServer().handlePlayerTurning(clientID, "clockwise");
                        break;
                }

                logger.info("executeOder reboot direction");
                break;

            case "BuyUpgrade":
                System.out.println("client " + clientID + "bought upgrade card");
                BuyUpgradeBody buyUpgradeBody = Protocol.readJsonBuyUpgrade(json);
                String boughtCardString = buyUpgradeBody.getCard();

                if(boughtCardString != null){// when really bought a upgrade card

                    //update info in Game
                    //int curCount = Game.upgradesCardsAllClients.get(clientID).get(boughtCardString);
                    //Game.upgradesCardsAllClients.get(clientID).put(boughtCardString, (curCount + 1));
                    if(boughtCardString.equals("RealLaser")){
                        Game.realLaserAllClients.put(clientID, true);
                    }

                    //inform all the clients this info
                    Server.getServer().handleUpgradeBought(clientID, boughtCardString);
                }

                //check if all clients finished buying
                Game.buyUpgradeCardsFinished.add(clientID);
                if (Game.buyUpgradeCardsFinished.size() == Game.clientIDs.size()) {
                    Server.getServer().handleActivePhase(2);
                    activePhase = 2;
                    Server.getServer().handleYourCards();
                } else {// else inform next player to buy upgrade card
                    int curClient = Game.priorityEachTurn.get(0);
                    Server.getServer().handleCurrentPlayer(curClient);
                }
                break;
        }
    }

    /**
     * Convert string update to object upgrade card.
     *
     * @param cardName the card name
     * @return the upgrade card
     */
    public static UpgradeCard convertStringUpdateToObject(String cardName) {
        UpgradeCard card = null;

        switch (cardName) {
            case "AdminPrivilege":
                card = new AdminPrivilege();
                break;
            case "RealLaser":
                card = new RealLaser();
                break;
            case "SpamBlocker":
                card = new SpamBlocker();
                break;
            case "MemorySwap":
                card = new MemorySwap();
                break;
        }

        return card;
    }


    /**
     * convert String cardName to object card
     *
     * @param cardName the card name
     * @return register card
     */
    public static RegisterCard convertCardToObject(String cardName) {
        RegisterCard card = null;

        switch (cardName) {
            case "Again":
                card = new Again();
                break;
            case "BackUp":
                card = new BackUp();
                break;
            case "MoveI":
                card = new MoveI();
                break;
            case "MoveII":
                card = new MoveII();
                break;
            case "MoveIII":
                card = new MoveIII();
                break;
            case "PowerUp":
                card = new PowerUp();
                break;
            case "TurnLeft":
                card = new TurnLeft();
                break;
            case "TurnRight":
                card = new TurnRight();
                break;
            case "UTurn":
                card = new UTurn();
                break;
            case "Spam":
                card = new Spam();
                break;
            case "Trojan":
                card = new Trojan();
                break;
            case "Virus":
                card = new Virus();
                break;
            case "Worm":
                card = new Worm();
                break;
            case "":
                card = new BlankCard();
                break;
        }

        return card;
    }


    /**
     * // if there are more than 2 clients and all clients are ready and map is selected, start the game
     *
     * @throws IOException the io exception
     */
    public static void checkAndStartGame() throws IOException {
        int numReadyClients = 0;
        for (int clientIDEach : Server.clientIDUndReady.keySet()) {
            if (Server.clientIDUndReady.get(clientIDEach) == true) {
                numReadyClients++;
            }
        }

        logger.info("number of ready clients: " + numReadyClients);
        if (numReadyClients > 1 && numReadyClients == Server.clientIDUndNames.size() && Game.hasMap == true) {
            logger.info("number enough, to play");
            logger.info("clientIds: " + Game.clientIDs);

            isGameOn = true;

            Server.getServer().handleGameStarted(Game.mapName);

            // for map twister, send checkpoints locations additionally
            if(Game.mapName.equals("Twister")){
                Server.getServer().handleCheckpointsLocations();
            }

            Server.getServer().handleActivePhase(0);

            // find the first client, who first logged in
            int clientFirst = (Integer) Server.clientList.keySet().toArray()[Server.clientListPointer];
            Server.clientListPointer++;
            Server.getServer().handleCurrentPlayer(clientFirst);
        }
    }
}
