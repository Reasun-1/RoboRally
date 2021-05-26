package protocol.submessagebody;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
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
