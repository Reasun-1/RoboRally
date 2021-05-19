package client.model;
import client.view.ChatController;
import client.view.LoginController;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class WindowLauncher {

    /**
     * Create a Login-Window and init the controller with a handle on the client
     *
     * @param client
     * @throws IOException
     */
    public void launchLogin(Client client) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Login");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Login.fxml"));
        Parent root = loader.load();
        LoginController ctrl = loader.getController();
        ctrl.init(client);
        stage.setScene(new Scene(root, 600, 400));
        stage.showAndWait();
        stage.setOnCloseRequest((event) -> Platform.exit());
    }

    public void launchChat(Client client) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Chat: " + client.getName());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Chat.fxml"));
        Parent root = loader.load();
        ChatController ctrl = loader.getController();
        ctrl.init(client);
        stage.setScene(new Scene(root, 1000, 600));
        stage.show();
        stage.setOnCloseRequest((event) -> Platform.exit());
    }


}
