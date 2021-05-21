package client.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.*;


/**
 * @author Rajna Fani
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */

public class ErrorWindowController {
    @FXML
    private Checkbox robotIndeed;
    @FXML
    private Label errorField; // label provides the change of the error message based on the type of error classified on the Client class
    @FXML
    private Button okButton;
    /**
     * Method to be called from WindowLauncher to set the error that happens.
     *
     * @param msg
     */
    public void init(String msg) {
        errorField.setText(msg);
    }


    /**
     * Method that closes the stage after pressing the "ok" button and the other window decided
     * by WindowLauncher Class will get opened
     * @param event
     */
    @FXML
    private void okButtonClicked(ActionEvent event) {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
