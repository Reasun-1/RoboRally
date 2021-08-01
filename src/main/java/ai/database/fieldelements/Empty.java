package ai.database.fieldelements;

import ai.database.Simulator;
import server.game.Direction;

public class Empty extends FeldObject {

    private String isOnBoard;

    public Empty() {
    }

    public Empty(String isOnBoard) {
        //this.type = type;
        this.isOnBoard = isOnBoard;
    }

    @Override
    public String getIsOnBoard() {
        return isOnBoard;
    }

    @Override
    public int doBoardFunction(int curX, int curY, Direction direction, FeldObject obj) {
        return Math.abs(Simulator.checkpointPosition.getX() - Simulator.curPosition.getX()) + Math.abs(Simulator.checkpointPosition.getY() - Simulator.curPosition.getY());
    }
}
