package protocol.submessagebody;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
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
