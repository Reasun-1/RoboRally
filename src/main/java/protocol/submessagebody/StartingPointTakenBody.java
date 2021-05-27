package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StartingPointTakenBody {
    private int x;
    private int y;
    private int clientID;

    public StartingPointTakenBody() {
    }

    public StartingPointTakenBody(int x, int y, int clientID) {
        this.x = x;
        this.y = y;
        this.clientID = clientID;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getClientID() {
        return clientID;
    }
}
