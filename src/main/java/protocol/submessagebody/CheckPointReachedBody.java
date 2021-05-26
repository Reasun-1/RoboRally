package protocol.submessagebody;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class CheckPointReachedBody {
    private int clientID;
    private int number;

    public CheckPointReachedBody() {
    }

    public CheckPointReachedBody(int clientID, int number) {
        this.clientID = clientID;
        this.number = number;
    }

    public int getClientID() {
        return clientID;
    }

    public int getNumber() {
        return number;
    }
}
