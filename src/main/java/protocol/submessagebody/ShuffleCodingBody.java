package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Shuffle coding body.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShuffleCodingBody {
    private int clientID;

    /**
     * Instantiates a new Shuffle coding body.
     */
    public ShuffleCodingBody() {
    }

    /**
     * Instantiates a new Shuffle coding body.
     *
     * @param clientID the client id
     */
    public ShuffleCodingBody(int clientID) {
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
