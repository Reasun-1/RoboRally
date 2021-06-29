package client.view;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * @author rajna fani
 * @author chiara welz
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */

public class UpgradeShopController {

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

    //adding sound effects when the window gets opened
    MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass().getResource("/soundEffects/upgradeSound.mp3").toString()));

    /**
     * Method to be called from WindowLauncher to check the entered name.
     * @param ??
     */

    public void init() {
        mediaPlayer.play();
        mediaPlayer.seek(Duration.ZERO);
    }

}
