package server.upgradecards;

import server.game.Game;
import server.network.Server;
import server.registercards.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * The type Spam blocker.
 *
 * @author Megzon Mehmedali
 * @author Can Ren
 * @author Jonas Gottal
 */
public class SpamBlocker extends UpgradeCard{
    /**
     * The Card type.
     */
    String cardType; // PROGRAMME DAMAGE SPECIAL UPGRADE
    /**
     * The Card name.
     */
    String cardName; // detailed name of each card
    /**
     * The Sub type.
     */
    String subType; // detailed type of card

    /**
     * Instantiates a new Spam blocker.
     */
    public SpamBlocker() {
        this.cardType = "UPGRADE";
        this.cardName = "SpamBlocker";
        this.subType = "Temporary";
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
    public String getSubType() { return subType; }

    @Override
    public void doCardFunction(int clientID) throws IOException {
        System.out.println("DO UPGRADE BLOCKER FUNCTION");

        int countNewcardForSpam = Game.countSpamAllClients.get(clientID);

        System.out.println("count spam in game: " + countNewcardForSpam);

        Stack<RegisterCard> tempDeck = Game.getInstance().drawTempDeck();

        List<String> newCards = new ArrayList<>();

        for (int i = 0; i < countNewcardForSpam; i++) {
            newCards.add(tempDeck.pop().getCardName());
        }
        Server.getServer().handleSpamBlocker(clientID, newCards);
    }
}
