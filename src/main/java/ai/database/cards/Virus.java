package ai.database.cards;

import ai.database.Simulator;
import server.game.Direction;
import server.game.Game;
import server.network.Server;
import server.registercards.MoveI;
import server.registercards.RegisterCard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Virus extends CardGeneral {

    String cardType; // PROGRAMME DAMAGE SPECIAL
    String cardName; // detailed name of each card

    public Virus() {
        this.cardType = "DAMAGE";
        this.cardName = "Virus";
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
