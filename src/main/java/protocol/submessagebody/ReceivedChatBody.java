package protocol.submessagebody;

/**
 * this class is for detailed messageBody
 * @author can ren
 */
public class ReceivedChatBody {

    private String message;

    // empty constructor is required for json
    public ReceivedChatBody() {
    }

    public ReceivedChatBody(String message) {
        //this.from = from;
        //this.isPrivate = isPrivate;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
