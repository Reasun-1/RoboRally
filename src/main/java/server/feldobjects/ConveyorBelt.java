package server.feldobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import server.game.Direction;
import server.game.Game;
import server.game.Position;
import server.network.Server;

import java.io.IOException;
import java.util.List;

/**
 * The FeldObject Conveyor belt:  conveyor belts move any robot resting on them
 * one or two spaces in the direction of the arrows.
 *
 * @author Jonas Gottal
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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
    public void doBoardFunction(int clientID, FeldObject obj) throws IOException {
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
     * @param clientNum
     * @throws IOException
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
     * @param clientNum
     * @throws IOException
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
}
