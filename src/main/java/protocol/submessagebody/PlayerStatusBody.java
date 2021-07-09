package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Player status body.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerStatusBody {
    private int clientID;
    private boolean ready;

    /**
     * Instantiates a new Player status body.
     */
    public PlayerStatusBody() {
    }

    /**
     * Instantiates a new Player status body.
     *
     * @param clientID the client id
     * @param ready    the ready
     */
    public PlayerStatusBody(int clientID, boolean ready) {
        this.clientID = clientID;
        this.ready = ready;
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
     * Is ready boolean.
     *
     * @return the boolean
     */
    public boolean isReady() {
        return ready;
    }
}
