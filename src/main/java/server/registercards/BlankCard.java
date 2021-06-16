package server.registercards;

import java.io.IOException;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class BlankCard extends RegisterCard{

    String cardType; // PROGRAMME DAMAGE SPECIAL
    String cardName; // detailed name of each card
    public static int cardCount = 0; // default

    public BlankCard() {
        this.cardType = "PROGRAMME";
        this.cardName = "";
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
    public void doCardFunction(int clientID) throws IOException {
        //do nothing
    }
}
