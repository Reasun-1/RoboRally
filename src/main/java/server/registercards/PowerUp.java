package server.registercards;

import server.game.Game;
import server.network.Server;

import java.io.IOException;

/**
 * The type Power up.
 *
 * @author Can Ren
 * @author Megzon Mehmedali
 */
public class PowerUp extends RegisterCard{
    /**
     * The Card type.
     */
    String cardType; // PROGRAMME DAMAGE SPECIAL
    /**
     * The Card name.
     */
    String cardName; // detailed name of each card
    /**
     * The constant cardCount.
     */
    public static int cardCount = 1; // only as info for shuffle the cards

    /**
     * Instantiates a new Power up.
     */
    public PowerUp() {
        this.cardType = "PROGRAMME";
        this.cardName = "PowerUp";
    }

    @Override
    public String getCardType() {
        return cardType;
    }

    @Override
    public String getCardName() {
        return cardName;
    }

    @Override
    public int getCardCount() {
        return cardCount;
    }

    @Override
    public void doCardFunction(int clientID) throws IOException {
        //update energy cubes in Game
        Game.energyCubes.put(clientID, Game.energyCubes.get(clientID)+1);
        // send inform via server to all clients
        Server.getServer().handleEnergy(clientID, 1,"PowerUpCard");
    }
}
