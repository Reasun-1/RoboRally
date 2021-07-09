package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Welcome body.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WelcomeBody {


    private int clientID;

    /**
     * Instantiates a new Welcome body.
     */
    public WelcomeBody() {
    }

    /**
     * Instantiates a new Welcome body.
     *
     * @param clientID the client id
     */
    public WelcomeBody(int clientID) {
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
