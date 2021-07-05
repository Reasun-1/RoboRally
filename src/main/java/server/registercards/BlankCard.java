package server.registercards;

import java.io.IOException;

/**
 * The type Blank card.
 *
 * @author Can Ren
 */
public class BlankCard extends RegisterCard{

    /**
     * The Card type.
     */
    String cardType; // PROGRAMME DAMAGE SPECIAL
    /**
     * The Card name.
     */
    String cardName; // detailed name of each card
    /**
     * The constant cardCount.
     */
    public static int cardCount = 0; // default

    /**
     * Instantiates a new Blank card.
     */
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
