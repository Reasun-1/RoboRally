package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Checkpoint moved body.
 *
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckpointMovedBody {

    private int checkpointID;
    private int x;
    private int y;

    /**
     * Instantiates a new Checkpoint moved body.
     */
    public CheckpointMovedBody() {
    }

    /**
     * Instantiates a new Checkpoint moved body.
     *
     * @param checkpointID the checkpoint id
     * @param x            the x
     * @param y            the y
     */
    public CheckpointMovedBody(int checkpointID, int x, int y) {
        this.checkpointID = checkpointID;
        this.x = x;
        this.y = y;
    }

    /**
     * Gets checkpoint id.
     *
     * @return the checkpoint id
     */
    public int getCheckpointID() {
        return checkpointID;
    }

    /**
     * Gets x.
     *
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public int getY() {
        return y;
    }
}
