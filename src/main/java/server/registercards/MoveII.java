package server.registercards;


import javafx.geometry.Pos;
import server.game.Direction;
import server.game.Game;
import server.game.Position;
import server.network.Server;

import java.io.IOException;

public class MoveII extends RegisterCard{

    String cardType; // PROGRAMME DAMAGE SPECIAL
    String cardName; // detailed name of each card
    public static int cardCount = 3; // only as info for shuffle the cards

    public MoveII() {

        this.cardType = "PROGRAMME";
        this.cardName = "MoveII";

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
        System.out.println("card moveII");
        Direction currentDirection = Game.directionsAllClients.get(clientID);
        Position currentPosition = Game.playerPositions.get(clientID);
        int curColumn = currentPosition.getX();
        int curRow = currentPosition.getY();
        // set new Position
        Position newPosition = new Position(curColumn, curRow);
        switch (currentDirection){
            case RIGHT:
                for (int i = curColumn; i < Math.min(curColumn+3,13); i++) {
                    String wallOri = Game.getInstance().checkWall(curRow, i);
                    if (i > curColumn && wallOri.equals("left")){
                        newPosition.setX(i-1);
                        break;
                    }else if(wallOri.equals("right")){
                        newPosition.setX(i);
                        break;
                    }else{
                        newPosition.setX(curColumn+2);
                    }
                }
                break;

            case LEFT:
                for (int i = curColumn; i > Math.max(curColumn-3,0); i--) {
                    String wallOri = Game.getInstance().checkWall(curRow, i);
                    if (wallOri.equals("left")){
                        newPosition.setX(i);
                        break;
                    }else if(i < curColumn && wallOri.equals("right")){
                        newPosition.setX(i+1);
                        break;
                    }else{
                        newPosition.setX(curColumn-2);
                    }
                }
                break;
            case UP:
                for (int i = curRow; i > Math.max(curRow-3, 0); i--) {
                    String wallOri = Game.getInstance().checkWall(i, curColumn);
                    if(wallOri.equals("top")){
                        newPosition.setY(i);
                        break;
                    }else if(i < curRow && wallOri.equals("bottom")){
                        newPosition.setY(i+1);
                        break;
                    }else{
                        newPosition.setY(curRow-2);
                    }
                }
                break;
            case DOWN:
                for (int i = curRow; i < Math.min(curRow+3, 10); i++) {
                    String wallOri = Game.getInstance().checkWall(i, curColumn);
                    if(i > curRow && wallOri.equals("top")){
                        newPosition.setY(i-1);
                        break;
                    }else if(wallOri.equals("bottom")){
                        newPosition.setY(i);
                        break;
                    }else{
                        newPosition.setY(curRow+2);
                    }
                }
                break;
        }

        // check if robot is still on board
        boolean isOnBoard = Game.getInstance().checkOnBoard(clientID, newPosition);
        if(isOnBoard){
            // set new Position in Game
            Game.playerPositions.put(clientID, newPosition);
            // transport new Position to client
            Server.getServer().handleMovement(clientID, newPosition.getX(), newPosition.getY());
        }

    }
}
