package ai.database.fieldelements;


import ai.database.Simulator;
import server.game.Direction;

public class StartPoint extends FeldObject {

    String isOnBoard;

    public StartPoint() {
    }

    public StartPoint(String isOnBoard) {

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
