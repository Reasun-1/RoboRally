package ai.database.cards;

import ai.database.Simulator;
import server.game.Direction;
import server.game.Game;
import server.game.Position;
import server.network.Server;
import server.registercards.RegisterCard;

import java.io.IOException;

public class MoveIII extends CardGeneral {

    String cardType; // PROGRAMME DAMAGE SPECIAL
    String cardName; // detailed name of each card

    public MoveIII() {
        this.cardType = "PROGRAMME";
        this.cardName = "MoveIII";
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

        // set new Position
        Position newPosition = new Position(x, y);
        switch (currentDirection){
            case RIGHT:
                for (int i = x; i < Math.min(x+4,13); i++) {
                    String wallOri = Simulator.getInstance().checkWall(i, y);
                    if (i > x && (wallOri.equals("left")|| Simulator.board.get(i).get(y).get(0).getClass().getSimpleName().equals("Antenna"))){
                        newPosition.setX(i-1);
                        break;
                    }else if(wallOri.equals("right")){
                        newPosition.setX(i);
                        break;
                    }else{
                        newPosition.setX(x+3);
                    }
                }
                break;

            case LEFT:
                for (int i = x; i > Math.max(x-4,-1); i--) {
                    String wallOri = Simulator.getInstance().checkWall(i, y);
                    if (wallOri.equals("left")){
                        newPosition.setX(i);
                        break;
                    }else if(i < x && (wallOri.equals("right")|| Simulator.board.get(i).get(y).get(0).getClass().getSimpleName().equals("Antenna"))){
                        newPosition.setX(i+1);
                        break;
                    }else{
                        newPosition.setX(x-3);
                    }
                }
                break;
            case UP:
                for (int i = y; i > Math.max(y-4, -1); i--) {
                    String wallOri = Simulator.getInstance().checkWall(x, i);
                    if(wallOri.equals("top")){
                        newPosition.setY(i);
                        break;
                    }else if(i < y && (wallOri.equals("bottom")|| Simulator.board.get(x).get(i).get(0).getClass().getSimpleName().equals("Antenna"))){
                        newPosition.setY(i+1);
                        break;
                    }else{
                        newPosition.setY(y-3);
                    }
                }
                break;
            case DOWN:
                for (int i = y; i < Math.min(y+4, 10); i++) {
                    String wallOri = Simulator.getInstance().checkWall(x, i);
                    if(i > y && (wallOri.equals("top")|| Simulator.board.get(x).get(i).get(0).getClass().getSimpleName().equals("Antenna"))){
                        newPosition.setY(i-1);
                        break;
                    }else if(wallOri.equals("bottom")){
                        newPosition.setY(i);
                        break;
                    }else{
                        newPosition.setY(y+3);
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
