package server.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Register.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Register {

    private int clientID;
    private String cardName;

    /**
     * Instantiates a new Register.
     */
    public Register() {
    }

    /**
     * Instantiates a new Register.
     *
     * @param clientID the client id
     * @param cardName the card name
     */
    public Register(int clientID, String cardName) {
        this.clientID = clientID;
        this.cardName = cardName;
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
     * Gets card name.
     *
     * @return the card name
     */
    public String getCardName() {
        return cardName;
    }
}
