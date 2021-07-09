package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Not your cards body.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotYourCardsBody {
    private int clientID;
    private int cardsInHand;

    /**
     * Instantiates a new Not your cards body.
     */
    public NotYourCardsBody() {
    }

    /**
     * Instantiates a new Not your cards body.
     *
     * @param clientID    the client id
     * @param cardsInHand the cards in hand
     */
    public NotYourCardsBody(int clientID, int cardsInHand) {
        this.clientID = clientID;
        this.cardsInHand = cardsInHand;
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
     * Gets cards in hand.
     *
     * @return the cards in hand
     */
    public int getCardsInHand() {
        return cardsInHand;
    }
}
