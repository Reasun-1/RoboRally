package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckpointMovedBody {

    private int checkpointID;
    private int x;
    private int y;

    public CheckpointMovedBody() {
    }

    public CheckpointMovedBody(int checkpointID, int x, int y) {
        this.checkpointID = checkpointID;
        this.x = x;
        this.y = y;
    }

    public int getCheckpointID() {
        return checkpointID;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
