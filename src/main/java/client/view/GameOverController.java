package client.view;

import client.model.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


/**
 * @author Yuliia Shaparenko
 * @author Rajna Fani
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class GameOverController {
    @FXML
    private TextArea winnerText;

    MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass().getResource("/soundEffects/gameOver.mp3").toString()));


    public void init(int winner) {
        mediaPlayer.play();
        mediaPlayer.setVolume(0.15);
        mediaPlayer.seek(Duration.ZERO);
        winnerText.setText(""+winner);
    }

}
