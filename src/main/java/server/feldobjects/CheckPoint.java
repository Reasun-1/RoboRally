package server.feldobjects;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.scene.robot.Robot;

/**
 * The FeldObject Checkpoint: You must reach checkpoints in numerical order.
 * In order to reach a checkpoint, you must be on it at the end of a register,
 * and you may enter a checkpoint from any side. After you reach a checkpoint,
 * take a checkpoint token, and place it on your player mat to track your progress in the race.
 *
 * @author Jonas Gottal
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckPoint extends FeldObject{

    //private String type;
    private String isOnBoard;
    private int count;

    public CheckPoint() {
    }

    public CheckPoint(String isOnBoard, int count) {
        //this.type = type;
        this.isOnBoard = isOnBoard;
        this.count = count;
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
    public int getCount() {
        return count;
    }

    @Override
    public void doBoardFunction(int clientID, FeldObject obj) {
        //TODO
    }
}
