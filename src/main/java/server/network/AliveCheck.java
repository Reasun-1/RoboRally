package server.network;

import org.apache.log4j.Logger;
import protocol.ExecuteOrder;

import java.io.IOException;


/**
 * The type Alive check.
 *
 * @author Can Ren
 */
public class AliveCheck implements Runnable {

    private static final Logger logger = Logger.getLogger(AliveCheck.class.getName());

    /**
     * The Time interval.
     */
// set every 5 second updates
    final long timeInterval = 10000;

    /**
     * The Flag alive check.
     */
// flag for end alive check
    public boolean flagAliveCheck = true;

    private int client;

    /**
     * Instantiates a new Alive check.
     *
     * @param client the client
     */
    public AliveCheck(int client) {
        this.client = client;
    }

    @Override
    public void run() {

        while (flagAliveCheck){

            // set connected back zu while loop
            ExecuteOrder.connectList.get(client).flagConnect = true;

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


            logger.info("AliveCheck sends alive check, clientNr. " + client);
            logger.info("client " +  client +" flag connect = " + ExecuteOrder.connectList.get(client).flagConnect);

        }
    }
}
