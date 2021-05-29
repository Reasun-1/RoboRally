package protocol;

import protocol.submessagebody.*;
import server.game.Game;
import server.network.Server;

import java.io.IOException;
import java.util.logging.Logger;


/**
 * this class is specially for decoding json message and execute the order in json
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

                if(Server.clientIDUndRobots.containsValue(figureNumber)){
                    Server.getServer().exception(clientID, "This figure exists already, choose again.");
                }else{
                    logger.info("addPlayer");
                    Server.clientIDUndRobots.put(clientID, figureNumber);
                    Server.clientIDUndNames.put(clientID, clientName);
                    // inform all existed players that one new play war added
                    Server.getServer().handlePlayerAddedToAll(clientID,clientName,figureNumber);


                    if(Server.clientIDUndNames.size() != 1){
                        // send infos of previous players also to current player
                        for(int clientIDEach : Server.clientIDUndNames.keySet()){
                            String nameEach = Server.clientIDUndNames.get(clientIDEach);
                            int figureEach = Server.clientIDUndRobots.get(clientIDEach);
                            Server.getServer().handlePlayerAddedToOne(clientID, clientIDEach, nameEach, figureEach);
                        }
                    }
                    // send status of previous players to current player
                    if(!Server.clientIDUndReady.isEmpty()){
                        for(int clientIDEach : Server.clientIDUndReady.keySet()){
                            Boolean isReadyEach = Server.clientIDUndReady.get(clientIDEach);
                            Server.getServer().handlePlayerStatusToOne(clientID,clientIDEach,isReadyEach);
                        }
                    }
                }
                break;
            case "SetStatus":
                logger.info("set Status in ExecuteOrder");
                SetStatusBody setStatusBody = Protocol.readJsonSetStatus(json);
                boolean isReady = setStatusBody.isReady();
                Server.clientIDUndReady.put(clientID, isReady);
                Server.getServer().handlePlayerStatus(clientID,isReady);
                break;
            case "SelectMap":
                logger.info("set Map in ExecuteOrder");
                SelectMapBody selectMapBody = Protocol.readJsonSelectMap(json);
                String mapName = selectMapBody.getAvailableMaps().get(0);
                Game.getInstance().setMap3DList(mapName);
                Server.getServer().handleMapSelected(mapName);

                // if there are more than 2 clients, start the game
                int numReadyClients = 0;
                for(int clientIDEach : Server.clientIDUndReady.keySet()){
                    if(Server.clientIDUndReady.get(clientIDEach) == true){
                        numReadyClients++;
                    }
                }
                logger.info("number of ready clients: " +  numReadyClients);
                if(numReadyClients > 1 && numReadyClients == Server.clientIDUndNames.size()){
                    logger.info("number enough, to play");
                    Server.getServer().handleGameStarted(mapName);
                    Server.getServer().handleActivePhase(0);
                }else{
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
                if (sendChatBody.getTo() != -1) {
                    int toClient = sendChatBody.getTo();
                    if (Server.clientList.containsKey(toClient)) {
                        // to target client
                        System.out.println(toClient);
                        Server.clientList.get(clientID).sendPrivateMessage(toClient, clientID + "[private to you]: " + message);
                        // also to myself as info
                        Server.clientList.get(clientID).sendPrivateMessage(clientID, clientID + "[private]: " + message);
                    } else {
                        System.out.println("There is no client with this name!"); // optional in terminal
                        Protocol protocol = new Protocol("Error", new ErrorBody("There is no client with this name!"));
                        String js = Protocol.writeJson(protocol);
                        Server.clientList.get(clientID).makeOrder(js);
                    }

                } else {
                    logger.info("send message to all");
                    Server.clientList.get(clientID).sendMessage(clientID,clientID + ": " + message);
                }
                break;

        }
    }
}
