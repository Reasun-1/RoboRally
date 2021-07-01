package server.upgradecards;

import java.io.IOException;

/**
 * @author can ren
 * @author Megzon Mehmedali
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class AdminPrivilege extends UpgradeCard {

    String cardType; // PROGRAMME DAMAGE SPECIAL UPGRADE
    String cardName; // detailed name of each card
    String subType; // detailed type of card

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
    }
}
