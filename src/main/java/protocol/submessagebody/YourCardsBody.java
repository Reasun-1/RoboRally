package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * The type Your cards body.
 *
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class YourCardsBody {
    private List<String> cardsInHand;

    /**
     * Instantiates a new Your cards body.
     */
    public YourCardsBody() {
    }

    /**
     * Instantiates a new Your cards body.
     *
     * @param cardsInHand the cards in hand
     */
    public YourCardsBody(List<String> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    /**
     * Gets cards in hand.
     *
     * @return the cards in hand
     */
    public List<String> getCardsInHand() {
        return cardsInHand;
    }

}
