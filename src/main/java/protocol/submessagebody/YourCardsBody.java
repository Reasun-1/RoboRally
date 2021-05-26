package protocol.submessagebody;

import java.util.List;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class YourCardsBody {
    private List<String> cardsInHand;

    public YourCardsBody() {
    }

    public YourCardsBody(List<String> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    public List<String> getCardsInHand() {
        return cardsInHand;
    }

}
