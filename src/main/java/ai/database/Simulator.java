package ai.database;

import ai.AIWithDatabase;
import org.apache.log4j.Logger;
import server.feldobjects.FeldObject;
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

    public static List<String> cardsInHand = new ArrayList<>();

    public static List<List<List<FeldObject>>> board = new ArrayList<>();

    public static Position curPosition = new Position();

    public static String curDirection;

    public static Position newPosition = new Position();

    public static String newDirection;

    public static int curMinDistanceToCheckpoint;

    public static List<String> curBest5Cards;

    public static Position checkpointPosition;

    public static List<Position> pitPositions = new ArrayList<>();


    public static Simulator getInstance() {
        return simulator;
    }

    public void findCheckpoint() {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 10; j++) {
                if (board.get(i).get(j).size() == 2) {
                    if (board.get(i).get(j).get(1).getClass().getSimpleName().equals("CheckPoint")) {
                        checkpointPosition = new Position(i, j);
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
        // deep copy
        List<String> all9Cards = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            all9Cards.add(aiDatabase.myCards.get(i));
        }

        List<String> temp5Cards = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            if (all9Cards.contains("TurnLeft")) {
                temp5Cards.add("TurnLeft");
                all9Cards.remove("TurnLeft");
            } else if (all9Cards.contains("MoveII")) {
                temp5Cards.add("MoveII");
                all9Cards.remove("MoveII");
            } else if (all9Cards.contains("MoveI")) {
                temp5Cards.add("MoveI");
                all9Cards.remove("MoveI");
            } else if (all9Cards.contains("TurnRight")) {
                temp5Cards.add("TurnRight");
                all9Cards.remove("TurnRight");
            } else if (all9Cards.contains("MoveIII")) {
                temp5Cards.add("MoveIII");
                all9Cards.remove("MoveIII");
            } else if (all9Cards.contains("BackUp")) {
                temp5Cards.add("BackUp");
                all9Cards.remove("BackUp");
            } else if (all9Cards.contains("UTurn")) {
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
            for (int i = temp5Cards.size(); i < 4; i++) {
                all9Cards.get(0);
                all9Cards.remove(0);
            }
        }

        compareCards(temp5Cards);

        return curBest5Cards;
    }

    public void compareCards(List<String> temp5Cards) {

        List<String> bestCards = new ArrayList<>();
        curMinDistanceToCheckpoint = Integer.MAX_VALUE;

        int startDistance = simulateMoves(temp5Cards);

        curMinDistanceToCheckpoint = startDistance;
        curBest5Cards = temp5Cards;

        for (int i = 0; i < 4; i++) {
            for (int j = i + 1; j < 4; j++) {

                swap2Cards(temp5Cards, i, j);

                int curDistanceToCheckpoint = simulateMoves(temp5Cards);

                if (curDistanceToCheckpoint < curMinDistanceToCheckpoint) {
                    curMinDistanceToCheckpoint = curDistanceToCheckpoint;
                    curBest5Cards = temp5Cards;
                }
            }
        }
    }

    public void swap2Cards(List<String> toSortlist, int i, int j) {
        String tempI = toSortlist.get(i);
        toSortlist.set(i, toSortlist.get(j));
        toSortlist.set(j, tempI);
    }

    public int simulateMoves(List<String> fiveCards) {

        return 0;
    }
}
