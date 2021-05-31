package protocol;

import protocol.submessagebody.*;
import server.game.Game;
import server.game.Position;
import server.network.Server;
import server.registercards.Again;
import server.registercards.Move2;
import server.registercards.RegisterCard;

import java.io.IOException;
import java.util.logging.Logger;


/**
 * this class is specially for decoding json message and execute the order in json
 *
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class ExecuteOrder {

    private static final Logger logger = Logger.getLogger(ExecuteOrder.class.getName());

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
                }
                break;
            case "SetStatus":
                logger.info("set Status in ExecuteOrder");
                SetStatusBody setStatusBody = Protocol.readJsonSetStatus(json);
                boolean isReady = setStatusBody.isReady();
                Server.clientIDUndReady.put(clientID, isReady);
                Server.getServer().handlePlayerStatus(clientID, isReady);
                break;
            case "SelectMap":
                logger.info("set Map in ExecuteOrder");
                SelectMapBody selectMapBody = Protocol.readJsonSelectMap(json);
                String mapName = selectMapBody.getAvailableMaps().get(0);
                Game.getInstance().setMap3DList(mapName);
                Server.getServer().handleMapSelected(mapName);

                // if there are more than 2 clients, start the game
                int numReadyClients = 0;
                for (int clientIDEach : Server.clientIDUndReady.keySet()) {
                    if (Server.clientIDUndReady.get(clientIDEach) == true) {
                        numReadyClients++;
                    }
                }
                logger.info("number of ready clients: " + numReadyClients);
                if (numReadyClients > 1 && numReadyClients == Server.clientIDUndNames.size()) {
                    logger.info("number enough, to play");
                    Server.getServer().handleGameStarted(mapName);
                    Server.getServer().handleActivePhase(0);

                    // find the first client, who first logged in
                    int clientFirst = (Integer) Server.clientList.keySet().toArray()[Server.clientListPointer];
                    Server.clientListPointer++;
                    Server.getServer().handleCurrentPlayer(clientFirst);
                } else {
                    Server.getServer().exception(clientID, "Not all players are ready or not enough players(>1), please wait and choose map again.");
                }
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
                        Server.clientList.get(clientID).sendPrivateMessage(toClient, clientID + "[private to you]: " + message);
                        // also to myself as info
                        Server.clientList.get(clientID).sendPrivateMessage(clientID, clientID + "[private to " + toClient + "]: " + message);
                    } else {
                        System.out.println("There is no client with this name!"); // optional in terminal
                        Protocol protocol = new Protocol("Error", new ErrorBody("There is no client with this name!"));
                        String js = Protocol.writeJson(protocol);
                        Server.clientList.get(clientID).makeOrder(js);
                    }
                    // public message
                } else {
                    logger.info("send message to all");
                    Server.clientList.get(clientID).sendMessage(clientID, clientID + ": " + message);
                }
                break;
            case "SetStartingPoint":
                SetStartingPointBody setStartingPointBody = Protocol.readJsonSetStartingPoint(json);
                int x = setStartingPointBody.getX();
                int y = setStartingPointBody.getY();

                // set client´s position in Game
                Game.playerPositions.put(clientID,new Position(x, y));
                // storage all clients´ start positions
                Game.startPositionsAllClients.put(clientID, new Position(x, y));
                // inform others about the client´s position
                Server.getServer().handleStartingPointTaken(clientID, x, y);

                // if next client exists, then it´s his/her turn
                if (Server.clientListPointer < Server.clientList.size()) {
                    int clientCurren = (Integer) Server.clientList.keySet().toArray()[Server.clientListPointer];
                    Server.clientListPointer++;
                    Server.getServer().handleCurrentPlayer(clientCurren);
                    // if all players haven chosen start points, go to next phase
                } else if (Server.clientListPointer == Server.clientList.size()) {
                    Server.getServer().handleActivePhase(2);
                    Server.getServer().handleYourCards();
                }
                break;
            case "SelectedCard":
                SelectedCardBody selectedCardBody = Protocol.readJsonSelectedCard(json);
                int registerNum = selectedCardBody.getRegister();

                // if register slot with card
                if(selectedCardBody.getCard() != null){
                    String cardName = selectedCardBody.getCard();
                    RegisterCard card = convertCardToObject(cardName);
                    Game.registersAllClients.get(clientID)[registerNum-1] = card;
                    logger.info(card.getCardName());
                    Server.getServer().handleCardSelected(clientID, registerNum, true);
                }else{ // if register slot without card
                    Game.registersAllClients.get(clientID)[registerNum-1] = null;
                    Server.getServer().handleCardSelected(clientID, registerNum, false);
                }
                break;
            case "SelectionFinished":
                SelectionFinishedBody selectionFinishedBody = Protocol.readJsonSelectionFinished(json);
                int clientFinished = selectionFinishedBody.getClientID();
                Game.selectionFinishList.add(clientFinished);

                // if only one client finished programming, timer starts
                if(Game.selectionFinishList.size() == 1){
                    Game.getInstance().startTimer();
                    Server.getServer().handleTimerStarted();
                    // if all clients finished programming, next phase begins
                }else if(Game.selectionFinishList.size() == Game.clientIDs.size()){
                    Game.getInstance().stopTimer();
                    logger.info("executeOrder all clients finished programming in time");

                    // Aktivierungsphase beginns
                    Server.getServer().handleActivePhase(3);
                    // inform all clients about current register cards of all
                    Server.getServer().handleCurrentCards();
                    // set priority for this turn
                    Game.getInstance().checkAndSetPriority();
                    // set player in turn
                    int curClient = Game.priorityEachTurn.get(0);
                    Server.getServer().handleCurrentPlayer(curClient);
                    Game.priorityEachTurn.remove(0);
                }
                break;
            case "PlayCard":
                logger.info("executeOrder playCard");
                PlayCardBody playCardBody = Protocol.readJsonPlayCard(json);
                String cardName = playCardBody.getCard();
                // server inform others which card was by whom played
                Server.getServer().handleCardPlayed(clientID, cardName);
                // logic function in Game: move or turn
                RegisterCard card = convertCardToObject(cardName);
                Game.getInstance().playCard(clientID, card);

                // check turnOver
                // check RoundOver
                // check GameOver
                // inform the nextPlayer
                // check if priority list in Game is empty, if empty=> reset priority and check if Game.registerPointer == 0? if == 0, then round over, got "yourCards"

                break;
        }
    }

    /**
     * convert String cardName to object card
     * @param cardName
     * @return
     */
    public static RegisterCard convertCardToObject(String cardName){
        if(cardName.equals("again")){
            return new Again("PROGRAMME", "again");
        }else if(cardName.equals("move2")){
            return new Move2("PROGRAMME", "move2");
        }else{
            return null;
        }
    }
}
