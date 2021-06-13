package server.registercards;

import server.game.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class Again extends RegisterCard{

    String cardType; // PROGRAMME DAMAGE SPECIAL
    String cardName; // detailed name of each card
    public static int cardCount = 2; // only as info for shuffle the cards

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
    public void doCardFunction(int clientID) throws IOException {
        int curReg = Game.registerPointer;
        int lastReg = curReg - 1;
        RegisterCard[] registerCards = Game.registersAllClients.get(clientID);
        RegisterCard lastCardPlayed = registerCards[lastReg];
        // if last card is also Again, do the function of the card before last card
        if(lastCardPlayed.getCardName().equals("Again")){
            registerCards[lastReg-1].doCardFunction(clientID);
        }else{
            lastCardPlayed.doCardFunction(clientID);
        }
        System.out.println("doFunction of card again");
    }

    // only test
    public static void main(String[] args) throws IOException {
        List<RegisterCard> test = new ArrayList<>();
        test.add(new Again());
        System.out.println(test.get(0));
        Again again = (Again)test.get(0);
        again.doCardFunction(2);
    }
}
