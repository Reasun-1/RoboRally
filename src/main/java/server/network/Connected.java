package server.network;

import protocol.ExecuteOrder;
import server.game.Timer;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class Connected implements Runnable{

    private static final Logger logger = Logger.getLogger(Connected.class.getName());

    //public final static Connected con = new Connected();

    private int client;

    // flag for end or stop the connect check
    public boolean flagConnect = true;

    // set every 15 second updates
    final long timeInterval = 15000;
    int count = 0;



    public Connected(int client) {
        this.client = client;
    }

    @Override
    public void run() {
        System.out.println("do connected out of while loop");
        try {
            Thread.sleep(12000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (flagConnect){
            System.out.println("in while loop");
            System.out.println("in connected flag is: " + flagConnect);
            //Thread.sleep(timeInterval);

            // try to close connection if no alive checked
            try {
                Server.getServer().clientList.get(client).closeConnect();
                System.out.println("client " + client + " disconnected.");
                // stop check alive to this disconnected client
                ExecuteOrder.aliveCheckList.get(client).flagAliveCheck = false;
                flagConnect = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
