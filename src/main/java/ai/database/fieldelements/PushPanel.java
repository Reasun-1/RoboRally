package ai.database.fieldelements;

import ai.database.Simulator;
import server.game.Direction;
import server.game.Game;
import server.game.Position;
import server.network.Server;

import java.io.IOException;
import java.util.List;

public class PushPanel extends FeldObject {


    private String isOnBoard;
    private List<String> orientations;
    private List<Integer> registers;

    public PushPanel() {
    }

    public PushPanel(String isOnBoard, List<String> orientations, List<Integer> registers) {
        //this.type = type;
        this.isOnBoard = isOnBoard;
        this.orientations = orientations;
        this.registers = registers;
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
    public List<Integer> getRegisters() {
        return registers;
    }

    @Override
    public int doBoardFunction(int curX, int curY, Direction direction, FeldObject obj){

        int resultDistance = Integer.MAX_VALUE;

        List<String> orientations = obj.getOrientations();
        String pushDir = orientations.get(0);
        List<Integer> registers = obj.getRegisters();


            Position newPosition = null;


            switch (pushDir) {
                case "top":
                    newPosition = new Position(curX, curY - 1);
                    break;
                case "bottom":
                    newPosition = new Position(curX, curY + 1);
                    break;
                case "right":
                    newPosition = new Position(curX + 1, curY);
                    break;
                case "left":
                    newPosition = new Position(curX - 1, curY);
                    break;
            }

        // check if robot is still on board
        if (newPosition.getX() < 0 || newPosition.getX() > 12 || newPosition.getY() < 0 || newPosition.getY() > 9) {
            resultDistance = 100;
        } else if (Simulator.board.get(newPosition.getX()).get(newPosition.getY()).get(0).getClass().getSimpleName().equals("Pit")) {// check if robot is on Pit
            resultDistance = 100;
        } else {
            Simulator.lastPosition = new Position(Simulator.curPosition.getX(), Simulator.curPosition.getY());
            Simulator.curPosition = new Position(newPosition.getX(), newPosition.getY());
            resultDistance = Math.abs(Simulator.checkpointPosition.getX() - Simulator.curPosition.getX()) + Math.abs(Simulator.checkpointPosition.getY() - Simulator.curPosition.getY());
        }

        return resultDistance;

    }
}
