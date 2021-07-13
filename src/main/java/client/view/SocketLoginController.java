package client.view;

import client.model.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Rajna Fani
 * @author Chiara Welz
 * @author Yuliia Shaparenko
 */
public class SocketLoginController {

    Client client;

    @FXML
    private TextField ip;

    @FXML
    private TextField port;

    @FXML
    private Button ok;

    public void init(Client client) throws IOException {
        this.client = client;
        //client.socket = new Socket(ip.getText(), Integer.valueOf(port.getText()));
    }

    @FXML
    public void clickOK() throws IOException {
        client.socket = new Socket(ip.getText(), Integer.valueOf(port.getText()));
        Stage stage = (Stage) ip.getScene().getWindow();
        stage.close();
    }
}
