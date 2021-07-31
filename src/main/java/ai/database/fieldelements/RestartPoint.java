package ai.database.fieldelements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import server.feldobjects.FeldObject;

import java.util.List;

public class RestartPoint extends ElementGeneral {

    String isOnBoard;
    List<String> orientations;

    public RestartPoint() {
    }

    public RestartPoint( String isOnBoard,List<String> orientations) {

        this.isOnBoard = isOnBoard;
        this.orientations = orientations;
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
    public void doBoardFunction(int clientID, ElementGeneral obj) {
        //TODO
    }
}
