package client.network;

import javafx.application.Application;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class Client extends Application{

    @Override
    public void init() {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {


    }


    @Override
    public void stop() {
        System.out.println("Stage is closing");
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }



}
