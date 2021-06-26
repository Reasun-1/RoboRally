package server.network;

import client.model.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class RunClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        Application.launch(Client.class, args);
    }
}
