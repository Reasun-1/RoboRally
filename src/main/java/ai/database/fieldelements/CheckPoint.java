package ai.database.fieldelements;
import server.game.Direction;
import server.game.Game;

import java.io.IOException;
import java.util.HashSet;

public class CheckPoint extends FeldObject {

    private String isOnBoard;
    private int count;

    public CheckPoint() {
    }

    public CheckPoint(String isOnBoard, int count) {

        this.isOnBoard = isOnBoard;
        this.count = count;
    }

    @Override
    public String getIsOnBoard() {
        return isOnBoard;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public int doBoardFunction(int x, int y, Direction dir, FeldObject obj){
        return 0;
    }
}
