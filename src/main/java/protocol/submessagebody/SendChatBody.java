package protocol.submessagebody;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class SendChatBody {
    private String to;
    private String message;

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
