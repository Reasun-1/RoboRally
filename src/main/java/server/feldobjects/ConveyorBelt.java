package server.feldobjects;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import server.game.Direction;

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

    private String type;
    private String isOnBoard;
    private int speed;
    private List<String> orientations;

    public ConveyorBelt() {
    }

    public ConveyorBelt(String type, String isOnBoard, int speed, List<String> orientations) {
        this.type = type;
        this.isOnBoard = isOnBoard;
        this.speed = speed;
        this.orientations = orientations;
    }

    @Override
    public String getType() {
        return type;
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
    public void doBoardFunction(int clientID) {
        //TODO
    }
}
