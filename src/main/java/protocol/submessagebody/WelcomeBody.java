package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class WelcomeBody {


    private int clientID;

    public WelcomeBody() {
    }

    public WelcomeBody(int clientID) {
        this.clientID = clientID;
    }

    public int getClientID() {
        return clientID;
    }
}
