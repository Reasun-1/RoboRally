package ai.database.cards;

import server.game.Game;
import server.registercards.RegisterCard;

import java.io.IOException;

public class Again extends CardGeneral{

    String cardType; // PROGRAMME DAMAGE SPECIAL

    String cardName; // detailed name of each card

    public Again() {
        this.cardType = "PROGRAMME";
        this.cardName = "Again";
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
    public String toString() {
        return "Again{" +
                "cardType='" + cardType + '\'' +
                ", cardName='" + cardName + '\'' +
                '}';
    }

}
