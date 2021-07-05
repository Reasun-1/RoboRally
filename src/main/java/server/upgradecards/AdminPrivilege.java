package server.upgradecards;

import server.game.Game;

import java.io.IOException;

/**
 * The type Admin privilege.
 *
 * @author Can Ren
 * @author Megzon Mehmedali
 */
public class AdminPrivilege extends UpgradeCard {

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
     * Instantiates a new Admin privilege.
     */
    public AdminPrivilege() {
        this.cardType = "UPGRADE";
        this.cardName = "AdminPrivilege";
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
        System.out.println("DO UPGRADE ADMIN FUNCTION");
        // set at which register the priority should be changed.
        Game.changePriorityRegPointer = Game.registerPointer;
        Game.changedPriorityList.add(clientID);
    }
}
