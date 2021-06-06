package server.feldobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Jonas Gottal
 * @author Can Ren
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pit extends FeldObject{

   // private String type;
    private String isOnBoard;

    public Pit() {
    }

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
    public void doBoardFunction(int clientID, FeldObject obj) {
        //TODO
    }
}
