package server.feldobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The FeldObject Start point: Where you place your robots initially.
 *
 * @author Jonas Gottal
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StartPoint extends FeldObject{

    /**
     * The Is on board.
     */
// String type;
    String isOnBoard;

    /**
     * Instantiates a new Start point.
     */
    public StartPoint() {
    }

    /**
     * Instantiates a new Start point.
     *
     * @param isOnBoard the is on board
     */
    public StartPoint(String isOnBoard) {
        //this.type = type;
        this.isOnBoard = isOnBoard;
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
    public void doBoardFunction(int clientID, FeldObject obj) {
        //TODO
    }
}
