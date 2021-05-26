package protocol.submessagebody;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class NotYourCardsBody {
    private int clientID;
    private int cardsInHand;

    public NotYourCardsBody() {
    }

    public NotYourCardsBody(int clientID, int cardsInHand) {
        this.clientID = clientID;
        this.cardsInHand = cardsInHand;
    }

    public int getClientID() {
        return clientID;
    }

    public int getCardsInHand() {
        return cardsInHand;
    }
}
