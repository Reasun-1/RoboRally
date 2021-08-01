package ai.database.cards;

import ai.database.Simulator;
import server.game.Direction;
import server.game.Position;


import java.io.IOException;

public class BackUp extends CardGeneral{


    String cardType; // PROGRAMME DAMAGE SPECIAL

    String cardName; // detailed name of each card

    public BackUp() {
        this.cardType = "PROGRAMME";
        this.cardName = "BackUp";
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
    public int doCardFunction(int x, int y, Direction direction){

        int resultDistance = Integer.MAX_VALUE;

        Position newPosition = new Position(x, y);
        switch (direction){
            case LEFT:
                for (int i = x; i < Math.min(x+2,13); i++) {
                    String wallOri = Simulator.getInstance().checkWall(i, y);
                    if (i > x && (wallOri.equals("left")|| Simulator.board.get(i).get(y).get(0).getClass().getSimpleName().equals("Antenna"))){
                        newPosition.setX(i-1);
                        break;
                    }else if(wallOri.equals("right")){
                        newPosition.setX(i);
                        break;
                    }else{
                        newPosition.setX(x+1);
                    }
                }
                break;

            case RIGHT:
                for (int i = x; i > Math.max(x-2,-1); i--) {
                    String wallOri = Simulator.getInstance().checkWall(i, y);
                    if (wallOri.equals("left")){
                        newPosition.setX(i);
                        break;
                    }else if(i < x && (wallOri.equals("right")|| Simulator.board.get(i).get(y).get(0).getClass().getSimpleName().equals("Antenna"))){
                        newPosition.setX(i+1);
                        break;
                    }else{
                        newPosition.setX(x-1);
                    }
                }
                break;
            case DOWN:
                for (int i = y; i > Math.max(y-2, -1); i--) {
                    String wallOri = Simulator.getInstance().checkWall(x, i);
                    if(wallOri.equals("top")){
                        newPosition.setY(i);
                        break;
                    }else if(i < y && (wallOri.equals("bottom")|| Simulator.board.get(x).get(i).get(0).getClass().getSimpleName().equals("Antenna"))){
                        newPosition.setY(i+1);
                        break;
                    }else{
                        newPosition.setY(y-1);
                    }
                }
                break;
            case UP:
                for (int i = y; i < Math.min(y+2, 10); i++) {
                    String wallOri = Simulator.getInstance().checkWall(x, i);
                    if(i > y && (wallOri.equals("top")|| Simulator.board.get(x).get(i).get(0).getClass().getSimpleName().equals("Antenna"))){
                        newPosition.setY(i-1);
                        break;
                    }else if(wallOri.equals("bottom")){
                        newPosition.setY(i);
                        break;
                    }else{
                        newPosition.setY(y+1);
                    }
                }
                break;
        }

        // check if robot is still on board
        if(newPosition.getX() < 0 || newPosition.getX() > 12 || newPosition.getY() < 0 || newPosition.getY() > 9){
            resultDistance = 100;
        }else if(Simulator.board.get(newPosition.getX()).get(newPosition.getY()).get(0).getClass().getSimpleName().equals("Pit")){// check if robot is on Pit
            resultDistance = 100;
        }else{ // do the board function
            Simulator.lastPosition  = new Position(Simulator.curPosition.getX(), Simulator.curPosition.getY());
            Simulator.curPosition = new Position(newPosition.getX(), newPosition.getY());
            resultDistance = Simulator.getInstance().doBoardFunction();
        }

        return resultDistance;
    }
}
