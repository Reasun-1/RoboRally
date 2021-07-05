package server.upgradecards;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.IOException;

/**
 * The type Upgrade card.
 *
 * @author Can Ren
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class UpgradeCard {

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
    String subType; // Passiv or Temporary


    /**
     * Gets card type.
     *
     * @return the card type
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * Gets card name.
     *
     * @return the card name
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * Gets sub type.
     *
     * @return the sub type
     */
    public String getSubType() { return subType; }

    /**
     * Do card function.
     *
     * @param clientID the client id
     * @throws IOException the io exception
     */
    public void doCardFunction(int clientID) throws IOException {}
}
