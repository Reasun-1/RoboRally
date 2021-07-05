package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Movement body.
 *
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovementBody {
    private int clientID;
    private int x;
    private int y;

    /**
     * Instantiates a new Movement body.
     */
    public MovementBody() {
    }

    /**
     * Instantiates a new Movement body.
     *
     * @param clientID the client id
     * @param x        the x
     * @param y        the y
     */
    public MovementBody(int clientID, int x, int y) {
        this.clientID = clientID;
        this.x = x;
        this.y = y;
    }

    /**
     * Gets client id.
     *
     * @return the client id
     */
    public int getClientID() {
        return clientID;
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
