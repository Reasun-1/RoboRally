package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConnectionUpdateBody {

    private int clientID;
    private boolean isConnected;
    private String action;

    public ConnectionUpdateBody() {
    }

    public ConnectionUpdateBody(int clientID, boolean isConnected, String action) {
        this.clientID = clientID;
        this.isConnected = isConnected;
        this.action = action;
    }

    public int getClientID() {
        return clientID;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public String getAction() {
        return action;
    }
}
