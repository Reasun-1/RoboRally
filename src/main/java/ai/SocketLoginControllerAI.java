package ai;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class SocketLoginControllerAI {

    AIWithDatabase aiDatabase;

    @FXML
    private TextField ip;

    @FXML
    private TextField port;

    @FXML
    private TextField version;

    @FXML
    private Button ok;

    public void init(AIWithDatabase aiDatabase) throws IOException {
        this.aiDatabase = aiDatabase;
        //client.socket = new Socket(ip.getText(), Integer.valueOf(port.getText()));
    }

    @FXML
    public void clickOK() throws IOException {
        aiDatabase.socket = new Socket(ip.getText(), Integer.valueOf(port.getText()));
        aiDatabase.versionProtocol = version.getText();
        Stage stage = (Stage) ip.getScene().getWindow();
        stage.close();
    }
}
