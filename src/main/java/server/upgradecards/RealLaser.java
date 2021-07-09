package server.upgradecards;

import java.io.IOException;

/**
 * The type Real laser.
 *
 * @author Megzon Mehmedali
 * @author Can Ren
 * @author Jonas Gottal
 */
public class RealLaser extends UpgradeCard{
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
     * Instantiates a new Real laser.
     */
    public RealLaser() {
        this.cardType = "UPGRADE";
        this.cardName = "RealLaser";
        this.subType = "Passiv";
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
        System.out.println("DO UPGRADE LASER FUNCTION");
    }
}
