package server.feldobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import server.game.Game;
import server.game.Position;
import server.network.Server;

import java.io.IOException;
import java.util.List;

/**
 * The FeldObject Push panel: Push panels push any robot resting on them into the
 * next space in the direction the push panel faces. The panels activate only in
 * the register that corresponds to the number on them.
 *
 * @author Jonas Gottal
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PushPanel extends FeldObject {

    //private String type;
    private String isOnBoard;
    private List<String> orientations;
    private List<Integer> registers;

    /**
     * Instantiates a new Push panel.
     */
    public PushPanel() {
    }

    /**
     * Instantiates a new Push panel.
     *
     * @param isOnBoard    the is on board
     * @param orientations the orientations
     * @param registers    the registers
     */
    public PushPanel(String isOnBoard, List<String> orientations, List<Integer> registers) {
        //this.type = type;
        this.isOnBoard = isOnBoard;
        this.orientations = orientations;
        this.registers = registers;
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
    public List<Integer> getRegisters() {
        return registers;
    }

    @Override
    public void doBoardFunction(int clientID, FeldObject obj) throws IOException {
        List<String> orientations = obj.getOrientations();
        String pushDir = orientations.get(0);
        List<Integer> registers = obj.getRegisters();

        // do push if the register no. in registers of push panels
        if (registers.contains(Game.registerPointer+1)) {


            Position position = Game.getInstance().playerPositions.get(clientID);
            int curX = position.getX();
            int curY = position.getY();

            Position newPosition = null;


            switch (pushDir) {
                case "top":
                    newPosition = new Position(curX, curY - 1);
                    break;
                case "bottom":
                    newPosition = new Position(curX, curY + 1);
                    break;
                case "right":
                    newPosition = new Position(curX + 1, curY);
                    break;
                case "left":
                    newPosition = new Position(curX - 1, curY);
                    break;
            }

            // check if robot is still on board
            boolean isOnBoard = Game.getInstance().checkOnBoard(clientID, newPosition);
            if (isOnBoard) {
                // set new Position in Game
                Game.playerPositions.put(clientID, newPosition);
                // transport new Position to client
                Server.getServer().handleMovement(clientID, newPosition.getX(), newPosition.getY());
            }
        }
    }
}
