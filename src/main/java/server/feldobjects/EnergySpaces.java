package server.feldobjects;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The FeldObject Energy space: When you end a register on an energy space,
 * if there is an energy cube there, take it. If you end the fifth register
 * on an energy space, take an energy cube from the energy bank.
 *
 * @author Jonas Gottal
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnergySpaces extends FeldObject{

    private String type;
    private String isOnBoard;
    private int count;

    public EnergySpaces() {
    }

    public EnergySpaces(String type, String isOnBoard, int count) {
        this.type = type;
        this.isOnBoard = isOnBoard;
        this.count = count;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getIsOnBoard() {
        return isOnBoard;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public void doBoardFunction(int clientID) {
        //TODO
    }
}