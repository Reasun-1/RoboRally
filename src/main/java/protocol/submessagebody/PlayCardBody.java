package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayCardBody {
    private String card;

    public PlayCardBody() {
    }

    public PlayCardBody(String card) {
        this.card = card;
    }

    public String getCard() {
        return card;
    }
}
