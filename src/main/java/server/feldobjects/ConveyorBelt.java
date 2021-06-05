package server.feldobjects;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import server.game.Direction;
import server.game.Game;
import server.game.Position;
import server.network.Server;

import java.io.IOException;
import java.util.List;

/**
 * The FeldObject Conveyor belt:  conveyor belts move any robot resting on them
 * one or two spaces in the direction of the arrows.
 *
 * @author Jonas Gottal
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConveyorBelt extends FeldObject{


    private String isOnBoard;
    private int speed;
    private List<String> orientations;

    public ConveyorBelt() {
    }

    public ConveyorBelt( String isOnBoard, int speed, List<String> orientations) {

        this.isOnBoard = isOnBoard;
        this.speed = speed;
        this.orientations = orientations;
    }

    @Override
    public String getIsOnBoard() {
        return isOnBoard;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public List<String> getOrientations() {
        return orientations;
    }

    @Override
    public void doBoardFunction(int clientID, FeldObject obj) throws IOException {
        int speed = obj.getSpeed();
        List<String> orientations = obj.getOrientations();
        String abfluss = orientations.get(0);

        Position position = Game.getInstance().playerPositions.get(clientID);
        int curX = position.getX();
        int curY = position.getY();

        Position newPosition = null;

        switch (abfluss){
            case "top":
                if(speed == 1){
                    newPosition = new Position(curX,curY-1);
                }else{
                    newPosition = new Position(curX,curY-2);
                }
                break;
            case "bottom":
                if(speed == 1){
                    newPosition = new Position(curX,curY+1);
                }else{
                    newPosition = new Position(curX,curY+2);
                }
                break;
            case "right":
                if(speed == 1){
                    newPosition = new Position(curX+1,curY);
                }else{
                    newPosition = new Position(curX+2,curY);
                }
                break;
            case "left":
                if(speed == 1){
                    newPosition = new Position(curX-1,curY);
                }else{
                    newPosition = new Position(curX-2,curY);
                }
                break;
        }

        // check if robot is still on board
        boolean isOnBoard = Game.getInstance().checkOnBoard(clientID, newPosition);
        if(isOnBoard){
            // set new Position in Game
            Game.playerPositions.put(clientID, newPosition);
            // transport new Position to client
            Server.getServer().handleMovement(clientID, newPosition.getX(), newPosition.getY());
        }
    }
}
