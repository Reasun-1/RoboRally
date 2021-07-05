package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * this class is for detailed messageBody
 *
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendChatBody {
    private String message;
    private int to;

    /**
     * Instantiates a new Send chat body.
     */
    public SendChatBody() {
    }

    /**
     * Instantiates a new Send chat body.
     *
     * @param message the message
     * @param to      the to
     */
    public SendChatBody(String message, int to) {
        this.message = message;
        this.to = to;
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
     * Gets to.
     *
     * @return the to
     */
    public int getTo() {
        return to;
    }
}
