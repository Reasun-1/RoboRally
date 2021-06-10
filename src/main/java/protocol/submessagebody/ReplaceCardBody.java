package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReplaceCardBody {
    private int register;
    private String newCard;
    private int clientID;

    public ReplaceCardBody() {
    }

    public ReplaceCardBody(int register, String newCard, int clientID) {
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

    public int getClientID() {
        return clientID;
    }
}
