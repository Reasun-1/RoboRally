package server.network;

import client.model.Client;
import javafx.application.Application;

import java.io.IOException;

/**
 * The type Run client.
 *
 * @author Can Ren
 */
public class RunClient {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws InterruptedException the interrupted exception
     * @throws IOException          the io exception
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        Application.launch(Client.class, args);
    }
}
