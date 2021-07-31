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

}
