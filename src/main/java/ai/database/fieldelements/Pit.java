package ai.database.fieldelements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import server.feldobjects.FeldObject;

import java.io.IOException;

public class Pit extends ElementGeneral {

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

    @Override
    public void doBoardFunction(int clientID, ElementGeneral obj) throws IOException {
        //Game.getInstance().reboot(clientID, new Position(Game.rebootPosition.getX(), Game.rebootPosition.getY()));
    }
}
