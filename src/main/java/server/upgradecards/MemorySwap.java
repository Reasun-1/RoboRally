package server.upgradecards;

import server.game.Game;
import server.network.Server;

import java.io.IOException;
import java.util.List;

/**
 * The type Memory swap.
 *
 * @author Megzon Mehmedali
 * @author Can Ren
 * @author Jonas Gottal
 */
public class MemorySwap extends UpgradeCard{
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
     * Instantiates a new Memory swap.
     */
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
