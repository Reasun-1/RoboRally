package ai.database.fieldelements;
import ai.database.Simulator;
import server.game.Direction;
import server.game.Game;
import server.network.Server;

import java.io.IOException;

public class EnergySpace extends FeldObject {


    private String isOnBoard;
    private int count;

    public EnergySpace() {
    }

    public EnergySpace(String isOnBoard, int count) {
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
    public int doBoardFunction(int curX, int curY, Direction direction, FeldObject obj){
        return Math.abs(Simulator.checkpointPosition.getX() - Simulator.curPosition.getX()) + Math.abs(Simulator.checkpointPosition.getY() - Simulator.curPosition.getY());

    }
}
