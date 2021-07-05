package client.model;
import client.view.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashMap;

public class WindowLauncher {

    public static HashMap<Integer, Stage> chatWindowStage = new HashMap<>();

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
        stage.setScene(new Scene(root, 1250, 750));
        stage.show();
        chatWindowStage.put(client.getClientID(), stage);
        //stage.setOnCloseRequest((event) -> Platform.exit());
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                //Platform.exit();
                //System.exit(0);
                try {
                    client.getSocket().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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

    public void launchGameFinished(int winner) throws IOException {
        Stage stage = new Stage();
        stage.setTitle("GameFinished");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/GameOverWindow.fxml"));
        Parent root = loader.load();
        GameOverController controller = loader.getController();
        controller.init(winner);
        stage.setScene(new Scene(root, 600, 400));
        stage.showAndWait();
        stage.setOnCloseRequest((event) -> Platform.exit());

    }

    public void launchUpgradeShop(Client client) throws IOException{
        Stage stage = new Stage();
        stage.setTitle("Upgrade Shop for " + client.getClientID());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/UpgradeShop.fxml"));
        Parent root = loader.load();
        UpgradeShopController controller = loader.getController();
        controller.init(client);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
        stage.setOnCloseRequest((event) -> Platform.exit());

    }

    public void launchMemorySwap(Client client) throws IOException{
        Stage stage = new Stage();
        stage.setTitle("Memory Swap");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/MemorySwap.fxml"));
        Parent root = loader.load();
        MemorySwapController controller = loader.getController();
        controller.init(client);
        stage.setScene(new Scene(root, 600, 400));
        stage.showAndWait();
        stage.setOnCloseRequest((event) -> Platform.exit());

    }

}
