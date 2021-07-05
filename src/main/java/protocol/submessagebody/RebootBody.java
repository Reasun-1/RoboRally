package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Reboot body.
 *
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RebootBody {
    private int clientID;

    /**
     * Instantiates a new Reboot body.
     */
    public RebootBody() {
    }

    /**
     * Instantiates a new Reboot body.
     *
     * @param clientID the client id
     */
    public RebootBody(int clientID) {
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
