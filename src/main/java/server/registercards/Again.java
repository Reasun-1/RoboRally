package server.registercards;

import java.util.ArrayList;
import java.util.List;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class Again extends RegisterCard{

    String cardType; // PROGRAMME DAMAGE SPECIAL
    String cardName; // detailed name of each card
    int cardCount; // only as info for shuffle the cards

    public Again() {
        this.cardType = "PROGRAMME";
        this.cardName = "Again";
        this.cardCount = 2;
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
    public String toString() {
        return "Again{" +
                "cardType='" + cardType + '\'' +
                ", cardName='" + cardName + '\'' +
                '}';
    }

    @Override
    public void doCardFunction(int clientID) {
        //TODO
        System.out.println("doFunction of card again");
    }

    // only test
    public static void main(String[] args) {
        List<RegisterCard> test = new ArrayList<>();
        test.add(new Again());
        System.out.println(test.get(0));
        Again again = (Again)test.get(0);
        again.doCardFunction(2);
    }
}
