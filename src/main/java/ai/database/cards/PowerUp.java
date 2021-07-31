package ai.database.cards;

import server.game.Game;
import server.network.Server;
import server.registercards.RegisterCard;

import java.io.IOException;

public class PowerUp extends CardGeneral {

    String cardType; // PROGRAMME DAMAGE SPECIAL
    String cardName; // detailed name of each card

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
    public void doCardFunction(int clientID) throws IOException {
        //update energy cubes in Game
        Game.energyCubes.put(clientID, Game.energyCubes.get(clientID)+1);
        // send inform via server to all clients
        Server.getServer().handleEnergy(clientID, 1,"PowerUpCard");
    }
}
