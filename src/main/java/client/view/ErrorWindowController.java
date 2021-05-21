package client.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.CheckBox;

import javax.swing.*;
import java.awt.*;


/**
 * @author Rajna Fani
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */

public class ErrorWindowController {
    @FXML
    private JCheckBox robotIndeed;
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
        //robotIndeed.setSelected(true); //if the "I am a robot" CheckBox is checked then you can press the OK Button
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}
