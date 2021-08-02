package ai.database.fieldelements;

import ai.database.Simulator;
import server.game.Direction;
import server.game.Game;
import server.game.Position;
import server.network.Server;

import java.io.IOException;
import java.util.List;

public class ConveyorBelt extends FeldObject {


    private String isOnBoard;
    private int speed;
    private List<String> orientations;

    public ConveyorBelt() {
    }

    public ConveyorBelt(String isOnBoard, int speed, List<String> orientations) {

        this.isOnBoard = isOnBoard;
        this.speed = speed;
        this.orientations = orientations;
    }

    @Override
    public String getIsOnBoard() {
        return isOnBoard;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public List<String> getOrientations() {
        return orientations;
    }

    @Override
    public int doBoardFunction(int curX, int curY, Direction direction, FeldObject obj) {
        int resultDistance = Integer.MAX_VALUE;

        int speed = obj.getSpeed();
        List<String> orientations = obj.getOrientations();
        String abfluss = orientations.get(0);

        Position newPosition = null;

        switch (abfluss) {
            case "top":
                if (speed == 1) { // green belt
                    newPosition = new Position(curX, curY - 1);
                    if (orientations.size() == 2) { // conditional limit
                        for (String entrance : orientations) {
                            if (entrance.equals("left")) {
                                if (Simulator.lastPosition.getX() - Simulator.curPosition.getX() == -1
                                        && Simulator.lastPosition.getY() == Simulator.curPosition.getY()) {
                                    turnCounterclockwise(direction);
                                }
                            } else if (entrance.equals("right")) {
                                if (Simulator.lastPosition.getX() - Simulator.curPosition.getX() == 1
                                        && Simulator.lastPosition.getY() == Simulator.curPosition.getY()) {
                                    turnClockwise(direction);
                                }
                            }
                        }
                    }
                } else { // blue belt
                    newPosition = new Position(curX, curY - 2);
                    if (orientations.size() == 1) {
                        if(!Simulator.board.get(curX).get(curY-1).get(0).getClass().getSimpleName().equals("ConveyorBelt")){
                            newPosition = new Position(curX, curY-1);
                        }else if(Simulator.board.get(curX).get(curY-1).get(0).getClass().getSimpleName().equals("ConveyorBelt")){
                            if(Simulator.board.get(curX).get(curY-1).get(0).orientations.size() == 3){
                                for(String entrance : Simulator.board.get(curX).get(curY-1).get(0).orientations){
                                    if(entrance.equals("bottom")){
                                        newPosition = new Position(curX -1, curY-1);
                                        Simulator.curDirection = Direction.turnCounterClock(Simulator.curDirection);
                                    }
                                }
                            }
                        }
                        if(Simulator.board.get(curX).get(curY-2).get(0).getClass().getSimpleName().equals("ConveyorBelt")){
                            if(Simulator.board.get(curX).get(curY-2).get(0).orientations.size() == 3){
                                for(String entrance : Simulator.board.get(curX).get(curY-2).get(0).orientations){
                                    if(entrance.equals("bottom")){
                                        Simulator.curDirection = Direction.turnCounterClock(Simulator.curDirection);
                                    }
                                }
                            }
                        }
                    }
                    if (orientations.size() == 2) {
                        for (String entrance : orientations) {
                            if (entrance.equals("left")) {
                                if (Simulator.lastPosition.getX() - Simulator.curPosition.getX() == -2
                                        && Simulator.lastPosition.getY() == Simulator.curPosition.getY()) {
                                    turnCounterclockwise(direction);
                                }
                            } else if (entrance.equals("right")) {
                                if (Simulator.lastPosition.getX() - Simulator.curPosition.getX() == 2
                                        && Simulator.lastPosition.getY() == Simulator.curPosition.getY()) {
                                    turnClockwise(direction);
                                }
                            }
                        }
                    }
                    if (orientations.size() > 2) { // if this is a tri-corner-belt
                        for (String entrance : orientations) {
                            if (entrance.equals("left")) {
                                if (Simulator.lastPosition.getX() < Simulator.curPosition.getX()
                                        && Simulator.lastPosition.getY() == Simulator.curPosition.getY()) {
                                    turnCounterclockwise(direction);
                                }

                            } else if (entrance.equals("right")) {
                                if (Simulator.lastPosition.getX() > Simulator.curPosition.getX()
                                        && Simulator.lastPosition.getY() == Simulator.curPosition.getY()) {
                                    turnClockwise(direction);
                                }
                            }
                        }
                    }
                }
                break;
            case "bottom":
                if (speed == 1) { // green belt
                    newPosition = new Position(curX, curY + 1);
                    if (orientations.size() == 2) { // condition limited
                        for (String entrance : orientations) {
                            if (entrance.equals("left")) {
                                if (Simulator.lastPosition.getX() - Simulator.curPosition.getX() == -1
                                        && Simulator.lastPosition.getY() == Simulator.curPosition.getY()) {
                                    turnClockwise(direction);
                                }

                            } else if (entrance.equals("right")) {
                                if (Simulator.lastPosition.getX() - Simulator.curPosition.getX() == 1
                                        && Simulator.lastPosition.getY() == Simulator.curPosition.getY()) {
                                    turnCounterclockwise(direction);
                                }
                            }
                        }
                    }
                } else { // blue belt
                    newPosition = new Position(curX, curY + 2);
                    if (orientations.size() == 1) {
                        if(!Simulator.board.get(curX).get(curY+1).get(0).getClass().getSimpleName().equals("ConveyorBelt")){
                            newPosition = new Position(curX, curY+1);
                        }else if(Simulator.board.get(curX).get(curY+1).get(0).getClass().getSimpleName().equals("ConveyorBelt")){
                            if(Simulator.board.get(curX).get(curY+1).get(0).orientations.size() == 3){
                                for(String entrance : Simulator.board.get(curX).get(curY+1).get(0).orientations){
                                    if(entrance.equals("top")){
                                        newPosition = new Position(curX +1, curY+1);
                                        Simulator.curDirection = Direction.turnCounterClock(Simulator.curDirection);
                                    }
                                }
                            }
                        }
                        if(Simulator.board.get(curX).get(curY+2).get(0).getClass().getSimpleName().equals("ConveyorBelt")){
                            if(Simulator.board.get(curX).get(curY+2).get(0).orientations.size() == 3){
                                for(String entrance : Simulator.board.get(curX).get(curY+2).get(0).orientations){
                                    if(entrance.equals("top")){
                                        Simulator.curDirection = Direction.turnCounterClock(Simulator.curDirection);
                                    }
                                }
                            }
                        }
                    }
                    if (orientations.size() == 2) { // condition limited
                        for (String entrance : orientations) {
                            if (entrance.equals("left")) {
                                if (Simulator.lastPosition.getX() - Simulator.curPosition.getX() == -2
                                        && Simulator.lastPosition.getY() == Simulator.curPosition.getY()) {
                                    turnClockwise(direction);
                                }

                            } else if (entrance.equals("right")) {
                                if (Simulator.lastPosition.getX() - Simulator.curPosition.getX() == 2
                                        && Simulator.lastPosition.getY() == Simulator.curPosition.getY()) {
                                    turnCounterclockwise(direction);
                                }
                            }
                        }
                    }
                    if (orientations.size() > 2) { // if this is a tri-corner-belt
                        for (String entrance : orientations) {
                            if (entrance.equals("left")) {
                                if (Simulator.lastPosition.getX() < Simulator.curPosition.getX()
                                        && Simulator.lastPosition.getY() == Simulator.curPosition.getY()) {
                                    turnClockwise(direction);
                                }

                            } else if (entrance.equals("right")) {
                                if (Simulator.lastPosition.getX() > Simulator.curPosition.getX()
                                        && Simulator.lastPosition.getY() == Simulator.curPosition.getY()) {
                                    turnCounterclockwise(direction);
                                }
                            }
                        }
                    }
                }
                break;
            case "right":
                if (speed == 1) {// green belt
                    newPosition = new Position(curX + 1, curY);
                    if (orientations.size() == 2) { // condition limited
                        for (String entrance : orientations) {
                            if (entrance.equals("top")) {
                                if (Simulator.lastPosition.getX() == Simulator.curPosition.getX()
                                        && Simulator.lastPosition.getY() - Simulator.curPosition.getY() == -1) {
                                    turnCounterclockwise(direction);
                                }

                            } else if (entrance.equals("bottom")) {
                                if (Simulator.lastPosition.getX() == Simulator.curPosition.getX()
                                        && Simulator.lastPosition.getY() - Simulator.curPosition.getY() == 1) {
                                    turnClockwise(direction);
                                }
                            }
                        }
                    }

                    if(Simulator.board.get(curX+1).get(curY).get(0).getClass().getSimpleName().equals("ConveyorBelt")){
                        if(Simulator.board.get(curX+1).get(curY).get(0).speed == 1){
                            if (Simulator.board.get(curX+1).get(curY).get(0).orientations.size() == 2) { // condition limited
                                for (String entrance : Simulator.board.get(curX+1).get(curY).get(0).orientations) {
                                    if (entrance.equals("top")) {
                                            turnCounterclockwise(direction);
                                    } else if (entrance.equals("bottom")) {
                                        Simulator.curDirection = Direction.turnClock(Simulator.curDirection);
                                    }
                                }
                            }
                        }
                    }

                } else { // blue belt
                    newPosition = new Position(curX + 2, curY);
                    if (orientations.size() == 1) {
                        if(!Simulator.board.get(curX+1).get(curY).get(0).getClass().getSimpleName().equals("ConveyorBelt")){
                            newPosition = new Position(curX+1, curY);
                        }else if(Simulator.board.get(curX+1).get(curY).get(0).getClass().getSimpleName().equals("ConveyorBelt")){
                            if(Simulator.board.get(curX+1).get(curY).get(0).orientations.size() == 3){
                                for(String entrance : Simulator.board.get(curX+1).get(curY).get(0).orientations){
                                    if(entrance.equals("left")){
                                        newPosition = new Position(curX +1, curY-1);
                                        Simulator.curDirection = Direction.turnCounterClock(Simulator.curDirection);
                                    }
                                }
                            }
                        }
                        if(Simulator.board.get(curX+2).get(curY).get(0).getClass().getSimpleName().equals("ConveyorBelt")){
                            if(Simulator.board.get(curX+2).get(curY).get(0).orientations.size() == 3){
                                for(String entrance : Simulator.board.get(curX+2).get(curY).get(0).orientations){
                                    if(entrance.equals("left")){
                                        Simulator.curDirection = Direction.turnCounterClock(Simulator.curDirection);
                                    }
                                }
                            }
                        }
                    }
                    if (orientations.size() == 2) { // condition limited
                        for (String entrance : orientations) {
                            if (entrance.equals("top")) {
                                if (Simulator.lastPosition.getX() == Simulator.curPosition.getX()
                                        && Simulator.lastPosition.getY() - Simulator.curPosition.getY() == -2) {
                                    turnCounterclockwise(direction);
                                }

                            } else if (entrance.equals("bottom")) {
                                if (Simulator.lastPosition.getX() == Simulator.curPosition.getX()
                                        && Simulator.lastPosition.getY() - Simulator.curPosition.getY() == 2) {
                                    turnClockwise(direction);
                                }
                            }
                        }
                    }
                    if (orientations.size() > 2) { // if this is a tri-corner-belt
                        for (String entrance : orientations) {
                            if (entrance.equals("top")) {
                                if (Simulator.lastPosition.getX() == Simulator.curPosition.getX()
                                        && Simulator.lastPosition.getY() < Simulator.curPosition.getY()) {
                                    turnCounterclockwise(direction);
                                }

                            } else if (entrance.equals("bottom")) {
                                if (Simulator.lastPosition.getX() == Simulator.curPosition.getX()
                                        && Simulator.lastPosition.getY() > Simulator.curPosition.getY()) {
                                    turnClockwise(direction);
                                }
                            }
                        }
                    }
                }
                break;
            case "left":
                if (speed == 1) { // green belt
                    newPosition = new Position(curX - 1, curY);
                    if (orientations.size() == 2) { // condition limited
                        for (String entrance : orientations) {
                            if (entrance.equals("top")) {
                                if (Simulator.lastPosition.getX() == Simulator.curPosition.getX()
                                        && Simulator.lastPosition.getY() - Simulator.curPosition.getY() == -1) {
                                    turnClockwise(direction);
                                }

                            } else if (entrance.equals("bottom")) {
                                if (Simulator.lastPosition.getX() == Simulator.curPosition.getX()
                                        && Simulator.lastPosition.getY() - Simulator.curPosition.getY() == 1) {
                                    turnCounterclockwise(direction);
                                }
                            }
                        }
                    }

                    if(Simulator.board.get(curX-1).get(curY).get(0).getClass().getSimpleName().equals("ConveyorBelt")){
                        if(Simulator.board.get(curX-1).get(curY).get(0).speed == 1){
                            if (Simulator.board.get(curX-1).get(curY).get(0).orientations.size() == 2) { // condition limited
                                for (String entrance : Simulator.board.get(curX-1).get(curY).get(0).orientations) {
                                    if (entrance.equals("top")) {
                                        turnClockwise(direction);
                                    } else if (entrance.equals("bottom")) {
                                        turnCounterclockwise(direction);
                                    }
                                }
                            }
                        }
                    }
                } else { // blue belt
                    newPosition = new Position(curX - 2, curY);
                    if (orientations.size() == 1) {
                        if(!Simulator.board.get(curX-1).get(curY).get(0).getClass().getSimpleName().equals("ConveyorBelt")){
                            newPosition = new Position(curX-1, curY);
                        }else if(Simulator.board.get(curX-1).get(curY).get(0).getClass().getSimpleName().equals("ConveyorBelt")){
                            if(Simulator.board.get(curX-1).get(curY).get(0).orientations.size() == 3){
                                for(String entrance : Simulator.board.get(curX+1).get(curY).get(0).orientations){
                                    if(entrance.equals("right")){
                                        newPosition = new Position(curX -1, curY+1);
                                        Simulator.curDirection = Direction.turnCounterClock(Simulator.curDirection);
                                    }
                                }
                            }
                        }
                        if(Simulator.board.get(curX-2).get(curY).get(0).getClass().getSimpleName().equals("ConveyorBelt")){
                            if(Simulator.board.get(curX-2).get(curY).get(0).orientations.size() == 3){
                                for(String entrance : Simulator.board.get(curX-2).get(curY).get(0).orientations){
                                    if(entrance.equals("right")){
                                        Simulator.curDirection = Direction.turnCounterClock(Simulator.curDirection);
                                    }
                                }
                            }
                        }
                    }
                    if (orientations.size() == 2) { // condition limited
                        for (String entrance : orientations) {
                            if (entrance.equals("top")) {
                                if (Simulator.lastPosition.getX() == Simulator.curPosition.getX()
                                        && Simulator.lastPosition.getY() - Simulator.curPosition.getY() == -2) {
                                    turnClockwise(direction);
                                }

                            } else if (entrance.equals("bottom")) {
                                if (Simulator.lastPosition.getX() == Simulator.curPosition.getX()
                                        && Simulator.lastPosition.getY() - Simulator.curPosition.getY() == 2) {
                                    turnCounterclockwise(direction);
                                }
                            }
                        }
                    }
                    if (orientations.size() > 2) { // if this is a tri-corner-belt
                        for (String entrance : orientations) {
                            if (entrance.equals("top")) {
                                if (Simulator.lastPosition.getX() == Simulator.curPosition.getX()
                                        && Simulator.lastPosition.getY() < Simulator.curPosition.getY()) {
                                    turnClockwise(direction);
                                }

                            } else if (entrance.equals("bottom")) {
                                if (Simulator.lastPosition.getX() == Simulator.curPosition.getX()
                                        && Simulator.lastPosition.getY() > Simulator.curPosition.getY()) {
                                    turnCounterclockwise(direction);
                                }
                            }
                        }
                    }
                }
                break;
        }


        // check if robot is still on board
        if (newPosition.getX() < 0 || newPosition.getX() > 12 || newPosition.getY() < 0 || newPosition.getY() > 9) {
            resultDistance = 100;
        } else if (Simulator.board.get(newPosition.getX()).get(newPosition.getY()).get(0).getClass().getSimpleName().equals("Pit")) {// check if robot is on Pit
            resultDistance = 100;
        } else {
            Simulator.lastPosition = new Position(Simulator.curPosition.getX(), Simulator.curPosition.getY());
            Simulator.curPosition = new Position(newPosition.getX(), newPosition.getY());
            resultDistance = Math.abs(Simulator.checkpointPosition.getX() - Simulator.curPosition.getX()) + Math.abs(Simulator.checkpointPosition.getY() - Simulator.curPosition.getY());
        }


        if(resultDistance == 0){
            System.out.println("===========0000===============Position: " + newPosition.getX() + " " + newPosition.getY());
        }
        return resultDistance;
    }


    public void turnClockwise(Direction direction) {

        Direction newDir = Direction.turnClock(direction);

        Simulator.curDirection = newDir;
    }


    public void turnCounterclockwise(Direction direction) {

        Direction newDir = Direction.turnCounterClock(direction);

        Simulator.curDirection = newDir;
    }

}
