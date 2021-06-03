package client.view;

import client.model.Client;
import client.viewmodel.ChatViewModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
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
    private GridPane gridPaneBoard;
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
    @FXML
    private ImageView DrawnCard5;
    @FXML
    private ImageView DrawnCard6;
    @FXML
    private ImageView DrawnCard7;
    @FXML
    private ImageView DrawnCard8;

    @FXML
    private ImageView Register1;
    @FXML
    private ImageView Register2;
    @FXML
    private ImageView Register3;
    @FXML
    private ImageView Register4;
    @FXML
    private ImageView Register5;

    @FXML
    private TextField drawnC0;
    @FXML
    private TextField drawnC1;
    @FXML
    private TextField drawnC2;
    @FXML
    private TextField drawnC3;
    @FXML
    private TextField drawnC4;
    @FXML
    private TextField drawnC5;
    @FXML
    private TextField drawnC6;
    @FXML
    private TextField drawnC7;
    @FXML
    private TextField drawnC8;

    @FXML
    private Button drawnB0;
    @FXML
    private Button drawnB1;
    @FXML
    private Button drawnB2;
    @FXML
    private Button drawnB3;
    @FXML
    private Button drawnB4;
    @FXML
    private Button drawnB5;
    @FXML
    private Button drawnB6;
    @FXML
    private Button drawnB7;
    @FXML
    private Button drawnB8;



    //============================MapBindings===========================================
    Image imageCheckpoint1 = new Image(getClass().getResource("/images/Checkpoints/Checkpoint1.png").toExternalForm());

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
        client.MYCARDSProperty().addListener(new ChangeListener<ObservableList<String>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<String>> observableValue, ObservableList<String> strings, ObservableList<String> t1) {
                System.out.println("drawn cards in GUI changed.");

                if(client.MYCARDSProperty().size() > 0){
                    int curCardIndex = client.MYCARDSProperty().size() - 1;
                    String curCard = client.MYCARDSProperty().get(curCardIndex);
                    Image curImage = null;
                    switch (curCard) {
                        case "Again":
                            curImage = imageAgain;
                            break;
                        case "BackUp":
                            curImage = imageMoveBack;
                            break;
                        case "MoveI":
                            curImage = imageMove1;
                            break;
                        case "MoveII":
                            curImage = imageMove2;
                            break;
                        case "MoveIII":
                            curImage = imageMove3;
                            break;
                        case "PowerUp":
                            curImage = imagePowerUp;
                            break;
                        case "TurnLeft":
                            curImage = imageTurnL;
                            break;
                        case "TurnRight":
                            curImage = imageTurnR;
                            break;
                        case "UTurn":
                            curImage = imageTurnU;
                            break;
                    }

                    switch (curCardIndex) {
                        case 0:
                            DrawnCard0.setImage(curImage);
                            break;
                        case 1:
                            DrawnCard1.setImage(curImage);
                            break;
                        case 2:
                            DrawnCard2.setImage(curImage);
                            break;
                        case 3:
                            DrawnCard3.setImage(curImage);
                            break;
                        case 4:
                            DrawnCard4.setImage(curImage);
                            break;
                        case 5:
                            DrawnCard5.setImage(curImage);
                            break;
                        case 6:
                            DrawnCard6.setImage(curImage);
                            break;
                        case 7:
                            DrawnCard7.setImage(curImage);
                            break;
                        case 8:
                            DrawnCard8.setImage(curImage);
                            break;
                    }
                }

            }
        });

        // bind flagRoundOver for new round
        client.flagRoundOverProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                // reset drwanCards in client
                client.MYCARDSProperty().clear();
                DrawnCard0.setImage(imageDiscard);
                DrawnCard1.setImage(imageDiscard);
                DrawnCard2.setImage(imageDiscard);
                DrawnCard3.setImage(imageDiscard);
                DrawnCard4.setImage(imageDiscard);
                DrawnCard5.setImage(imageDiscard);
                DrawnCard6.setImage(imageDiscard);
                DrawnCard7.setImage(imageDiscard);
                DrawnCard8.setImage(imageDiscard);
                // reset MYREGISTER[]
                for (int i = 0; i < 5; i++) {
                    client.getMYREGISTER()[i] = new SimpleStringProperty("");
                }
                Register1.setImage(imageDiscard);
                Register2.setImage(imageDiscard);
                Register3.setImage(imageDiscard);
                Register4.setImage(imageDiscard);
                Register5.setImage(imageDiscard);

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
    private void finishEvent() throws JsonProcessingException {
        client.selectFinish();
    }

    @FXML
    private void playNextRegistserEvent() throws JsonProcessingException {
        // get current register card
        String cardName = client.getMYREGISTER()[client.registerPointer].get();
        client.playNextRegister(cardName);
        client.registerPointer++;
        System.out.println("registerpointer " + client.registerPointer);
        // if round over, reset register pointer to 0 for next round
        if(client.registerPointer == 5){
            System.out.println("round over checked by GUI");
            client.registerPointer = 0;
        }
    }


    //=========================EventDrawnCards=================================

    @FXML
    public void drawnButton0() throws JsonProcessingException {
        System.out.println("drawnButton0 clicked.");
        // set Image to right register
        int regNum = Integer.valueOf(drawnC0.getText());
        Image image = DrawnCard0.getImage();
        setRegCard(regNum, image);

        // send selected card message to server
        String cardName0 = client.MYCARDSProperty().get(0);
        client.setRegister(cardName0, regNum);
    }

    @FXML
    public void drawnButton1() throws JsonProcessingException {
        System.out.println("drawnButton1 clicked.");
        // set Image to right register
        int regNum = Integer.valueOf(drawnC1.getText());
        Image image = DrawnCard1.getImage();
        setRegCard(regNum, image);
        String cardName1 = client.MYCARDSProperty().get(1);
        client.setRegister(cardName1, regNum);
    }
    @FXML
    public void drawnButton2() throws JsonProcessingException {
        System.out.println("drawnButton2 clicked.");
        // set Image to right register
        int regNum = Integer.valueOf(drawnC2.getText());
        Image image = DrawnCard2.getImage();
        setRegCard(regNum, image);
        String cardName2 = client.MYCARDSProperty().get(2);
        client.setRegister(cardName2, regNum);
    }
    @FXML
    public void drawnButton3() throws JsonProcessingException {
        System.out.println("drawnButton3 clicked.");
        // set Image to right register
        int regNum = Integer.valueOf(drawnC3.getText());
        Image image = DrawnCard3.getImage();
        setRegCard(regNum, image);
        String cardName3 = client.MYCARDSProperty().get(3);
        client.setRegister(cardName3, regNum);
    }
    @FXML
    public void drawnButton4() throws JsonProcessingException {
        System.out.println("drawnButton4 clicked.");
        // set Image to right register
        int regNum = Integer.valueOf(drawnC4.getText());
        Image image = DrawnCard4.getImage();
        setRegCard(regNum, image);
        String cardName4 = client.MYCARDSProperty().get(4);
        client.setRegister(cardName4, regNum);
    }

    @FXML
    public void drawnButton5() throws JsonProcessingException {
        System.out.println("drawnButton5 clicked.");
        // set Image to right register
        int regNum = Integer.valueOf(drawnC5.getText());
        Image image = DrawnCard5.getImage();
        setRegCard(regNum, image);
        String cardName5 = client.MYCARDSProperty().get(5);
        client.setRegister(cardName5, regNum);
    }

    @FXML
    public void drawnButton6() throws JsonProcessingException {
        System.out.println("drawnButton6 clicked.");
        // set Image to right register
        int regNum = Integer.valueOf(drawnC6.getText());
        Image image = DrawnCard6.getImage();
        setRegCard(regNum, image);
        String cardName6 = client.MYCARDSProperty().get(6);
        client.setRegister(cardName6, regNum);
    }

    @FXML
    public void drawnButton7() throws JsonProcessingException {
        System.out.println("drawnButton7 clicked.");
        // set Image to right register
        int regNum = Integer.valueOf(drawnC7.getText());
        Image image = DrawnCard7.getImage();
        setRegCard(regNum, image);
        String cardName7 = client.MYCARDSProperty().get(7);
        client.setRegister(cardName7, regNum);
    }

    @FXML
    public void drawnButton8() throws JsonProcessingException {
        System.out.println("drawnButton8 clicked.");
        // set Image to right register
        int regNum = Integer.valueOf(drawnC8.getText());
        Image image = DrawnCard8.getImage();
        setRegCard(regNum, image);
        String cardName8 = client.MYCARDSProperty().get(8);
        client.setRegister(cardName8, regNum);
    }

    /**
     * for each drawnButton function
     * @param regNum
     * @param image
     */
    public void setRegCard(int regNum, Image image){
        switch (regNum){
            case 1:
                Register1.setImage(image);
                break;
            case 2:
                Register2.setImage(image);
                break;
            case 3:
                Register3.setImage(image);
                break;
            case 4:
                Register4.setImage(image);
                break;
            case 5:
                Register5.setImage(image);
                break;
        }
    }
    public void testBoardButtonEvent(){
        ImageView boardElemen = new ImageView(imageCheckpoint1);
        boardElemen.setFitHeight(43);
        boardElemen.setFitWidth(43);
        gridPaneBoard.add(boardElemen,1,1);
    }
}
