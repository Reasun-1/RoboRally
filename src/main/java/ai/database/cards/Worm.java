package ai.database.cards;

import ai.database.Simulator;
import server.game.Direction;
import server.game.Game;
import server.game.Position;
import server.registercards.RegisterCard;

import java.io.IOException;

import static server.game.Game.rebootPosition;

public class Worm extends CardGeneral {

    String cardType; // PROGRAMME DAMAGE SPECIAL
    String cardName; // detailed name of each card

    public static int cardCount = 6; // only as info for shuffle the cards

    public Worm() {
        this.cardType = "DAMAGE";
        this.cardName = "Worm";
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
    public int doCardFunction(int x, int y, Direction curDirection){
        return Math.abs(Simulator.checkpointPosition.getX()-x) + Math.abs(Simulator.checkpointPosition.getY()-y);
    }
}
