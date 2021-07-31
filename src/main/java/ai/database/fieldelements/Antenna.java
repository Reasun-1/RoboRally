package ai.database.fieldelements;

import java.util.List;

public class Antenna extends ElementGeneral {

    private String isOnBoard;
    private List<String> orientations;

    public Antenna() {
    }

    public Antenna(String isOnBoard, List<String> orientations) {

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
