package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Selected card body.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectedCardBody {
    private String card;
    private int register;

    /**
     * Instantiates a new Selected card body.
     */
    public SelectedCardBody() {
    }

    /**
     * Instantiates a new Selected card body.
     *
     * @param card     the card
     * @param register the register
     */
    public SelectedCardBody(String card, int register) {
        this.card = card;
        this.register = register;
    }

    /**
     * Gets card.
     *
     * @return the card
     */
    public String getCard() {
        return card;
    }

    /**
     * Gets register.
     *
     * @return the register
     */
    public int getRegister() {
        return register;
    }
}
