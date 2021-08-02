package client.view;

import client.model.Client;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/***
 * @author Chiara Welz
 * @author Rajna Fani
 * @author Yuliia Shaparenko
 * @author Can Ren
 */
public class selectDirectionControllr {

    private Client client;

    @FXML
    private Button up, right, down, left;

    public void init(Client client) {
        this.client = client;
    }

    @FXML
    public void clickUp() throws JsonProcessingException {

        client.handleRebootDirection("top");

        Stage stage = (Stage) up.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void clickDown() throws JsonProcessingException {

        client.handleRebootDirection("bottom");

        Stage stage = (Stage) down.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void clickRight() throws JsonProcessingException {

        client.handleRebootDirection("right");

        Stage stage = (Stage) right.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void clickLeft() throws JsonProcessingException {

        client.handleRebootDirection("left");

        Stage stage = (Stage) left.getScene().getWindow();
        stage.close();
    }
}
