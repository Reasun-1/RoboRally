package server.feldobjects;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The FeldObject Empty: PlaceHolder without any Function
 *
 * @author Jonas Gottal
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Empty extends FeldObject{

    //private String type;
    private String isOnBoard;

    /**
     * Instantiates a new Empty.
     */
    public Empty() {
    }

    /**
     * Instantiates a new Empty.
     *
     * @param isOnBoard the is on board
     */
    public Empty(String isOnBoard) {
        //this.type = type;
        this.isOnBoard = isOnBoard;
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
    public void doBoardFunction(int clientID, FeldObject obj) {

        //TODO
    }
}
