package server.registercards;

import server.game.Game;
import server.network.Server;

import java.io.IOException;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class PowerUp extends RegisterCard{
    String cardType; // PROGRAMME DAMAGE SPECIAL
    String cardName; // detailed name of each card
    public static int cardCount = 1; // only as info for shuffle the cards

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
