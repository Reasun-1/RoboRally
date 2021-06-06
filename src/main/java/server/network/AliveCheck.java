package server.network;

import server.game.Timer;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class AliveCheck implements Runnable {

    private static final Logger logger = Logger.getLogger(Timer.class.getName());

    public final static AliveCheck aliveCheck = new AliveCheck();

    // set every 5 second updates
    final long timeInterval = 10000;
    int count = 0;
    // flag for end or stop the timer
    public static boolean flag = true;

    @Override
    public void run() {

        while (true){
            count++;
            try {
                Thread.sleep(timeInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Server.getServer().handleAlive();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
