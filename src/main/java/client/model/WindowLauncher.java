package client.model;

import client.network.Client;
import client.view.ErrorWindowController;
import client.view.LoginController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author can ren
 * @author chiara welz
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
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
        loader.setLocation(getClass().getResource("/client/view/Login.fxml"));
        Parent root = loader.load();
        LoginController ctrl = loader.getController();
        ctrl.init(client);
        stage.setScene(new Scene(root, 600, 400));
        root.getStylesheets().add(getClass().getResource("/client/view/Gui.css").toString());
        stage.showAndWait();
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
        loader.setLocation(getClass().getResource("/client/view/ErrorWindow.fxml"));
        Parent root = loader.load();
        ErrorWindowController ctrl = loader.getController();
        //ctrl.init(message);
        stage.setScene(new Scene(root, 388, 186));
        stage.showAndWait();
        stage.setOnCloseRequest((event) -> Platform.exit());
    }


}
