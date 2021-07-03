package client.view;

import client.model.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;


/**
 * @author rajna fani
 * @author chiara welz
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */

public class MemorySwapController {

    private Client client;

    @FXML
    private Button card1Button, card2Button, card3Button, card4Button, card5Button,
            card6Button, card7Button, card8Button, card9Button, card10Button,card11Button, card12Button;

    @FXML
    private ImageView card1, card2, card3, card4, card5, card6,
            card7, card8, card9, card10, card11, card12;

    @FXML
    private Label chooseCards;

    public void init(Client client){

        this.client = client;

    }
}
