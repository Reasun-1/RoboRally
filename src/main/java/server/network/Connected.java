package server.network;

import org.apache.log4j.Logger;
import protocol.ExecuteOrder;

import java.io.IOException;


/**
 * The type Connected.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
public class Connected implements Runnable{

    private static final Logger logger = Logger.getLogger(Connected.class.getName());

    //public final static Connected con = new Connected();

    private int client;

    /**
     * The Flag connect.
     */
// flag for end or stop the connect check
    public boolean flagConnect;

    /**
     * The Time interval.
     */
// set every 15 second updates
    final long timeInterval = 15000;
    /**
     * The Count.
     */
    int count = 0;


    /**
     * Instantiates a new Connected.
     *
     * @param client the client
     */
    public Connected(int client) {
        this.client = client;
        flagConnect = false;
    }

    @Override
    public void run() {
        System.out.println("do connected out of while loop");
        /*
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

         */
        while (true){

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            logger.info(client + " in while loop print flagconnect: " + flagConnect);

            if(flagConnect){
                logger.info(client + " in first if print flagconnect: " + flagConnect);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(flagConnect){

                    logger.info("clientNr. "+ client + " in second if print flagconnect");
                    logger.info("clientNr. "+ client +" in connected flag is: " + flagConnect);


                    // try to close connection if no alive checked
                    try {
                        Server.getServer().clientList.get(client).closeConnect();
                        logger.info("client " + client + " disconnected.");
                        // stop check alive to this disconnected client
                        ExecuteOrder.aliveCheckList.get(client).flagAliveCheck = false;
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


            }

        }
    }
}
