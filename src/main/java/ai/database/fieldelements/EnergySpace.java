package ai.database.fieldelements;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import server.feldobjects.FeldObject;
import server.game.Game;
import server.network.Server;

import java.io.IOException;

public class EnergySpace extends ElementGeneral {


    private String isOnBoard;
    private int count;

    public EnergySpace() {
    }

    public EnergySpace(String isOnBoard, int count) {
        //this.type = type;
        this.isOnBoard = isOnBoard;
        this.count = count;
    }

    @Override
    public String getIsOnBoard() {
        return isOnBoard;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public void doBoardFunction(int clientID, ElementGeneral obj) throws IOException {
        int addNumEnergy = obj.getCount();
        //update energy cubes in Game
        Game.energyCubes.put(clientID, Game.energyCubes.get(clientID)+addNumEnergy);
        // send inform via server to all clients
        Server.getServer().handleEnergy(clientID, addNumEnergy,"EnergySpace");
    }
}
