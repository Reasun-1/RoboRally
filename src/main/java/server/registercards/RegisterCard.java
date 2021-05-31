package server.registercards;

import java.io.IOException;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public abstract class RegisterCard {

    String cardType; // PROGRAMME DAMAGE SPECIAL
    String cardName; // detailed name of each card
    int cardCount; // total count of this card

    public String getCardType() {
        return cardType;
    }

    public String getCardName() {
        return cardName;
    }

    public int getCardCount() {
        return cardCount;
    }

    public void doCardFunction(int clientID) throws IOException {}
}
