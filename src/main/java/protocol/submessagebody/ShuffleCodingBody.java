package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShuffleCodingBody {
    private int clientID;

    public ShuffleCodingBody() {
    }

    public ShuffleCodingBody(int clientID) {
        this.clientID = clientID;
    }

    public int getClientID() {
        return clientID;
    }
}
