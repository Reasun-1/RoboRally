package ai.database.fieldelements;

import java.io.IOException;

public class Pit extends FeldObject {

    private String isOnBoard;

    public Pit() {
    }


    public Pit(String isOnBoard) {

        this.isOnBoard = isOnBoard;
    }


    @Override
    public String getIsOnBoard() {
        return isOnBoard;
    }


}
