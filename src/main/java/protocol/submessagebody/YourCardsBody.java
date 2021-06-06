package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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
