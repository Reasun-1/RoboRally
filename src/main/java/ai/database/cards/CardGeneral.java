package ai.database.cards;

import java.io.IOException;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public abstract class CardGeneral {

    String cardType; // PROGRAMME DAMAGE SPECIAL
    String cardName; // detailed name of each card

    public String getCardType() {
        return cardType;
    }

    public String getCardName() {
        return cardName;
    }

    public void doCardFunction(int clientID) throws IOException {}
}
