package server.network;

import protocol.ExecuteOrder;
import server.game.Timer;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class AliveCheck implements Runnable {

    private static final Logger logger = Logger.getLogger(AliveCheck.class.getName());

    // set every 5 second updates
    final long timeInterval = 10000;

    // flag for end alive check
    public boolean flagAliveCheck = true;

    private int client;

    public AliveCheck(int client) {
        this.client = client;
    }

    @Override
    public void run() {

        while (flagAliveCheck){
            try {
                Server.getServer().handleAlive(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(timeInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // set connected back zu while loop
            ExecuteOrder.connectList.get(client).flagConnect = true;

        }
    }
}
