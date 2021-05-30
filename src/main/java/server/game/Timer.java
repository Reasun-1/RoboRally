package server.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class Timer implements Runnable {

    private static final Logger logger = Logger.getLogger(Timer.class.getName());

    public final static Timer timer = new Timer();

    // set every 1 second updates
    final long timeInterval = 1000;
    int count = 0;
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

        // make a temp list to calculate who did not finish programming
        List<Integer> whoNotFinishProgramming = new ArrayList<>();
        whoNotFinishProgramming.addAll(Game.clientIDs);
        // remove who has finished
        for(int clientFinished : Game.selectionFinishList){
            whoNotFinishProgramming = removeOneClientFromList(whoNotFinishProgramming, clientFinished);
        }
        // if someone not finished programming within 30 seconds, invoke CardsYouGotNow in Server
        if(whoNotFinishProgramming.size() != 0){
            logger.info("Timer : time out, random cards");
            // give 5 random cards to each unfinished clients
            // Aktivierungsphase beginns
            // set priority for this turn
            // set player in turn
        }
    }

    // help function to remove one client from a list
    public static List<Integer> removeOneClientFromList(List<Integer> list, int clientID){
        Iterator iterator = list.iterator();
        while (iterator.hasNext()){
            Object cur = iterator.next();
            if(cur.equals(5)){
                iterator.remove();
            }
        }
        return list;
    }

    // main only for test
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(Timer.timer);
        thread.start();

        Thread.sleep(5000);

        Timer.flag = false;
    }

}
