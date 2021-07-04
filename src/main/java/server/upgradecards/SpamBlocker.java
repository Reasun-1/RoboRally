package server.upgradecards;

import server.game.Game;
import server.network.Server;
import server.registercards.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author can ren
 * @author Megzon Mehmedali
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class SpamBlocker extends UpgradeCard{
    String cardType; // PROGRAMME DAMAGE SPECIAL UPGRADE
    String cardName; // detailed name of each card
    String subType; // detailed type of card

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
