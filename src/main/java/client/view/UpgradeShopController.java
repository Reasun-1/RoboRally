package client.view;


import client.model.Client;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

/**
 * @author rajna fani
 * @author chiara welz
 * @author yuliia shaparenko
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */

public class UpgradeShopController {

    private Client client;

    @FXML
    private Button noSelection;
    @FXML
    private Button finishButton;
    @FXML
    private Button btnAdmin, btnSpam, btnLaser, btnMemory;
    @FXML
    private ImageView adminPrivilege, spamBlocker, rearLaser, memorySwap;
    @FXML
    private Label adminKosten, spamKosten, rearKosten, memoryKosten;
    @FXML
    private Label energyNow, chosenUpgrade;


    //adding sound effects when the window gets opened
    MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass().getResource("/soundEffects/upgradeSound.mp3").toString()));

    /**
     * Method to be called from WindowLauncher to check the entered name.
     * @param
     */

    public void init(Client client) {
        this.client = client;

        mediaPlayer.play();
        mediaPlayer.setVolume(0.15);
        mediaPlayer.seek(Duration.ZERO);

        //set the labels 0
        adminKosten.textProperty().set(String.valueOf(0));
        rearKosten.textProperty().set(String.valueOf(0));
        spamKosten.textProperty().set(String.valueOf(0));
        memoryKosten.textProperty().set(String.valueOf(0));

        //binds count of energy cubes
        energyNow.textProperty().bindBidirectional(client.energyCountProperty());

        //binds
        finishButton.disableProperty().bind(chosenUpgrade.textProperty().isEmpty());
    }


    /**
     * Method set the text "AdminPrivilige" in the textfield by press the button of this upgradecard.
     * @param event
     * @throws JsonProcessingException
     */
    @FXML
    private void adminAction(ActionEvent event) throws JsonProcessingException {
        chosenUpgrade.setText("AdminPrivilege");
    }
    /**
     * Method set the text "SpamBlocker" in the textfield by press the button of this upgradecard.
     * @param event
     * @throws JsonProcessingException
     */
    @FXML
    private void spamAction(ActionEvent event) throws JsonProcessingException {
        chosenUpgrade.setText("SpamBlocker");
    }
    /**
     * Method set the text "RealLaser" in the textfield by press the button of this upgradecard.
     * @param event
     * @throws JsonProcessingException
     */
    @FXML
    private void laserAction(ActionEvent event) throws JsonProcessingException {
        chosenUpgrade.setText("RealLaser");
    }
    /**
     * Method set the text "MemorySwap" in the textfield by press the button of this upgradecard.
     * @param event
     * @throws JsonProcessingException
     */
    @FXML
    private void memoryAction(ActionEvent event) throws JsonProcessingException {
        chosenUpgrade.setText("MemorySwap");
    }

    /**
     * Method creates a new event where another scene(window)
     * is opened after pressing the "finish"-Button on Upgrade Shop
     * @param event
     * @throws JsonProcessingException
     */
    @FXML
    private void finishAction(ActionEvent event) throws JsonProcessingException {
        Stage stage = (Stage) finishButton.getScene().getWindow();
        client.handleBuyUpgrade(chosenUpgrade.getText());
        stage.close();
    }

    /**
     * Method creates a new event where another scene(window)
     * is opened after pressing the "noSelection"-Button on Upgrade Shop
     * @param event
     * @throws JsonProcessingException
     */
    @FXML
    private void noSelectionAction(ActionEvent event) throws JsonProcessingException {
        Stage stage = (Stage) noSelection.getScene().getWindow();
        client.handleBuyUpgrade(null);
        //client.setPlayerValues(name.getText(), Integer.valueOf(robotFigure.getText()));
        //client.s
        stage.close();
    }


}
