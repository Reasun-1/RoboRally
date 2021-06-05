package server.feldobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Jonas Gottal
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class StartPoint extends FeldObject{

   // String type;
    String isOnBoard;

    public StartPoint() {
    }

    public StartPoint(String isOnBoard) {
        //this.type = type;
        this.isOnBoard = isOnBoard;
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
    public void doBoardFunction(int clientID, FeldObject obj) {
        //TODO
    }
}
