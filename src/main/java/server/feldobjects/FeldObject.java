package server.feldobjects;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.IOException;
import java.util.List;

/**
 * The type Feld object.
 *
 * @author Jonas Gottal
 * @author Can Ren
 */


// JsonTypeInfo IMPORTANT!! for extends relation between two classes
//@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public abstract class FeldObject {

    /**
     * The Is on board.
     */
//String type;
    String isOnBoard;
    /**
     * The Speed.
     */
    int speed;
    /**
     * The Orientations.
     */
    List<String> orientations;
    /**
     * The Registers.
     */
    List<Integer> registers;
    /**
     * The Count.
     */
    int count;

   /* public String getType() {
        return this.type;
    }

    */

    /**
     * Gets is on board.
     *
     * @return the is on board
     */
    public String getIsOnBoard() {
        return isOnBoard;
    }

    /**
     * Gets speed.
     *
     * @return the speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Gets orientations.
     *
     * @return the orientations
     */
    public List<String> getOrientations() {
        return orientations;
    }

    /**
     * Gets registers.
     *
     * @return the registers
     */
    public List<Integer> getRegisters() {
        return registers;
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * Do board function.
     *
     * @param clientID the client id
     * @param obj      the obj
     * @throws IOException the io exception
     */
    public void doBoardFunction(int clientID, FeldObject obj) throws IOException {}
}
