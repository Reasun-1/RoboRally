package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Card selected body.
 *
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardSelectedBody {
    private int clientID;
    private int register;
    private boolean filled;

    /**
     * Instantiates a new Card selected body.
     */
    public CardSelectedBody() {
    }

    /**
     * Instantiates a new Card selected body.
     *
     * @param clientID the client id
     * @param register the register
     * @param filled   the filled
     */
    public CardSelectedBody(int clientID, int register, boolean filled) {
        this.clientID = clientID;
        this.register = register;
        this.filled = filled;
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
     * Gets register.
     *
     * @return the register
     */
    public int getRegister() {
        return register;
    }

    /**
     * Is filled boolean.
     *
     * @return the boolean
     */
    public boolean isFilled() {
        return filled;
    }
}
