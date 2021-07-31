package ai.database.cards;

import server.registercards.RegisterCard;

import java.io.IOException;

public class BlankCard extends CardGeneral {

    String cardType; // PROGRAMME DAMAGE SPECIAL
    String cardName; // detailed name of each card

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
