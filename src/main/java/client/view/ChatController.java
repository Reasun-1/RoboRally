package client.view;

import client.model.Client;
import client.model.WindowLauncher;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import server.feldobjects.FeldObject;
import server.game.Direction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//import client.viewmodel.ChatViewModel;


/**
 * The controller for Chat Window.
 *
 * @author Can Ren
 * @author Rajna Fani
 * @author Chiara Welz
 * @author Yuliia Shaparenko
 * @author Jonas Gottal
 */
public class ChatController {

    private Client client;

    @FXML
    private TextArea outOfRoundCards1; //registers the written messages on TextField
    @FXML
    private TextArea playersInServer, playersWhoReady;
    @FXML
    private TextArea information; // bind INFORMATION StringProperty in Client
    @FXML
    private TextArea currentPhase;
    @FXML
    private GridPane gridPaneBoard,gridPaneRobot, gridPaneStartPoint, gridPaneCheckpoint;
    @FXML
    private TextField messageField; //bind the typed message with message history scroll pane
    @FXML
    private TextField RegNrForAdmin; // to get the register Nr. for playing card AdminPrivilage

    @FXML
    private Button sendButton; //send from messageField a typed message to message history
    @FXML
    private Button selectMap; // bind the BooleanProperty canSelectMap in Client
    @FXML
    private Button setRegister01; // invoke methode setRegisterEvent()
    @FXML
    private Button finish; // invoke methode finishEvent()
    @FXML
    private Button canPlayNextRegister; // invoke methode playNextRegistserEvent()
    @FXML
    private Button remove1, remove2, remove3, remove4, remove5;
    @FXML
    private Button UpgradeAdmin, UpgradeBlocker, UpgradeMemory;
    @FXML
    private ImageView UpgradeLaser;
    @FXML
    private ImageView myFigure;
    @FXML
    private Label energyCube, timer, LabelAdmin, LabelLaser, LabelBlocker, LabelMemory;

    @FXML
    private ImageView DrawnCard0, DrawnCard1, DrawnCard2, DrawnCard3, DrawnCard4, DrawnCard5, DrawnCard6, DrawnCard7, DrawnCard8;
    @FXML
    private ImageView Register1, Register2, Register3, Register4, Register5;
    @FXML
    private ImageView LHulk, LSpinbot, LSquashbot, LTrundlebot, LTwitch, LTwonky;
    @FXML
    private ComboBox<String> mapList, sendto;

    private HashMap<Integer, Integer> regButton = new HashMap<>();//key=Register, value=button


    private final WindowLauncher LAUNCHER = new WindowLauncher();

    //The Temp card name for drag&drop
    String tempCardName = "";

    //The Temp button number for drag&drop
    int tempButtonNum;

    //Button for read and not ready
    @FXML
    private Button readyButton, notReadyButton;



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
    Image imageSpam = new Image(getClass().getResource("/images/Cards/DamageCards/D-Spam.jpg").toExternalForm());
    Image imageTrojan = new Image(getClass().getResource("/images/Cards/DamageCards/D-TrojanHorse.jpg").toExternalForm());
    Image imageVirus = new Image(getClass().getResource("/images/Cards/DamageCards/D-Virus.jpg").toExternalForm());
    Image imageWorm = new Image(getClass().getResource("/images/Cards/DamageCards/D-Worm.jpg").toExternalForm());

//============================MapBindings===========================================
    Image imageCheckpoint1 = new Image(getClass().getResource("/images/Checkpoints/Checkpoint1.png").toExternalForm());
    Image imageCheckpoint2 = new Image(getClass().getResource("/images/Checkpoints/Checkpoint2.png").toExternalForm());
    Image ImageCheckpoint3 = new Image(getClass().getResource("/images/Checkpoints/Checkpoint3.png").toExternalForm());
    Image ImageCheckpoint4 = new Image(getClass().getResource("/images/Checkpoints/Checkpoint4.png").toExternalForm());
    Image ImageCheckpoint5 = new Image(getClass().getResource("/images/Checkpoints/Checkpoint5.png").toExternalForm());
    Image ImageCheckpoint6 = new Image(getClass().getResource("/images/Checkpoints/Checkpoint6.png").toExternalForm());
    Image BlueConveyorBelts = new Image(getClass().getResource("/images/ConveyorBelts/BlueConveyorBelts.png").toExternalForm());
    Image BlueConveyorBelts90 = new Image(getClass().getResource("/images/ConveyorBelts/BlueConveyorBelts-90.png").toExternalForm());
    Image BlueConveyorBelts90mirror = new Image(getClass().getResource("/images/ConveyorBelts/BlueConveyorBelts-90mirro.png").toExternalForm());
    Image BlueConveyorBeltsR = new Image(getClass().getResource("/images/ConveyorBelts/BlueConveyorBelts-R.png").toExternalForm());
    Image BlueConveyorBelts21 = new Image(getClass().getResource("/images/ConveyorBelts/BlueConveyorBelts-2-1.png").toExternalForm());
    Image BlueConveyorBelts21mirror = new Image(getClass().getResource("/images/ConveyorBelts/BlueConveyorBelts-2-1-mirrored.png").toExternalForm());
    Image GreenConveyorBelts = new Image(getClass().getResource("/images/ConveyorBelts/GreenConveyorBelts.png").toExternalForm());
    Image GreenConveyorBelts90 = new Image(getClass().getResource("/images/ConveyorBelts/GreenConveyorBelts-90.png").toExternalForm());
    Image GreenConveyorBeltsD = new Image(getClass().getResource("/images/ConveyorBelts/GreenConveyorBelts-D.png").toExternalForm());
    Image GreenConveyorBeltsL = new Image(getClass().getResource("/images/ConveyorBelts/GreenConveyorBelts-L.png").toExternalForm());
    Image GreenConveyorBeltsDmirror = new Image(getClass().getResource("/images/ConveyorBelts/GreenConveyorBelts-D-mirrored.png").toExternalForm());
    Image GreenConveyorBelts90mirrored = new Image(getClass().getResource("/images/ConveyorBelts/GreenConveyorBelts-90mirrored.png").toExternalForm());
    Image EnergyOff = new Image(getClass().getResource("/images/Energy/energyOff.png").toExternalForm());
    Image EnergyOn = new Image(getClass().getResource("/images/Energy/energyOn.png").toExternalForm());
    Image GearTLeft = new Image(getClass().getResource("/images/Gear/Gear-Tleft.png").toExternalForm());
    Image GearTRight = new Image(getClass().getResource("/images/Gear/Gear-Tright.png").toExternalForm());
    Image BoardLasers1 = new Image(getClass().getResource("/images/Lasers/BoardLasers1.png").toExternalForm());
    Image BoardLasers2 = new Image(getClass().getResource("/images/Lasers/BoardLasers2.png").toExternalForm());
    Image BoardLasers3 = new Image(getClass().getResource("/images/Lasers/BoardLasers3.png").toExternalForm());
    Image Lasers1 = new Image(getClass().getResource("/images/Lasers/Lasers1.png").toExternalForm());
    Image Lasers2 = new Image(getClass().getResource("/images/Lasers/Lasers2.png").toExternalForm());
    Image Lasers3 = new Image(getClass().getResource("/images/Lasers/Lasers3.png").toExternalForm());
    Image PushPanel1 = new Image(getClass().getResource("/images/PushPanel/PushPanel-1.png").toExternalForm());
    Image PushPanel1_3_5 = new Image(getClass().getResource("/images/PushPanel/PushPanel-1-3-5.png").toExternalForm());
    Image PushPanel2 = new Image(getClass().getResource("/images/PushPanel/PushPanel-2.png").toExternalForm());
    Image PushPanel2_4 = new Image(getClass().getResource("/images/PushPanel/PushPanel-2-4.png").toExternalForm());
    Image PushPanel3 = new Image(getClass().getResource("/images/PushPanel/PushPanel-3.png").toExternalForm());
    Image PushPanel4 = new Image(getClass().getResource("/images/PushPanel/PushPanel-4.png").toExternalForm());
    Image PushPanel5 = new Image(getClass().getResource("/images/PushPanel/PushPanel-5.png").toExternalForm());


