package server.feldobjects;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import server.game.Direction;

import java.util.List;

/**
 * The FeldObject Wall: Robots cannot move through walls, and
 * robot and board lasers cannot shoot through walls.
 *
 * @author Jonas Gottal
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Wall extends FeldObject{

   // private String type;
    private String isOnBoard;
    private List<String> orientations;

    public Wall() {
    }

    public Wall( String isOnBoard, List<String> orientations) {
        //this.type = type;
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
    public void doBoardFunction(int clientID, FeldObject obj) {
        //TODO
    }
}
