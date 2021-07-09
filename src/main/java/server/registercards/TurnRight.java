package server.registercards;

import server.game.Direction;
import server.game.Game;
import server.network.Server;

import java.io.IOException;

/**
 * The type Turn right.
 *
 * @author Megzon Mehmedali
 * @author Can Ren
 * @author Jonas Gottal
 */
public class TurnRight extends RegisterCard{

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
    public static int cardCount = 3; // only as info for shuffle the cards

    /**
     * Instantiates a new Turn right.
     */
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
    public int getCardCount() {
        return cardCount;
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
