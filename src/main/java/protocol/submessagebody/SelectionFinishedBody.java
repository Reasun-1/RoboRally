package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Selection finished body.
 *
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectionFinishedBody {
    private int clientID;

    /**
     * Instantiates a new Selection finished body.
     */
    public SelectionFinishedBody() {
    }

    /**
     * Instantiates a new Selection finished body.
     *
     * @param clientID the client id
     */
    public SelectionFinishedBody(int clientID) {
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
