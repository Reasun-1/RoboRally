package server.feldobjects;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import server.game.Direction;
import server.game.Game;
import server.game.Position;

import java.util.List;
import java.util.TreeMap;

/**
 * The FeldObject PriorityAntenna: The priority antenna helps determine whose turn it is.
 * As a board element, it acts as a wall. Robots cannot move through, push, shoot through,
 * or occupy the same space as the priority antenna.
 *
 * @author Jonas Gottal
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Antenna extends FeldObject{

    //private String type;
    private String isOnBoard;
    private List<String> orientations;

    public Antenna() {
    }

    public Antenna(String isOnBoard, List<String> orientations) {
        //this.type = type;
        this.isOnBoard = isOnBoard;
        this.orientations = orientations;
    }

   /* @Override
    public String getType() {
        return type;
    }

    */

    @Override
    public String getIsOnBoard() {
        return isOnBoard;
    }

    @Override
    public List<String> getOrientations() {
        return orientations;
    }

    @Override
    public void doBoardFunction(int clientID, FeldObject obj) {
        //TODO
    }



    /**
     * Calculate distances.
     *
     * @param direction the orientation
     */
    /*
    public static void calculateDistances(Direction direction){

        // To determine who is closest to the priority antenna, start at the antenna and count the number of spaces to each robot.
        // Count by row, then by column. (Betrag Differenz xPos und yPos )
        // In case of a tie:  imagine an invisible line pointing straight out from the
        // antenna’s dish. Once this line reaches the tied robots, it moves clockwise, and
        // the tied robots have priority according to the order in which the line reaches them.

        if (Game.priorityEachTurn.isEmpty()){

            TreeMap<Float, Integer> distanceMap = new TreeMap<>();
            // List of Distances by IDs
            for (int clientID : Game.activePlayersList){
                Position A = Game.playerPositions.get(clientID);
                //Position B = Game.getAntennaPosition();
                Position B = new Position(0,5);
                float distance = distanceBetweenAntennaAndRobot(A, B);
                while(distanceMap.containsKey(distance)){
                    Integer clientIDinList = distanceMap.get(distance);
                    if(swapPlayerPriority( clientIDinList, clientID)){
                        distance-=0.5;
                    }
                    else{
                        distance+=0.5;
                    }
                }
                distanceMap.put(distance, clientID);
            }
            for (Integer clientID : distanceMap.values()){
                Game.priorityEachTurn.add(clientID);
            }

            // Set priorityEachTurn with smallest distance first to largest
            // check for tie
            // if two have same priority --> check for swap (boolean)
        }
    }



    public static boolean swapPlayerPriority(Integer clientID_1, Integer clientID_2) {
        // get Position for Client ID
        // get Antenna Orientation


        switch (orientations.get(0)){
            // For each direction :

            case "top": ; //
                // In Direction, check if both on the right or left side of Antenna

                // both right side --> return the one further above
                if(game.getPositionOneClient(clientID_1).getX()>=game.getAntennaPosition().getX() && game.getPositionOneClient(clientID_2).getX()>=game.getAntennaPosition().getX()){
                    return game.getPositionOneClient(clientID_1).getY() > game.getPositionOneClient(clientID_2).getY();
                }
                // both left side --> return the one further down
                else if (game.getPositionOneClient(clientID_1).getX()<game.getAntennaPosition().getX() && game.getPositionOneClient(clientID_2).getX()<game.getAntennaPosition().getX()){
                    return game.getPositionOneClient(clientID_1).getY() < game.getPositionOneClient(clientID_2).getY();
                }
                // different side: return the one on the right
                else return game.getPositionOneClient(clientID_2).getX()>game.getAntennaPosition().getX();



            case "right":; //Change X and Y

                // both below --> return the one further right
                if(game.getPositionOneClient(clientID_1).getY()>=game.getAntennaPosition().getY() && game.getPositionOneClient(clientID_2).getY()>=game.getAntennaPosition().getY()){
                    return game.getPositionOneClient(clientID_1).getX() > game.getPositionOneClient(clientID_2).getX();
                }
                // both above --> return the one further left
                else if (game.getPositionOneClient(clientID_1).getY()<game.getAntennaPosition().getY() && game.getPositionOneClient(clientID_2).getY()<game.getAntennaPosition().getY()){
                    return game.getPositionOneClient(clientID_1).getX() < game.getPositionOneClient(clientID_2).getX();
                }
                // different side: return the one below
                else return game.getPositionOneClient(clientID_2).getY()>game.getAntennaPosition().getY();

            case "bottom": ;// Change > and <
                // both left side --> return the one further down
                if(game.getPositionOneClient(clientID_1).getX()<=game.getAntennaPosition().getX() && game.getPositionOneClient(clientID_2).getX()<=game.getAntennaPosition().getX()){
                    return game.getPositionOneClient(clientID_1).getY() < game.getPositionOneClient(clientID_2).getY();
                }
                // both right side --> return the one further up
                else if (game.getPositionOneClient(clientID_1).getX()>game.getAntennaPosition().getX() && game.getPositionOneClient(clientID_2).getX()>game.getAntennaPosition().getX()){
                    return game.getPositionOneClient(clientID_1).getY() > game.getPositionOneClient(clientID_2).getY();
                }
                // different side: return the one on the left
                else return game.getPositionOneClient(clientID_2).getX()<game.getAntennaPosition().getX();


            case "left":; // Change X and Y and < and >

                // both above --> return the one further left
                if(game.getPositionOneClient(clientID_1).getY()<=game.getAntennaPosition().getY() && game.getPositionOneClient(clientID_2).getY()<=game.getAntennaPosition().getY()){
                    return game.getPositionOneClient(clientID_1).getX() < game.getPositionOneClient(clientID_2).getX();
                }
                // both below --> return the one further right
                else if (game.getPositionOneClient(clientID_1).getY()>game.getAntennaPosition().getY() && game.getPositionOneClient(clientID_2).getY()>game.getAntennaPosition().getY()){
                    return game.getPositionOneClient(clientID_1).getX() > game.getPositionOneClient(clientID_2).getX();
                }
                // different side: return the one above
                else return game.getPositionOneClient(clientID_2).getY()<game.getAntennaPosition().getY();

        }

        return false;
    }




    public static int distanceBetweenAntennaAndRobot(Position antenna, Position robot){
        int distance = abs(antenna.getX()-robot.getX()) + abs(antenna.getY()-robot.getY());
        return distance;
    }

     */



}
