package server.network;

import ai.AILow;
import client.model.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The type Main.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws InterruptedException the interrupted exception
     * @throws IOException          the io exception
     */
    /*
    public static void main(String[] args) throws InterruptedException, IOException {

        Thread t0 = new Thread(new Runnable() {

            public void run() {
                try {
                    Server server = new Server();
                    server.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        t0.start();

        Thread.sleep(5000);

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                Application.launch(Client.class, args);

            }
        });
        t1.start();


        Thread.sleep(5000);


        //optional: play with AI
        AILow ki = new AILow();
        Thread thread = new Thread(ki);
        thread.start();




        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName());
                    new Client().start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

     */

}
