package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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

