package ai;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class WindowLauncherAI {


    public void launchSocketAI(AIWithDatabase aiDatabase) throws IOException{
        Stage stage = new Stage();
        stage.setTitle("socket setting");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/SocketLoginAI.fxml"));
        Parent root = loader.load();
        SocketLoginControllerAI controller = loader.getController();
        controller.init(aiDatabase);
        stage.setScene(new Scene(root, 400, 400));
        stage.showAndWait();
    }


}
