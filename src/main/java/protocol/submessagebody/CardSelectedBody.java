package protocol.submessagebody;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class CardSelectedBody {
    private int CardSelected;
    private int register;
    private boolean filled;

    public CardSelectedBody() {
    }

    public CardSelectedBody(int cardSelected, int register, boolean filled) {
        CardSelected = cardSelected;
        this.register = register;
        this.filled = filled;
    }

    public int getCardSelected() {
        return CardSelected;
    }

    public int getRegister() {
        return register;
    }

    public boolean isFilled() {
        return filled;
    }
}
