package protocol.submessagebody;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class ReceivedChatBody {
    //private String from;
    //private boolean isPrivate;
    private String message;

    public ReceivedChatBody() {
    }

    public ReceivedChatBody(String message) {
        //this.from = from;
        //this.isPrivate = isPrivate;
        this.message = message;
    }


    /*public String getFrom() {
        return from;
    }


    public boolean isPrivate() {
        return isPrivate;
    }

     */


    public String getMessage() {
        return message;
    }

}
