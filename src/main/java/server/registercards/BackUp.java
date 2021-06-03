package server.registercards;

import java.io.IOException;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class BackUp extends RegisterCard{

    String cardType; // PROGRAMME DAMAGE SPECIAL
    String cardName; // detailed name of each card
    public static int cardCount = 1; // only as info for shuffle the cards

    public BackUp() {
        this.cardType = "PROGRAMME";
        this.cardName = "BackUp";
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
    public int getCardCount() {
        return cardCount;
    }

    @Override
    public void doCardFunction(int clientID) throws IOException {
        //TODO
    }
}