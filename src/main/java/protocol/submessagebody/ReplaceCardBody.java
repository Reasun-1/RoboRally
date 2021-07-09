package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Replace card body.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplaceCardBody {
    private int register;
    private String newCard;
    private int clientID;

    /**
     * Instantiates a new Replace card body.
     */
    public ReplaceCardBody() {
    }

    /**
     * Instantiates a new Replace card body.
     *
     * @param register the register
     * @param newCard  the new card
     * @param clientID the client id
     */
    public ReplaceCardBody(int register, String newCard, int clientID) {
        this.register = register;
        this.newCard = newCard;
        this.clientID = clientID;
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
     * Gets new card.
     *
     * @return the new card
     */
    public String getNewCard() {
        return newCard;
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
