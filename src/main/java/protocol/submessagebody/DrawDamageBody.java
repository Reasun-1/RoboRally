package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * The type Draw damage body.
 *
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DrawDamageBody {
    private int clientID;
    private List<String> cards;

    /**
     * Instantiates a new Draw damage body.
     */
    public DrawDamageBody() {
    }

    /**
     * Instantiates a new Draw damage body.
     *
     * @param clientID the client id
     * @param cards    the cards
     */
    public DrawDamageBody(int clientID, List<String> cards) {
        this.clientID = clientID;
        this.cards = cards;
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
     * Gets cards.
     *
     * @return the cards
     */
    public List<String> getCards() {
        return cards;
    }
}
