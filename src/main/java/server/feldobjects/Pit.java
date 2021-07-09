package server.feldobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.IOException;

/**
 * The FeldObject Pit: If you land on a pit, you immediately fall in and must reboot your robot.
 *
 * @author Jonas Gottal
 * @author Can Ren
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pit extends FeldObject{

   // private String type;
    private String isOnBoard;

    /**
     * Instantiates a new Pit.
     */
    public Pit() {
    }

    /**
     * Instantiates a new Pit.
     *
     * @param isOnBoard the is on board
     */
    public Pit(String isOnBoard) {
        //this.type = type;
        this.isOnBoard = isOnBoard;
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
    public void doBoardFunction(int clientID, FeldObject obj) throws IOException {
        //Game.getInstance().reboot(clientID, new Position(Game.rebootPosition.getX(), Game.rebootPosition.getY()));
    }
}
