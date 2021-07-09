package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Connection update body.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConnectionUpdateBody {

    private int clientID;
    private boolean isConnected;
    private String action;

    /**
     * Instantiates a new Connection update body.
     */
    public ConnectionUpdateBody() {
    }

    /**
     * Instantiates a new Connection update body.
     *
     * @param clientID    the client id
     * @param isConnected the is connected
     * @param action      the action
     */
    public ConnectionUpdateBody(int clientID, boolean isConnected, String action) {
        this.clientID = clientID;
        this.isConnected = isConnected;
        this.action = action;
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
     * Is connected boolean.
     *
     * @return the boolean
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * Gets action.
     *
     * @return the action
     */
    public String getAction() {
        return action;
    }
}
