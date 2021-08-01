package ai.database.fieldelements;

import ai.database.Simulator;
import server.game.Direction;

import java.util.List;

public class RestartPoint extends FeldObject {

    String isOnBoard;
    List<String> orientations;

    public RestartPoint() {
    }

    public RestartPoint( String isOnBoard,List<String> orientations) {

        this.isOnBoard = isOnBoard;
        this.orientations = orientations;
    }

    @Override
    public String getIsOnBoard() {
        return isOnBoard;
    }

    @Override
    public List<String> getOrientations() {
        return orientations;
    }

    @Override
    public int doBoardFunction(int curX, int curY, Direction direction, FeldObject obj) {
       return Math.abs(Simulator.checkpointPosition.getX() - Simulator.curPosition.getX()) + Math.abs(Simulator.checkpointPosition.getY() - Simulator.curPosition.getY());
    }
}
