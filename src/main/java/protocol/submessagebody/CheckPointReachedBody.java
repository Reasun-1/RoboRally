package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Check point reached body.
 *
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckPointReachedBody {
    private int clientID;
    private int number;

    /**
     * Instantiates a new Check point reached body.
     */
    public CheckPointReachedBody() {
    }

    /**
     * Instantiates a new Check point reached body.
     *
     * @param clientID the client id
     * @param number   the number
     */
    public CheckPointReachedBody(int clientID, int number) {
        this.clientID = clientID;
        this.number = number;
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
     * Gets number.
     *
     * @return the number
     */
    public int getNumber() {
        return number;
    }
}
