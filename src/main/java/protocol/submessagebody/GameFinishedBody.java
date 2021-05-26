package protocol.submessagebody;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
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
