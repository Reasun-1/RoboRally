package protocol.submessagebody;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class ReplaceCardBody {
    private int register;
    private String newCard;
    private String clientID;

    public ReplaceCardBody() {
    }

    public ReplaceCardBody(int register, String newCard, String clientID) {
        this.register = register;
        this.newCard = newCard;
        this.clientID = clientID;
    }

    public int getRegister() {
        return register;
    }

    public String getNewCard() {
        return newCard;
    }

    public String getClientID() {
        return clientID;
    }
}
