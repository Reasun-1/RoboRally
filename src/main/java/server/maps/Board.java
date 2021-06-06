package server.maps;

import server.feldobjects.*;
import server.game.Game;
import server.network.Server;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * @author Jonas Gottal
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class Board {
    private static final Logger logger = Logger.getLogger(Board.class.getName());

    public static void buildDizzyHighway(){
        logger.info("Board sets map of Dizzy Highway.");
        // fill first row with board elements
        Game.board.get(0).get(0).add(new Empty( "Start A"));
        Game.board.get(0).get(1).add(new Empty("Start A"));
        Game.board.get(0).get(2).add(new ConveyorBelt( "Start A",1, new ArrayList<>(){{add("right");add("left");}}));
        Game.board.get(0).get(3).add(new Empty( "5B"));
        Game.board.get(0).get(4).add(new ConveyorBelt( "5B",2, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(0).get(5).add(new ConveyorBelt( "5B",2, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(0).get(6).add(new Empty( "5B"));
        Game.board.get(0).get(7).add(new Empty("5B"));
        Game.board.get(0).get(8).add(new Empty( "5B"));
        Game.board.get(0).get(9).add(new Empty("5B"));
        Game.board.get(0).get(10).add(new Empty( "5B"));
        Game.board.get(0).get(11).add(new Empty("5B"));
        Game.board.get(0).get(12).add(new EnergySpace("5B",1));

        // fill second row with board elements
        Game.board.get(1).get(0).add(new Empty("Start A"));
        Game.board.get(1).get(1).add(new StartPoint( "Start A"));
        Game.board.get(1).get(2).add(new Empty( "Start A"));
        Game.board.get(1).get(3).add(new Empty( "5B"));
        Game.board.get(1).get(4).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("bottom");add("top");add("right");}}));
        Game.board.get(1).get(5).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("left");add("top");add("right");}}));
        Game.board.get(1).get(6).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("left");}}));
        Game.board.get(1).get(7).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("left");}}));
        Game.board.get(1).get(8).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("left");}}));
        Game.board.get(1).get(9).add(new ConveyorBelt("5B", 2, new ArrayList<>(){{add("left");}}));
        Game.board.get(1).get(10).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("left");}}));
        Game.board.get(1).get(11).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("left");add("right");add("bottom");}}));
        Game.board.get(1).get(12).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("left");}}));

        // fill third row with board elements
        Game.board.get(2).get(0).add(new Empty("Start A"));
        Game.board.get(2).get(1).add(new Wall("Start A", new ArrayList<>(){{add("top");}}));
        Game.board.get(2).get(2).add(new Empty( "Start A"));
        Game.board.get(2).get(3).add(new Empty("5B"));
        Game.board.get(2).get(4).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(2).get(5).add(new EnergySpace("5B",1));
        Game.board.get(2).get(6).add(new Empty( "5B"));
        Game.board.get(2).get(7).add(new Empty( "5B"));
        Game.board.get(2).get(8).add(new Empty( "5B"));
        Game.board.get(2).get(9).add(new Empty("5B"));
        Game.board.get(2).get(10).add(new Empty( "5B"));
        Game.board.get(2).get(11).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("top");add("right");add("bottom");}}));
        Game.board.get(2).get(12).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("left");}}));


        // fill forth row with board elements
        Game.board.get(3).get(0).add(new StartPoint( "Start A"));
        Game.board.get(3).get(1).add(new Empty( "Start A"));
        Game.board.get(3).get(2).add(new Empty( "Start A"));
        Game.board.get(3).get(3).add(new Empty("5B"));
        Game.board.get(3).get(4).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(3).get(5).add(new Empty("5B"));
        Game.board.get(3).get(6).add(new Wall("5B", new ArrayList<>(){{add("top");}}));
        Game.board.get(3).get(7).add(new Empty( "5B"));
        Game.board.get(3).get(7).add(new RestartPoint("DizzyHighway", new ArrayList<>(){{add("bottom");}}));
        Game.board.get(3).get(8).add(new Laser("5B", new ArrayList<>(){{add("right");}}, 1));
        Game.board.get(3).get(8).add(new Wall( "5B", new ArrayList<>(){{add("left");}}));
        Game.board.get(3).get(9).add(new Wall( "5B", new ArrayList<>(){{add("right");}}));
        Game.board.get(3).get(10).add(new Empty( "5B"));
        Game.board.get(3).get(11).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("top");}}));
        Game.board.get(3).get(12).add(new Empty( "5B"));
        Game.board.get(3).get(12).add(new CheckPoint("DizzyHighway", 1));

        // fill fifth row with board elements
        Game.board.get(4).get(0).add(new Antenna("Start A", new ArrayList<>(){{add("right");}}));
        Game.board.get(4).get(1).add(new StartPoint("Start A"));
        Game.board.get(4).get(2).add(new Wall("Start A", new ArrayList<>(){{add("right");}}));
        Game.board.get(4).get(3).add(new Empty("5B"));
        Game.board.get(4).get(4).add(new ConveyorBelt("5B", 2, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(4).get(5).add(new Empty( "5B"));
        Game.board.get(4).get(6).add(new Laser("5B", new ArrayList<>(){{add("top");}}, 1));
        Game.board.get(4).get(6).add(new Wall( "5B", new ArrayList<>(){{add("bottom");}}));
        Game.board.get(4).get(7).add(new Empty( "5B"));
        Game.board.get(4).get(8).add(new EnergySpace("5B", 1));
        Game.board.get(4).get(9).add(new Empty("5B"));
        Game.board.get(4).get(10).add(new Empty("5B"));
        Game.board.get(4).get(11).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("top");}}));
        Game.board.get(4).get(12).add(new Empty("5B"));

        // fill sixth row with board elements
        Game.board.get(5).get(0).add(new Empty("Start A"));
        Game.board.get(5).get(1).add(new StartPoint("Start A"));
        Game.board.get(5).get(2).add(new Wall( "Start A", new ArrayList<>(){{add("right");}}));
        Game.board.get(5).get(3).add(new Empty( "5B"));
        Game.board.get(5).get(4).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(5).get(5).add(new Empty( "5B"));
        Game.board.get(5).get(6).add(new Empty( "5B"));
        Game.board.get(5).get(7).add(new EnergySpace("5B", 1));
        Game.board.get(5).get(8).add(new Empty( "5B"));
        Game.board.get(5).get(9).add(new Laser("5B", new ArrayList<>(){{add("bottom");}}, 1));
        Game.board.get(5).get(9).add(new Wall( "5B", new ArrayList<>(){{add("top");}}));
        Game.board.get(5).get(10).add(new Empty( "5B"));
        Game.board.get(5).get(11).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("top");}}));
        Game.board.get(5).get(12).add(new Empty("5B"));

        // fill seventh row with board elements
        Game.board.get(6).get(0).add(new StartPoint( "Start A"));
        Game.board.get(6).get(1).add(new Empty("Start A"));
        Game.board.get(6).get(2).add(new Empty( "Start A"));
        Game.board.get(6).get(3).add(new Empty("5B"));
        Game.board.get(6).get(4).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(6).get(5).add(new Empty( "5B"));
        Game.board.get(6).get(6).add(new Wall( "5B", new ArrayList<>(){{add("left");}}));
        Game.board.get(6).get(7).add(new Laser( "5B", new ArrayList<>(){{add("left");}}, 1));
        Game.board.get(6).get(7).add(new Wall("5B",new ArrayList<>(){{add("right");}}));
        Game.board.get(6).get(8).add(new Empty( "5B"));
        Game.board.get(6).get(9).add(new Wall( "5B", new ArrayList<>(){{add("bottom");}}));
        Game.board.get(6).get(10).add(new Empty( "5B"));
        Game.board.get(6).get(11).add(new ConveyorBelt("5B", 2, new ArrayList<>(){{add("top");}}));
        Game.board.get(6).get(12).add(new Empty("5B"));

        // fill eighth row with board elements
        Game.board.get(7).get(0).add(new Empty("Start A"));
        Game.board.get(7).get(1).add(new Wall( "Start A", new ArrayList<>(){{add("bottom");}}));
        Game.board.get(7).get(2).add(new Empty( "Start A"));
        Game.board.get(7).get(3).add(new ConveyorBelt("5B", 2, new ArrayList<>(){{add("right");}}));
        Game.board.get(7).get(4).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("bottom");add("left");add("top");}}));
        Game.board.get(7).get(5).add(new Empty("5B"));
        Game.board.get(7).get(6).add(new Empty( "5B"));
        Game.board.get(7).get(7).add(new Empty("5B"));
        Game.board.get(7).get(8).add(new Empty( "5B"));
        Game.board.get(7).get(9).add(new Empty("5B"));
        Game.board.get(7).get(10).add(new EnergySpace( "5B", 1));
        Game.board.get(7).get(11).add(new ConveyorBelt("5B", 2, new ArrayList<>(){{add("top");}}));
        Game.board.get(7).get(12).add(new Empty("5B"));

        // fill ninth row with board elements
        Game.board.get(8).get(0).add(new Empty( "Start A"));
        Game.board.get(8).get(1).add(new StartPoint( "Start A"));
        Game.board.get(8).get(2).add(new Empty( "Start A"));
        Game.board.get(8).get(3).add(new ConveyorBelt("5B", 2, new ArrayList<>(){{add("right");}}));
        Game.board.get(8).get(4).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("right");add("left");add("top");}}));
        Game.board.get(8).get(5).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("right");}}));
        Game.board.get(8).get(6).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("right");}}));
        Game.board.get(8).get(7).add(new ConveyorBelt("5B", 2, new ArrayList<>(){{add("right");}}));
        Game.board.get(8).get(8).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("right");}}));
        Game.board.get(8).get(9).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("right");}}));
        Game.board.get(8).get(10).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("right");add("left");add("bottom");}}));
        Game.board.get(8).get(11).add(new ConveyorBelt("5B", 2, new ArrayList<>(){{add("top");add("left");add("bottom");}}));
        Game.board.get(8).get(12).add(new Empty("5B"));

        // fill tenth row with board elements
        Game.board.get(9).get(0).add(new Empty( "Start A"));
        Game.board.get(9).get(1).add(new Empty( "Start A"));
        Game.board.get(9).get(2).add(new ConveyorBelt( "Start A", 1, new ArrayList<>(){{add("right");add("left");}}));
        Game.board.get(9).get(3).add(new EnergySpace("5B", 1));
        Game.board.get(9).get(4).add(new Empty("5B"));
        Game.board.get(9).get(5).add(new Empty("5B"));
        Game.board.get(9).get(6).add(new Empty("5B"));
        Game.board.get(9).get(7).add(new Empty("5B"));
        Game.board.get(9).get(8).add(new Empty( "5B"));
        Game.board.get(9).get(9).add(new Empty("5B"));
        Game.board.get(9).get(10).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("top");}}));
        Game.board.get(9).get(11).add(new ConveyorBelt("5B", 2, new ArrayList<>(){{add("top");}}));
        Game.board.get(9).get(12).add(new Empty("5B"));


    }

    // only for test
    public static void main(String[] args) {

        Game.getInstance().initBoard();
        buildDizzyHighway();

        for (int i = 0; i < Game.board.size(); i++) {
            for (int j = 0; j < Game.board.get(0).size(); j++) {

                if(Game.board.get(i).get(j).size() != 0){
                    System.out.print(Game.board.get(i).get(j).get(0).getClass().getSimpleName() + " ");
                }else{
                    System.out.print(Game.board.get(i).get(j) + " ");
                }
            }
            System.out.println("");
        }
    }

}
