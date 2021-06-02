package server.feldobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */


// JsonTypeInfo IMPORTANT!! for extends relation between two classes
//@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public abstract class FeldObject {

    //String type;
    String isOnBoard;
    int speed;
    List<String> orientations;
    List<Integer> registers;
    int count;

   /* public String getType() {
        return this.type;
    }

    */

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

    public void doBoardFunction(int clientID){}
}
