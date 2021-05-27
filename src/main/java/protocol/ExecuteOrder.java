package protocol;

import protocol.submessagebody.ErrorBody;
import protocol.submessagebody.HelloServerBody;
import protocol.submessagebody.SendChatBody;
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
                if (sendChatBody.getTo() != 0) {
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
