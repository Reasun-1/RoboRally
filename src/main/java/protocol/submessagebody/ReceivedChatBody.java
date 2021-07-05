package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * this class is for detailed messageBody
 *
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceivedChatBody {

    private String message;
    private int from;
    private @JsonProperty(value="isPrivate") boolean isPrivate;

    /**
     * Instantiates a new Received chat body.
     */
    public ReceivedChatBody() {
    }

    /**
     * Instantiates a new Received chat body.
     *
     * @param message   the message
     * @param from      the from
     * @param isPrivate the is private
     */
    public ReceivedChatBody(String message, int from, boolean isPrivate) {
        this.message = message;
        this.from = from;
        this.isPrivate = isPrivate;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets from.
     *
     * @return the from
     */
    public int getFrom() {
        return from;
    }

    /**
     * Is private boolean.
     *
     * @return the boolean
     */
    @JsonProperty(value="isPrivate")
    public boolean isPrivate() {
        return isPrivate;
    }
}
