package server.feldobjects;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import server.game.Game;
import server.network.Server;

import java.io.IOException;

/**
 * The FeldObject Energy space: When you end a register on an energy space,
 * if there is an energy cube there, take it. If you end the fifth register
 * on an energy space, take an energy cube from the energy bank.
 *
 * @author Jonas Gottal
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnergySpace extends FeldObject{


    private String isOnBoard;
    private int count;

    /**
     * Instantiates a new Energy space.
     */
    public EnergySpace() {
    }

    /**
     * Instantiates a new Energy space.
     *
     * @param isOnBoard the is on board
     * @param count     the count
     */
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
    public void doBoardFunction(int clientID, FeldObject obj) throws IOException {
        int addNumEnergy = obj.getCount();
        //update energy cubes in Game
        Game.energyCubes.put(clientID, Game.energyCubes.get(clientID)+addNumEnergy);
        // send inform via server to all clients
        Server.getServer().handleEnergy(clientID, addNumEnergy,"EnergySpace");
    }
}
