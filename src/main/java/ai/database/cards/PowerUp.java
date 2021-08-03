package ai.database.cards;

import ai.database.Simulator;
import server.game.Direction;
import server.game.Game;
import server.network.Server;
import server.registercards.RegisterCard;

import java.io.IOException;

public class PowerUp extends CardGeneral {

    String cardType; // PROGRAMME DAMAGE SPECIAL
    String cardName; // detailed name of each card

    public PowerUp() {
        this.cardType = "PROGRAMME";
        this.cardName = "PowerUp";
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
    public int doCardFunction(int x, int y, Direction currentDirection){
        int resultDistance = Integer.MAX_VALUE;

        resultDistance = Simulator.getInstance().doBoardFunction();

        return resultDistance;    }
}
