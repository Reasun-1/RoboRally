package client.view;

import client.model.Client;
import client.viewmodel.ChatViewModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class ChatController {

    @FXML
    private TextArea outOfRoundCards1; //registers the written messages on TextField

    @FXML
    private TextArea playersInServer;

    @FXML
    private TextArea playersWhoReady;

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField messageField; //bind the typed message with message history scroll pane

    @FXML
    private TextField sendTo; //send Message to a specific player on private

    @FXML
    private Button sendButton; //send from messageField a typed message to message history

    private Client client;

    public void init(Client client) {
        this.client = client;

        //connects the send button and the message field together (if message field is empty then u can't press the send button)
        sendButton.disableProperty().bind(messageField.textProperty().isEmpty());

        //binds the button of sending a message with the chat TextArea that saves all the messages(chat history)
        outOfRoundCards1.textProperty().bindBidirectional(client.getChatHistory());

        //bind the players who are in server
        playersInServer.textProperty().bindBidirectional(client.PLAYERSINSERVERProperty());

        //bind the players who are ready to play
        playersWhoReady.textProperty().bindBidirectional(client.PLAYERSWHOAREREADYProperty());

    }

    @FXML
    //send method makes the message get sent from message field to messages History(ScrollPane)
    private void send() throws JsonProcessingException {
       /* if (sendTo.getText().isEmpty()) {
            client.sendMessage(messageField.getText());
        } else {
            client.sendPersonalMessage(Integer.valueOf(sendTo.getText()), messageField.getText());
        }
        messageField.clear();
        sendTo.clear();

        */
    }

    @FXML
    private void setReady() throws JsonProcessingException { client.setReady(); }
    @FXML
    private void setUnready() throws JsonProcessingException { client.setUnready(); }

}
