package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterChosenBody {
    private int clientID;
    private int register;

    public RegisterChosenBody() {
    }

    public RegisterChosenBody(int clientID, int register) {
        this.clientID = clientID;
        this.register = register;
    }

    public int getClientID() {
        return clientID;
    }

    public int getRegister() {
        return register;
    }
}
