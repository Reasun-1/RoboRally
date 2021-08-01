package ai.database.cards;

import ai.database.Simulator;
import server.game.Direction;
import server.game.Game;
import server.network.Server;
import server.registercards.MoveI;
import server.registercards.RegisterCard;

import java.io.IOException;

public class Spam extends CardGeneral {

    String cardType; // PROGRAMME DAMAGE SPECIAL
    String cardName; // detailed name of each card

    public Spam() {
        this.cardType = "DAMAGE";
        this.cardName = "Spam";
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
        return Math.abs(Simulator.checkpointPosition.getX()-x) + Math.abs(Simulator.checkpointPosition.getY()-y);
    }
}
