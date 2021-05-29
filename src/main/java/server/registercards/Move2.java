package server.registercards;


public class Move2 extends RegisterCard{


    String cardType; // PROGRAMME DAMAGE SPECIAL
    String cardName; // detailed name of each card
    int cardCount; // only as info for shuffle the cards

    public Move2() {
    }

    public Move2(String cardType, String cardName) {
        this.cardType = cardType;
        this.cardName = cardName;
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
}
