package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectionFinishedBody {
    private int clientID;

    public SelectionFinishedBody() {
    }

    public SelectionFinishedBody(int clientID) {
        this.clientID = clientID;
    }

    public int getClientID() {
        return clientID;
    }
}
