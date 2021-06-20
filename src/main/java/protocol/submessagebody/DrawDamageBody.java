package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DrawDamageBody {
    private int clientID;
    private List<String> cards;

    public DrawDamageBody() {
    }

    public DrawDamageBody(int clientID, List<String> cards) {
        this.clientID = clientID;
        this.cards = cards;
    }

    public int getClientID() {
        return clientID;
    }

    public List<String> getCards() {
        return cards;
    }
}
