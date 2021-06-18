package server.registercards;

import server.game.Game;
import server.game.Position;

import java.io.IOException;

import static server.game.Game.rebootPosition;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class Worm extends RegisterCard{
    String cardType; // PROGRAMME DAMAGE SPECIAL
    String cardName; // detailed name of each card
    public static int cardCount = 6; // only as info for shuffle the cards

    public Worm() {
        this.cardType = "DAMAGE";
        this.cardName = "Worm";
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
        System.out.println("player played worm");
        Position position = Game.playerPositions.get(clientID);
        if (position.getX() >= 3) {
            Game.getInstance().reboot(clientID, new Position(rebootPosition.getX(), rebootPosition.getY()),false);
        } else if (position.getX() < 3) {
            Game.getInstance().reboot(clientID, Game.startPositionsAllClients.get(clientID),false);
        }
    }
}
