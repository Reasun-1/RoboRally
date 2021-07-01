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
    private ImageView adminPrivilege, spamBlocker, rearLaser, memorySwap;
    @FXML
    private Label adminKosten, spamKosten, rearKosten, memoryKosten;
    @FXML
    private Label energyNow;

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

        adminKosten.textProperty().set(String.valueOf(0));
        rearKosten.textProperty().set(String.valueOf(0));
        spamKosten.textProperty().set(String.valueOf(0));
        memoryKosten.textProperty().set(String.valueOf(0));

        //binds count of energy cubes
        energyNow.textProperty().bindBidirectional(client.energyCountProperty());

        /*
        for(String availableUpgrade : client.availableUpgradesCards) {
            switch (availableUpgrade) {
                case "AdminPrivilege":
                    adminKosten.textProperty().set(adminKosten + client.availableUpgradesCards.get(+1));
                    break;
                case "RealLaser":
                    rearKosten.textProperty().set(rearKosten + client.availableUpgradesCards.get(+1));
                    break;
                case "SpamBlocker":
                    spamKosten.textProperty().set(spamKosten + client.availableUpgradesCards.get(+1));
                    break;
                case "MemorySwap":
                    memoryKosten.textProperty().set(memoryKosten + client.availableUpgradesCards.get(+1));
                    break;
            }

        } */
    }


    /**
     * Method creates a new event where another scene(window)
     * is opened after pressing the "start" Button on login window
     * @param event
     */
        /*
    @FXML
    private void noSelectionClicked(ActionEvent event) throws JsonProcessingException {
        Stage stage = (Stage) noSelection.getScene().getWindow();
        //client.setPlayerValues(name.getText(), Integer.valueOf(robotFigure.getText()));
        //client.s
        stage.close();
    } */


    }
