package server.feldobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import server.game.Game;
import server.game.Position;

import java.util.ArrayList;
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
public class Antenna extends FeldObject {

    //private String type;
    private String isOnBoard;
    private List<String> orientations;

    /**
     * Instantiates a new Antenna.
     */
    public Antenna() {
    }

    /**
     * Instantiates a new Antenna.
     *
     * @param isOnBoard    the is on board
     * @param orientations the orientations
     */
    public Antenna(String isOnBoard, List<String> orientations) {
        //this.type = type;
        this.isOnBoard = isOnBoard;
        this.orientations = orientations;
    }

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
     */
    public static void calculateDistances() {

        //if(Game.registerPointer != Game.changePriorityRegPointer){
        if(!Game.hashMapPriority.containsKey(Game.registerPointer)){

            // To determine who is closest to the priority antenna, start at the antenna and count the number of spaces to each robot.
            // Count by row, then by column. (Betrag Differenz xPos und yPos )
            // In case of a tie:  imagine an invisible line pointing straight out from the
            // antenna’s dish. Once this line reaches the tied robots, it moves clockwise, and
            // the tied robots have priority according to the order in which the line reaches them.
            TreeMap<Float, Integer> distanceMap = new TreeMap<>();
            // List of Distances by IDs
            for (int clientID : Game.activePlayersList) {
                Position A = Game.playerPositions.get(clientID);
                //Position B = Game.positionAntenna;
                Position B = Game.positionAntenna;
                float distance = distanceBetweenAntennaAndRobot(A, B);
                if (distanceMap.containsKey(distance)) {
                    Integer clientIDinList = distanceMap.get(distance);
                    if (swapPlayerPriority(clientIDinList, clientID)) {
                        distance -= 0.5;
                    } else {
                        distance += 0.5;
                    }
                }
                distanceMap.put(distance, clientID);
            }

            for (Integer clientID : distanceMap.values()) {
                Game.priorityEachTurn.add(clientID);
            }
            // Set priorityEachTurn with smallest distance first to largest
            // check for tie
            // if two have same priority --> check for swap (boolean)

        }else{// use the changed priority list

            System.out.println("ANTENNA print regpointer: " + Game.registerPointer);

            List<Integer> changedList = Game.hashMapPriority.get(Game.registerPointer);

            List<Integer> allclients = new ArrayList<>();
            for(int clt : Game.clientIDs){
                allclients.add(clt);
            }

            List<Integer> notActiveClients = new ArrayList<>();
            notActiveClients = allclients;
            notActiveClients.removeAll(Game.activePlayersList);

            List<Integer> activeAndPriorityClients = new ArrayList<>();
            for(int clt : changedList){// deep copy from changedPriority
                activeAndPriorityClients.add(clt);
            }
            activeAndPriorityClients.removeAll(notActiveClients);

            List<Integer> activeWithoutPriority = new ArrayList<>();
            for(int clt : Game.activePlayersList){// deep copy from activePlayersList
                activeWithoutPriority.add(clt);
            }
            activeWithoutPriority.removeAll(activeAndPriorityClients);

            activeAndPriorityClients.addAll(activeWithoutPriority);

            for(int clt : activeAndPriorityClients){
                Game.priorityEachTurn.add(clt);
            }

            Game.changedPriorityList.clear();
            Game.changePriorityRegPointer = -1;

            System.out.println("change priority by Admin "+Game.priorityEachTurn);
        }
    }


