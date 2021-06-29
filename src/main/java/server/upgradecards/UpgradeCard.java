package server.upgradecards;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.IOException;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class UpgradeCard {

    String cardType; // PROGRAMME DAMAGE SPECIAL UPGRADE
    String cardName; // detailed name of each card
    String subType; // Passiv or Temporary


    public String getCardType() {
        return cardType;
    }

    public String getCardName() {
        return cardName;
    }

    public String getSubType() { return subType; }

    public void doCardFunction(int clientID) throws IOException {}
}
