package server.registercards;


import javafx.geometry.Pos;
import server.game.Direction;
import server.game.Game;
import server.game.Position;

import java.io.IOException;

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

    @Override
    public void doCardFunction(int clientID) throws IOException {
        Direction currentDirection = Game.directionsAllClients.get(clientID);
        Position currentPosition = Game.positionsAllClients.get(clientID);
        int curX = currentPosition.getX();
        int curY = currentPosition.getY();
        // set new Position
        Position newPosition = new Position(curX, curY);
        switch (currentDirection){
            case RIGHT:
                newPosition.setX(curX+2);
                break;
            case LEFT:
                newPosition.setX(curX-2);
                break;
            case UP:
                newPosition.setY(curY-2);
                break;
            case DOWN:
                newPosition.setY(curY+2);
                break;
        }

        // check if robot out of board
        boolean isOnBoard = Game.getInstance().checkOnBoard(clientID, newPosition);

        // if robot still on board, do board functon
        if(isOnBoard){

        }
    }
}
