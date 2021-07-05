package server.upgradecards;

import server.game.Game;
import server.network.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author can ren
 * @author Megzon Mehmedali
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class MemorySwap extends UpgradeCard{
    String cardType; // PROGRAMME DAMAGE SPECIAL UPGRADE
    String cardName; // detailed name of each card
    String subType; // detailed type of card

    public MemorySwap() {
        this.cardType = "UPGRADE";
        this.cardName = "MemorySwap";
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
        System.out.println("DO UPGRADE MEMORY FUNCTION");
        List<String> threeCards = Game.getInstance().drawThreeCards();
        Server.getServer().handleMemorySwap(clientID, threeCards);
    }

}
