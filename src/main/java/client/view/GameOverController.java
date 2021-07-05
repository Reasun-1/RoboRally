package client.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


/**
 * The controller for  Game over.
 *
 * @author Yuliia Shaparenko
 * @author Rajna Fani
 */
public class GameOverController {
    @FXML
    private TextArea winnerText;

    /**
     * The Media player.
     */
    MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass().getResource("/soundEffects/gameOver.mp3").toString()));


    /**
     * Init.
     *
     * @param winner the winner
     */
    public void init(int winner) {
        mediaPlayer.play();
        mediaPlayer.setVolume(0.15);
        mediaPlayer.seek(Duration.ZERO);
        winnerText.setText(""+winner);
    }

}
