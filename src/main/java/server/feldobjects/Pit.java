package server.feldobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pit extends FeldObject{

    private String type;
    private String isOnBoard;

    public Pit() {
    }

    public Pit(String type, String isOnBoard) {
        this.type = type;
        this.isOnBoard = isOnBoard;
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
    public void doBoardFunction(int clientID) {
        //TODO
    }
}
