package ai.database.fieldelements;

import server.game.Direction;
import server.game.Game;
import server.game.Position;
import server.network.Server;

import java.io.IOException;
import java.util.List;

public class ConveyorBelt extends ElementGeneral {


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
    public void doBoardFunction(int clientID, ElementGeneral obj) throws IOException {
        int speed = obj.getSpeed();
        List<String> orientations = obj.getOrientations();
        String abfluss = orientations.get(0);

        Position position = Game.getInstance().playerPositions.get(clientID);
        int curX = position.getX();
        int curY = position.getY();

        Position newPosition = null;

        switch (abfluss) {
            case "top":
                if (speed == 1) { // green belt
                    newPosition = new Position(curX, curY - 1);
                    if(orientations.size() == 2){ // conditional limit
                        for(String entrance : orientations){
                            if(entrance.equals("left")){
                                if (Game.playersLastPositions.get(clientID).getX() - Game.playerPositions.get(clientID).getX() == -1
                                        && Game.playerPositions.get(clientID).getY() == Game.playerPositions.get(clientID).getY()) {
                                    turnCounterclockwise(clientID);
                                }
                            }else if(entrance.equals("right")){
                                if (Game.playersLastPositions.get(clientID).getX() - Game.playerPositions.get(clientID).getX() == 1
                                        && Game.playerPositions.get(clientID).getY() == Game.playerPositions.get(clientID).getY()) {
                                    turnClockwise(clientID);
                                }
                            }
                        }
                    }
                } else { // blue belt
                    newPosition = new Position(curX, curY - 2);
                    if(orientations.size() == 2){
                        for(String entrance : orientations){
                            if(entrance.equals("left")){
                                if (Game.playersLastPositions.get(clientID).getX() - Game.playerPositions.get(clientID).getX() == -2
                                        && Game.playerPositions.get(clientID).getY() == Game.playerPositions.get(clientID).getY()) {
                                    turnCounterclockwise(clientID);
                                }
                            }else if(entrance.equals("right")){
                                if (Game.playersLastPositions.get(clientID).getX() - Game.playerPositions.get(clientID).getX() == 2
                                        && Game.playerPositions.get(clientID).getY() == Game.playerPositions.get(clientID).getY()) {
                                    turnClockwise(clientID);
                                }
                            }
                        }
                    }
                    if (orientations.size() > 2) { // if this is a tri-corner-belt
                        for (String entrance : orientations) {
                            if (entrance.equals("left")) {
                                if (Game.playersLastPositions.get(clientID).getX() < Game.playerPositions.get(clientID).getX()
                                        && Game.playerPositions.get(clientID).getY() == Game.playerPositions.get(clientID).getY()) {
                                    turnCounterclockwise(clientID);
                                }

                            } else if (entrance.equals("right")) {
                                if (Game.playersLastPositions.get(clientID).getX() > Game.playerPositions.get(clientID).getX()
                                        && Game.playerPositions.get(clientID).getY() == Game.playerPositions.get(clientID).getY()) {
                                    turnClockwise(clientID);
                                }
                            }
                        }
                    }
                }
                break;
            case "bottom":
                if (speed == 1) { // green belt
                    newPosition = new Position(curX, curY + 1);
                    if(orientations.size() == 2){ // condition limited
                        for (String entrance : orientations) {
                            if (entrance.equals("left")) {
                                if (Game.playersLastPositions.get(clientID).getX() - Game.playerPositions.get(clientID).getX() == -1
                                        && Game.playersLastPositions.get(clientID).getY() == Game.playerPositions.get(clientID).getY()) {
                                    turnClockwise(clientID);
                                }

                            } else if (entrance.equals("right")) {
                                if (Game.playersLastPositions.get(clientID).getX() - Game.playerPositions.get(clientID).getX() == 1
                                        && Game.playersLastPositions.get(clientID).getY() == Game.playerPositions.get(clientID).getY()) {
                                    turnCounterclockwise(clientID);
                                }
                            }
                        }
                    }
                } else { // blue belt
                    newPosition = new Position(curX, curY + 2);
                    if(orientations.size() == 2){ // condition limited
                        for (String entrance : orientations) {
                            if (entrance.equals("left")) {
                                if (Game.playersLastPositions.get(clientID).getX() - Game.playerPositions.get(clientID).getX() == -2
                                        && Game.playersLastPositions.get(clientID).getY() == Game.playerPositions.get(clientID).getY()) {
                                    turnClockwise(clientID);
                                }

                            } else if (entrance.equals("right")) {
                                if (Game.playersLastPositions.get(clientID).getX() - Game.playerPositions.get(clientID).getX() == 2
                                        && Game.playersLastPositions.get(clientID).getY() == Game.playerPositions.get(clientID).getY()) {
                                    turnCounterclockwise(clientID);
                                }
                            }
                        }
                    }
                    if (orientations.size() > 2) { // if this is a tri-corner-belt
                        for (String entrance : orientations) {
                            if (entrance.equals("left")) {
                                if (Game.playersLastPositions.get(clientID).getX() < Game.playerPositions.get(clientID).getX()
                                        && Game.playersLastPositions.get(clientID).getY() == Game.playerPositions.get(clientID).getY()) {
                                    turnClockwise(clientID);
                                }

                            } else if (entrance.equals("right")) {
                                if (Game.playersLastPositions.get(clientID).getX() > Game.playerPositions.get(clientID).getX()
                                        && Game.playersLastPositions.get(clientID).getY() == Game.playerPositions.get(clientID).getY()) {
                                    turnCounterclockwise(clientID);
                                }
                            }
                        }
                    }
                }
                break;
            case "right":
                if (speed == 1) {// green belt
                    newPosition = new Position(curX + 1, curY);
                    if(orientations.size() == 2){ // condition limited
                        for (String entrance : orientations) {
                            if (entrance.equals("top")) {
                                if (Game.playersLastPositions.get(clientID).getX() == Game.playerPositions.get(clientID).getX()
                                        && Game.playersLastPositions.get(clientID).getY() - Game.playerPositions.get(clientID).getY() == -1) {
                                    turnCounterclockwise(clientID);
                                }

                            } else if (entrance.equals("bottom")) {
                                if (Game.playersLastPositions.get(clientID).getX() == Game.playerPositions.get(clientID).getX()
                                        && Game.playersLastPositions.get(clientID).getY() - Game.playerPositions.get(clientID).getY() == 1) {
                                    turnClockwise(clientID);
                                }
                            }
                        }
                    }
                } else { // blue belt
                    newPosition = new Position(curX + 2, curY);
                    if(orientations.size() == 2){ // condition limited
                        for (String entrance : orientations) {
                            if (entrance.equals("top")) {
                                if (Game.playersLastPositions.get(clientID).getX() == Game.playerPositions.get(clientID).getX()
                                        && Game.playersLastPositions.get(clientID).getY() - Game.playerPositions.get(clientID).getY() == -2) {
                                    turnCounterclockwise(clientID);
                                }

                            } else if (entrance.equals("bottom")) {
                                if (Game.playersLastPositions.get(clientID).getX() == Game.playerPositions.get(clientID).getX()
                                        && Game.playersLastPositions.get(clientID).getY() - Game.playerPositions.get(clientID).getY() == 2) {
                                    turnClockwise(clientID);
                                }
                            }
                        }
                    }
                    if (orientations.size() > 2) { // if this is a tri-corner-belt
                        for (String entrance : orientations) {
                            if (entrance.equals("top")) {
                                if (Game.playersLastPositions.get(clientID).getX() == Game.playerPositions.get(clientID).getX()
                                        && Game.playersLastPositions.get(clientID).getY() < Game.playerPositions.get(clientID).getY()) {
                                    turnCounterclockwise(clientID);
                                }

                            } else if (entrance.equals("bottom")) {
                                if (Game.playersLastPositions.get(clientID).getX() == Game.playerPositions.get(clientID).getX()
                                        && Game.playersLastPositions.get(clientID).getY() > Game.playerPositions.get(clientID).getY()) {
                                    turnClockwise(clientID);
                                }
                            }
                        }
                    }
                }
                break;
            case "left":
                if (speed == 1) { // green belt
                    newPosition = new Position(curX - 1, curY);
                    if(orientations.size() == 2){ // condition limited
                        for (String entrance : orientations) {
                            if (entrance.equals("top")) {
                                if (Game.playersLastPositions.get(clientID).getX() == Game.playerPositions.get(clientID).getX()
                                        && Game.playersLastPositions.get(clientID).getY() - Game.playerPositions.get(clientID).getY() == -1) {
                                    turnClockwise(clientID);
                                }

                            } else if (entrance.equals("bottom")) {
                                if (Game.playersLastPositions.get(clientID).getX() == Game.playerPositions.get(clientID).getX()
                                        && Game.playersLastPositions.get(clientID).getY() - Game.playerPositions.get(clientID).getY() == 1) {
                                    turnCounterclockwise(clientID);
                                }
                            }
                        }
                    }
                } else { // blue belt
                    newPosition = new Position(curX - 2, curY);
                    if(orientations.size() == 2){ // condition limited
                        for (String entrance : orientations) {
                            if (entrance.equals("top")) {
                                if (Game.playersLastPositions.get(clientID).getX() == Game.playerPositions.get(clientID).getX()
                                        && Game.playersLastPositions.get(clientID).getY() - Game.playerPositions.get(clientID).getY() == -2) {
                                    turnClockwise(clientID);
                                }

                            } else if (entrance.equals("bottom")) {
                                if (Game.playersLastPositions.get(clientID).getX() == Game.playerPositions.get(clientID).getX()
                                        && Game.playersLastPositions.get(clientID).getY() - Game.playerPositions.get(clientID).getY() == 2) {
                                    turnCounterclockwise(clientID);
                                }
                            }
                        }
                    }
                    if (orientations.size() > 2) { // if this is a tri-corner-belt
                        for (String entrance : orientations) {
                            if (entrance.equals("top")) {
                                if (Game.playersLastPositions.get(clientID).getX() == Game.playerPositions.get(clientID).getX()
                                        && Game.playersLastPositions.get(clientID).getY() < Game.playerPositions.get(clientID).getY()) {
                                    turnClockwise(clientID);
                                }

                            } else if (entrance.equals("bottom")) {
                                if (Game.playersLastPositions.get(clientID).getX() == Game.playerPositions.get(clientID).getX()
                                        && Game.playersLastPositions.get(clientID).getY() > Game.playerPositions.get(clientID).getY()) {
                                    turnCounterclockwise(clientID);
                                }
                            }
                        }
                    }
                }
                break;
        }

        // check if robot is still on board
        boolean isOnBoard = Game.getInstance().checkOnBoardFromBelt(clientID, newPosition);
        if (isOnBoard) {
            // update the last position
            Position curPo = Game.playerPositions.get(clientID);
            Game.playersLastPositions.put(clientID, curPo);
            // set new Position in Game
            Game.playerPositions.put(clientID, newPosition);
            // transport new Position to client
            Server.getServer().handleMovement(clientID, newPosition.getX(), newPosition.getY());
        }
    }

