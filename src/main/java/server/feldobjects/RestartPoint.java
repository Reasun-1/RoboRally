package server.feldobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * The FeldObject Restart point: When you fall off the board or into a pit,
 * or when you activate a worm card, you must reboot your robot.
 *
 * @author Jonas Gottal
 * @author Can Ren
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestartPoint extends FeldObject{

    /**
     * The Is on board.
     */
//String type;
    String isOnBoard;
    /**
     * The Orientations.
     */
    List<String> orientations;

    /**
     * Instantiates a new Restart point.
     */
    public RestartPoint() {
    }

    /**
     * Instantiates a new Restart point.
     *
     * @param isOnBoard    the is on board
     * @param orientations the orientations
     */
    public RestartPoint( String isOnBoard,List<String> orientations) {
      //  this.type = type;
        this.isOnBoard = isOnBoard;
        this.orientations = orientations;
    }

    /*@Override
    public String getType() {
        return type;
    }

     */

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