    Image Antenna = new Image(getClass().getResource("/images/Antenna.png").toExternalForm());
    Image Pits = new Image(getClass().getResource("/images/Pits.png").toExternalForm());
    Image HulkBot = new Image(getClass().getResource("/images/Robots/Hulk.png").toExternalForm());
    Image SpinBot = new Image(getClass().getResource("/images/Robots/Spinbot.png").toExternalForm());
    Image SquashBot = new Image(getClass().getResource("/images/Robots/Squashbot.png").toExternalForm());
    Image TrundleBot = new Image(getClass().getResource("/images/Robots/Trundlebot.png").toExternalForm());
    Image TwitchBot = new Image(getClass().getResource("/images/Robots/Twitch.png").toExternalForm());
    Image TwonkyBot = new Image(getClass().getResource("/images/Robots/Twonky.png").toExternalForm());
    Image DefaultBot = new Image(getClass().getResource("/images/Robots/defaultRobot.png").toExternalForm());
    Image Startpoint1 = new Image(getClass().getResource("/images/Startpoints/Start-1.png").toExternalForm());
    Image Reboot = new Image(getClass().getResource("/images/Reboot.jpg").toExternalForm());
    Image WallNormal = new Image(getClass().getResource("/images/Wall/Walls.png").toExternalForm());
    Image WallEdge = new Image(getClass().getResource("/images/Wall/Walls-Edge.png").toExternalForm());

