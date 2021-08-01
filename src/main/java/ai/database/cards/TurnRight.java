package ai.database.cards;

import ai.database.Simulator;
import server.game.Direction;
import server.game.Game;
import server.network.Server;
import server.registercards.RegisterCard;

import java.io.IOException;

public class TurnRight extends CardGeneral {

    String cardType; // PROGRAMME DAMAGE SPECIAL
    String cardName; // detailed name of each card

    public TurnRight() {
        this.cardType = "PROGRAMME";
        this.cardName = "TurnRight";
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

        Simulator.curDirection = Direction.turnClock(currentDirection);

        resultDistance = Simulator.getInstance().doBoardFunction();

        return resultDistance;
    }
}
