package server.feldobjects;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.geometry.Orientation;

import java.util.List;

/**
 * The FeldObject Gear: Gears rotate robots resting on them 90 degrees
 * in the direction of the arrows.
 *
 * @author Jonas Gottal
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Gear extends FeldObject{

    //private String type;
    private String isOnBoard;
    private List<String> orientations;

    public Gear() {
    }

    public Gear(String isOnBoard, List<String> orientations) {

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
    public void doBoardFunction(int clientID) {
        //TODO
    }
}
