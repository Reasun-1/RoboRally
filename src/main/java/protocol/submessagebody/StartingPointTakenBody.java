package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Starting point taken body.
 *
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StartingPointTakenBody {
    private int x;
    private int y;
    private int clientID;

    /**
     * Instantiates a new Starting point taken body.
     */
    public StartingPointTakenBody() {
    }

    /**
     * Instantiates a new Starting point taken body.
     *
     * @param x        the x
     * @param y        the y
     * @param clientID the client id
     */
    public StartingPointTakenBody(int x, int y, int clientID) {
        this.x = x;
        this.y = y;
        this.clientID = clientID;
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

    /**
     * Gets client id.
     *
     * @return the client id
     */
    public int getClientID() {
        return clientID;
    }
}
