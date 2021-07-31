package ai.database.fieldelements;
import server.game.Game;

import java.io.IOException;
import java.util.HashSet;

public class CheckPoint extends ElementGeneral {

    private String isOnBoard;
    private int count;

    public CheckPoint() {
    }

    public CheckPoint(String isOnBoard, int count) {
        //this.type = type;
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
    public void doBoardFunction(int clientID, ElementGeneral obj) throws IOException {
        int checkPointNum = obj.getCount();
        HashSet<Integer> set = Game.arrivedCheckpoints.get(clientID);
        set.add(checkPointNum);
        Game.arrivedCheckpoints.put(clientID, set);
        Game.getInstance().checkGameOver();
    }
}
