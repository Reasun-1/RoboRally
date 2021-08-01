package ai.database.fieldelements;

import ai.database.Simulator;
import server.game.Direction;
import server.game.Game;
import server.network.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Laser extends FeldObject {

    private String isOnBoard;
    private List<String> orientations;
    private int count;

    public Laser() {
    }

    public Laser(String isOnBoard, List<String> orientations, int count) {

        this.isOnBoard = isOnBoard;
        this.orientations = orientations;
        this.count = count;
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
    public int getCount() {
        return count;
    }

    @Override
    public int doBoardFunction(int curX, int curY, Direction direction, FeldObject obj){
        return Math.abs(Simulator.checkpointPosition.getX() - Simulator.curPosition.getX()) + Math.abs(Simulator.checkpointPosition.getY() - Simulator.curPosition.getY());
    }

}
