package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Player turning body.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerTurningBody {
    private int clientID;
    private String rotation;

    /**
     * Instantiates a new Player turning body.
     */
    public PlayerTurningBody() {
    }

    /**
     * Instantiates a new Player turning body.
     *
     * @param clientID the client id
     * @param rotation the rotation
     */
    public PlayerTurningBody(int clientID, String rotation) {
        this.clientID = clientID;
        this.rotation = rotation;
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
     * Gets rotation.
     *
     * @return the rotation
     */
    public String getRotation() {
        return rotation;
    }
}

