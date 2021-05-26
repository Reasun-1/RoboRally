package protocol.submessagebody;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
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
