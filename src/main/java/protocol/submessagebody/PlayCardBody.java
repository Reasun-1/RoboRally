package protocol.submessagebody;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
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
