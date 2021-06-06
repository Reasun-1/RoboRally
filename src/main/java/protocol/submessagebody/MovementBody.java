package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovementBody {
    private int clientID;
    private int x;
    private int y;

    public MovementBody() {
    }

    public MovementBody(int clientID, int x, int y) {
        this.clientID = clientID;
        this.x = x;
        this.y = y;
    }

    public int getClientID() {
        return clientID;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
