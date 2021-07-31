package ai.database.fieldelements;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import server.feldobjects.FeldObject;
import server.game.Direction;
import server.game.Game;
import server.network.Server;

import java.io.IOException;
import java.util.List;

public class Gear extends ElementGeneral {

    private String isOnBoard;
    private List<String> orientations;

    public Gear() {
    }

    public Gear(String isOnBoard, List<String> orientations) {

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
    public void doBoardFunction(int clientID, ElementGeneral obj) throws IOException {
        String rotateDirection = obj.getOrientations().get(0);
        if(rotateDirection.equals("clockwise")){
            //set direction of this client -90
            Direction curDir = Game.directionsAllClients.get(clientID);
            Direction newDir = Direction.turnClock(curDir);

            //update new direction in Game
            Game.directionsAllClients.put(clientID, newDir);
            // transport the new direction to clients
            Server.getServer().handlePlayerTurning(clientID, "clockwise");
        }else{
            //set direction of this client -90
            Direction curDir = Game.directionsAllClients.get(clientID);
            Direction newDir = Direction.turnCounterClock(curDir);

            //update new direction in Game
            Game.directionsAllClients.put(clientID, newDir);
            // transport the new direction to clients
            Server.getServer().handlePlayerTurning(clientID, "counterclockwise");
        }
    }
}
