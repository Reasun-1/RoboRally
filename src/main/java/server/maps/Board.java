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
        Game.board.get(1).get(0).add(new Empty( "Start A"));
        Game.board.get(1).get(1).add(new StartPoint("Start A"));
        Game.board.get(1).get(2).add(new Empty( "Start A"));

    }

    // only for test
    public static void main(String[] args) {

        Game.getInstance().initBoard();
        buildDizzyHighway();

        for (int i = 0; i < Game.board.size(); i++) {
            for (int j = 0; j < Game.board.get(0).size(); j++) {

                if(Game.board.get(i).get(j).size() != 0){
                    System.out.print(Game.board.get(i).get(j).get(0) + " ");
                }else{
                    System.out.print(Game.board.get(i).get(j) + " ");
                }
            }
            System.out.println("");
        }
    }

}
