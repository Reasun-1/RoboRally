package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardSelectedBody {
    private int clientID;
    private int register;
    private boolean filled;

    public CardSelectedBody() {
    }

    public CardSelectedBody(int clientID, int register, boolean filled) {
        this.clientID = clientID;
        this.register = register;
        this.filled = filled;
    }

    public int getClientID() {
        return clientID;
    }

    public int getRegister() {
        return register;
    }

    public boolean isFilled() {
        return filled;
    }
}
