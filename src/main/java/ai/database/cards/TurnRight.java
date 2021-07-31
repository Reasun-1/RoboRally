package ai.database.cards;

import server.game.Direction;
import server.game.Game;
import server.network.Server;
import server.registercards.RegisterCard;

import java.io.IOException;

public class TurnRight extends CardGeneral {

    String cardType; // PROGRAMME DAMAGE SPECIAL
    String cardName; // detailed name of each card

    public TurnRight() {
        this.cardType = "PROGRAMME";
        this.cardName = "TurnRight";
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
        //set direction of this client -90
        Direction curDir = Game.directionsAllClients.get(clientID);
        Direction newDir = Direction.turnClock(curDir);

        //update new direction in Game
        Game.directionsAllClients.put(clientID, newDir);
        // transport the new direction to clients
        Server.getServer().handlePlayerTurning(clientID, "clockwise");
    }
}
