package ai.database.fieldelements;

import server.game.Direction;

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

    @Override
    public int doBoardFunction(int x, int y, Direction dir, FeldObject obj) {
        return 100;
    }
}
