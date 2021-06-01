package server.feldobjects;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * The FeldObject PriorityAntenna: The priority antenna helps determine whose turn it is.
 * As a board element, it acts as a wall. Robots cannot move through, push, shoot through,
 * or occupy the same space as the priority antenna.
 *
 * @author Jonas Gottal
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Antenna extends FeldObject{

    private String type;
    private String isOnBoard;
    private List<String> orientations;

    public Antenna() {
    }

    public Antenna(String type, String isOnBoard, List<String> orientations) {
        this.type = type;
        this.isOnBoard = isOnBoard;
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
    public List<String> getOrientations() {
        return orientations;
    }

    @Override
    public void doBoardFunction(int clientID) {
        //TODO
    }

    /**
     * Calculate distances.
     *
     * @param orientation the orientation
     */
//Todo • Die "Antenna" Orientierung gibt die Senderichtung an.
    public void calculateDistances(String orientation){
       // To determine who is closest to the priority antenna, start at the antenna and count the number of spaces to each robot.
       // Count by row, then by column.

    }
}
