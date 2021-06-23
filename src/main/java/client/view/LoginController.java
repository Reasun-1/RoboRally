package client.view;
import client.model.Client;
import client.viewmodel.LoginViewModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.awt.event.MouseListener;

/**
 * @author chiara welz
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



    /**
     * Method to be called from WindowLauncher to check the entered name.
     * @param client
     */
    public void init(Client client) {
        this.client = client;

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
