package ai.database.fieldelements;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import server.feldobjects.FeldObject;

public class Empty extends ElementGeneral {

    private String isOnBoard;

    public Empty() {
    }

    public Empty(String isOnBoard) {
        //this.type = type;
        this.isOnBoard = isOnBoard;
    }

    @Override
    public String getIsOnBoard() {
        return isOnBoard;
    }

    @Override
    public void doBoardFunction(int clientID, ElementGeneral obj) {
    }
}
