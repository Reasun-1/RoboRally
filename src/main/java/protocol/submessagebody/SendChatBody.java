package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * this class is for detailed messageBody
 * @author can ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendChatBody {
    private String message;
    private int to;

    public SendChatBody() {
    }

    public SendChatBody(String message, int to) {
        this.message = message;
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public int getTo() {
        return to;
    }
}
