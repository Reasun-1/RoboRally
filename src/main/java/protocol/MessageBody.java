package protocol;

import server.feldelement.FeldType;

import java.security.PrivateKey;
import java.util.List;
import java.util.Map;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class MessageBody {

    private String protocol;
    private int playerID;
    private String name;
    private String message;
    private String to;
    private String from;
    private boolean isPrivate;
    private String error;

    public MessageBody() {
    }

    public String getProtocol() {
        return protocol;
    }

    public int getPlayerID() {
        return playerID;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getError() {
        return error;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public void setError(String error) {
        this.error = error;
    }
}
