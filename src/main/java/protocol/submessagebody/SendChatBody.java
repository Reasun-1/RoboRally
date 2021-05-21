package protocol.submessagebody;

/**
 * this class is for detailed messageBody
 * @author can ren
 */
public class SendChatBody {
    private String to;
    private String message;

    // empty constructor is required for json
    public SendChatBody() {
    }

    public SendChatBody(String to, String message) {
        this.to = to;
        this.message = message;
    }

    public String getTo() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "SendChatBody{" +
                "to='" + to + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
