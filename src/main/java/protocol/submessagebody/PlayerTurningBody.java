package protocol.submessagebody;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class PlayerTurningBody {
    private int clientID;
    private String rotation;

    public PlayerTurningBody() {
    }

    public PlayerTurningBody(int clientID, String rotation) {
        this.clientID = clientID;
        this.rotation = rotation;
    }

    public int getClientID() {
        return clientID;
    }

    public String getRotation() {
        return rotation;
    }
}

