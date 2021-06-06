package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectedCardBody {
    private String card;
    private int register;

    public SelectedCardBody() {
    }

    public SelectedCardBody(String card, int register) {
        this.card = card;
        this.register = register;
    }

    public String getCard() {
        return card;
    }

    public int getRegister() {
        return register;
    }
}