    /**
     * Swap player priority boolean.
     *
     * @param clientID_1 the client id 1
     * @param clientID_2 the client id 2
     * @return the boolean
     */
    public static boolean swapPlayerPriority(Integer clientID_1, Integer clientID_2) {
        // get Position for Client ID
        // get Antenna Orientation


        switch (Game.directionAntenna) {
            // For each direction :

            case "top":
                ; //
                // In Direction, check if both on the right or left side of Antenna

                // both right side --> return the one further above
                if (Game.playerPositions.get(clientID_1).getX() >= Game.positionAntenna.getX() && Game.playerPositions.get(clientID_2).getX() >= Game.positionAntenna.getX()) {
                    return Game.playerPositions.get(clientID_1).getY() > Game.playerPositions.get(clientID_2).getY();
                }
                // both left side --> return the one further down
                else if (Game.playerPositions.get(clientID_1).getX() < Game.positionAntenna.getX() && Game.playerPositions.get(clientID_2).getX() < Game.positionAntenna.getX()) {
                    return Game.playerPositions.get(clientID_1).getY() < Game.playerPositions.get(clientID_2).getY();
                }
                // different side: return the one on the right
                else return Game.playerPositions.get(clientID_2).getX() > Game.positionAntenna.getX();


            case "right":
                ; //Change X and Y

                // both below --> return the one further right
                if (Game.playerPositions.get(clientID_1).getY() >= Game.positionAntenna.getY() && Game.playerPositions.get(clientID_2).getY() >= Game.positionAntenna.getY()) {
                    return Game.playerPositions.get(clientID_1).getX() > Game.playerPositions.get(clientID_2).getX();
                }
                // both above --> return the one further left
                else if (Game.playerPositions.get(clientID_1).getY() < Game.positionAntenna.getY() && Game.playerPositions.get(clientID_2).getY() < Game.positionAntenna.getY()) {
                    return Game.playerPositions.get(clientID_1).getX() < Game.playerPositions.get(clientID_2).getX();
                }
                // different side: return the one below
                else return Game.playerPositions.get(clientID_2).getY() > Game.positionAntenna.getY();

            case "bottom":
                ;// Change > and <
                // both left side --> return the one further down
                if (Game.playerPositions.get(clientID_1).getX() <= Game.positionAntenna.getX() && Game.playerPositions.get(clientID_2).getX() <= Game.positionAntenna.getX()) {
                    return Game.playerPositions.get(clientID_1).getY() < Game.playerPositions.get(clientID_2).getY();
                }
                // both right side --> return the one further up
                else if (Game.playerPositions.get(clientID_1).getX() > Game.positionAntenna.getX() && Game.playerPositions.get(clientID_2).getX() > Game.positionAntenna.getX()) {
                    return Game.playerPositions.get(clientID_1).getY() > Game.playerPositions.get(clientID_2).getY();
                }
                // different side: return the one on the left
                else return Game.playerPositions.get(clientID_2).getX() < Game.positionAntenna.getX();


            case "left":
                ; // Change X and Y and < and >

                // both above --> return the one further left
                if (Game.playerPositions.get(clientID_1).getY() <= Game.positionAntenna.getY() && Game.playerPositions.get(clientID_2).getY() <= Game.positionAntenna.getY()) {
                    return Game.playerPositions.get(clientID_1).getX() < Game.playerPositions.get(clientID_2).getX();
                }
                // both below --> return the one further right
                else if (Game.playerPositions.get(clientID_1).getY() > Game.positionAntenna.getY() && Game.playerPositions.get(clientID_2).getY() > Game.positionAntenna.getY()) {
                    return Game.playerPositions.get(clientID_1).getX() > Game.playerPositions.get(clientID_2).getX();
                }
                // different side: return the one above
                else return Game.playerPositions.get(clientID_2).getY() < Game.positionAntenna.getY();

        }

        return false;
    }


    /**
     * Distance between antenna and robot int.
     *
     * @param antenna the antenna
     * @param robot   the robot
     * @return the int
     */
    public static int distanceBetweenAntennaAndRobot(Position antenna, Position robot) {
        int distance = Math.abs(antenna.getX() - robot.getX()) + Math.abs(antenna.getY() - robot.getY());
        return distance;
    }

    // only for test
    /*
    public static void main(String[] args) {
        Game.positionAntenna = new Position(0, 5);
        Game.directionAntenna = "right";
        Position p1 = new Position(2, 7);
        Position p2 = new Position(3, 6);

        int client1 = 1;
        int client2 = 2;

        Game.playerPositions.put(1, p1);
        Game.playerPositions.put(2, p2);

        Game.activePlayersList.add(client1);
        Game.activePlayersList.add(client2);

        calculateDistances();

        System.out.println(Game.priorityEachTurn);
    }
     */
}
