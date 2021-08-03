package ai.database;

import ai.AIWithDatabase;
import ai.database.cards.*;
import ai.database.fieldelements.FeldObject;
import com.sun.security.jgss.GSSUtil;
import org.apache.log4j.Logger;
import server.game.Direction;
import server.game.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class Simulator {

    private static final Logger logger = Logger.getLogger(Simulator.class.getName());

    private final static Simulator simulator = new Simulator();

    public static AIWithDatabase aiWithDatabase;

    public static List<String> cardsInHand = new ArrayList<>();

    public static List<List<List<ai.database.fieldelements.FeldObject>>> board = new ArrayList<>();

    public static Position curPosition = new Position();

    public static Direction curDirection;

    public static Position lastPosition = new Position();

    public static String newDirection;

    public static int curMinDistanceToCheckpoint;

    public static List<String> curBest5Cards = new ArrayList<>();

    public static Position checkpointPosition;

    public static List<Position> pitPositions = new ArrayList<>();

    public static int curToReachCheckpoint;


    public static Simulator getInstance() {
        return simulator;
    }

    public void findCheckpoint(int numCheckpoint) {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 10; j++) {
                if (board.get(i).get(j).size() == 2) {
                    if (board.get(i).get(j).get(1).getClass().getSimpleName().equals("CheckPoint")) {
                        if(board.get(i).get(j).get(1).getCount() == numCheckpoint){
                            checkpointPosition = new Position(i, j);
                        }
                    }
                }

                if (board.get(i).get(j).size() == 3) {
                    if (board.get(i).get(j).get(2).getClass().getSimpleName().equals("CheckPoint")) {
                        if(board.get(i).get(j).get(2).getCount() == numCheckpoint){
                            checkpointPosition = new Position(i, j);
                        }
                    }
                }
            }
        }
    }

    public void findPit() {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 10; j++) {
                if (board.get(i).get(j).size() == 1) {
                    if (board.get(i).get(j).get(0).getClass().getSimpleName().equals("Pit")) {
                        pitPositions.add(new Position(i, j));
                    }
                }
            }
        }
    }

    public List<String> findBest5Cards(List<String> originalCards, AIWithDatabase aiDatabase) {

        aiWithDatabase = aiDatabase;
        curBest5Cards.clear();

        curPosition = new Position(aiDatabase.currentPositions.get(aiDatabase.clientID)[0],aiDatabase.currentPositions.get(aiDatabase.clientID)[1]);
        curDirection = aiDatabase.currentDirections.get(aiDatabase.clientID);

        // deep copy
        List<String> all9Cards = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            all9Cards.add(aiDatabase.myCards.get(i));
        }

        logger.info("all9Cards at the beginning: " + all9Cards);

        List<String> temp5Cards = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            if (all9Cards.contains("TurnLeft")) {
                temp5Cards.add("TurnLeft");
                all9Cards.remove("TurnLeft");
            }

            if (temp5Cards.size() == 5) {
                break;
            }

            if (all9Cards.contains("MoveII")) {
                temp5Cards.add("MoveII");
                all9Cards.remove("MoveII");
            }

            if (temp5Cards.size() == 5) {
                break;
            }

            if (all9Cards.contains("MoveI")) {
                temp5Cards.add("MoveI");
                all9Cards.remove("MoveI");
            }

            if (temp5Cards.size() == 5) {
                break;
            }

            if (all9Cards.contains("TurnRight")) {
                temp5Cards.add("TurnRight");
                all9Cards.remove("TurnRight");
            }

            if (temp5Cards.size() == 5) {
                break;
            }

            if (all9Cards.contains("MoveIII")) {
                temp5Cards.add("MoveIII");
                all9Cards.remove("MoveIII");
            }

            if (temp5Cards.size() == 5) {
                break;
            }

            if (all9Cards.contains("BackUp")) {
                temp5Cards.add("BackUp");
                all9Cards.remove("BackUp");
            }
            if (temp5Cards.size() == 5) {
                break;
            }

            if (all9Cards.contains("UTurn")) {
                temp5Cards.add("UTurn");
                all9Cards.remove("UTurn");
            } else if (all9Cards.contains("PowerUp")) {
                temp5Cards.add("PowerUp");
                all9Cards.remove("PowerUp");
            }

            if (temp5Cards.size() == 5) {
                break;
            }
        }

        if (temp5Cards.size() < 5) {
            for (int i = temp5Cards.size(); i < 5; i++) {
                all9Cards.get(0);
                all9Cards.remove(0);
            }
        }

        logger.info("temp5Cards before comparing: " + temp5Cards);

        compareCards(temp5Cards);

        return curBest5Cards;
    }

    public void compareCards(List<String> temp5Cards) {

        curMinDistanceToCheckpoint = Integer.MAX_VALUE;

        int startDistance = simulateMoves(temp5Cards);

        curMinDistanceToCheckpoint = startDistance;

        // deep copy!!
        for (int i = 0; i < 5; i++) {
            curBest5Cards.add(temp5Cards.get(i));
        }

        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 5; j++) {

                logger.info("swap i j : " + i + " " + j);

                List<String> permutedList = new ArrayList<>();
                for (int k = 0; k < 5; k++) {
                    permutedList.add(temp5Cards.get(k));
                }

                List<String> newTemp5Cards = swap2Cards(permutedList, i, j);

                logger.info("swaped temp5cards: " + newTemp5Cards);

                int curDistanceToCheckpoint = simulateMoves(newTemp5Cards);
                logger.info("current direction: " +  curDirection);

                if(curDistanceToCheckpoint == 0){
                    curBest5Cards.clear();
                    // deep copy!!!
                    for (int k = 0; k < 5; k++) {
                        curBest5Cards.add(newTemp5Cards.get(k));
                    }

                    curMinDistanceToCheckpoint = 0;
                    logger.info("to checkpoint 0 distance!!");
                    logger.info("curBest5 for now: " + curBest5Cards);
                    logger.info("curBestScore/Distance: " + curDistanceToCheckpoint);
                    logger.info("current Position: " + curPosition.getX() + " " + curPosition.getY());
                    logger.info("real direction: " +  aiWithDatabase.currentDirections.get(aiWithDatabase.clientID));
                    logger.info("current checkpoint: " + curToReachCheckpoint + " coordination: " + checkpointPosition.getX() + " " + checkpointPosition.getY());
                    break;
                }

                if (curDistanceToCheckpoint < curMinDistanceToCheckpoint) {
                    curMinDistanceToCheckpoint = curDistanceToCheckpoint;
                    curBest5Cards.clear();
                    // deep copy!!!
                    for (int k = 0; k < 5; k++) {
                        curBest5Cards.add(newTemp5Cards.get(k));
                    }

                    logger.info("curBest5 for now: " + curBest5Cards);
                    logger.info("curBestScore/Distance: " + curDistanceToCheckpoint);
                    logger.info("current checkpoint: " + curToReachCheckpoint + " coordination: " + checkpointPosition.getX() + " " + checkpointPosition.getY());
                    logger.info("real direction: " +  aiWithDatabase.currentDirections.get(aiWithDatabase.clientID));
                }

                logger.info("check cur best cards: " +  curBest5Cards);
            }
        }

        swap2Cards(temp5Cards, 0, 1);

        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 5; j++) {

                logger.info("swap i j : " + i + " " + j);

                List<String> permutedList = new ArrayList<>();
                for (int k = 0; k < 5; k++) {
                    permutedList.add(temp5Cards.get(k));
                }

                List<String> newTemp5Cards = swap2Cards(permutedList, i, j);

                logger.info("swaped temp5cards: " + newTemp5Cards);

                int curDistanceToCheckpoint = simulateMoves(newTemp5Cards);
                logger.info("current direction: " +  curDirection);

                if(curDistanceToCheckpoint == 0){
                    curBest5Cards.clear();
                    // deep copy!!!
                    for (int k = 0; k < 5; k++) {
                        curBest5Cards.add(newTemp5Cards.get(k));
                    }

                    curMinDistanceToCheckpoint = 0;
                    logger.info("to checkpoint 0 distance!!");
                    logger.info("curBest5 for now: " + curBest5Cards);
                    logger.info("curBestScore/Distance: " + curDistanceToCheckpoint);
                    logger.info("current Position: " + curPosition.getX() + " " + curPosition.getY());
                    logger.info("real direction: " +  aiWithDatabase.currentDirections.get(aiWithDatabase.clientID));
                    logger.info("current checkpoint: " + curToReachCheckpoint + " coordination: " + checkpointPosition.getX() + " " + checkpointPosition.getY());
                    break;
                }

                if (curDistanceToCheckpoint < curMinDistanceToCheckpoint) {
                    curMinDistanceToCheckpoint = curDistanceToCheckpoint;
                    curBest5Cards.clear();
                    // deep copy!!!
                    for (int k = 0; k < 5; k++) {
                        curBest5Cards.add(newTemp5Cards.get(k));
                    }

                    logger.info("curBest5 for now: " + curBest5Cards);
                    logger.info("curBestScore/Distance: " + curDistanceToCheckpoint);
                    logger.info("current checkpoint: " + curToReachCheckpoint + " coordination: " + checkpointPosition.getX() + " " + checkpointPosition.getY());
                    logger.info("real direction: " +  aiWithDatabase.currentDirections.get(aiWithDatabase.clientID));
                }

                logger.info("check cur best cards: " +  curBest5Cards);
            }
        }

        swap2Cards(temp5Cards, 0, 2);

        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 5; j++) {

                logger.info("swap i j : " + i + " " + j);

                List<String> permutedList = new ArrayList<>();
                for (int k = 0; k < 5; k++) {
                    permutedList.add(temp5Cards.get(k));
                }

                List<String> newTemp5Cards = swap2Cards(permutedList, i, j);

                logger.info("swaped temp5cards: " + newTemp5Cards);

                int curDistanceToCheckpoint = simulateMoves(newTemp5Cards);
                logger.info("current direction: " +  curDirection);

                if(curDistanceToCheckpoint == 0){
                    curBest5Cards.clear();
                    // deep copy!!!
                    for (int k = 0; k < 5; k++) {
                        curBest5Cards.add(newTemp5Cards.get(k));
                    }

                    curMinDistanceToCheckpoint = 0;
                    logger.info("to checkpoint 0 distance!!");
                    logger.info("curBest5 for now: " + curBest5Cards);
                    logger.info("curBestScore/Distance: " + curDistanceToCheckpoint);
                    logger.info("current Position: " + curPosition.getX() + " " + curPosition.getY());
                    logger.info("real direction: " +  aiWithDatabase.currentDirections.get(aiWithDatabase.clientID));
                    logger.info("current checkpoint: " + curToReachCheckpoint + " coordination: " + checkpointPosition.getX() + " " + checkpointPosition.getY());
                    break;
                }

                if (curDistanceToCheckpoint < curMinDistanceToCheckpoint) {
                    curMinDistanceToCheckpoint = curDistanceToCheckpoint;
                    curBest5Cards.clear();
                    // deep copy!!!
                    for (int k = 0; k < 5; k++) {
                        curBest5Cards.add(newTemp5Cards.get(k));
                    }

                    logger.info("curBest5 for now: " + curBest5Cards);
                    logger.info("curBestScore/Distance: " + curDistanceToCheckpoint);
                    logger.info("current checkpoint: " + curToReachCheckpoint + " coordination: " + checkpointPosition.getX() + " " + checkpointPosition.getY());
                    logger.info("real direction: " +  aiWithDatabase.currentDirections.get(aiWithDatabase.clientID));
                }

                logger.info("check cur best cards: " +  curBest5Cards);
            }
        }

        swap2Cards(temp5Cards, 0, 3);

        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 5; j++) {

                logger.info("swap i j : " + i + " " + j);

                List<String> permutedList = new ArrayList<>();
                for (int k = 0; k < 5; k++) {
                    permutedList.add(temp5Cards.get(k));
                }

                List<String> newTemp5Cards = swap2Cards(permutedList, i, j);

                logger.info("swaped temp5cards: " + newTemp5Cards);

                int curDistanceToCheckpoint = simulateMoves(newTemp5Cards);
                logger.info("current direction: " +  curDirection);

                if(curDistanceToCheckpoint == 0){
                    curBest5Cards.clear();
                    // deep copy!!!
                    for (int k = 0; k < 5; k++) {
                        curBest5Cards.add(newTemp5Cards.get(k));
                    }

                    curMinDistanceToCheckpoint = 0;
                    logger.info("to checkpoint 0 distance!!");
                    logger.info("curBest5 for now: " + curBest5Cards);
                    logger.info("curBestScore/Distance: " + curDistanceToCheckpoint);
                    logger.info("current Position: " + curPosition.getX() + " " + curPosition.getY());
                    logger.info("real direction: " +  aiWithDatabase.currentDirections.get(aiWithDatabase.clientID));
                    logger.info("current checkpoint: " + curToReachCheckpoint + " coordination: " + checkpointPosition.getX() + " " + checkpointPosition.getY());
                    break;
                }

                if (curDistanceToCheckpoint < curMinDistanceToCheckpoint) {
                    curMinDistanceToCheckpoint = curDistanceToCheckpoint;
                    curBest5Cards.clear();
                    // deep copy!!!
                    for (int k = 0; k < 5; k++) {
                        curBest5Cards.add(newTemp5Cards.get(k));
                    }

                    logger.info("curBest5 for now: " + curBest5Cards);
                    logger.info("curBestScore/Distance: " + curDistanceToCheckpoint);
                    logger.info("current checkpoint: " + curToReachCheckpoint + " coordination: " + checkpointPosition.getX() + " " + checkpointPosition.getY());
                    logger.info("real direction: " +  aiWithDatabase.currentDirections.get(aiWithDatabase.clientID));
                }

                logger.info("check cur best cards: " +  curBest5Cards);
            }
        }

        swap2Cards(temp5Cards, 0, 4);

        for (int i = 0; i < 5; i++) {
            for (int j = i + 1; j < 5; j++) {

                logger.info("swap i j : " + i + " " + j);

                List<String> permutedList = new ArrayList<>();
                for (int k = 0; k < 5; k++) {
                    permutedList.add(temp5Cards.get(k));
                }

                List<String> newTemp5Cards = swap2Cards(permutedList, i, j);

                logger.info("swaped temp5cards: " + newTemp5Cards);

                int curDistanceToCheckpoint = simulateMoves(newTemp5Cards);
                logger.info("current direction: " +  curDirection);

                if(curDistanceToCheckpoint == 0){
                    curBest5Cards.clear();
                    // deep copy!!!
                    for (int k = 0; k < 5; k++) {
                        curBest5Cards.add(newTemp5Cards.get(k));
                    }

                    curMinDistanceToCheckpoint = 0;
                    logger.info("to checkpoint 0 distance!!");
                    logger.info("curBest5 for now: " + curBest5Cards);
                    logger.info("curBestScore/Distance: " + curDistanceToCheckpoint);
                    logger.info("current Position: " + curPosition.getX() + " " + curPosition.getY());
                    logger.info("real direction: " +  aiWithDatabase.currentDirections.get(aiWithDatabase.clientID));
                    logger.info("current checkpoint: " + curToReachCheckpoint + " coordination: " + checkpointPosition.getX() + " " + checkpointPosition.getY());
                    break;
                }

                if (curDistanceToCheckpoint < curMinDistanceToCheckpoint) {
                    curMinDistanceToCheckpoint = curDistanceToCheckpoint;
                    curBest5Cards.clear();
                    // deep copy!!!
                    for (int k = 0; k < 5; k++) {
                        curBest5Cards.add(newTemp5Cards.get(k));
                    }

                    logger.info("curBest5 for now: " + curBest5Cards);
                    logger.info("curBestScore/Distance: " + curDistanceToCheckpoint);
                    logger.info("current checkpoint: " + curToReachCheckpoint + " coordination: " + checkpointPosition.getX() + " " + checkpointPosition.getY());
                    logger.info("real direction: " +  aiWithDatabase.currentDirections.get(aiWithDatabase.clientID));
                }

                logger.info("check cur best cards: " +  curBest5Cards);
            }
        }


    }

    public List<String> swap2Cards(List<String> toSortlist, int i, int j) {
        String tempI = toSortlist.get(i);
        toSortlist.set(i, toSortlist.get(j));
        toSortlist.set(j, tempI);
        return toSortlist;
    }

    public int simulateMoves(List<String> fiveCards) {

        int resultDistance = Integer.MAX_VALUE;

        curPosition = new Position(aiWithDatabase.currentPositions.get(aiWithDatabase.clientID)[0],aiWithDatabase.currentPositions.get(aiWithDatabase.clientID)[1]);
        curDirection = aiWithDatabase.currentDirections.get(aiWithDatabase.clientID);


        List<CardGeneral> cards = new ArrayList<>();

        for(String cardName : fiveCards){
            cards.add(convertStringToCards(cardName));
        }

        for (int i = 0; i < cards.size(); i++) {

            CardGeneral card = cards.get(i);

            if(!card.getCardName().equals("Again")){
                resultDistance = card.doCardFunction(curPosition.getX(), curPosition.getY(), curDirection);
            }else{
                resultDistance = cards.get(i-1).doCardFunction(curPosition.getX(), curPosition.getY(), curDirection);
            }

            if(resultDistance == 100){

                curPosition = new Position(aiWithDatabase.currentPositions.get(aiWithDatabase.clientID)[0],aiWithDatabase.currentPositions.get(aiWithDatabase.clientID)[1]);
                curDirection = aiWithDatabase.currentDirections.get(aiWithDatabase.clientID);

                break;
            }else if(resultDistance == 0){
                break;
            }
        }

        logger.info("simulate temp score: " + resultDistance);

        return resultDistance;
    }

    public CardGeneral convertStringToCards(String cardName){
        CardGeneral card = null;

        switch (cardName){
            case "Again":
                card = new Again();
                break;
            case "BackUp":
                card = new BackUp();
            break;
            case "BlankCard":
                card = new BlankCard();
            break;
            case "MoveI":
                card = new MoveI();
            break;
            case "MoveII":
                card = new MoveII();
            break;
            case "MoveIII":
                card = new MoveIII();
            break;
            case "PowerUp":
                card = new PowerUp();
            break;
            case "Spam":
                card = new Spam();
            break;
            case "Trojan":
                card = new Trojan();
            break;
            case "TurnLeft":
                card = new TurnLeft();
            break;
            case "TurnRight":
                card = new TurnRight();
            break;
            case "UTurn":
                card = new UTurn();
            break;
            case "Virus":
                card = new Virus();
            break;
            case "Worm":
                card = new Worm();
            break;
        }
        return card;
    }

    public String checkWall(int row, int column) {

        String wallOrientation = "";
        List<FeldObject> feldObjects = board.get(row).get(column);
        for (FeldObject obj : feldObjects) {
            if (obj.getClass().getSimpleName().equals("Wall")) {
                wallOrientation = obj.getOrientations().get(0);
            }
        }
        return wallOrientation;
    }

    public int doBoardFunction(){
        int result = Integer.MAX_VALUE;

        int x = curPosition.getX();
        int y = curPosition.getY();
        Direction dir = curDirection;

        List<FeldObject> feldObjects = Simulator.board.get(x).get(y);
        for (FeldObject obj : feldObjects) {
            if (!obj.getClass().getSimpleName().equals("Pit")) {
                result = obj.doBoardFunction(x, y, dir, obj);
            }
        }
        return result;
    }
}
