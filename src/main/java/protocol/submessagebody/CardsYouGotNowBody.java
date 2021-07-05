package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * The type Cards you got now body.
 *
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardsYouGotNowBody {
    private List<String> cards;

    /**
     * Instantiates a new Cards you got now body.
     */
    public CardsYouGotNowBody() {
    }

    /**
     * Instantiates a new Cards you got now body.
     *
     * @param cards the cards
     */
    public CardsYouGotNowBody(List<String> cards) {
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
