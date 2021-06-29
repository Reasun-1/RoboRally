package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuyUpgradeBody {

    private @JsonProperty(value="isBuying") boolean isBuying;
    private String card;

    public BuyUpgradeBody() {
    }

    public BuyUpgradeBody(boolean isBuying, String card) {
        this.isBuying = isBuying;
        this.card = card;
    }

    @JsonProperty(value="isBuying")
    public boolean isBuying() {
        return isBuying;
    }

    public String getCard() {
        return card;
    }
}
