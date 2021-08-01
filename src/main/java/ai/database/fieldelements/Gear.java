package ai.database.fieldelements;
import ai.database.Simulator;
import server.game.Direction;
import server.game.Game;
import server.network.Server;

import java.io.IOException;
import java.util.List;

public class Gear extends FeldObject {

    private String isOnBoard;
    private List<String> orientations;

    public Gear() {
    }

    public Gear(String isOnBoard, List<String> orientations) {

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
    public int doBoardFunction(int curX, int curY, Direction direction, FeldObject obj){

        String rotateDirection = obj.getOrientations().get(0);
        if(rotateDirection.equals("clockwise")){

            Simulator.curDirection = Direction.turnClock(direction);

        }else{

            Simulator.curDirection = Direction.turnCounterClock(direction);

        }
        return Math.abs(Simulator.checkpointPosition.getX() - Simulator.curPosition.getX()) + Math.abs(Simulator.checkpointPosition.getY() - Simulator.curPosition.getY());
    }
}
