package ai.database.cards;

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
    public void doCardFunction(int clientID) throws IOException {

        System.out.println("card moveIII");
        Direction currentDirection = Game.directionsAllClients.get(clientID);
        Position currentPosition = Game.playerPositions.get(clientID);
        int x = currentPosition.getX();
        int y = currentPosition.getY();

        // set new Position
        Position newPosition = new Position(x, y);
        switch (currentDirection){
            case RIGHT:
                for (int i = x; i < Math.min(x+4,13); i++) {
                    String wallOri = Game.getInstance().checkWall(i, y);
                    if (i > x && (wallOri.equals("left")|| Game.board.get(i).get(y).get(0).getClass().getSimpleName().equals("Antenna"))){
                        newPosition.setX(i-1);
                        break;
                    }else if(wallOri.equals("right")){
                        newPosition.setX(i);
                        break;
                    }else{
                        newPosition.setX(x+3);
                    }
                    // check other robot in the way
                    int clientPushed = Game.getInstance().checkOtherRobot(clientID, i,y);
                    if(clientPushed != 0){ // if there is one robot, which is pushed
                        Game.getInstance().checkAndSetPushedPosition(clientPushed, new Position(x+4,y));
                    }
                }
                break;

            case LEFT:
                for (int i = x; i > Math.max(x-4,-1); i--) {
                    String wallOri = Game.getInstance().checkWall(i, y);
                    if (wallOri.equals("left")){
                        newPosition.setX(i);
                        break;
                    }else if(i < x && (wallOri.equals("right")|| Game.board.get(i).get(y).get(0).getClass().getSimpleName().equals("Antenna"))){
                        newPosition.setX(i+1);
                        break;
                    }else{
                        newPosition.setX(x-3);
                    }
                    // check other robot in the way
                    int clientPushed = Game.getInstance().checkOtherRobot(clientID,i,y);
                    if(clientPushed != 0){ // if there is one robot, which is pushed
                        Game.getInstance().checkAndSetPushedPosition(clientPushed, new Position(x-4,y));
                    }
                }
                break;
            case UP:
                for (int i = y; i > Math.max(y-4, -1); i--) {
                    String wallOri = Game.getInstance().checkWall(x, i);
                    if(wallOri.equals("top")){
                        newPosition.setY(i);
                        break;
                    }else if(i < y && (wallOri.equals("bottom")|| Game.board.get(x).get(i).get(0).getClass().getSimpleName().equals("Antenna"))){
                        newPosition.setY(i+1);
                        break;
                    }else{
                        newPosition.setY(y-3);
                    }
                    // check other robot in the way
                    int clientPushed = Game.getInstance().checkOtherRobot(clientID,i,y);
                    if(clientPushed != 0){ // if there is one robot, which is pushed
                        Game.getInstance().checkAndSetPushedPosition(clientPushed, new Position(x,y-4));
                    }
                }
                break;
            case DOWN:
                for (int i = y; i < Math.min(y+4, 10); i++) {
                    String wallOri = Game.getInstance().checkWall(x, i);
                    if(i > y && (wallOri.equals("top")|| Game.board.get(x).get(i).get(0).getClass().getSimpleName().equals("Antenna"))){
                        newPosition.setY(i-1);
                        break;
                    }else if(wallOri.equals("bottom")){
                        newPosition.setY(i);
                        break;
                    }else{
                        newPosition.setY(y+3);
                    }
                    // check other robot in the way
                    int clientPushed = Game.getInstance().checkOtherRobot(clientID,i,y);
                    if(clientPushed != 0){ // if there is one robot, which is pushed
                        Game.getInstance().checkAndSetPushedPosition(clientPushed, new Position(x,y+4));
                    }
                }
                break;
        }

        // check if robot is still on board
        boolean isOnBoard = Game.getInstance().checkOnBoard(clientID, newPosition);
        if(isOnBoard){
            // update the last position
            Position curPo = Game.playerPositions.get(clientID);
            Game.playersLastPositions.put(clientID, curPo);
            // set new Position in Game
            Game.playerPositions.put(clientID, newPosition);
            // transport new Position to client
            Server.getServer().handleMovement(clientID, newPosition.getX(), newPosition.getY());
        }
    }
}
