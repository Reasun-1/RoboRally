package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Play card body.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayCardBody {
    private String card;

    /**
     * Instantiates a new Play card body.
     */
    public PlayCardBody() {
    }

    /**
     * Instantiates a new Play card body.
     *
     * @param card the card
     */
    public PlayCardBody(String card) {
        this.card = card;
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