    /**
     * Init.
     *
     * @param client the client
     */
    public void init(Client client) {
        this.client = client;

        //connects the send button and the message field together (if message field is empty then u can't press the send button)
        sendButton.disableProperty().bind(messageField.textProperty().isEmpty());

        //binds the button of sending a message with the chat TextArea that saves all the messages(chat history)
        outOfRoundCards1.textProperty().bindBidirectional(client.getChatHistory());

        //binds count of energy cubes
        energyCube.textProperty().bindBidirectional(client.energyCountProperty());

        //bind the players who are in server
        playersInServer.textProperty().bindBidirectional(client.PLAYERSINSERVERProperty());

        //bind the players who are ready to play
        playersWhoReady.textProperty().bindBidirectional(client.PLAYERSWHOAREREADYProperty());

        //bind the player who can select the map
        selectMap.disableProperty().bind(client.CANSELECTMAPProperty().not());
        mapList.disableProperty().bind(client.CANSELECTMAPProperty().not());

        //bind Information StringProperty in Client to get the current info
        information.textProperty().bindBidirectional(client.INFORMATIONProperty());

        //bind timer to timerScreen in client class
        timer.textProperty().bindBidirectional(client.timerScreenProperty());

        //bind GAMEPHASE in client
        currentPhase.textProperty().bindBidirectional(client.GAMEPHASEProperty());


        //bind CANCLICKFINISH in client
        finish.disableProperty().bind(client.CANCLICKFINISHProperty().not());

        //bind CANPLAYNEXTREGISTER in client
        canPlayNextRegister.disableProperty().bind(client.CANPLAYNEXTREGISTERProperty().not());

        currentPhase.setStyle("-fx-text-fill: lightskyblue; -fx-control-inner-background: black; -fx-font-size: 14px;");
        information.setStyle("-fx-text-fill: lightskyblue; -fx-control-inner-background: black; -fx-font-size: 14px;");
        outOfRoundCards1.setStyle("-fx-text-fill: lightskyblue; -fx-control-inner-background: black; -fx-font-size: 12px;");

        //init remove buttons deactive as default
        remove1.setDisable(true);
        remove2.setDisable(true);
        remove3.setDisable(true);
        remove4.setDisable(true);
        remove5.setDisable(true);

        // bind maps to map list for comboBox
        client.MAPSProperty().addListener(new ChangeListener<ObservableList<String>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<String>> observableValue, ObservableList<String> strings, ObservableList<String> t1) {
                ObservableList<String> mapObsList = client.getMAPS();
                System.out.println("in Controller " + mapObsList);
                if (mapList.getItems().size() < 5) {
                    mapList.getItems().clear();
                    mapList.getItems().addAll(mapObsList);
                }
            }
        });

        // bind robotsNames Propertylist in Client with comboBox
        client.ROBOTSNAMESFORCHATProperty().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                ObservableList<String> robotsnamesforchat = client.getROBOTSNAMESFORCHAT();
                sendto.getItems().clear();
                sendto.getItems().addAll(robotsnamesforchat);
                sendto.setPromptText("public");

                LHulk.setImage(DefaultBot);
                LSpinbot.setImage(DefaultBot);
                LSquashbot.setImage(DefaultBot);
                LTrundlebot.setImage(DefaultBot);
                LTwitch.setImage(DefaultBot);
                LTwonky.setImage(DefaultBot);

                ListProperty<String> robots = client.ROBOTSNAMESFORCHATProperty();
                for(String robot : robots){
                    switch(robot){
                        case "Hulk":
                            LHulk.setImage(HulkBot);
                            break;
                        case "Spinbot":
                            LSpinbot.setImage(SpinBot);
                            break;
                        case "Squashbot":
                            LSquashbot.setImage(SquashBot);
                            break;
                        case "Trundlebot":
                            LTrundlebot.setImage(TrundleBot);
                            break;
                        case "Twitch":
                            LTwitch.setImage(TwitchBot);
                            break;
                        case "Twonky":
                            LTwonky.setImage(TwonkyBot);
                            break;
                    }
                }
            }
        });


        //bind flag replace register
        client.flagReplaceRegisterProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                int curRegi = client.registerPointer;
                updateRegisters();
                //clear the registers before
                switch (curRegi) {
                    case 1:
                        //Register1.setImage(imageDiscard);
                        break;
                    case 2:
                        Register1.setImage(imageDiscard);
                        //Register2.setImage(imageDiscard);
                        break;
                    case 3:
                        Register1.setImage(imageDiscard);
                        Register2.setImage(imageDiscard);
                        //Register3.setImage(imageDiscard);
                        break;
                    case 4:
                        Register1.setImage(imageDiscard);
                        Register2.setImage(imageDiscard);
                        Register3.setImage(imageDiscard);
                        //Register4.setImage(imageDiscard);
                        break;
                }
            }
        });

        //bind time out for registers
        client.flagTimeOutProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                updateRegisters();
            }
        });

        //bind position and rotations update for listener
        client.flagPositionsProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

                // reset robot gridpane
                gridPaneRobot.getChildren().clear();
                // reset all rotates of imageViews

                // get robots current direction
                for (int clientNum : client.getCurrentPositions().keySet()) {
                    Direction direction = client.getCurrentDirections().get(clientNum);
                    int rotateGrad = 0;
                    switch (direction) {
                        case RIGHT:
                            rotateGrad = 90;
                            break;
                        case DOWN:
                            rotateGrad = 180;
                            break;
                        case LEFT:
                            rotateGrad = 270;
                            break;
                        case UP:
                            rotateGrad = 0;
                            break;
                    }

                    // get robots current position
                    int curX = client.getCurrentPositions().get(clientNum)[0];
                    int curY = client.getCurrentPositions().get(clientNum)[1];
                    int clientFig = client.getRobotFigureAllClients().get(clientNum);
                    switch (clientFig) {
                        case 1:
                            ImageView imgV1 = new ImageView(HulkBot);
                            imgV1.setFitHeight(43);
                            imgV1.setFitWidth(43);
                            imgV1.setRotate(imgV1.getRotate() + rotateGrad);
                            gridPaneRobot.add(imgV1, curX, curY);
                            break;
                        case 2:
                            ImageView imgV2 = new ImageView(SpinBot);
                            imgV2.setFitHeight(43);
                            imgV2.setFitWidth(43);
                            imgV2.setRotate(imgV2.getRotate() + rotateGrad);
                            gridPaneRobot.add(imgV2, curX, curY);
                            break;
                        case 3:
                            ImageView imgV3 = new ImageView(SquashBot);
                            imgV3.setFitHeight(43);
                            imgV3.setFitWidth(43);
                            imgV3.setRotate(imgV3.getRotate() + rotateGrad);
                            gridPaneRobot.add(imgV3, curX, curY);
                            break;
                        case 4:
                            ImageView imgV4 = new ImageView(TrundleBot);
                            imgV4.setFitHeight(43);
                            imgV4.setFitWidth(43);
                            imgV4.setRotate(imgV4.getRotate() + rotateGrad);
                            gridPaneRobot.add(imgV4, curX, curY);
                            break;
                        case 5:
                            ImageView imgV5 = new ImageView(TwitchBot);
                            imgV5.setFitHeight(43);
                            imgV5.setFitWidth(43);
                            imgV5.setRotate(imgV5.getRotate() + rotateGrad);
                            gridPaneRobot.add(imgV5, curX, curY);
                            break;
                        case 6:
                            ImageView imgV6 = new ImageView(TwonkyBot);
                            imgV6.setFitHeight(43);
                            imgV6.setFitWidth(43);
                            imgV6.setRotate(imgV6.getRotate() + rotateGrad);
                            gridPaneRobot.add(imgV6, curX, curY);
                            break;
                    }
                }
            }
        });

        //bind myFigure for listener
        client.flagMyFigureProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                HashMap<Integer, Integer> robotFigureAllClients = client.getRobotFigureAllClients();
                for (int clientNum : robotFigureAllClients.keySet()) {
                    if (clientNum == client.getClientID()) {
                        int figNum = robotFigureAllClients.get(clientNum);
                        switch (figNum) {
                            case 1:
                                myFigure.setImage(HulkBot);
                                break;
                            case 2:
                                myFigure.setImage(SpinBot);
                                break;
                            case 3:
                                myFigure.setImage(SquashBot);
                                break;
                            case 4:
                                myFigure.setImage(TrundleBot);
                                break;
                            case 5:
                                myFigure.setImage(TwitchBot);
                                break;
                            case 6:
                                myFigure.setImage(TwonkyBot);
                                break;
                        }
                    }
                }
            }
        });


        //====================Bindings for Map=========================
        client.flagMapUpdateProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                List<List<List<FeldObject>>> mapInGUI = client.getMapInGUI();
                System.out.println(mapInGUI.size());
                setMapInGUI(mapInGUI);
            }
        });

        //====================Bindings for drawnCards===================
        client.MYCARDSProperty().addListener(new ChangeListener<ObservableList<String>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<String>> observableValue, ObservableList<String> strings, ObservableList<String> t1) {

                for (int i = 0; i < client.MYCARDSProperty().size(); i++) {
                        String curCard = client.MYCARDSProperty().get(i);
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
                            case "Spam":
                                curImage = imageSpam;
                                break;
                            case "Trojan":
                                curImage = imageTrojan;
                                break;
                            case "Virus":
                                curImage = imageVirus;
                                break;
                            case "Worm":
                                curImage = imageWorm;
                                break;
                        }

                        switch (i) {
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
                setRoundOver();
            }
        });

        // bind flagClearRegister to clear register cards
        client.flagClearRegistersProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                setRoundOver();
            }
        });

        // ====================bind drag&drop for choosing cards==========================

        DrawnCard0.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = DrawnCard0.startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(DrawnCard0.getImage());
                db.setContent(content);

                tempButtonNum = 0;
                tempCardName = client.MYCARDSProperty().get(0);
            }
        });
        DrawnCard1.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = DrawnCard1.startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(DrawnCard1.getImage());
                db.setContent(content);

                tempButtonNum = 1;
                tempCardName = client.MYCARDSProperty().get(1);
            }
        });
        DrawnCard2.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = DrawnCard2.startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(DrawnCard2.getImage());
                db.setContent(content);

                tempButtonNum = 2;
                tempCardName = client.MYCARDSProperty().get(2);
            }
        });
        DrawnCard3.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = DrawnCard3.startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(DrawnCard3.getImage());
                db.setContent(content);

                tempButtonNum = 3;
                tempCardName = client.MYCARDSProperty().get(3);
            }
        });
        DrawnCard4.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = DrawnCard4.startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(DrawnCard4.getImage());
                db.setContent(content);

                tempButtonNum = 4;
                tempCardName = client.MYCARDSProperty().get(4);
            }
        });
        DrawnCard5.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = DrawnCard5.startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(DrawnCard5.getImage());
                db.setContent(content);

                tempButtonNum = 5;
                tempCardName = client.MYCARDSProperty().get(5);
            }
        });
        DrawnCard6.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = DrawnCard6.startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(DrawnCard6.getImage());
                db.setContent(content);

                tempButtonNum = 6;
                tempCardName = client.MYCARDSProperty().get(6);
            }
        });
        DrawnCard7.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = DrawnCard7.startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(DrawnCard7.getImage());
                db.setContent(content);

                tempButtonNum = 7;
                tempCardName = client.MYCARDSProperty().get(7);
            }
        });
        DrawnCard8.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = DrawnCard8.startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(DrawnCard8.getImage());
                db.setContent(content);

                tempButtonNum = 8;
                tempCardName = client.MYCARDSProperty().get(8);
            }
        });


        Register1.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                dragEvent.acceptTransferModes(TransferMode.COPY);
            }
        });

        Register1.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                String url = Register1.getImage().getUrl();
                String urlSub = url.substring(url.length()-11,url.length());

                if(tempCardName.equals("Again")){
                    try {
                        LAUNCHER.launchError("Card Again can not be set in the first Register. Choose another card.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if(urlSub.equals("Discard.jpg") && !tempCardName.equals("Again")){
                    Register1.setImage(dragEvent.getDragboard().getImage());
                    regButton.put(1, tempButtonNum);
                    clearDrawnCardImage(tempButtonNum);
                    try {
                        // send selected card message to serverv
                        client.setRegister(tempCardName, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    remove1.setDisable(false);
                }
            }
        });

        Register2.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                dragEvent.acceptTransferModes(TransferMode.COPY);
            }
        });

        Register2.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {

                String url = Register2.getImage().getUrl();
                String urlSub = url.substring(url.length()-11,url.length());

                if(urlSub.equals("Discard.jpg")){
                    Register2.setImage(dragEvent.getDragboard().getImage());
                    regButton.put(2, tempButtonNum);
                    clearDrawnCardImage(tempButtonNum);
                    try {
                        // send selected card message to serverv
                        client.setRegister(tempCardName, 2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    remove2.setDisable(false);
                }
            }
        });

        Register3.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                dragEvent.acceptTransferModes(TransferMode.COPY);
            }
        });

        Register3.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {

                String url = Register3.getImage().getUrl();
                String urlSub = url.substring(url.length()-11,url.length());

                if(urlSub.equals("Discard.jpg")){
                    Register3.setImage(dragEvent.getDragboard().getImage());
                    regButton.put(3, tempButtonNum);
                    clearDrawnCardImage(tempButtonNum);
                    try {
                        // send selected card message to serverv
                        client.setRegister(tempCardName, 3);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    remove3.setDisable(false);
                }
            }
        });

        Register4.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                dragEvent.acceptTransferModes(TransferMode.COPY);
            }
        });

        Register4.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {

                String url = Register4.getImage().getUrl();
                String urlSub = url.substring(url.length()-11,url.length());

                if(urlSub.equals("Discard.jpg")){
                    Register4.setImage(dragEvent.getDragboard().getImage());
                    regButton.put(4, tempButtonNum);
                    clearDrawnCardImage(tempButtonNum);
                    try {
                        // send selected card message to serverv
                        client.setRegister(tempCardName, 4);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    remove4.setDisable(false);
                }
            }
        });

        Register5.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                dragEvent.acceptTransferModes(TransferMode.COPY);
            }
        });

        Register5.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {

                String url = Register5.getImage().getUrl();
                String urlSub = url.substring(url.length()-11,url.length());

                if(urlSub.equals("Discard.jpg")){
                    Register5.setImage(dragEvent.getDragboard().getImage());
                    regButton.put(5, tempButtonNum);
                    clearDrawnCardImage(tempButtonNum);
                    try {
                        // send selected card message to serverv
                        client.setRegister(tempCardName, 5);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    remove5.setDisable(false);
                }
            }
        });

        //bind CANSETSTARTPOINT to set the roboter in the game field
        client.CANSETSTARTPOINTProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(client.CANSETSTARTPOINT.get() == true){
                    gridPaneStartPoint.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            System.out.println(event.getSource());
                            GridPane gp = (GridPane) event.getTarget();
                            int x = (int) (event.getX() / 43);
                            int y = (int) (event.getY() / 43);
                            try {
                                client.setStartPoint(x,y);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }else{
                    gridPaneStartPoint.setOnMouseClicked(null);
                }
            }
        });

        //bind flagMyUpgrades to set the chosen UpgradeCards from the client
        client.flagMyUpgradesProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                for(String upCard : client.myUpgradesCards.keySet()){

                    switch (upCard){
                        case "ADMIN PRIVILEGE":
                            LabelAdmin.textProperty().set(""+(client.myUpgradesCards.get("ADMIN PRIVILEGE")));
                            break;
                        case "REAR LASER":
                            LabelLaser.textProperty().set(""+(client.myUpgradesCards.get("REAR LASER")));
                            break;
                        case "SPAM BLOCKER":
                            LabelBlocker.textProperty().set(""+(client.myUpgradesCards.get("SPAM BLOCKER")));
                            break;
                        case "MEMORY SWAP":
                            LabelMemory.textProperty().set(""+(client.myUpgradesCards.get("MEMORY SWAP")));
                            break;
                    }

                }
            }
        });

        UpgradeAdmin.setDisable(true);
        UpgradeMemory.setDisable(true);
        UpgradeBlocker.setDisable(true);
        UpgradeLaser.setOpacity(0.50);

        //bind flagAdmin for the ADMIN PRIVILEGE Upgrade Card
        client.flagAdminProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if(client.GAMEPHASE.get().equals("Programmierphase") && client.myUpgradesCards.get("ADMIN PRIVILEGE") != 0){
                    UpgradeAdmin.setDisable(false);
                }else{
                    UpgradeAdmin.setDisable(true);
                }
            }
        });

        //bind flagMemory for the MemorySwap Upgrade Card
        client.flagMemoryProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if(client.GAMEPHASE.get().equals("Programmierphase") && (client.myUpgradesCards.get("MEMORY SWAP") != 0)){
                    UpgradeMemory.setDisable(false);
                }else{
                    UpgradeMemory.setDisable(true);
                }
                if(client.myUpgradesCards.get("REAR LASER") != 0){
                    UpgradeLaser.setOpacity(1.0);
                }
            }
        });

        //bind flagBlocker for the SPAM BLOCKER Upgrade Card
        client.flagBlockerProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if(client.GAMEPHASE.get().equals("Programmierphase") && client.myUpgradesCards.get("SPAM BLOCKER") != 0){
                    UpgradeBlocker.setDisable(false);
                }else{
                    UpgradeBlocker.setDisable(true);
                }
            }
        });

        //bind flagMovingCheckpoints for the moving Checkpoints on the Twister Map
        client.flagMovingCheckpointsProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                // reset robot gridpane
                gridPaneCheckpoint.getChildren().clear();

                for(int checkPoint : client.movingCheckpoints.keySet()){
                    int[] loc = client.movingCheckpoints.get(checkPoint);
                    int curX = loc[0];
                    int curY = loc[1];

                    switch (checkPoint){
                        case 1:
                            ImageView check1 = new ImageView(imageCheckpoint1);
                            check1.setFitHeight(43);
                            check1.setFitWidth(43);
                            gridPaneCheckpoint.add(check1, curX, curY);
                            break;
                        case 2:
                            ImageView check2 = new ImageView(imageCheckpoint2);
                            check2.setFitHeight(43);
                            check2.setFitWidth(43);
                            gridPaneCheckpoint.add(check2, curX, curY);
                            break;
                        case 3:
                            ImageView check3 = new ImageView(ImageCheckpoint3);
                            check3.setFitHeight(43);
                            check3.setFitWidth(43);
                            gridPaneCheckpoint.add(check3, curX, curY);
                            break;
                        case 4:
                            ImageView check4 = new ImageView(ImageCheckpoint4);
                            check4.setFitHeight(43);
                            check4.setFitWidth(43);
                            gridPaneCheckpoint.add(check4, curX, curY);
                            break;
                    }
                }
            }
        });

        client.gameOnProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(client.gameOn.get() == true){
                    readyButton.setDisable(true);
                    notReadyButton.setDisable(true);
                }
            }
        });
    }

    /**
     * play upgrade Admin
     *
     * @throws JsonProcessingException the json processing exception
     */
    public void activeAdmin() throws JsonProcessingException {
        if(RegNrForAdmin.getText().equals("1") || RegNrForAdmin.getText().equals("2") || RegNrForAdmin.getText().equals("3") || RegNrForAdmin.getText().equals("4") || RegNrForAdmin.getText().equals("5")){

            System.out.println("activeAdmin Card ativated.");
            //update showCard and count of Card
            UpgradeAdmin.setDisable(true);
            client.flagMyUpgrades.set(client.flagMyUpgrades.get()+1);

            //client.playUpgrade("ADMIN PRIVILEGE");
            client.handleChooseRegister(Integer.valueOf(RegNrForAdmin.getText()));

            RegNrForAdmin.clear();

        }

    }

    /**
     * play upgrade Blocker
     *
     * @throws JsonProcessingException the json processing exception
     */
    public void activeBlocker() throws JsonProcessingException {
        System.out.println("activeBlocker Card ativated.");
        int curCount = client.myUpgradesCards.get("SPAM BLOCKER");
        client.myUpgradesCards.put("SPAM BLOCKER", curCount-1);
        //update showCard and count of Card
        client.flagBlocker.set(client.flagBlocker.get()+1);
        client.flagMyUpgrades.set(client.flagMyUpgrades.get()+1);

        client.playUpgrade("SPAM BLOCKER");
        //remove all the damage cards in hand
        List<String> tempToRemove = new ArrayList<>();
        for(String card : client.MYCARDS.get()){
            if(card.equals("Virus") || card.equals("Worm") || card.equals("Spam") || card.equals("Trojan")){
                tempToRemove.add(card);
            }
        }
        client.MYCARDS.removeAll(tempToRemove);
        System.out.println("after removed damage cards"+client.MYCARDS);
    }

    /**
     * play upgrade Memory
     *
     * @throws IOException the io exception
     */
    public void activeMemory() throws IOException {
        System.out.println("activeMemory Card activated.");
        int curCount = client.myUpgradesCards.get("MEMORY SWAP");
        client.myUpgradesCards.put("MEMORY SWAP", curCount-1);
        //update showCard and count of Card
        client.flagMemory.set(client.flagMemory.get()+1);
        client.flagMyUpgrades.set(client.flagMyUpgrades.get()+1);

        client.playUpgrade("MEMORY SWAP");

        LAUNCHER.launchMemorySwap(client);
    }

    /**
     * Clear drawn card image.
     *
     * @param cardButtoNum the card butto num
     */
    public void clearDrawnCardImage(int cardButtoNum){

        switch (cardButtoNum){
            case 0:
                DrawnCard0.setImage(imageDiscard);
                break;
            case 1:
                DrawnCard1.setImage(imageDiscard);
                break;
            case 2:
                DrawnCard2.setImage(imageDiscard);
                break;
            case 3:
                DrawnCard3.setImage(imageDiscard);
                break;
            case 4:
                DrawnCard4.setImage(imageDiscard);
                break;
            case 5:
                DrawnCard5.setImage(imageDiscard);
                break;
            case 6:
                DrawnCard6.setImage(imageDiscard);
                break;
            case 7:
                DrawnCard7.setImage(imageDiscard);
                break;
            case 8:
                DrawnCard8.setImage(imageDiscard);
                break;
        }
    }

    /**
     * Update registers.
     */
    public void updateRegisters() {
        StringProperty[] myregister = client.getMYREGISTER();
        for (int i = 0; i < 5; i++) {
            String cardName = myregister[i].get();
            Image curImage = null;
            switch (cardName) {
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
                case "Spam":
                    curImage = imageSpam;
                    break;
                case "Trojan":
                    curImage = imageTrojan;
                    break;
                case "Virus":
                    curImage = imageVirus;
                    break;
                case "Worm":
                    curImage = imageWorm;
                    break;
            }
            switch (i + 1) {
                case 1:
                    Register1.setImage(curImage);
                    break;
                case 2:
                    Register2.setImage(curImage);
                    break;
                case 3:
                    Register3.setImage(curImage);
                    break;
                case 4:
                    Register4.setImage(curImage);
                    break;
                case 5:
                    Register5.setImage(curImage);
                    break;
            }

        }
    }

    /**
     * Sets round over.
     */
    public void setRoundOver() {
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

        // reset register pointer to 0
        client.registerPointer = 0;
    }



    /** send method makes the message get sent from message
     * field to messages History(ScrollPane)**/
    @FXML
    private void send() throws JsonProcessingException {
        ObservableList<String> robotsnamesforchat = client.getROBOTSNAMESFORCHAT();
        if (sendto.getValue() == null) { // if no message destination, then it??s a public message
            client.sendMessage(messageField.getText());
        } else {
            int robotTO = 0;
            int clientSendTo = 0;
            String robotName = sendto.getValue();

            for(int robotNum : client.robotNumAndNames.keySet()){
                if(robotName.equals(client.robotNumAndNames.get(robotNum))){
                    robotTO = robotNum;
                }
            }

            for(int clientNum : client.robotFigureAllClients.keySet()){
                if(client.robotFigureAllClients.get(clientNum) == robotTO){
                    clientSendTo = clientNum;
                }
            }

            System.out.println(robotTO + " robotTO");
            client.sendPersonalMessage(clientSendTo, messageField.getText());
        }
        messageField.clear();

        sendto.getSelectionModel().clearSelection();
        sendto.setButtonCell(new ListCell<String>(){
            @Override
            protected void updateItem(String item, boolean empty){
                super.updateItem(item, empty);
                if(empty || item == null){
                    setText("public");
                }else{
                    setText(item);
                }
            }
        });
    }


    // Method for the "ready" Button
    @FXML
    private void setReady() throws JsonProcessingException {
        client.setReady();
    }


    // Method for the "not ready" Button
    @FXML
    private void setUnready() throws JsonProcessingException {
        client.setUnready();
    }


    //Method for the "Select Map" Button
    @FXML
    private void selectMapEvent() throws JsonProcessingException {
        String mapSelected = mapList.getValue();
        client.handleMapSelected(mapSelected);
    }



    // Method for the "Finish" Button
    @FXML
    private void finishEvent() throws JsonProcessingException {
        client.selectFinish();
        remove1.setDisable(true);
        remove2.setDisable(true);
        remove3.setDisable(true);
        remove4.setDisable(true);
        remove5.setDisable(true);
    }


    // Method to play the next card for the "Next Register" Button
    @FXML
    private void playNextRegistserEvent() throws JsonProcessingException {
        // get current register card
        String cardName = client.getMYREGISTER()[client.registerPointer].get();
        client.playNextRegister(cardName);
        switch (client.registerPointer) {
            case 0:
                Register1.setImage(imageDiscard);
                break;
            case 1:
                Register2.setImage(imageDiscard);
                break;
            case 2:
                Register3.setImage(imageDiscard);
                break;
            case 3:
                Register4.setImage(imageDiscard);
                break;
            case 4:
                Register5.setImage(imageDiscard);
                break;
        }
        client.registerPointer++;
        System.out.println("registerpointer " + client.registerPointer);
        // if round over, reset register pointer to 0 for next round
        if (client.registerPointer == 5) {
            client.flagRoundOverProperty().set(client.flagRoundOverProperty().getValue() + 1);
            System.out.println("round over checked by GUI");
            client.registerPointer = 0;
        }
    }

    /**
     * for each drawnButton function
     *
     * @param regNum the reg num
     * @param image  the image
     */
    public void setRegCard(int regNum, Image image) {
        switch (regNum) {
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

    /**
     * if remove the register, the image will show again in drawn cards
     *
     * @param buttonNum the button num
     * @param backImage the back image
     */
    public void setBackToDrawnCard(int buttonNum, Image backImage) {
        switch (buttonNum) {
            case 0:
                DrawnCard0.setImage(backImage);
                break;
            case 1:
                DrawnCard1.setImage(backImage);
                break;
            case 2:
                DrawnCard2.setImage(backImage);
                break;
            case 3:
                DrawnCard3.setImage(backImage);
                break;
            case 4:
                DrawnCard4.setImage(backImage);
                break;
            case 5:
                DrawnCard5.setImage(backImage);
                break;
            case 6:
                DrawnCard6.setImage(backImage);
                break;
            case 7:
                DrawnCard7.setImage(backImage);
                break;
            case 8:
                DrawnCard8.setImage(backImage);
                break;
        }
    }

    /**
     * find the removed back card image
     *
     * @param backCardName the back card name
     * @return image
     */
    public Image findBackImg(String backCardName) {

        Image backImg = null;

        switch (backCardName) {
            case "Again":
                backImg = imageAgain;
                break;
            case "BackUp":
                backImg = imageMoveBack;
                break;
            case "MoveI":
                backImg = imageMove1;
                break;
            case "MoveII":
                backImg = imageMove2;
                break;
            case "MoveIII":
                backImg = imageMove3;
                break;
            case "PowerUp":
                backImg = imagePowerUp;
                break;
            case "TurnLeft":
                backImg = imageTurnL;
                break;
            case "TurnRight":
                backImg = imageTurnR;
                break;
            case "UTurn":
                backImg = imageTurnU;
                break;
            case "Spam":
                backImg = imageSpam;
                break;
            case "Trojan":
                backImg = imageTrojan;
                break;
            case "Virus":
                backImg = imageVirus;
                break;
            case "Worm":
                backImg = imageWorm;
                break;
        }
        return backImg;
    }

    /**
     * Clear register 1.
     *
     * @throws IOException the io exception
     */
// clear each register and set image back to drawn cards
    public void clearRegister1() throws IOException {
        Integer buttonNum = regButton.get(1);
        String backCardName = client.getMYREGISTER()[0].get();
        Image backImg = findBackImg(backCardName);

        setBackToDrawnCard(buttonNum, backImg);

        client.setRegister(null, 1);
        Register1.setImage(imageDiscard);
        remove1.setDisable(true);
    }

    /**
     * Clear register 2.
     *
     * @throws IOException the io exception
     */
    public void clearRegister2() throws IOException {
        Integer buttonNum = regButton.get(2);
        String backCardName = client.getMYREGISTER()[1].get();
        Image backImg = findBackImg(backCardName);

        setBackToDrawnCard(buttonNum, backImg);

        client.setRegister(null, 2);
        Register2.setImage(imageDiscard);
        remove2.setDisable(true);
    }

    /**
     * Clear register 3.
     *
     * @throws IOException the io exception
     */
    public void clearRegister3() throws IOException {
        Integer buttonNum = regButton.get(3);
        String backCardName = client.getMYREGISTER()[2].get();
        Image backImg = findBackImg(backCardName);

        setBackToDrawnCard(buttonNum, backImg);

        client.setRegister(null, 3);
        Register3.setImage(imageDiscard);
        remove3.setDisable(true);
    }

    /**
     * Clear register 4.
     *
     * @throws IOException the io exception
     */
    public void clearRegister4() throws IOException {
        Integer buttonNum = regButton.get(4);
        String backCardName = client.getMYREGISTER()[3].get();
        Image backImg = findBackImg(backCardName);

        setBackToDrawnCard(buttonNum, backImg);

        client.setRegister(null, 4);
        Register4.setImage(imageDiscard);
        remove4.setDisable(true);
    }

    /**
     * Clear register 5.
     *
     * @throws IOException the io exception
     */
    public void clearRegister5() throws IOException {
        Integer buttonNum = regButton.get(5);
        String backCardName = client.getMYREGISTER()[4].get();
        Image backImg = findBackImg(backCardName);

        setBackToDrawnCard(buttonNum, backImg);

        client.setRegister(null, 5);
        Register5.setImage(imageDiscard);
        remove5.setDisable(true);
    }

    /**
     * set Map in GUI
     *
     * @param map the map
     */
    public void setMapInGUI(List<List<List<FeldObject>>> map) {
        System.out.println("flag setMap GUI");

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 10; j++) {
                List<FeldObject> feldObjects = map.get(i).get(j);
                for (FeldObject obj : feldObjects) {
                    if (!obj.getClass().getSimpleName().equals("Empty")) {

                        switch (obj.getClass().getSimpleName()) {
                            case "Antenna":

                                ImageView antennaImg = new ImageView(Antenna);
                                antennaImg.setFitHeight(43);
                                antennaImg.setFitWidth(43);
                                antennaImg.setRotate(antennaImg.getRotate() + 90);


                                gridPaneBoard.add(antennaImg, i, j);
                                break;
                            case "CheckPoint":

                                ImageView checkpointImg = null;
                                int checkPointNum = obj.getCount();
                                if (checkPointNum == 1) {
                                    checkpointImg = new ImageView(imageCheckpoint1);
                                    checkpointImg.setFitHeight(43);
                                    checkpointImg.setFitWidth(43);

                                } else if (checkPointNum == 2) {
                                    checkpointImg = new ImageView(imageCheckpoint2);
                                    checkpointImg.setFitHeight(43);
                                    checkpointImg.setFitWidth(43);
                                } else if (checkPointNum == 3) {
                                    checkpointImg = new ImageView(ImageCheckpoint3);
                                    checkpointImg.setFitHeight(43);
                                    checkpointImg.setFitWidth(43);
                                } else if (checkPointNum == 4) {
                                    checkpointImg = new ImageView(ImageCheckpoint4);
                                    checkpointImg.setFitHeight(43);
                                    checkpointImg.setFitWidth(43);
                                } else if (checkPointNum == 5) {
                                    checkpointImg = new ImageView(ImageCheckpoint5);
                                    checkpointImg.setFitHeight(43);
                                    checkpointImg.setFitWidth(43);
                                } else if (checkPointNum == 6) {
                                    checkpointImg = new ImageView(ImageCheckpoint6);
                                    checkpointImg.setFitHeight(43);
                                    checkpointImg.setFitWidth(43);
                                }
                                gridPaneBoard.add(checkpointImg, i, j);


                                break;

                            case "ConveyorBelt":
                                // if it is a green belt
                                if (obj.getSpeed() == 1) {

                                    if(obj.getOrientations().size() == 1){ // if only one direction

                                        ImageView beltGreenImg = new ImageView(GreenConveyorBelts);
                                        beltGreenImg.setFitHeight(43);
                                        beltGreenImg.setFitWidth(43);

                                        if(obj.getOrientations().get(0).equals("right")){
                                            beltGreenImg.setRotate(beltGreenImg.getRotate() + 90);
                                        }else if(obj.getOrientations().get(0).equals("bottom")){
                                            beltGreenImg.setRotate(beltGreenImg.getRotate() + 180);
                                        }else if(obj.getOrientations().get(0).equals("left")){
                                            beltGreenImg.setRotate(beltGreenImg.getRotate() + 270);
                                        }

                                        gridPaneBoard.add(beltGreenImg, i, j);
                                    }

                                    if(obj.getOrientations().size() == 2){ // for a corner belt
                                        if(obj.getOrientations().get(0).equals("top") && obj.getOrientations().get(1).equals("right")){
                                            ImageView topRight = new ImageView(GreenConveyorBelts90mirrored);
                                            topRight.setFitHeight(43);
                                            topRight.setFitWidth(43);
                                            topRight.setRotate(topRight.getRotate()+270);
                                            gridPaneBoard.add(topRight,i,j);
                                        }else if(obj.getOrientations().get(0).equals("right") && obj.getOrientations().get(1).equals("bottom")){
                                            ImageView rightBottom = new ImageView(GreenConveyorBelts90mirrored);
                                            rightBottom.setFitHeight(43);
                                            rightBottom.setFitWidth(43);
                                            gridPaneBoard.add(rightBottom,i,j);
                                        }else if(obj.getOrientations().get(0).equals("bottom") && obj.getOrientations().get(1).equals("left")){
                                            ImageView bottomLeft = new ImageView(GreenConveyorBelts90mirrored);
                                            bottomLeft.setFitHeight(43);
                                            bottomLeft.setFitWidth(43);
                                            bottomLeft.setRotate(bottomLeft.getRotate()+90);
                                            gridPaneBoard.add(bottomLeft,i,j);
                                        }else if(obj.getOrientations().get(0).equals("left") && obj.getOrientations().get(1).equals("top")){
                                            ImageView leftTop = new ImageView(GreenConveyorBelts90mirrored);
                                            leftTop.setFitHeight(43);
                                            leftTop.setFitWidth(43);
                                            leftTop.setRotate(leftTop.getRotate()+180);
                                            gridPaneBoard.add(leftTop,i,j);

                                            // mirrored direction
                                        }else if(obj.getOrientations().get(0).equals("top") && obj.getOrientations().get(1).equals("left")){
                                            ImageView topLeft = new ImageView(GreenConveyorBelts90);
                                            topLeft.setFitHeight(43);
                                            topLeft.setFitWidth(43);
                                            topLeft.setRotate(topLeft.getRotate()+90);
                                            gridPaneBoard.add(topLeft,i,j);
                                        }else if(obj.getOrientations().get(0).equals("right") && obj.getOrientations().get(1).equals("top")){
                                            ImageView rightTop = new ImageView(GreenConveyorBelts90);
                                            rightTop.setFitHeight(43);
                                            rightTop.setFitWidth(43);
                                            rightTop.setRotate(rightTop.getRotate()+180);
                                            gridPaneBoard.add(rightTop,i,j);
                                        }else if(obj.getOrientations().get(0).equals("bottom") && obj.getOrientations().get(1).equals("right")){
                                            ImageView bottomRight = new ImageView(GreenConveyorBelts90);
                                            bottomRight.setFitHeight(43);
                                            bottomRight.setFitWidth(43);
                                            bottomRight.setRotate(bottomRight.getRotate()+270);
                                            gridPaneBoard.add(bottomRight,i,j);
                                        }else if(obj.getOrientations().get(0).equals("left") && obj.getOrientations().get(1).equals("bottom")){
                                            ImageView leftBottom = new ImageView(GreenConveyorBelts90);
                                            leftBottom.setFitHeight(43);
                                            leftBottom.setFitWidth(43);
                                            gridPaneBoard.add(leftBottom,i,j);
                                        }else if(obj.getOrientations().get(0).equals("left") && obj.getOrientations().get(1).equals("right")){
                                            ImageView beltGreenSimple = new ImageView(GreenConveyorBelts);
                                            beltGreenSimple.setFitHeight(43);
                                            beltGreenSimple.setFitWidth(43);
                                            beltGreenSimple.setRotate(beltGreenSimple.getRotate()+270);
                                            gridPaneBoard.add(beltGreenSimple, i, j);
                                        }else if(obj.getOrientations().get(0).equals("right") && obj.getOrientations().get(1).equals("left")){
                                            ImageView beltGreenSimple = new ImageView(GreenConveyorBelts);
                                            beltGreenSimple.setFitHeight(43);
                                            beltGreenSimple.setFitWidth(43);
                                            beltGreenSimple.setRotate(beltGreenSimple.getRotate()+90);
                                            gridPaneBoard.add(beltGreenSimple, i, j);
                                        }
                                    }


                                } else {// then a blue belt
                                    ImageView beltBlueImg = new ImageView(BlueConveyorBelts);
                                    beltBlueImg.setFitHeight(43);
                                    beltBlueImg.setFitWidth(43);

                                    if (obj.getOrientations().get(0).equals("top")) {

                                        if (obj.getOrientations().size() == 2 && obj.getOrientations().get(1).equals("bottom")) {
                                            gridPaneBoard.add(beltBlueImg, i, j);
                                        }

                                        // if there are several orientations, choose another pic
                                        if (obj.getOrientations().size() > 2) {
                                            if (obj.getOrientations().get(1).equals("right") || obj.getOrientations().get(2).equals("right")) {
                                                ImageView bluerightImg = new ImageView(BlueConveyorBelts21mirror);
                                                bluerightImg.setFitHeight(43);
                                                bluerightImg.setFitWidth(43);
                                                bluerightImg.setRotate(bluerightImg.getRotate() + 90);
                                                gridPaneBoard.add(bluerightImg, i, j);

                                            } else if (obj.getOrientations().get(1).equals("left") || obj.getOrientations().get(2).equals("left")) {
                                                ImageView blueleftImg = new ImageView(BlueConveyorBelts21);
                                                blueleftImg.setFitHeight(43);
                                                blueleftImg.setFitWidth(43);
                                                blueleftImg.setRotate(blueleftImg.getRotate() + 180);
                                                gridPaneBoard.add(blueleftImg, i, j);
                                            }
                                        }

                                        if(obj.getOrientations().size() == 2){
                                            if(obj.getOrientations().get(1).equals("right")){
                                                ImageView topRightBlue = new ImageView(BlueConveyorBelts90);
                                                topRightBlue.setFitHeight(43);
                                                topRightBlue.setFitWidth(43);
                                                topRightBlue.setRotate(topRightBlue.getRotate() + 270);
                                                gridPaneBoard.add(topRightBlue, i, j);
                                            }else if(obj.getOrientations().get(1).equals("left")){
                                                ImageView topLeftBlue = new ImageView(BlueConveyorBelts90mirror);
                                                topLeftBlue.setFitHeight(43);
                                                topLeftBlue.setFitWidth(43);
                                                topLeftBlue.setRotate(topLeftBlue.getRotate() + 90);
                                                gridPaneBoard.add(topLeftBlue, i, j);
                                            }
                                        }


                                    } else if (obj.getOrientations().get(0).equals("right")) {

                                        if (obj.getOrientations().size() == 2 && obj.getOrientations().get(1).equals("left")) {
                                            beltBlueImg.setRotate(beltBlueImg.getRotate() + 90);
                                            gridPaneBoard.add(beltBlueImg, i, j);
                                        }

                                        // if there are several orientations, choose another pic
                                        if (obj.getOrientations().size() == 3) {
                                            if (obj.getOrientations().get(1).equals("top") || obj.getOrientations().get(2).equals("top")) {
                                                ImageView bluetopImg = new ImageView(BlueConveyorBelts21);
                                                bluetopImg.setFitHeight(43);
                                                bluetopImg.setFitWidth(43);
                                                bluetopImg.setRotate(bluetopImg.getRotate() + 270);
                                                gridPaneBoard.add(bluetopImg, i, j);
                                            } else if (obj.getOrientations().get(1).equals("bottom") || obj.getOrientations().get(2).equals("bottom")) {
                                                ImageView blueleftImg = new ImageView(BlueConveyorBelts21mirror);
                                                blueleftImg.setFitHeight(43);
                                                blueleftImg.setFitWidth(43);
                                                blueleftImg.setRotate(blueleftImg.getRotate() + 180);
                                                gridPaneBoard.add(blueleftImg, i, j);
                                            }
                                        }

                                        if(obj.getOrientations().size() == 2){
                                            if(obj.getOrientations().get(1).equals("top")){
                                                ImageView rightTopBlue = new ImageView(BlueConveyorBelts90mirror);
                                                rightTopBlue.setFitHeight(43);
                                                rightTopBlue.setFitWidth(43);
                                                rightTopBlue.setRotate(rightTopBlue.getRotate() + 180);
                                                gridPaneBoard.add(rightTopBlue, i, j);
                                            }else if(obj.getOrientations().get(1).equals("bottom")){
                                                ImageView rightTopBlue = new ImageView(BlueConveyorBelts90);
                                                rightTopBlue.setFitHeight(43);
                                                rightTopBlue.setFitWidth(43);
                                                gridPaneBoard.add(rightTopBlue, i, j);
                                            }
                                        }

                                    } else if (obj.getOrientations().get(0).equals("bottom")) {

                                        if (obj.getOrientations().size() == 2 && obj.getOrientations().get(1).equals("top")) {
                                            beltBlueImg.setRotate(beltBlueImg.getRotate() + 180);
                                            gridPaneBoard.add(beltBlueImg, i, j);
                                        }

                                        // if there are several orientations, choose another pic
                                        if (obj.getOrientations().size() > 2) {
                                            if (obj.getOrientations().get(1).equals("right") || obj.getOrientations().get(2).equals("right")) {
                                                ImageView bluebrImg = new ImageView(BlueConveyorBelts21);
                                                bluebrImg.setFitHeight(43);
                                                bluebrImg.setFitWidth(43);
                                                gridPaneBoard.add(bluebrImg, i, j);
                                            } else if (obj.getOrientations().get(1).equals("left") || obj.getOrientations().get(2).equals("left")) {
                                                ImageView blueblImg = new ImageView(BlueConveyorBelts21mirror);
                                                blueblImg.setFitHeight(43);
                                                blueblImg.setFitWidth(43);
                                                blueblImg.setRotate(blueblImg.getRotate()+270);
                                                gridPaneBoard.add(blueblImg, i, j);
                                            }
                                        }

                                        if(obj.getOrientations().size() == 2){
                                            if(obj.getOrientations().get(1).equals("right")){
                                                ImageView bottomRightBlue = new ImageView(BlueConveyorBelts90mirror);
                                                bottomRightBlue.setFitHeight(43);
                                                bottomRightBlue.setFitWidth(43);
                                                bottomRightBlue.setRotate(bottomRightBlue.getRotate() + 270);
                                                gridPaneBoard.add(bottomRightBlue, i, j);
                                            }else if(obj.getOrientations().get(1).equals("left")){
                                                ImageView bottomLeftBlue = new ImageView(BlueConveyorBelts90);
                                                bottomLeftBlue.setFitHeight(43);
                                                bottomLeftBlue.setFitWidth(43);
                                                bottomLeftBlue.setRotate(bottomLeftBlue.getRotate() + 90);
                                                gridPaneBoard.add(bottomLeftBlue, i, j);
                                            }
                                        }

                                    } else if (obj.getOrientations().get(0).equals("left")) {
                                        if (obj.getOrientations().size() == 2 && obj.getOrientations().get(1).equals("right")) {
                                            beltBlueImg.setRotate(beltBlueImg.getRotate() + 270);
                                            gridPaneBoard.add(beltBlueImg, i, j);
                                        }

                                        // if there are several orientations, choose another pic
                                        if (obj.getOrientations().size() > 2) {
                                            if (obj.getOrientations().get(1).equals("top") || obj.getOrientations().get(2).equals("top")) {
                                                ImageView blueltImg = new ImageView(BlueConveyorBelts21mirror);
                                                blueltImg.setFitHeight(43);
                                                blueltImg.setFitWidth(43);
                                                gridPaneBoard.add(blueltImg, i, j);
                                            } else if (obj.getOrientations().get(1).equals("bottom") || obj.getOrientations().get(2).equals("bottom")) {
                                                ImageView bluelbImg = new ImageView(BlueConveyorBelts21);
                                                bluelbImg.setFitHeight(43);
                                                bluelbImg.setFitWidth(43);
                                                bluelbImg.setRotate(bluelbImg.getRotate() + 90);
                                                gridPaneBoard.add(bluelbImg, i, j);
                                            }
                                        }

                                        if(obj.getOrientations().size() == 2){
                                            if(obj.getOrientations().get(1).equals("top")){
                                                ImageView leftTopBlue = new ImageView(BlueConveyorBelts90);
                                                leftTopBlue.setFitHeight(43);
                                                leftTopBlue.setFitWidth(43);
                                                leftTopBlue.setRotate(leftTopBlue.getRotate() + 180);
                                                gridPaneBoard.add(leftTopBlue, i, j);
                                            }else if(obj.getOrientations().get(1).equals("bottom")){
                                                ImageView leftBottomBlue = new ImageView(BlueConveyorBelts90mirror);
                                                leftBottomBlue.setFitHeight(43);
                                                leftBottomBlue.setFitWidth(43);
                                                gridPaneBoard.add(leftBottomBlue, i, j);
                                            }
                                        }

                                    }
                                }
                                break;
                            case "EnergySpace":
                                ImageView energyImg = new ImageView(EnergyOn);
                                energyImg.setFitHeight(43);
                                energyImg.setFitWidth(43);
                                gridPaneBoard.add(energyImg, i, j);
                                break;
                            case "Gear":

                                ImageView gearImg = null;
                                List<String> orientations = obj.getOrientations();
                                String richtung = orientations.get(0);

                                if (richtung.equals("clockwise")) {
                                    gearImg = new ImageView(GearTRight);
                                } else if (richtung.equals("counterclockwise")) {
                                    gearImg = new ImageView(GearTLeft);
                                }
                                gearImg.setFitHeight(43);
                                gearImg.setFitWidth(43);
                                gridPaneBoard.add(gearImg, i, j);
                                break;

                            case "Laser":
                                ImageView laserImg = new ImageView(Lasers1);
                                laserImg.setFitHeight(43);
                                laserImg.setFitWidth(43);
                                if (obj.getOrientations().get(0).equals("top") || obj.getOrientations().get(0).equals("bottom")) {
                                    laserImg.setRotate(laserImg.getRotate() + 90);
                                    gridPaneBoard.add(laserImg, i, j);
                                } else {
                                    gridPaneBoard.add(laserImg, i, j);
                                }

                                break;
                            case "Pit":
                                ImageView pitImg = new ImageView(Pits);
                                pitImg.setFitHeight(43);
                                pitImg.setFitWidth(43);
                                gridPaneBoard.add(pitImg, i, j);
                                break;
                            case "PushPanel":
                                ImageView pushImg = new ImageView(PushPanel1);
                                pushImg.setFitHeight(43);
                                pushImg.setFitWidth(43);
                                String pushDirection = obj.getOrientations().get(0);
                                if (pushDirection.equals("bottom")) {
                                    pushImg.setRotate(pushImg.getRotate() + 90);
                                } else if (pushDirection.equals("left")) {
                                    pushImg.setRotate(pushImg.getRotate() + 180);
                                } else if (pushDirection.equals("top")) {
                                    pushImg.setRotate(pushImg.getRotate() + 270);
                                }
                                gridPaneBoard.add(pushImg, i, j);
                                break;
                            case "RestartPoint":
                                ImageView restartImg = new ImageView(Reboot);
                                restartImg.setFitHeight(43);
                                restartImg.setFitWidth(43);
                                String dir = obj.getOrientations().get(0);
                                if(dir.equals("right")){
                                    restartImg.setRotate(restartImg.getRotate() + 90);
                                }else if(dir.equals("bottom")){
                                    restartImg.setRotate(restartImg.getRotate() + 180);
                                }else if(dir.equals("left")){
                                    restartImg.setRotate(restartImg.getRotate() + 270);
                                }

                                gridPaneBoard.add(restartImg, i, j);
                                break;
                            case "StartPoint":
                                ImageView startImg = new ImageView(Startpoint1);
                                startImg.setFitHeight(43);
                                startImg.setFitWidth(43);
                                gridPaneBoard.add(startImg, i, j);
                                break;
                            case "Wall":
                                ImageView wallImg = new ImageView(WallNormal);
                                wallImg.setFitHeight(43);
                                wallImg.setFitWidth(43);
                                if (obj.getOrientations().get(0).equals("left")) {
                                    gridPaneBoard.add(wallImg, i, j);
                                } else if (obj.getOrientations().get(0).equals("top")) {
                                    wallImg.setRotate(wallImg.getRotate() + 90);
                                    gridPaneBoard.add(wallImg, i, j);
                                } else if (obj.getOrientations().get(0).equals("right")) {
                                    wallImg.setRotate(wallImg.getRotate() + 180);
                                    gridPaneBoard.add(wallImg, i, j);
                                } else if (obj.getOrientations().get(0).equals("bottom")) {
                                    wallImg.setRotate(wallImg.getRotate() + 270);
                                    gridPaneBoard.add(wallImg, i, j);
                                }
                                break;
                        }
                    }
                }
            }
        }

        String mapName = client.mapName;

        switch (mapName){
            case "Dizzy Highway":
                ImageView laserHori1 = new ImageView(Lasers1);
                laserHori1.setFitHeight(43);
                laserHori1.setFitWidth(43);
                gridPaneBoard.add(laserHori1,9,3);

                ImageView laserHori2 = new ImageView(Lasers1);
                laserHori2.setFitHeight(43);
                laserHori2.setFitWidth(43);
                gridPaneBoard.add(laserHori2,6,6);

                ImageView laserVert1 = new ImageView(Lasers1);
                laserVert1.setFitHeight(43);
                laserVert1.setFitWidth(43);
                laserVert1.setRotate(laserVert1.getRotate()+90);
                gridPaneBoard.add(laserVert1,6,3);

                ImageView laserVert2 = new ImageView(Lasers1);
                laserVert2.setFitHeight(43);
                laserVert2.setFitWidth(43);
                laserVert2.setRotate(laserVert2.getRotate()+90);
                gridPaneBoard.add(laserVert2,9,6);
                break;
            case "Lost Bearings":
                ImageView laserHori3 = new ImageView(Lasers1);
                laserHori3.setFitHeight(43);
                laserHori3.setFitWidth(43);
                gridPaneBoard.add(laserHori3,6,3);

                ImageView laserHori4 = new ImageView(Lasers1);
                laserHori4.setFitHeight(43);
                laserHori4.setFitWidth(43);
                gridPaneBoard.add(laserHori4,7,3);

                ImageView laserHori5 = new ImageView(Lasers1);
                laserHori5.setFitHeight(43);
                laserHori5.setFitWidth(43);
                gridPaneBoard.add(laserHori5,8,3);

                ImageView laserHori6 = new ImageView(Lasers1);
                laserHori6.setFitHeight(43);
                laserHori6.setFitWidth(43);
                gridPaneBoard.add(laserHori6,7,6);

                ImageView laserHori7 = new ImageView(Lasers1);
                laserHori7.setFitHeight(43);
                laserHori7.setFitWidth(43);
                gridPaneBoard.add(laserHori7,8,6);

                ImageView laserHori8 = new ImageView(Lasers1);
                laserHori8.setFitHeight(43);
                laserHori8.setFitWidth(43);
                gridPaneBoard.add(laserHori8,9,6);

                break;
            case "Death Trap":
                break;
            case "Twister":

                ImageView laserHoriTwister1 = new ImageView(Lasers1);
                laserHoriTwister1.setFitHeight(43);
                laserHoriTwister1.setFitWidth(43);
                gridPaneBoard.add(laserHoriTwister1,7,0);

                ImageView laserHoriTwister2 = new ImageView(Lasers1);
                laserHoriTwister2.setFitHeight(43);
                laserHoriTwister2.setFitWidth(43);
                gridPaneBoard.add(laserHoriTwister2,8,9);

                ImageView laserVertiTwister3 = new ImageView(Lasers1);
                laserVertiTwister3.setFitHeight(43);
                laserVertiTwister3.setFitWidth(43);
                laserVertiTwister3.setRotate(laserVertiTwister3.getRotate()+90);
                gridPaneBoard.add(laserVertiTwister3,3,5);

                ImageView laserVertiTwister4 = new ImageView(Lasers1);
                laserVertiTwister4.setFitHeight(43);
                laserVertiTwister4.setFitWidth(43);
                laserVertiTwister4.setRotate(laserVertiTwister4.getRotate()+90);
                gridPaneBoard.add(laserVertiTwister4,12,4);

                break;
            case "Extra Crispy":

                ImageView laserHori9 = new ImageView(Lasers1);
                laserHori9.setFitHeight(43);
                laserHori9.setFitWidth(43);
                gridPaneBoard.add(laserHori9,10,0);

                ImageView laserHori10 = new ImageView(Lasers1);
                laserHori10.setFitHeight(43);
                laserHori10.setFitWidth(43);
                gridPaneBoard.add(laserHori10,5,9);

                ImageView laserVert3 = new ImageView(Lasers1);
                laserVert3.setFitHeight(43);
                laserVert3.setFitWidth(43);
                laserVert3.setRotate(laserVert3.getRotate()+90);
                gridPaneBoard.add(laserVert3,5,3);

                ImageView laserVert4 = new ImageView(Lasers1);
                laserVert4.setFitHeight(43);
                laserVert4.setFitWidth(43);
                laserVert4.setRotate(laserVert4.getRotate()+90);
                gridPaneBoard.add(laserVert4,5,4);

                ImageView laserVert5 = new ImageView(Lasers1);
                laserVert5.setFitHeight(43);
                laserVert5.setFitWidth(43);
                laserVert5.setRotate(laserVert5.getRotate()+90);
                gridPaneBoard.add(laserVert5,5,5);

                ImageView laserVert6 = new ImageView(Lasers1);
                laserVert6.setFitHeight(43);
                laserVert6.setFitWidth(43);
                laserVert6.setRotate(laserVert6.getRotate()+90);
                gridPaneBoard.add(laserVert6,5,6);

                ImageView laserVert7 = new ImageView(Lasers1);
                laserVert7.setFitHeight(43);
                laserVert7.setFitWidth(43);
                laserVert7.setRotate(laserVert7.getRotate()+90);
                gridPaneBoard.add(laserVert7,10,3);

                ImageView laserVert8 = new ImageView(Lasers1);
                laserVert8.setFitHeight(43);
                laserVert8.setFitWidth(43);
                laserVert8.setRotate(laserVert8.getRotate()+90);
                gridPaneBoard.add(laserVert8,10,4);

                ImageView laserVert9 = new ImageView(Lasers1);
                laserVert9.setFitHeight(43);
                laserVert9.setFitWidth(43);
                laserVert9.setRotate(laserVert9.getRotate()+90);
                gridPaneBoard.add(laserVert9,10,5);

                ImageView laserVert10 = new ImageView(Lasers1);
                laserVert10.setFitHeight(43);
                laserVert10.setFitWidth(43);
                laserVert10.setRotate(laserVert10.getRotate()+90);
                gridPaneBoard.add(laserVert10,10,6);

                break;
        }
    }
}
