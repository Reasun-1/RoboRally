package client.view;


import client.model.Client;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
    private Label energyNow;

    @FXML
    private Button noSelection;

    @FXML
    private Button finishButton;

    @FXML
    private ImageView adminPrivilege;

    @FXML
    private ImageView spamBlocker;

    @FXML
    private ImageView rearLaser;

    @FXML
    private ImageView memorySwap;

    @FXML
    private Label adminKosten;

    @FXML
    private Label spamKosten;

    @FXML
    private Label rearKosten;

    @FXML
    private Label memoryKosten;

    //adding sound effects when the window gets opened
    MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass().getResource("/soundEffects/upgradeSound.mp3").toString()));

    /**
     * Method to be called from WindowLauncher to check the entered name.
     * @param
     */

    public void init(boolean isBuying, String curCount) {


        mediaPlayer.play();
        mediaPlayer.seek(Duration.ZERO);

        client.flagRefreshUpdateShopProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                for(String upGradeCard : client.availableUpgradesCards){
                    switch (upGradeCard){
                        case "AdminPrivilege":
                            adminKosten.textProperty().set(""+(client.availableUpgradesCards.get(+1)));
                            break;
                        case "RealLaser":
                            rearKosten.textProperty().set(""+(client.myUpgradesCards.get(+1)));
                            break;
                        case "SpamBlocker":
                            spamKosten.textProperty().set(""+(client.myUpgradesCards.get(+1)));
                            break;
                        case "MemorySwap":
                            memoryKosten.textProperty().set(""+(client.myUpgradesCards.get(+1)));
                            break;
                    }
                }

            }
        });





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
    }
    */

}
