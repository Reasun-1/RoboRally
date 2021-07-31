package ai.database.fieldelements;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import server.feldobjects.FeldObject;

import java.util.List;

public class Wall extends ElementGeneral {

    private String isOnBoard;
    private List<String> orientations;

    public Wall() {
    }

    public Wall( String isOnBoard, List<String> orientations) {

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
