package client.view;

import client.model.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;


/**
 * @author Yuliia Shaparenko
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class GameOverController {
    @FXML
    private TextArea winnerText;

    public void init(int winner) {
        winnerText.setText(""+winner);
    }

}
