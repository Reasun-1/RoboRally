package server.feldobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * The FeldObject Board laser: Board Lasers fire, hitting any robots in their line of sight.
 * Board lasers cannot fire through walls, the priority antenna, or hit more than one robot,
 * and they shoot from the red and white pointer.
 * (Take a SPAM damage card for each laser that hits you.)
 *
 * @author Jonas Gottal
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Laser extends FeldObject{

   // private String type;
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

   /* @Override
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
    public int getCount() {
        return count;
    }

    @Override
    public void doBoardFunction(int clientID, FeldObject obj) {
        //TODO
    }
}
