package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * this class is for detailed messageBody
 * @author can ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceivedChatBody {

    private String message;
    private int from;
    private boolean isPrivate;

    public ReceivedChatBody() {
    }

    public ReceivedChatBody(String message, int from, boolean isPrivate) {
        this.message = message;
        this.from = from;
        this.isPrivate = isPrivate;
    }

    public String getMessage() {
        return message;
    }

    public int getFrom() {
        return from;
    }

    public boolean isPrivate() {
        return isPrivate;
    }
}
