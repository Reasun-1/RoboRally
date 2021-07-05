package client.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * The controller for Error window .
 *
 * @author Rajna Fani
 */
public class ErrorWindowController {

    @FXML
    private Label errorField; // label provides the change of the error message based on the type of error classified on the Client class
    @FXML
    private Button okButton;

    /**
     * The Media player.
     */
    MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass().getResource("/soundEffects/errorWindow.mp3").toString()));


    /**
     * Method to be called from WindowLauncher to set the error that happens.
     *
     * @param msg the msg
     */
    public void init(String msg) {
        errorField.setText(msg);
        mediaPlayer.setVolume(0.15);
        mediaPlayer.play();
        mediaPlayer.seek(Duration.ZERO);

    }


    /**
     * Method that closes the stage after pressing the "ok" button and the other window decided
     * by WindowLauncher Class will get opened
     * @param event
     */
    @FXML
    private void okButtonClicked(ActionEvent event) {
        //robotIndeed.setSelected(true); //if the "I am a robot" CheckBox is checked then you can press the OK Button
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
