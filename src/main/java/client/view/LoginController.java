package client.view;
import client.model.Client;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.beans.property.ListProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


/**
 * @author chiara welz
 * @author Rajna Fani
 * @author yuliia shaparenko
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class LoginController {

    private Client client;

    @FXML
    private Label clientID;
    @FXML
    private TextField name;
    @FXML
    private TextArea robotFigure;
    @FXML
    private Button startButton;
    @FXML
    private Button iconHulk, iconSpinbot, iconSquashbot, iconTrundlebot, iconTwitch, iconTwonky;

    MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass().getResource("/soundEffects/loginwindowSound.mp3").toString()));

    /**
     * Method to be called from WindowLauncher to check the entered name.
     * @param client
     */
    public void init(Client client) {
        this.client = client;
        mediaPlayer.setVolume(0.15);
        mediaPlayer.play();
        mediaPlayer.seek(Duration.ZERO);

        clientID.textProperty().bindBidirectional(client.getCLIENTIDASSTRINGPROPERTY());

        startButton.disableProperty().bind(robotFigure.textProperty().isEmpty().or(name.textProperty().isEmpty()));

        //Chose the robot NR. per button
        iconHulk.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                robotFigure.setText(String.valueOf(1));
            }
        });

        iconSpinbot.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                robotFigure.setText(String.valueOf(2));
            }
        });

        iconSquashbot.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                robotFigure.setText(String.valueOf(3));
            }
        });

        iconTrundlebot.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                robotFigure.setText(String.valueOf(4));
            }
        });

        iconTwitch.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                robotFigure.setText(String.valueOf(5));
            }
        });

        iconTwonky.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                robotFigure.setText(String.valueOf(6));
            }
        });


        //bind robotsNames Propertylist in Client with Buttons
        client.ROBOTSNAMESFORCHATProperty().addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {
                iconHulk.setDisable(false);
                iconSpinbot.setDisable(false);
                iconSquashbot.setDisable(false);
                iconTrundlebot.setDisable(false);
                iconTwitch.setDisable(false);
                iconTwonky.setDisable(false);

                ListProperty<String> robots = client.ROBOTSNAMESFORCHATProperty();
                for(String robot : robots){
                    switch (robot){
                        case "Hulk":
                            iconHulk.setDisable(true);
                            break;
                        case "Spinbot":
                            iconSpinbot.setDisable(true);
                            break;
                        case "Squashbot":
                            iconSquashbot.setDisable(true);
                            break;
                        case "Trundlebot":
                            iconTrundlebot.setDisable(true);
                            break;
                        case "Twitch":
                            iconTwitch.setDisable(true);
                            break;
                        case "Twonky":
                            iconTwonky.setDisable(true);
                            break;

                    }
                }
            }
        });
    }

    @FXML
    /**
     * Method creates a new event where another scene(window)
     * is opened after pressing the "start" Button on login window
     * @param event
     */
    private void loginButton(ActionEvent event) throws JsonProcessingException {
        Stage stage = (Stage) startButton.getScene().getWindow();
        //client.checkName(name.getText());
        //client.setName(name.getText());
        //client.setRobotFigureAllClients(client.getClientID(), Integer.valueOf(robotFigure.getText()));
        client.setPlayerValues(name.getText(), Integer.valueOf(robotFigure.getText()));
        stage.close();
    }



}
