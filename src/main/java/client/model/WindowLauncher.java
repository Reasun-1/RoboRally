package client.model;
import client.view.ChatController;
import client.view.ErrorWindowController;
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
        //stage.showAndWait();
        stage.show();
        stage.setOnCloseRequest((event) -> Platform.exit());
    }

    public void launchChat(Client client) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Chat: " + client.getClientID());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ChatAndGame.fxml"));
        Parent root = loader.load();
        ChatController ctrl = loader.getController();
        ctrl.init(client);
        stage.setScene(new Scene(root, 1226, 779));
        stage.show();
        stage.setOnCloseRequest((event) -> Platform.exit());
    }

    /**
     * Create an Error-Window and init the controller with the message to display
     *
     * @param message
     * @throws IOException
     */
    public void launchError(String message) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Error");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ErrorWindow.fxml"));
        Parent root = loader.load();
        ErrorWindowController ctrl = loader.getController();
        ctrl.init(message);
        stage.setScene(new Scene(root, 600, 400));
        stage.showAndWait();
        stage.setOnCloseRequest((event) -> Platform.exit());
    }

}
