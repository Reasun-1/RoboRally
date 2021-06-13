package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * this class is for detailed messageBody
 * @author can ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceivedChatBody {

    private String message;
    private int from;
    private @JsonProperty(value="isPrivate") boolean isPrivate;

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

    @JsonProperty(value="isPrivate")
    public boolean isPrivate() {
        return isPrivate;
    }
}
