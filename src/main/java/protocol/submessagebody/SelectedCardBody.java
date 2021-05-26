package protocol.submessagebody;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class SelectedCardBody {
    private String card;
    private int register;

    public SelectedCardBody() {
    }

    public SelectedCardBody(String card, int register) {
        this.card = card;
        this.register = register;
    }

    public String getCard() {
        return card;
    }

    public int getRegister() {
        return register;
    }
}
