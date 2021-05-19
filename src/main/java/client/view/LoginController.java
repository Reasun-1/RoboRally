package client.view;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import client.network.Client;


import java.awt.*;


/**
 * @author can ren
 * @author yuliia shaparenko
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class LoginController {

    @FXML
    private TextField name;

    @FXML
    private Button startButton;

    private Client client;

    public void init(Client client) {
        this.client = client;
    }

//    @FXML
//    private void loginButton{
//
//    }

}
