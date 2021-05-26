package protocol.submessagebody;

import java.util.List;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class CardsYouGotNowBody {
    private List<String> cards;

    public CardsYouGotNowBody() {
    }

    public CardsYouGotNowBody(List<String> cards) {
        this.cards = cards;
    }

    public List<String> getCards() {
        return cards;
    }
}
