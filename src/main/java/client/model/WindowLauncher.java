package client.model;

import client.network.Client;
import client.view.LoginController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author can ren
 * @author Chiara Welz
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class WindowLauncher {
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


}
