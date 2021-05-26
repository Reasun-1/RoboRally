package protocol.submessagebody;

/**
 * this class is for detailed messageBody
 * @author can ren
 */
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
