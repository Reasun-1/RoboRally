package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameFinishedBody {
    private int clientID;

    public GameFinishedBody() {
    }

    public GameFinishedBody(int clientID) {
        this.clientID = clientID;
    }

    public int getClientID() {
        return clientID;
    }
}