    /**
     * for the tri-corner-belt turn
     *
     * @param clientNum the client num
     * @throws IOException the io exception
     */
    public void turnClockwise(int clientNum) throws IOException {
        //set direction of this client -90
        Direction curDir = Game.directionsAllClients.get(clientNum);
        Direction newDir = Direction.turnClock(curDir);

        //update new direction in Game
        Game.directionsAllClients.put(clientNum, newDir);
        // transport the new direction to clients
        Server.getServer().handlePlayerTurning(clientNum, "clockwise");
    }

    /**
     * for the tri-corner-belt turn
     *
     * @param clientNum the client num
     * @throws IOException the io exception
     */
    public void turnCounterclockwise(int clientNum) throws IOException {
        //set direction of this client -90
        Direction curDir = Game.directionsAllClients.get(clientNum);
        Direction newDir = Direction.turnCounterClock(curDir);

        //update new direction in Game
        Game.directionsAllClients.put(clientNum, newDir);
        // transport the new direction to clients
        Server.getServer().handlePlayerTurning(clientNum, "counterclockwise");
    }

    /**
     * for the map twister
     *
     * @param checkpointNr the checkpoint nr
     * @param belt         the belt
     */
    public void moveCheckpoints(int checkpointNr, ConveyorBelt belt){
        String dir = belt.getOrientations().get(0);
        int[] curLocation = Game.movingCheckpoints.get(checkpointNr);
        int curX = curLocation[0];
        int curY = curLocation[1];

        int[] newLocation = new int[2];

        switch (dir){
            case "top":
                newLocation[0] = curX;
                newLocation[1] = curY-2;
                break;
            case "bottom":
                newLocation[0] = curX;
                newLocation[1] = curY +2;
                break;
            case "left":
                newLocation[0] = curX-2;
                newLocation[1] = curY;
                break;
            case "right":
                newLocation[0] = curX+2;
                newLocation[1] = curY;
                break;
        }
        Game.movingCheckpoints.put(checkpointNr, newLocation);
    }
}
