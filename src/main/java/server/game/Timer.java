package server.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.log4j.Logger;
import protocol.ExecuteOrder;
import server.network.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Timer.
 *
 * @author Can Ren
 */
public class Timer implements Runnable {

    private static final Logger logger = Logger.getLogger(Timer.class.getName());

    /**
     * The constant timer.
     */
    public final static Timer timer = new Timer();

    /**
     * The Time interval.
     */
// set every 1 second updates
    final long timeInterval = 1000;
    /**
     * The Count.
     */
    int count = 0;
    /**
     * The constant flag.
     */
// flag for end or stop the timer
    public static boolean flag = true;

    @Override
    public void run() {

        while (flag){
            count++;
            System.out.println(count);
            try {
                Thread.sleep(timeInterval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // till 30 seconds, timer will stop
            if(count == 30){flag =false;}
        }
        // after timer stops, invoke other methods
        System.out.println("Timer: timer ended");

        // if time runs 30 seconds out, check who did not finish
        if(count == 30){
            // make a temp list to calculate who did not finish programming
            List<Integer> whoNotFinishProgramming = new ArrayList<>();
            whoNotFinishProgramming.addAll(Game.clientIDs);
            // remove who has finished
            for(int clientFinished : Game.selectionFinishList){
                whoNotFinishProgramming = Game.removeOneClientFromList(whoNotFinishProgramming, clientFinished);
            }
            logger.info("Timer info: clientsFinished: " + Game.selectionFinishList);
            logger.info("Timer info: restClients: " + whoNotFinishProgramming);
            // if someone not finished programming within 30 seconds, invoke CardsYouGotNow in Server
            if(whoNotFinishProgramming.size() != 0){
                logger.info("Timer : time out, random cards");
                // server informs who did not finished in time
                try {
                    Server.getServer().handleTimerEnded(whoNotFinishProgramming);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // give 5 random cards to each unfinished clients
                try {
                    Server.getServer().handleCardsYouGotNow(whoNotFinishProgramming);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Aktivierungsphase beginns
                try {
                    Server.getServer().handleActivePhase(3);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ExecuteOrder.activePhase = 3;
                // inform all clients about current register cards of all
                try {
                    Server.getServer().handleCurrentCards();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // set priority for this turn
                Game.getInstance().checkAndSetPriority();

                // set player in turn
                int curClient = Game.priorityEachTurn.get(0);
                try {
                    Server.getServer().handleCurrentPlayer(curClient);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Game.priorityEachTurn.remove(0);
            }
        }
    }

    // main only for test
    /*
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(Timer.timer);
        thread.start();

        Thread.sleep(5000);

        Timer.flag = false;
    }
     */

}
