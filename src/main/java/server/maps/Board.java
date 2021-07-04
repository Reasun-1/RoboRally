package server.maps;

import org.apache.log4j.Logger;
import server.feldobjects.*;
import server.game.Game;

import java.util.ArrayList;


/**
 * @author Jonas Gottal
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class Board {
    private static final Logger logger = Logger.getLogger(Board.class.getName());

    public static void buildDizzyHighway(){
        logger.info("Board sets map of Dizzy Highway.");

        Game.board.get(0).get(0).add(new Empty( "Start A"));
        Game.board.get(0).get(1).add(new Empty("Start A"));
        Game.board.get(0).get(2).add(new Empty("Start A"));
        Game.board.get(0).get(3).add(new StartPoint( "Start A"));
        Game.board.get(0).get(4).add(new Antenna("Start A", new ArrayList<>(){{add("right");}}));
        Game.board.get(0).get(5).add(new Empty("Start A"));
        Game.board.get(0).get(6).add(new StartPoint( "Start A"));
        Game.board.get(0).get(7).add(new Empty("Start A"));
        Game.board.get(0).get(8).add(new Empty( "Start A"));
        Game.board.get(0).get(9).add(new Empty( "Start A"));

        Game.board.get(1).get(0).add(new Empty("Start A"));
        Game.board.get(1).get(1).add(new StartPoint( "Start A"));
        Game.board.get(1).get(2).add(new Wall("Start A", new ArrayList<>(){{add("top");}}));
        Game.board.get(1).get(3).add(new Empty( "Start A"));
        Game.board.get(1).get(4).add(new StartPoint("Start A"));
        Game.board.get(1).get(5).add(new StartPoint("Start A"));
        Game.board.get(1).get(6).add(new Empty("Start A"));
        Game.board.get(1).get(7).add(new Wall( "Start A", new ArrayList<>(){{add("bottom");}}));
        Game.board.get(1).get(8).add(new StartPoint( "Start A"));
        Game.board.get(1).get(9).add(new Empty( "Start A"));

        Game.board.get(2).get(0).add(new ConveyorBelt( "Start A",1, new ArrayList<>(){{add("right");add("left");}}));
        Game.board.get(2).get(1).add(new Empty( "Start A"));
        Game.board.get(2).get(2).add(new Empty( "Start A"));
        Game.board.get(2).get(3).add(new Empty( "Start A"));
        Game.board.get(2).get(4).add(new Wall("Start A", new ArrayList<>(){{add("right");}}));
        Game.board.get(2).get(5).add(new Wall( "Start A", new ArrayList<>(){{add("right");}}));
        Game.board.get(2).get(6).add(new Empty( "Start A"));
        Game.board.get(2).get(7).add(new Empty( "Start A"));
        Game.board.get(2).get(8).add(new Empty( "Start A"));
        Game.board.get(2).get(9).add(new ConveyorBelt( "Start A", 1, new ArrayList<>(){{add("right");add("left");}}));

        Game.board.get(3).get(0).add(new Empty( "5B"));
        Game.board.get(3).get(1).add(new Empty( "5B"));
        Game.board.get(3).get(2).add(new Empty("5B"));
        Game.board.get(3).get(3).add(new Empty("5B"));
        Game.board.get(3).get(4).add(new Empty("5B"));
        Game.board.get(3).get(5).add(new Empty( "5B"));
        Game.board.get(3).get(6).add(new Empty("5B"));
        Game.board.get(3).get(7).add(new ConveyorBelt("5B", 2, new ArrayList<>(){{add("right");}}));
        Game.board.get(3).get(8).add(new ConveyorBelt("5B", 2, new ArrayList<>(){{add("right");}}));
        Game.board.get(3).get(9).add(new EnergySpace("5B", 1));

        Game.board.get(4).get(0).add(new ConveyorBelt( "5B",2, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(4).get(1).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("bottom");add("top");add("right");}}));
        Game.board.get(4).get(2).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(4).get(3).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(4).get(4).add(new ConveyorBelt("5B", 2, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(4).get(5).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(4).get(6).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(4).get(7).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("bottom");add("left");add("top");}}));
        Game.board.get(4).get(8).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("right");add("left");add("top");}}));
        Game.board.get(4).get(9).add(new Empty("5B"));

        Game.board.get(5).get(0).add(new ConveyorBelt( "5B",2, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(5).get(1).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("left");add("top");add("right");}}));
        Game.board.get(5).get(2).add(new EnergySpace("5B",1));
        Game.board.get(5).get(3).add(new Empty("5B"));
        Game.board.get(5).get(4).add(new Empty( "5B"));
        Game.board.get(5).get(5).add(new Empty( "5B"));
        Game.board.get(5).get(6).add(new Empty( "5B"));
        Game.board.get(5).get(7).add(new Empty("5B"));
        Game.board.get(5).get(8).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("right");}}));
        Game.board.get(5).get(9).add(new Empty("5B"));

        Game.board.get(6).get(0).add(new Empty( "5B"));
        Game.board.get(6).get(1).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("left");}}));
        Game.board.get(6).get(2).add(new Empty( "5B"));
        Game.board.get(6).get(3).add(new Wall("5B", new ArrayList<>(){{add("top");}}));
        Game.board.get(6).get(4).add(new Laser("5B", new ArrayList<>(){{add("top");}}, 1));
        Game.board.get(6).get(4).add(new Wall( "5B", new ArrayList<>(){{add("bottom");}}));
        Game.board.get(6).get(5).add(new Empty( "5B"));
        Game.board.get(6).get(6).add(new Wall( "5B", new ArrayList<>(){{add("left");}}));
        Game.board.get(6).get(7).add(new Empty( "5B"));
        Game.board.get(6).get(8).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("right");}}));
        Game.board.get(6).get(9).add(new Empty("5B"));

        Game.board.get(7).get(0).add(new Empty("5B"));
        Game.board.get(7).get(1).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("left");}}));
        Game.board.get(7).get(2).add(new Empty( "5B"));
        Game.board.get(7).get(3).add(new Empty( "5B"));
        Game.board.get(7).get(3).add(new RestartPoint("DizzyHighway", new ArrayList<>(){{add("bottom");}}));
        Game.board.get(7).get(4).add(new Empty( "5B"));
        Game.board.get(7).get(5).add(new EnergySpace("5B", 1));
        Game.board.get(7).get(6).add(new Laser( "5B", new ArrayList<>(){{add("left");}}, 1));
        Game.board.get(7).get(6).add(new Wall("5B",new ArrayList<>(){{add("right");}}));
        Game.board.get(7).get(7).add(new Empty("5B"));
        Game.board.get(7).get(8).add(new ConveyorBelt("5B", 2, new ArrayList<>(){{add("right");}}));
        Game.board.get(7).get(9).add(new Empty("5B"));

        Game.board.get(8).get(0).add(new Empty( "5B"));
        Game.board.get(8).get(1).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("left");}}));
        Game.board.get(8).get(2).add(new Empty( "5B"));
        Game.board.get(8).get(3).add(new Laser("5B", new ArrayList<>(){{add("right");}}, 1));
        Game.board.get(8).get(3).add(new Wall( "5B", new ArrayList<>(){{add("left");}}));
        Game.board.get(8).get(4).add(new EnergySpace("5B", 1));
        Game.board.get(8).get(5).add(new Empty( "5B"));
        Game.board.get(8).get(6).add(new Empty( "5B"));
        Game.board.get(8).get(7).add(new Empty( "5B"));
        Game.board.get(8).get(8).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("right");}}));
        Game.board.get(8).get(9).add(new Empty( "5B"));

        Game.board.get(9).get(0).add(new Empty("5B"));
        Game.board.get(9).get(1).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("left");}}));
        Game.board.get(9).get(2).add(new Empty("5B"));
        Game.board.get(9).get(3).add(new Wall( "5B", new ArrayList<>(){{add("right");}}));
        Game.board.get(9).get(4).add(new Empty("5B"));
        Game.board.get(9).get(5).add(new Laser("5B", new ArrayList<>(){{add("bottom");}}, 1));
        Game.board.get(9).get(5).add(new Wall( "5B", new ArrayList<>(){{add("top");}}));
        Game.board.get(9).get(6).add(new Wall( "5B", new ArrayList<>(){{add("bottom");}}));
        Game.board.get(9).get(7).add(new Empty("5B"));
        Game.board.get(9).get(8).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("right");}}));
        Game.board.get(9).get(9).add(new Empty("5B"));

        Game.board.get(10).get(0).add(new Empty( "5B"));
        Game.board.get(10).get(1).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("left");}}));
        Game.board.get(10).get(2).add(new Empty( "5B"));
        Game.board.get(10).get(3).add(new Empty( "5B"));
        Game.board.get(10).get(4).add(new Empty("5B"));
        Game.board.get(10).get(5).add(new Empty( "5B"));
        Game.board.get(10).get(6).add(new Empty( "5B"));
        Game.board.get(10).get(7).add(new EnergySpace( "5B", 1));
        Game.board.get(10).get(8).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("right");add("bottom");add("left");}}));
        Game.board.get(10).get(9).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("top");}}));

        Game.board.get(11).get(0).add(new Empty("5B"));
        Game.board.get(11).get(1).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("left");add("right");add("bottom");}}));
        Game.board.get(11).get(2).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("top");add("right");add("bottom");}}));
        Game.board.get(11).get(3).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("top");}}));
        Game.board.get(11).get(4).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("top");}}));
        Game.board.get(11).get(5).add(new ConveyorBelt("5B", 2, new ArrayList<>(){{add("top");}}));
        Game.board.get(11).get(6).add(new ConveyorBelt("5B", 2, new ArrayList<>(){{add("top");}}));
        Game.board.get(11).get(7).add(new ConveyorBelt("5B", 2, new ArrayList<>(){{add("top");}}));
        Game.board.get(11).get(8).add(new ConveyorBelt("5B", 2, new ArrayList<>(){{add("top");add("bottom");add("left");}}));
        Game.board.get(11).get(9).add(new ConveyorBelt("5B", 2, new ArrayList<>(){{add("top");}}));

        Game.board.get(12).get(0).add(new EnergySpace("5B",1));
        Game.board.get(12).get(1).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("left");}}));
        Game.board.get(12).get(2).add(new ConveyorBelt( "5B", 2, new ArrayList<>(){{add("left");}}));
        Game.board.get(12).get(3).add(new Empty( "5B"));
        Game.board.get(12).get(3).add(new CheckPoint("DizzyHighway", 1));
        Game.board.get(12).get(4).add(new Empty("5B"));
        Game.board.get(12).get(5).add(new Empty("5B"));
        Game.board.get(12).get(6).add(new Empty("5B"));
        Game.board.get(12).get(7).add(new Empty("5B"));
        Game.board.get(12).get(8).add(new Empty("5B"));
        Game.board.get(12).get(9).add(new Empty("5B"));

    }

    public static void buildLostBearings(){
        logger.info("Board sets map of Lost Bearings.");
        // fill first row with board elements
        Game.board.get(0).get(0).add(new Empty( "Start A"));
        Game.board.get(0).get(0).add(new RestartPoint("LostBearings", new ArrayList<>(){{add("right");}}));
        Game.board.get(0).get(1).add(new Empty("Start A"));
        Game.board.get(0).get(2).add(new Empty("Start A"));
        Game.board.get(0).get(3).add(new StartPoint( "Start A"));
        Game.board.get(0).get(4).add(new Antenna("Start A", new ArrayList<>(){{add("right");}}));
        Game.board.get(0).get(5).add(new Empty("Start A"));
        Game.board.get(0).get(6).add(new StartPoint( "Start A"));
        Game.board.get(0).get(7).add(new Empty("Start A"));
        Game.board.get(0).get(8).add(new Empty( "Start A"));
        Game.board.get(0).get(9).add(new Empty( "Start A"));

        Game.board.get(1).get(0).add(new Empty("Start A"));
        Game.board.get(1).get(1).add(new StartPoint( "Start A"));
        Game.board.get(1).get(2).add(new Wall("Start A", new ArrayList<>(){{add("top");}}));
        Game.board.get(1).get(3).add(new Empty( "Start A"));
        Game.board.get(1).get(4).add(new StartPoint("Start A"));
        Game.board.get(1).get(5).add(new StartPoint("Start A"));
        Game.board.get(1).get(6).add(new Empty("Start A"));
        Game.board.get(1).get(7).add(new Wall( "Start A", new ArrayList<>(){{add("bottom");}}));
        Game.board.get(1).get(8).add(new StartPoint( "Start A"));
        Game.board.get(1).get(9).add(new Empty( "Start A"));

        Game.board.get(2).get(0).add(new ConveyorBelt( "Start A",1, new ArrayList<>(){{add("right");add("left");}}));
        Game.board.get(2).get(1).add(new Empty( "Start A"));
        Game.board.get(2).get(2).add(new Empty( "Start A"));
        Game.board.get(2).get(3).add(new Empty( "Start A"));
        Game.board.get(2).get(4).add(new Wall("Start A", new ArrayList<>(){{add("right");}}));
        Game.board.get(2).get(5).add(new Wall( "Start A", new ArrayList<>(){{add("right");}}));
        Game.board.get(2).get(6).add(new Empty( "Start A"));
        Game.board.get(2).get(7).add(new Empty( "Start A"));
        Game.board.get(2).get(8).add(new Empty( "Start A"));
        Game.board.get(2).get(9).add(new ConveyorBelt( "Start A", 1, new ArrayList<>(){{add("right");add("left");}}));

        Game.board.get(3).get(0).add(new Empty( "1A"));
        Game.board.get(3).get(1).add(new ConveyorBelt( "1A",1, new ArrayList<>(){{add("left");}}));
        Game.board.get(3).get(2).add(new Empty( "1A"));
        Game.board.get(3).get(3).add(new Empty( "1A"));
        Game.board.get(3).get(4).add(new Empty( "1A"));
        Game.board.get(3).get(5).add(new Empty( "1A"));
        Game.board.get(3).get(6).add(new Empty( "1A"));
        Game.board.get(3).get(7).add(new Empty( "1A"));
        Game.board.get(3).get(8).add(new ConveyorBelt( "1A",1, new ArrayList<>(){{add("right");}}));
        Game.board.get(3).get(9).add(new Empty( "1A"));

        Game.board.get(4).get(0).add(new ConveyorBelt( "1A",1, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(4).get(1).add(new ConveyorBelt( "1A",1, new ArrayList<>(){{add("left");add("top");}}));
        Game.board.get(4).get(2).add(new Empty( "1A"));
        Game.board.get(4).get(3).add(new Empty( "1A"));
        Game.board.get(4).get(4).add(new Empty( "1A"));
        Game.board.get(4).get(5).add(new Empty( "1A"));
        Game.board.get(4).get(5).add(new CheckPoint("LostBearings", 2));
        Game.board.get(4).get(6).add(new Empty( "1A"));
        Game.board.get(4).get(7).add(new Empty( "1A"));
        Game.board.get(4).get(8).add(new ConveyorBelt( "1A",1, new ArrayList<>(){{add("bottom");add("left");}}));
        Game.board.get(4).get(9).add(new ConveyorBelt( "1A",1, new ArrayList<>(){{add("bottom");}}));

        Game.board.get(5).get(0).add(new Empty( "1A"));
        Game.board.get(5).get(1).add(new Empty( "1A"));
        Game.board.get(5).get(2).add(new EnergySpace("1A", 1));
        Game.board.get(5).get(3).add(new ConveyorBelt( "1A",2, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(5).get(4).add(new Gear( "1A", new ArrayList<>(){{add("counterclockwise");}}));
        Game.board.get(5).get(5).add(new Gear( "1A", new ArrayList<>(){{add("clockwise");}}));
        Game.board.get(5).get(6).add(new ConveyorBelt( "1A",2, new ArrayList<>(){{add("top");}}));
        Game.board.get(5).get(7).add(new EnergySpace("1A", 1));
        Game.board.get(5).get(8).add(new Empty( "1A"));
        Game.board.get(5).get(9).add(new Empty( "1A"));

        Game.board.get(6).get(0).add(new Empty( "1A"));
        Game.board.get(6).get(1).add(new ConveyorBelt( "1A",1, new ArrayList<>(){{add("left");}}));
        Game.board.get(6).get(2).add(new Pit("1A"));
        Game.board.get(6).get(3).add(new Wall("1A", new ArrayList<>(){{add("left");}}));
        Game.board.get(6).get(4).add(new Empty( "1A"));
        Game.board.get(6).get(5).add(new Empty( "1A"));
        Game.board.get(6).get(6).add(new Laser( "1A", new ArrayList<>(){{add("right");}}, 1));
        Game.board.get(6).get(6).add(new Wall("1A", new ArrayList<>(){{add("left");}}));
        Game.board.get(6).get(7).add(new Pit("1A"));
        Game.board.get(6).get(8).add(new Empty( "1A"));
        Game.board.get(6).get(9).add(new Empty( "1A"));

        Game.board.get(7).get(0).add(new Empty( "1A"));
        Game.board.get(7).get(1).add(new ConveyorBelt( "1A",1, new ArrayList<>(){{add("left");}}));
        Game.board.get(7).get(2).add(new Empty( "1A"));
        Game.board.get(7).get(3).add(new Empty( "1A"));
        Game.board.get(7).get(4).add(new EnergySpace("1A", 1));
        Game.board.get(7).get(5).add(new Gear( "1A", new ArrayList<>(){{add("clockwise");}}));
        Game.board.get(7).get(6).add(new Empty( "1A"));
        Game.board.get(7).get(7).add(new Empty( "1A"));
        Game.board.get(7).get(8).add(new Empty( "1A"));
        Game.board.get(7).get(9).add(new Empty( "1A"));

        Game.board.get(8).get(0).add(new Empty( "1A"));
        Game.board.get(8).get(1).add(new ConveyorBelt( "1A",1, new ArrayList<>(){{add("right");}}));
        Game.board.get(8).get(2).add(new Empty( "1A"));
        Game.board.get(8).get(2).add(new CheckPoint("LostBearings", 3));
        Game.board.get(8).get(3).add(new Empty( "1A"));
        Game.board.get(8).get(4).add(new Gear( "1A", new ArrayList<>(){{add("counterclockwise");}}));
        Game.board.get(8).get(5).add(new EnergySpace("1A", 1));
        Game.board.get(8).get(6).add(new Empty( "1A"));
        Game.board.get(8).get(7).add(new Empty( "1A"));
        Game.board.get(8).get(7).add(new CheckPoint("LostBearings", 4));
        Game.board.get(8).get(8).add(new Empty( "1A"));
        Game.board.get(8).get(9).add(new Empty( "1A"));

        Game.board.get(9).get(0).add(new Empty( "1A"));
        Game.board.get(9).get(1).add(new ConveyorBelt( "1A",1, new ArrayList<>(){{add("right");}}));
        Game.board.get(9).get(2).add(new Pit("1A"));
        Game.board.get(9).get(3).add(new Laser( "1A", new ArrayList<>(){{add("left");}}, 1));
        Game.board.get(9).get(3).add(new Wall("1A", new ArrayList<>(){{add("right");}}));
        Game.board.get(9).get(4).add(new Empty( "1A"));
        Game.board.get(9).get(5).add(new Empty( "1A"));
        Game.board.get(9).get(6).add(new Wall("1A", new ArrayList<>(){{add("right");}}));
        Game.board.get(9).get(7).add(new Pit("1A"));
        Game.board.get(9).get(8).add(new Empty( "1A"));
        Game.board.get(9).get(9).add(new Empty( "1A"));

        Game.board.get(10).get(0).add(new Empty( "1A"));
        Game.board.get(10).get(1).add(new Empty( "1A"));
        Game.board.get(10).get(2).add(new EnergySpace("1A", 1));
        Game.board.get(10).get(3).add(new ConveyorBelt( "1A",2, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(10).get(4).add(new Gear( "1A", new ArrayList<>(){{add("clockwise");}}));
        Game.board.get(10).get(5).add(new Gear( "1A", new ArrayList<>(){{add("counterclockwise");}}));
        Game.board.get(10).get(6).add(new ConveyorBelt( "1A",2, new ArrayList<>(){{add("top");}}));
        Game.board.get(10).get(7).add(new EnergySpace("1A", 1));
        Game.board.get(10).get(8).add(new Empty( "1A"));
        Game.board.get(10).get(9).add(new Empty( "1A"));

        Game.board.get(11).get(0).add(new ConveyorBelt( "1A",1, new ArrayList<>(){{add("top");}}));
        Game.board.get(11).get(1).add(new ConveyorBelt( "1A",1, new ArrayList<>(){{add("top");add("right");}}));
        Game.board.get(11).get(2).add(new Empty( "1A"));
        Game.board.get(11).get(3).add(new Empty( "1A"));
        Game.board.get(11).get(4).add(new Empty( "1A"));
        Game.board.get(11).get(4).add(new CheckPoint("LostBearings", 1));
        Game.board.get(11).get(5).add(new Empty( "1A"));
        Game.board.get(11).get(6).add(new Empty( "1A"));
        Game.board.get(11).get(7).add(new Empty( "1A"));
        Game.board.get(11).get(8).add(new ConveyorBelt( "1A",1, new ArrayList<>(){{add("right");add("bottom");}}));
        Game.board.get(11).get(9).add(new ConveyorBelt( "1A",1, new ArrayList<>(){{add("top");}}));

        Game.board.get(12).get(0).add(new Empty( "1A"));
        Game.board.get(12).get(1).add(new ConveyorBelt( "1A",1, new ArrayList<>(){{add("left");}}));
        Game.board.get(12).get(2).add(new Empty( "1A"));
        Game.board.get(12).get(3).add(new Empty( "1A"));
        Game.board.get(12).get(4).add(new Empty( "1A"));
        Game.board.get(12).get(5).add(new Empty( "1A"));
        Game.board.get(12).get(6).add(new Empty( "1A"));
        Game.board.get(12).get(7).add(new Empty( "1A"));
        Game.board.get(12).get(8).add(new ConveyorBelt( "1A",1, new ArrayList<>(){{add("right");}}));
        Game.board.get(12).get(9).add(new Empty( "1A"));

    }

    public static void buildDeathTrap(){
        logger.info("Board sets map of Death Trap.");
        // fill first row with board elements
        Game.board.get(0).get(0).add(new Empty( "2A"));
        Game.board.get(0).get(1).add(new Empty( "2A"));
        Game.board.get(0).get(1).add(new CheckPoint("DeathTrap", 5));
        Game.board.get(0).get(2).add(new Empty( "2A"));
        Game.board.get(0).get(3).add(new Empty( "2A"));
        Game.board.get(0).get(4).add(new Empty( "2A"));
        Game.board.get(0).get(5).add(new ConveyorBelt( "2A", 1, new ArrayList<>(){{add("bottom");add("right");}}));
        Game.board.get(0).get(6).add(new ConveyorBelt( "2A", 1, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(0).get(7).add(new ConveyorBelt( "2A", 1, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(0).get(8).add(new ConveyorBelt( "2A", 1, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(0).get(9).add(new Empty( "2A"));

        Game.board.get(1).get(0).add(new Empty( "2A"));
        Game.board.get(1).get(1).add(new PushPanel( "2A", new ArrayList<>(){{add("bottom");}}, new ArrayList<>(){{add(1);add(3);add(5);}}));
        Game.board.get(1).get(1).add(new Wall( "2A", new ArrayList<>(){{add("top");}}));
        Game.board.get(1).get(2).add(new Pit( "2A"));
        Game.board.get(1).get(3).add(new Empty( "2A"));
        Game.board.get(1).get(4).add(new ConveyorBelt( "2A", 1, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(1).get(5).add(new ConveyorBelt( "2A", 1, new ArrayList<>(){{add("left");add("top");}}));
        Game.board.get(1).get(6).add(new Wall( "2A", new ArrayList<>(){{add("right");}}));
        Game.board.get(1).get(7).add(new Empty( "2A"));
        Game.board.get(1).get(7).add(new CheckPoint("DeathTrap", 1));
        Game.board.get(1).get(8).add(new PushPanel( "2A", new ArrayList<>(){{add("right");}}, new ArrayList<>(){{add(1);add(3);add(5);}}));
        Game.board.get(1).get(9).add(new Empty( "2A"));

        Game.board.get(2).get(0).add(new ConveyorBelt( "2A", 1, new ArrayList<>(){{add("left");}}));
        Game.board.get(2).get(1).add(new Empty( "2A"));
        Game.board.get(2).get(2).add(new PushPanel( "2A", new ArrayList<>(){{add("right");}}, new ArrayList<>(){{add(2);add(4);}}));
        Game.board.get(2).get(2).add(new Wall( "2A", new ArrayList<>(){{add("left");}}));
        Game.board.get(2).get(3).add(new EnergySpace("2A", 1));
        Game.board.get(2).get(4).add(new Empty( "2A"));
        Game.board.get(2).get(5).add(new Empty( "2A"));
        Game.board.get(2).get(6).add(new Pit( "2A"));
        Game.board.get(2).get(7).add(new PushPanel( "2A", new ArrayList<>(){{add("top");}}, new ArrayList<>(){{add(2);add(4);}}));
        Game.board.get(2).get(7).add(new Wall( "2A", new ArrayList<>(){{add("bottom");}}));
        Game.board.get(2).get(8).add(new Pit( "2A"));
        Game.board.get(2).get(9).add(new Empty( "2A"));

        Game.board.get(3).get(0).add(new ConveyorBelt( "2A", 1, new ArrayList<>(){{add("left");}}));
        Game.board.get(3).get(1).add(new Wall( "2A", new ArrayList<>(){{add("bottom");}}));
        Game.board.get(3).get(2).add(new Pit( "2A"));
        Game.board.get(3).get(3).add(new Empty( "2A"));
        Game.board.get(3).get(4).add(new Pit( "2A"));
        Game.board.get(3).get(5).add(new PushPanel( "2A", new ArrayList<>(){{add("top");}}, new ArrayList<>(){{add(1);add(3);add(5);}}));
        Game.board.get(3).get(5).add(new Wall( "2A", new ArrayList<>(){{add("bottom");}}));
        Game.board.get(3).get(6).add(new Empty( "2A"));
        Game.board.get(3).get(7).add(new EnergySpace("2A", 1));
        Game.board.get(3).get(8).add(new Empty( "2A"));
        Game.board.get(3).get(9).add(new Empty( "2A"));

        Game.board.get(4).get(0).add(new ConveyorBelt( "2A", 1, new ArrayList<>(){{add("left");add("bottom");}}));
        Game.board.get(4).get(1).add(new ConveyorBelt( "2A", 1, new ArrayList<>(){{add("top");add("right");}}));
        Game.board.get(4).get(2).add(new EnergySpace("2A", 1));
        Game.board.get(4).get(3).add(new PushPanel( "2A", new ArrayList<>(){{add("top");}}, new ArrayList<>(){{add(2);add(4);}}));
        Game.board.get(4).get(3).add(new Wall( "2A", new ArrayList<>(){{add("bottom");}}));
        Game.board.get(4).get(4).add(new Empty( "2A"));
        Game.board.get(4).get(4).add(new CheckPoint("DeathTrap", 2));
        Game.board.get(4).get(5).add(new PushPanel( "2A", new ArrayList<>(){{add("bottom");}}, new ArrayList<>(){{add(2);add(4);}}));
        Game.board.get(4).get(6).add(new EnergySpace("2A", 1));
        Game.board.get(4).get(7).add(new Empty( "2A"));
        Game.board.get(4).get(8).add(new ConveyorBelt( "2A", 1, new ArrayList<>(){{add("right");}}));
        Game.board.get(4).get(9).add(new Empty( "2A"));

        Game.board.get(5).get(0).add(new Empty( "2A"));
        Game.board.get(5).get(1).add(new ConveyorBelt( "2A", 1, new ArrayList<>(){{add("left");}}));
        Game.board.get(5).get(2).add(new Empty( "2A"));
        Game.board.get(5).get(3).add(new Empty( "2A"));
        Game.board.get(5).get(4).add(new Empty( "2A"));
        Game.board.get(5).get(5).add(new Wall( "2A", new ArrayList<>(){{add("bottom");}}));
        Game.board.get(5).get(6).add(new Empty( "2A"));
        Game.board.get(5).get(7).add(new Empty( "2A"));
        Game.board.get(5).get(8).add(new ConveyorBelt( "2A", 1, new ArrayList<>(){{add("bottom");add("left");}}));
        Game.board.get(5).get(9).add(new ConveyorBelt( "2A", 1, new ArrayList<>(){{add("right");add("top");}}));

        Game.board.get(6).get(0).add(new Empty( "2A"));
        Game.board.get(6).get(1).add(new Empty( "2A"));
        Game.board.get(6).get(2).add(new EnergySpace("2A", 1));
        Game.board.get(6).get(3).add(new Empty( "2A"));
        Game.board.get(6).get(4).add(new PushPanel( "2A", new ArrayList<>(){{add("bottom");}}, new ArrayList<>(){{add(1);add(3);add(5);}}));
        Game.board.get(6).get(4).add(new Wall( "2A", new ArrayList<>(){{add("top");}}));
        Game.board.get(6).get(5).add(new Pit( "2A"));
        Game.board.get(6).get(6).add(new Empty( "2A"));
        Game.board.get(6).get(7).add(new Pit( "2A"));
        Game.board.get(6).get(8).add(new Wall( "2A", new ArrayList<>(){{add("top");}}));
        Game.board.get(6).get(9).add(new ConveyorBelt( "2A", 1, new ArrayList<>(){{add("right");}}));

        Game.board.get(7).get(0).add(new Empty( "2A"));
        Game.board.get(7).get(1).add(new Pit( "2A"));
        Game.board.get(7).get(2).add(new PushPanel( "2A", new ArrayList<>(){{add("bottom");}}, new ArrayList<>(){{add(2);add(4);}}));
        Game.board.get(7).get(2).add(new Wall( "2A", new ArrayList<>(){{add("top");}}));
        Game.board.get(7).get(3).add(new Pit( "2A"));
        Game.board.get(7).get(4).add(new Empty( "2A"));
        Game.board.get(7).get(5).add(new Empty( "2A"));
        Game.board.get(7).get(6).add(new EnergySpace("2A", 1));
        Game.board.get(7).get(7).add(new PushPanel( "2A", new ArrayList<>(){{add("left");}}, new ArrayList<>(){{add(2);add(4);}}));
        Game.board.get(7).get(7).add(new Wall( "2A", new ArrayList<>(){{add("right");}}));
        Game.board.get(7).get(8).add(new Empty( "2A"));
        Game.board.get(7).get(8).add(new CheckPoint("DeathTrap", 3));
        Game.board.get(7).get(9).add(new ConveyorBelt( "2A", 1, new ArrayList<>(){{add("right");}}));

        Game.board.get(8).get(0).add(new Empty( "2A"));
        Game.board.get(8).get(1).add(new PushPanel( "2A", new ArrayList<>(){{add("left");}}, new ArrayList<>(){{add(1);add(3);add(5);}}));
        Game.board.get(8).get(1).add(new Wall( "2A", new ArrayList<>(){{add("right");}}));
        Game.board.get(8).get(2).add(new Empty( "2A"));
        Game.board.get(8).get(2).add(new CheckPoint("DeathTrap", 4));
        Game.board.get(8).get(3).add(new Wall( "2A", new ArrayList<>(){{add("left");}}));
        Game.board.get(8).get(4).add(new ConveyorBelt( "2A", 1, new ArrayList<>(){{add("right");add("bottom");}}));
        Game.board.get(8).get(5).add(new ConveyorBelt( "2A", 1, new ArrayList<>(){{add("top");}}));
        Game.board.get(8).get(6).add(new Empty( "2A"));
        Game.board.get(8).get(7).add(new Pit( "2A"));
        Game.board.get(8).get(8).add(new PushPanel( "2A", new ArrayList<>(){{add("top");}}, new ArrayList<>(){{add(1);add(3);add(5);}}));
        Game.board.get(8).get(8).add(new Wall( "2A", new ArrayList<>(){{add("bottom");}}));
        Game.board.get(8).get(9).add(new ConveyorBelt( "2A", 1, new ArrayList<>(){{add("right");}}));

        Game.board.get(9).get(0).add(new Empty( "2A"));
        Game.board.get(9).get(1).add(new ConveyorBelt( "2A", 1, new ArrayList<>(){{add("top");}}));
        Game.board.get(9).get(2).add(new ConveyorBelt( "2A", 1, new ArrayList<>(){{add("top");}}));
        Game.board.get(9).get(3).add(new ConveyorBelt( "2A", 1, new ArrayList<>(){{add("top");}}));
        Game.board.get(9).get(4).add(new ConveyorBelt( "2A", 1, new ArrayList<>(){{add("top");add("left");}}));
        Game.board.get(9).get(5).add(new Empty( "2A"));
        Game.board.get(9).get(6).add(new Empty( "2A"));
        Game.board.get(9).get(7).add(new Empty( "2A"));
        Game.board.get(9).get(8).add(new Empty( "2A"));
        Game.board.get(9).get(9).add(new Empty( "2A"));

        Game.board.get(10).get(0).add(new ConveyorBelt( "Start A", 1, new ArrayList<>(){{add("left");add("right");}}));
        Game.board.get(10).get(1).add(new Empty( "Start A"));
        Game.board.get(10).get(2).add(new Empty("Start A"));
        Game.board.get(10).get(3).add(new Empty("Start A"));
        Game.board.get(10).get(4).add(new Wall( "Start A", new ArrayList<>(){{add("left");}}));
        Game.board.get(10).get(5).add(new Wall( "Start A", new ArrayList<>(){{add("left");}}));
        Game.board.get(10).get(6).add(new Empty("Start A"));
        Game.board.get(10).get(7).add(new Empty("Start A"));
        Game.board.get(10).get(8).add(new Empty("Start A"));
        Game.board.get(10).get(9).add(new ConveyorBelt( "Start A",1, new ArrayList<>(){{add("left");add("right");}}));

        Game.board.get(11).get(0).add(new Empty( "Start A"));
        Game.board.get(11).get(1).add(new StartPoint( "Start A"));
        Game.board.get(11).get(2).add(new Wall( "Start A", new ArrayList<>(){{add("top");}}));
        Game.board.get(11).get(3).add(new Empty( "Start A"));
        Game.board.get(11).get(4).add(new StartPoint("Start A"));
        Game.board.get(11).get(5).add(new StartPoint("Start A"));
        Game.board.get(11).get(6).add(new Empty("Start A"));
        Game.board.get(11).get(7).add(new Wall( "Start A", new ArrayList<>(){{add("bottom");}}));
        Game.board.get(11).get(8).add(new StartPoint("Start A"));
        Game.board.get(11).get(9).add(new Empty("Start A"));

        Game.board.get(12).get(0).add(new Empty( "Start A"));
        Game.board.get(12).get(1).add(new Empty( "Start A"));
        Game.board.get(12).get(2).add(new Empty( "Start A"));
        Game.board.get(12).get(3).add(new StartPoint( "Start A"));
        Game.board.get(12).get(4).add(new Empty("Start A"));
        Game.board.get(12).get(5).add(new Antenna("Start A", new ArrayList<>(){{add("left");}}));
        Game.board.get(12).get(6).add(new StartPoint("Start A"));
        Game.board.get(12).get(7).add(new Empty("Start A"));
        Game.board.get(12).get(8).add(new Empty("Start A"));
        Game.board.get(12).get(9).add(new Empty( "Start A"));
        Game.board.get(12).get(9).add(new RestartPoint("DeathTrap", new ArrayList<>(){{add("left");}}));

    }

    public static void buildTwister(){
        logger.info("Board sets map of Twister.");
        // fill first row with board elements
        Game.board.get(0).get(0).add(new Empty( "Start A"));
        Game.board.get(0).get(1).add(new Empty("Start A"));
        Game.board.get(0).get(2).add(new Empty("Start A"));
        Game.board.get(0).get(3).add(new StartPoint( "Start A"));
        Game.board.get(0).get(4).add(new Antenna("Start A", new ArrayList<>(){{add("right");}}));
        Game.board.get(0).get(5).add(new Empty("Start A"));
        Game.board.get(0).get(6).add(new StartPoint( "Start A"));
        Game.board.get(0).get(7).add(new Empty("Start A"));
        Game.board.get(0).get(7).add(new RestartPoint("Twister", new ArrayList<>(){{add("right");}}));
        Game.board.get(0).get(8).add(new Empty( "Start A"));
        Game.board.get(0).get(9).add(new Empty( "Start A"));

        Game.board.get(1).get(0).add(new Empty("Start A"));
        Game.board.get(1).get(1).add(new StartPoint( "Start A"));
        Game.board.get(1).get(2).add(new Wall("Start A", new ArrayList<>(){{add("top");}}));
        Game.board.get(1).get(3).add(new Empty( "Start A"));
        Game.board.get(1).get(4).add(new StartPoint("Start A"));
        Game.board.get(1).get(5).add(new StartPoint("Start A"));
        Game.board.get(1).get(6).add(new Empty("Start A"));
        Game.board.get(1).get(7).add(new Wall( "Start A", new ArrayList<>(){{add("bottom");}}));
        Game.board.get(1).get(8).add(new StartPoint( "Start A"));
        Game.board.get(1).get(9).add(new Empty( "Start A"));

        Game.board.get(2).get(0).add(new ConveyorBelt( "Start A",1, new ArrayList<>(){{add("right");add("left");}}));
        Game.board.get(2).get(1).add(new Empty( "Start A"));
        Game.board.get(2).get(2).add(new Empty( "Start A"));
        Game.board.get(2).get(3).add(new Empty( "Start A"));
        Game.board.get(2).get(4).add(new Wall("Start A", new ArrayList<>(){{add("right");}}));
        Game.board.get(2).get(5).add(new Wall( "Start A", new ArrayList<>(){{add("right");}}));
        Game.board.get(2).get(6).add(new Empty( "Start A"));
        Game.board.get(2).get(7).add(new Empty( "Start A"));
        Game.board.get(2).get(8).add(new Empty( "Start A"));
        Game.board.get(2).get(9).add(new ConveyorBelt( "Start A", 1, new ArrayList<>(){{add("right");add("left");}}));

        Game.board.get(3).get(0).add(new Empty( "6B"));
        Game.board.get(3).get(1).add(new Empty( "6B"));
        Game.board.get(3).get(2).add(new Empty( "6B"));
        Game.board.get(3).get(3).add(new Empty( "6B"));
        Game.board.get(3).get(4).add(new Wall("6B", new ArrayList<>(){{add("top");}}));
        Game.board.get(3).get(4).add(new Laser( "6B", new ArrayList<>(){{add("bottom");}}, 1));
        Game.board.get(3).get(5).add(new Wall("6B", new ArrayList<>(){{add("bottom");}}));
        Game.board.get(3).get(6).add(new Empty( "6B"));
        Game.board.get(3).get(7).add(new Empty( "6B"));
        Game.board.get(3).get(8).add(new Empty( "6B"));
        Game.board.get(3).get(9).add(new Empty( "6B"));


        Game.board.get(4).get(0).add(new Empty( "6B"));
        Game.board.get(4).get(1).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("right");add("bottom");}}));
        Game.board.get(4).get(2).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("top");}}));
        Game.board.get(4).get(3).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("top");add("right");}}));
        Game.board.get(4).get(4).add(new Empty( "6B"));
        Game.board.get(4).get(5).add(new Empty( "6B"));
        Game.board.get(4).get(6).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("right");add("bottom");}}));
        Game.board.get(4).get(7).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("top");}}));
        Game.board.get(4).get(8).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("top");add("right");}}));
        Game.board.get(4).get(9).add(new Empty( "6B"));


        Game.board.get(5).get(0).add(new Empty( "6B"));
        Game.board.get(5).get(1).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("right");}}));
        Game.board.get(5).get(2).add(new EnergySpace("6B", 1));
        Game.board.get(5).get(3).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("left");}}));
        Game.board.get(5).get(4).add(new Empty( "6B"));
        Game.board.get(5).get(5).add(new Empty( "6B"));
        Game.board.get(5).get(6).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("right");}}));
        Game.board.get(5).get(7).add(new EnergySpace("6B", 1));
        Game.board.get(5).get(8).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("left");}}));
        Game.board.get(5).get(9).add(new Empty( "6B"));


        Game.board.get(6).get(0).add(new Empty( "6B"));
        Game.board.get(6).get(1).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("bottom");add("left");}}));
        Game.board.get(6).get(2).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(6).get(3).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("left");add("top");}}));
        Game.board.get(6).get(4).add(new Empty( "6B"));
        Game.board.get(6).get(5).add(new Empty( "6B"));
        Game.board.get(6).get(6).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("bottom");add("left");}}));
        Game.board.get(6).get(7).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(6).get(8).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("left");add("top");}}));
        Game.board.get(6).get(9).add(new Empty( "6B"));

        Game.board.get(7).get(0).add(new Wall("6B", new ArrayList<>(){{add("left");}}));
        Game.board.get(7).get(1).add(new Empty( "6B"));
        Game.board.get(7).get(2).add(new Empty( "6B"));
        Game.board.get(7).get(3).add(new Empty( "6B"));
        Game.board.get(7).get(4).add(new Wall("6B", new ArrayList<>(){{add("bottom");}}));
        Game.board.get(7).get(4).add(new EnergySpace("6B", 1));
        Game.board.get(7).get(5).add(new Wall("6B", new ArrayList<>(){{add("right");}}));
        Game.board.get(7).get(6).add(new Empty( "6B"));
        Game.board.get(7).get(7).add(new Empty( "6B"));
        Game.board.get(7).get(8).add(new Empty( "6B"));
        Game.board.get(7).get(9).add(new Wall("6B", new ArrayList<>(){{add("left");}}));
        Game.board.get(7).get(9).add(new Laser( "6B", new ArrayList<>(){{add("right");}}, 1));

        Game.board.get(8).get(0).add(new Wall("6B", new ArrayList<>(){{add("right");}}));
        Game.board.get(8).get(0).add(new Laser( "6B", new ArrayList<>(){{add("left");}}, 1));
        Game.board.get(8).get(1).add(new Empty( "6B"));
        Game.board.get(8).get(2).add(new Empty( "6B"));
        Game.board.get(8).get(3).add(new Empty( "6B"));
        Game.board.get(8).get(4).add(new Wall("6B", new ArrayList<>(){{add("left");}}));
        Game.board.get(8).get(5).add(new Wall("6B", new ArrayList<>(){{add("top");}}));
        Game.board.get(8).get(5).add(new EnergySpace("6B", 1));
        Game.board.get(8).get(6).add(new Empty( "6B"));
        Game.board.get(8).get(7).add(new Empty( "6B"));
        Game.board.get(8).get(8).add(new Empty( "6B"));
        Game.board.get(8).get(9).add(new Wall("6B", new ArrayList<>(){{add("right");}}));

        Game.board.get(9).get(0).add(new Empty( "6B"));
        Game.board.get(9).get(1).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("right");add("bottom");}}));
        Game.board.get(9).get(2).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("top");}}));
        Game.board.get(9).get(3).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("top");add("right");}}));
        Game.board.get(9).get(4).add(new Empty( "6B"));
        Game.board.get(9).get(5).add(new Empty( "6B"));
        Game.board.get(9).get(6).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("right");add("bottom");}}));
        Game.board.get(9).get(7).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("top");}}));
        Game.board.get(9).get(8).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("top");add("right");}}));
        Game.board.get(9).get(9).add(new Empty( "6B"));

        Game.board.get(10).get(0).add(new Empty( "6B"));
        Game.board.get(10).get(1).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("right");}}));
        Game.board.get(10).get(2).add(new EnergySpace("6B", 1));
        Game.board.get(10).get(3).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("left");}}));
        Game.board.get(10).get(4).add(new Empty( "6B"));
        Game.board.get(10).get(5).add(new Empty( "6B"));
        Game.board.get(10).get(6).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("right");}}));
        Game.board.get(10).get(7).add(new EnergySpace("6B", 1));
        Game.board.get(10).get(8).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("left");}}));
        Game.board.get(10).get(9).add(new Empty( "6B"));


        Game.board.get(11).get(0).add(new Empty( "6B"));
        Game.board.get(11).get(1).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("bottom");add("left");}}));
        Game.board.get(11).get(2).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(11).get(3).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("left");add("top");}}));
        Game.board.get(11).get(4).add(new Empty( "6B"));
        Game.board.get(11).get(5).add(new Empty( "6B"));
        Game.board.get(11).get(6).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("bottom");add("left");}}));
        Game.board.get(11).get(7).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(11).get(8).add(new ConveyorBelt( "6B",2, new ArrayList<>(){{add("left");add("top");}}));
        Game.board.get(11).get(9).add(new Empty( "6B"));


        Game.board.get(12).get(0).add(new Empty( "6B"));
        Game.board.get(12).get(1).add(new Empty( "6B"));
        Game.board.get(12).get(2).add(new Empty( "6B"));
        Game.board.get(12).get(3).add(new Empty( "6B"));
        Game.board.get(12).get(4).add(new Wall("6B", new ArrayList<>(){{add("top");}}));
        Game.board.get(12).get(5).add(new Laser( "6B", new ArrayList<>(){{add("top");}}, 1));
        Game.board.get(12).get(5).add(new Wall("6B", new ArrayList<>(){{add("bottom");}}));
        Game.board.get(12).get(6).add(new Empty( "6B"));
        Game.board.get(12).get(7).add(new Empty( "6B"));
        Game.board.get(12).get(8).add(new Empty( "6B"));
        Game.board.get(12).get(9).add(new Empty( "6B"));

    }


    public static void buildExtraCrispy(){
        logger.info("Board sets map of Extra Crispy.");
        // fill first row with board elements
        Game.board.get(0).get(0).add(new Empty( "Start A"));
        Game.board.get(0).get(0).add(new RestartPoint("ExtraCrispy", new ArrayList<>(){{add("right");}}));
        Game.board.get(0).get(1).add(new Empty("Start A"));
        Game.board.get(0).get(2).add(new Empty("Start A"));
        Game.board.get(0).get(3).add(new StartPoint( "Start A"));
        Game.board.get(0).get(4).add(new Antenna("Start A", new ArrayList<>(){{add("right");}}));
        Game.board.get(0).get(5).add(new Empty("Start A"));
        Game.board.get(0).get(6).add(new StartPoint( "Start A"));
        Game.board.get(0).get(7).add(new Empty("Start A"));
        Game.board.get(0).get(8).add(new Empty( "Start A"));
        Game.board.get(0).get(9).add(new Empty( "Start A"));

        Game.board.get(1).get(0).add(new Empty("Start A"));
        Game.board.get(1).get(1).add(new StartPoint( "Start A"));
        Game.board.get(1).get(2).add(new Wall("Start A", new ArrayList<>(){{add("top");}}));
        Game.board.get(1).get(3).add(new Empty( "Start A"));
        Game.board.get(1).get(4).add(new StartPoint("Start A"));
        Game.board.get(1).get(5).add(new StartPoint("Start A"));
        Game.board.get(1).get(6).add(new Empty("Start A"));
        Game.board.get(1).get(7).add(new Wall( "Start A", new ArrayList<>(){{add("bottom");}}));
        Game.board.get(1).get(8).add(new StartPoint( "Start A"));
        Game.board.get(1).get(9).add(new Empty( "Start A"));

        Game.board.get(2).get(0).add(new ConveyorBelt( "Start A",1, new ArrayList<>(){{add("right");add("left");}}));
        Game.board.get(2).get(1).add(new Empty( "Start A"));
        Game.board.get(2).get(2).add(new Empty( "Start A"));
        Game.board.get(2).get(3).add(new Empty( "Start A"));
        Game.board.get(2).get(4).add(new Wall("Start A", new ArrayList<>(){{add("right");}}));
        Game.board.get(2).get(5).add(new Wall( "Start A", new ArrayList<>(){{add("right");}}));
        Game.board.get(2).get(6).add(new Empty( "Start A"));
        Game.board.get(2).get(7).add(new Empty( "Start A"));
        Game.board.get(2).get(8).add(new Empty( "Start A"));
        Game.board.get(2).get(9).add(new ConveyorBelt( "Start A", 1, new ArrayList<>(){{add("right");add("left");}}));

        Game.board.get(3).get(0).add(new Empty( "4A"));
        Game.board.get(3).get(1).add(new Empty( "4A"));
        Game.board.get(3).get(2).add(new Empty( "4A"));
        Game.board.get(3).get(3).add(new Empty( "4A"));
        Game.board.get(3).get(4).add(new EnergySpace("4A", 1));
        Game.board.get(3).get(4).add(new Wall("4A", new ArrayList<>(){{add("top");}}));
        Game.board.get(3).get(5).add(new Wall("4A", new ArrayList<>(){{add("bottom");}}));
        Game.board.get(3).get(6).add(new Empty( "4A"));
        Game.board.get(3).get(7).add(new Empty( "4A"));
        Game.board.get(3).get(8).add(new Empty( "4A"));
        Game.board.get(3).get(9).add(new EnergySpace("4A", 1));

        Game.board.get(4).get(0).add(new ConveyorBelt( "4A",2, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(4).get(1).add(new ConveyorBelt( "4A",2, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(4).get(2).add(new ConveyorBelt( "4A",2, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(4).get(3).add(new ConveyorBelt( "4A",2, new ArrayList<>(){{add("right");add("top");}}));
        Game.board.get(4).get(4).add(new Empty( "4A"));
        Game.board.get(4).get(5).add(new Empty( "4A"));
        Game.board.get(4).get(6).add(new ConveyorBelt( "4A",2, new ArrayList<>(){{add("right");add("bottom");}}));
        Game.board.get(4).get(7).add(new ConveyorBelt( "4A",2, new ArrayList<>(){{add("top");}}));
        Game.board.get(4).get(8).add(new ConveyorBelt( "4A",2, new ArrayList<>(){{add("top");}}));
        Game.board.get(4).get(9).add(new Laser( "4A", new ArrayList<>(){{add("right");}}, 1));
        Game.board.get(4).get(9).add(new Wall("4A", new ArrayList<>(){{add("left");}}));

        Game.board.get(5).get(0).add(new Empty( "4A"));
        Game.board.get(5).get(1).add(new Empty( "4A"));
        Game.board.get(5).get(2).add(new Laser( "4A", new ArrayList<>(){{add("bottom");}}, 1));
        Game.board.get(5).get(2).add(new Wall("4A", new ArrayList<>(){{add("top");}}));
        Game.board.get(5).get(2).add(new CheckPoint("ExtraCrispy", 4));
        Game.board.get(5).get(3).add(new ConveyorBelt( "4A",2, new ArrayList<>(){{add("right");}}));
        Game.board.get(5).get(4).add(new Empty( "4A"));
        Game.board.get(5).get(5).add(new Empty( "4A"));
        Game.board.get(5).get(6).add(new ConveyorBelt( "4A",2, new ArrayList<>(){{add("right");}}));
        Game.board.get(5).get(7).add(new Laser( "4A", new ArrayList<>(){{add("top");}}, 1));
        Game.board.get(5).get(7).add(new Wall("4A", new ArrayList<>(){{add("bottom");}}));
        Game.board.get(5).get(7).add(new CheckPoint("ExtraCrispy", 2));
        Game.board.get(5).get(8).add(new Empty( "4A"));
        Game.board.get(5).get(9).add(new Empty( "4A"));

        Game.board.get(6).get(0).add(new Empty( "4A"));
        Game.board.get(6).get(1).add(new ConveyorBelt( "4A",1, new ArrayList<>(){{add("bottom");add("right");}}));
        Game.board.get(6).get(2).add(new Pit("4A"));
        Game.board.get(6).get(3).add(new Pit("4A"));
        Game.board.get(6).get(4).add(new Gear( "4A", new ArrayList<>(){{add("clockwise");}}));
        Game.board.get(6).get(5).add(new Wall("4A", new ArrayList<>(){{add("left");}}));
        Game.board.get(6).get(6).add(new Pit("4A"));
        Game.board.get(6).get(7).add(new Pit("4A"));
        Game.board.get(6).get(8).add(new Empty( "4A"));
        Game.board.get(6).get(9).add(new Laser( "4A", new ArrayList<>(){{add("left");}}, 1));
        Game.board.get(6).get(9).add(new Wall("4A", new ArrayList<>(){{add("right");}}));

        Game.board.get(7).get(0).add(new Empty( "4A"));
        Game.board.get(7).get(1).add(new ConveyorBelt( "4A",1, new ArrayList<>(){{add("left");}}));
        Game.board.get(7).get(2).add(new Empty( "4A"));
        Game.board.get(7).get(3).add(new Laser( "4A", new ArrayList<>(){{add("right");}}, 1));
        Game.board.get(7).get(3).add(new Wall("4A", new ArrayList<>(){{add("left");}}));
        Game.board.get(7).get(4).add(new Empty( "4A"));
        Game.board.get(7).get(5).add(new EnergySpace("4A", 1));
        Game.board.get(7).get(6).add(new Laser( "4A", new ArrayList<>(){{add("right");}}, 1));
        Game.board.get(7).get(6).add(new Wall("4A", new ArrayList<>(){{add("left");}}));
        Game.board.get(7).get(7).add(new Empty( "4A"));
        Game.board.get(7).get(8).add(new Empty( "4A"));
        Game.board.get(7).get(9).add(new Empty( "4A"));

        Game.board.get(8).get(0).add(new EnergySpace("4A", 1));
        Game.board.get(8).get(1).add(new Empty( "4A"));
        Game.board.get(8).get(2).add(new Empty( "4A"));
        Game.board.get(8).get(3).add(new Laser( "4A", new ArrayList<>(){{add("left");}}, 1));
        Game.board.get(8).get(3).add(new Wall("4A", new ArrayList<>(){{add("right");}}));
        Game.board.get(8).get(4).add(new Empty( "4A"));
        Game.board.get(8).get(5).add(new Empty( "4A"));
        Game.board.get(8).get(6).add(new Laser( "4A", new ArrayList<>(){{add("left");}}, 1));
        Game.board.get(8).get(6).add(new Wall("4A", new ArrayList<>(){{add("right");}}));
        Game.board.get(8).get(7).add(new Empty( "4A"));
        Game.board.get(8).get(8).add(new ConveyorBelt( "4A",1, new ArrayList<>(){{add("right");}}));
        Game.board.get(8).get(9).add(new Empty( "4A"));

        Game.board.get(9).get(0).add(new Laser( "4A", new ArrayList<>(){{add("right");}}, 1));
        Game.board.get(9).get(0).add(new Wall("4A", new ArrayList<>(){{add("left");}}));
        Game.board.get(9).get(1).add(new Empty( "4A"));
        Game.board.get(9).get(2).add(new Pit("4A"));
        Game.board.get(9).get(3).add(new Pit("4A"));
        Game.board.get(9).get(4).add(new Wall("4A", new ArrayList<>(){{add("right");}}));
        Game.board.get(9).get(5).add(new Gear( "4A", new ArrayList<>(){{add("counterclockwise");}}));
        Game.board.get(9).get(6).add(new Pit("4A"));
        Game.board.get(9).get(7).add(new Pit("4A"));
        Game.board.get(9).get(8).add(new ConveyorBelt( "4A",1, new ArrayList<>(){{add("top");add("left");}}));
        Game.board.get(9).get(9).add(new Empty( "4A"));

        Game.board.get(10).get(0).add(new Empty( "4A"));
        Game.board.get(10).get(1).add(new Empty( "4A"));
        Game.board.get(10).get(2).add(new Laser( "4A", new ArrayList<>(){{add("bottom");}}, 1));
        Game.board.get(10).get(2).add(new Wall("4A", new ArrayList<>(){{add("top");}}));
        Game.board.get(10).get(2).add(new CheckPoint("ExtraCrispy", 1));
        Game.board.get(10).get(3).add(new ConveyorBelt( "4A",2, new ArrayList<>(){{add("left");}}));
        Game.board.get(10).get(4).add(new Empty( "4A"));
        Game.board.get(10).get(5).add(new Empty( "4A"));
        Game.board.get(10).get(6).add(new ConveyorBelt( "4A",2, new ArrayList<>(){{add("left");}}));
        Game.board.get(10).get(7).add(new Laser( "4A", new ArrayList<>(){{add("top");}}, 1));
        Game.board.get(10).get(7).add(new Wall("4A", new ArrayList<>(){{add("bottom");}}));
        Game.board.get(10).get(7).add(new CheckPoint("ExtraCrispy", 3));
        Game.board.get(10).get(8).add(new Empty( "4A"));
        Game.board.get(10).get(9).add(new Empty( "4A"));

        Game.board.get(11).get(0).add(new Laser( "4A", new ArrayList<>(){{add("left");}}, 1));
        Game.board.get(11).get(0).add(new Wall("4A", new ArrayList<>(){{add("right");}}));
        Game.board.get(11).get(1).add(new ConveyorBelt( "4A",2, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(11).get(2).add(new ConveyorBelt( "4A",2, new ArrayList<>(){{add("bottom");}}));
        Game.board.get(11).get(3).add(new ConveyorBelt( "4A",2, new ArrayList<>(){{add("left");add("top");}}));
        Game.board.get(11).get(4).add(new EnergySpace("4A", 1));
        Game.board.get(11).get(5).add(new Empty( "4A"));
        Game.board.get(11).get(6).add(new ConveyorBelt( "4A",2, new ArrayList<>(){{add("left");add("bottom");}}));
        Game.board.get(11).get(7).add(new ConveyorBelt( "4A",2, new ArrayList<>(){{add("top");}}));
        Game.board.get(11).get(8).add(new ConveyorBelt( "4A",2, new ArrayList<>(){{add("top");}}));
        Game.board.get(11).get(9).add(new ConveyorBelt( "4A",2, new ArrayList<>(){{add("top");}}));

        Game.board.get(12).get(0).add(new Empty( "4A"));
        Game.board.get(12).get(1).add(new Empty( "4A"));
        Game.board.get(12).get(2).add(new Empty( "4A"));
        Game.board.get(12).get(3).add(new Empty( "4A"));
        Game.board.get(12).get(4).add(new Wall("4A", new ArrayList<>(){{add("top");}}));
        Game.board.get(12).get(5).add(new Wall("4A", new ArrayList<>(){{add("bottom");}}));
        Game.board.get(12).get(6).add(new Empty( "4A"));
        Game.board.get(12).get(7).add(new Empty( "4A"));
        Game.board.get(12).get(8).add(new Empty( "4A"));
        Game.board.get(12).get(9).add(new Empty( "4A"));

    }


    // only for test
    /*
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
     */


}
