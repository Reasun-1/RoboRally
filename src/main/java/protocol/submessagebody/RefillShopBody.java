package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * The type Refill shop body.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefillShopBody {
    private List<String> cards;

    /**
     * Instantiates a new Refill shop body.
     */
    public RefillShopBody() {
    }

    /**
     * Instantiates a new Refill shop body.
     *
     * @param cards the cards
     */
    public RefillShopBody(List<String> cards) {
        this.cards = cards;
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
