package server.feldobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author Jonas Gottal
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RestartPoint extends FeldObject{

    //String type;
    String isOnBoard;
    List<String> orientations;

    public RestartPoint() {
    }

    public RestartPoint( String isOnBoard,List<String> orientations) {
      //  this.type = type;
        this.isOnBoard = isOnBoard;
        this.orientations = orientations;
    }

    /*@Override
    public String getType() {
        return type;
    }

     */

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
}
