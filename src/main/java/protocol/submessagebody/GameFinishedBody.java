package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Game finished body.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameFinishedBody {
    private int clientID;

    /**
     * Instantiates a new Game finished body.
     */
    public GameFinishedBody() {
    }

    /**
     * Instantiates a new Game finished body.
     *
     * @param clientID the client id
     */
    public GameFinishedBody(int clientID) {
        this.clientID = clientID;
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
