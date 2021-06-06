package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentPlayerBody {
    private int clientID;

    public CurrentPlayerBody() {
    }

    public CurrentPlayerBody(int clientID) {
        this.clientID = clientID;
    }

    public int getClientID() {
        return clientID;
    }
}
