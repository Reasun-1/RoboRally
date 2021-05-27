package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardPlayedBody {
    private int clientID;
    private String card;

    public CardPlayedBody() {
    }

    public CardPlayedBody(int clientID, String card) {
        this.clientID = clientID;
        this.card = card;
    }

    public int getClientID() {
        return clientID;
    }

    public String getCard() {
        return card;
    }
}
