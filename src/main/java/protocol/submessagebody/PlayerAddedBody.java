package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerAddedBody {
    private int clientID;
    private String name;
    private int figure;

    public PlayerAddedBody() {
    }

    public PlayerAddedBody(int clientID, String name, int figure) {
        this.clientID = clientID;
        this.name = name;
        this.figure = figure;
    }

    public int getClientID() {
        return clientID;
    }

    public String getName() {
        return name;
    }

    public int getFigure() {
        return figure;
    }
}
