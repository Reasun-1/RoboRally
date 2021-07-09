package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Current player body.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentPlayerBody {
    private int clientID;

    /**
     * Instantiates a new Current player body.
     */
    public CurrentPlayerBody() {
    }

    /**
     * Instantiates a new Current player body.
     *
     * @param clientID the client id
     */
    public CurrentPlayerBody(int clientID) {
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
