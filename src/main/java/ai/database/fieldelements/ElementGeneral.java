package ai.database.fieldelements;

import java.io.IOException;
import java.util.List;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public abstract class ElementGeneral {


    String isOnBoard;
    int speed;
    List<String> orientations;
    List<Integer> registers;
    int count;

    public String getIsOnBoard() {
        return isOnBoard;
    }

    public int getSpeed() {
        return speed;
    }

    public List<String> getOrientations() {
        return orientations;
    }

    public List<Integer> getRegisters() {
        return registers;
    }

    public int getCount() {
        return count;
    }

    public void doBoardFunction(int clientID, ElementGeneral obj) throws IOException {}
}
