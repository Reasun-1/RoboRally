package ai.database.fieldelements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import server.feldobjects.FeldObject;


public class StartPoint extends ElementGeneral {

    String isOnBoard;

    public StartPoint() {
    }

    public StartPoint(String isOnBoard) {

        this.isOnBoard = isOnBoard;
    }


    @Override
    public String getIsOnBoard() {
        return isOnBoard;
    }

    @Override
    public void doBoardFunction(int clientID, ElementGeneral obj) {
        //TODO
    }
}
