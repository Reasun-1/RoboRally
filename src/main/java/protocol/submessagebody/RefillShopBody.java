package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefillShopBody {
    private List<String> cards;

    public RefillShopBody() {
    }

    public RefillShopBody(List<String> cards) {
        this.cards = cards;
    }

    public List<String> getCards() {
        return cards;
    }
}
