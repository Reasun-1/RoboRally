package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Upgrade bought body.
 *
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpgradeBoughtBody {

    private int clientID;
    private String card;

    /**
     * Instantiates a new Upgrade bought body.
     */
    public UpgradeBoughtBody() {
    }

    /**
     * Instantiates a new Upgrade bought body.
     *
     * @param clientID the client id
     * @param card     the card
     */
    public UpgradeBoughtBody(int clientID, String card) {
        this.clientID = clientID;
        this.card = card;
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
     * Gets card.
     *
     * @return the card
     */
    public String getCard() {
        return card;
    }
}

