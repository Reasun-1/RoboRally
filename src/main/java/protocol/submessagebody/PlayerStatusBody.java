package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerStatusBody {
    private int clientID;
    private boolean ready;

    public PlayerStatusBody() {
    }

    public PlayerStatusBody(int clientID, boolean ready) {
        this.clientID = clientID;
        this.ready = ready;
    }

    public int getClientID() {
        return clientID;
    }

    public boolean isReady() {
        return ready;
    }
}
