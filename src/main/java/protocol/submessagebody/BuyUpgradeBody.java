package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Buy upgrade body.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuyUpgradeBody {

    private @JsonProperty(value="isBuying") boolean isBuying;
    private String card;

    /**
     * Instantiates a new Buy upgrade body.
     */
    public BuyUpgradeBody() {
    }

    /**
     * Instantiates a new Buy upgrade body.
     *
     * @param isBuying the is buying
     * @param card     the card
     */
    public BuyUpgradeBody(boolean isBuying, String card) {
        this.isBuying = isBuying;
        this.card = card;
    }

    /**
     * Is buying boolean.
     *
     * @return the boolean
     */
    @JsonProperty(value="isBuying")
    public boolean isBuying() {
        return isBuying;
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
