package server.feldobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import server.game.Game;
import server.game.Position;
import server.network.Server;

import java.io.IOException;

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
    public void doBoardFunction(int clientID, FeldObject obj) throws IOException {
        //Game.getInstance().reboot(clientID, new Position(Game.rebootPosition.getX(), Game.rebootPosition.getY()));
    }
}
