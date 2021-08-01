package ai.database.cards;

import ai.database.Simulator;
import server.game.Direction;
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
    public int doCardFunction(int x, int y, Direction curDirection) {
        return Math.abs(Simulator.checkpointPosition.getX()-x) + Math.abs(Simulator.checkpointPosition.getY()-y);
    }
}
