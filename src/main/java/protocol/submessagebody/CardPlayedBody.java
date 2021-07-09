package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Card played body.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardPlayedBody {
    private int clientID;
    private String card;

    /**
     * Instantiates a new Card played body.
     */
    public CardPlayedBody() {
    }

    /**
     * Instantiates a new Card played body.
     *
     * @param clientID the client id
     * @param card     the card
     */
    public CardPlayedBody(int clientID, String card) {
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
