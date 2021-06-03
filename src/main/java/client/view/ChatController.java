package client.view;

import client.model.Client;
import client.viewmodel.ChatViewModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;


/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class ChatController {

    private Client client;

    @FXML
    private TextArea outOfRoundCards1; //registers the written messages on TextField

    @FXML
    private TextArea playersInServer;

    @FXML
    private TextArea playersWhoReady;

    @FXML
    private TextArea currentPhase;

    @FXML
    private GridPane gridPane;

    @FXML
    private TextField messageField; //bind the typed message with message history scroll pane

    @FXML
    private TextField information; // bind INFORMATION StringProperty in Client

    @FXML
    private TextField mapName; // info invoked by button select map

    @FXML
    private TextField sendTo; //send Message to a specific player on private

    @FXML
    private TextField startPointX;

    @FXML
    private TextField startPointY;

    @FXML
    private Button sendButton; //send from messageField a typed message to message history

    @FXML
    private Button selectMap; // bind the BooleanProperty canSelectMap in Client

    @FXML
    private Button setStartPoint; // bind the BooleanProperty canSelectStartPoint in Client

    @FXML
    private Button setRegister01; // invoke methode setRegisterEvent()

    @FXML
    private Button finish; // invoke methode finishEvent()

    @FXML
    private Button canPlayNextRegister; // invoke methode playNextRegistserEvent()

    @FXML
    private ImageView testImageView;

    @FXML
    private Button testButton;

    //====================DrawnCardsBindings===================================
    Image imageAgain = new Image(getClass().getResource("/images/Cards/C-Again.jpg").toExternalForm());
    Image imageDiscard = new Image(getClass().getResource("/images/Cards/C-Discard.jpg").toExternalForm());
    Image imageMove1 = new Image(getClass().getResource("/images/Cards/C-Move1.jpg").toExternalForm());
    Image imageMove2 = new Image(getClass().getResource("/images/Cards/C-Move2.jpg").toExternalForm());
    Image imageMove3 = new Image(getClass().getResource("/images/Cards/C-Move3.jpg").toExternalForm());
    Image imageMoveBack = new Image(getClass().getResource("/images/Cards/C-MoveBack.jpg").toExternalForm());
    Image imagePowerUp = new Image(getClass().getResource("/images/Cards/C-PowerUp.jpg").toExternalForm());
    Image imageTurnL = new Image(getClass().getResource("/images/Cards/C-TurnL.jpg").toExternalForm());
    Image imageTurnR = new Image(getClass().getResource("/images/Cards/C-TurnR.jpg").toExternalForm());
    Image imageTurnU = new Image(getClass().getResource("/images/Cards/C-TurnU.jpg").toExternalForm());

    @FXML
    private ImageView DrawnCard0;
    @FXML
    private ImageView DrawnCard1;
    @FXML
    private ImageView DrawnCard2;
    @FXML
    private ImageView DrawnCard3;
    @FXML
    private ImageView DrawnCard4;

    private StringProperty drawnCard1;
    private StringProperty drawnCard2;
    private StringProperty drawnCard3;
    private StringProperty drawnCard4;
    private StringProperty drawnCard5;


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

        //bind the player who can select the map
        selectMap.disableProperty().bind(client.CANSELECTMAPProperty().not());

        //bind the player who can select a start point
        setStartPoint.disableProperty().bind(client.CANSETSTARTPOINTProperty().not());

        //bind Information StringProperty in Client to get the current info
        information.textProperty().bindBidirectional(client.INFORMATIONProperty());

        //bind GAMEPHASE in client
        currentPhase.textProperty().bindBidirectional(client.GAMEPHASEProperty());

        //bind CANCLICKFINISH in client
        finish.disableProperty().bind(client.CANCLICKFINISHProperty().not());

        //bind CANPLAYNEXTREGISTER in client
        canPlayNextRegister.disableProperty().bind(client.CANPLAYNEXTREGISTERProperty().not());

        //====================Bindings for drawnCards===================
        StringProperty mydrawnCard0 = client.getMYDRAWNCARDS()[0];
        mydrawnCard0.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                System.out.println(t1);
                System.out.println("drawn card 1 changed!!");
                testImageView.setImage(imageAgain);
            }
        });

    }

    //only for test
    @FXML
    public void testButtonClick() {
        System.out.println("button clicked.");

        System.out.println(imageAgain.getHeight());
        testImageView.setImage(imageAgain);
    }

    @FXML
    //send method makes the message get sent from message field to messages History(ScrollPane)
    private void send() throws JsonProcessingException {
        if (sendTo.getText().isEmpty()) {
            client.sendMessage(messageField.getText());
        } else {
            client.sendPersonalMessage(Integer.valueOf(sendTo.getText()), messageField.getText());
        }
        messageField.clear();
        sendTo.clear();
    }

    @FXML
    private void setReady() throws JsonProcessingException {
        client.setReady();
    }

    @FXML
    private void setUnready() throws JsonProcessingException {
        client.setUnready();
    }

    @FXML
    private void selectMapEvent() throws JsonProcessingException {
        client.handleMapSelected(mapName.getText());
    }

    @FXML
    private void setStartPointEvent() throws JsonProcessingException {
        client.setStartPoint(Integer.valueOf(startPointX.getText()), Integer.valueOf(startPointY.getText()));
    }

    @FXML
    private void setRegisterEvent() throws JsonProcessingException {
        // soon: bind with imageView
        // only for test
        String cardName = "Again";
        int registerNum = 1;
        client.setRegister(cardName, registerNum);
    }

    @FXML
    private void finishEvent() throws JsonProcessingException {
        client.selectFinish();
    }

    @FXML
    private void playNextRegistserEvent() throws JsonProcessingException {
        String cardName = "Again";
        client.playNextRegister(cardName);
    }
}
