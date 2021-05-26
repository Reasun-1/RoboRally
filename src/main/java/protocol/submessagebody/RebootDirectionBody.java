package protocol.submessagebody;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class RebootDirectionBody {
    private String right;

    public RebootDirectionBody() {
    }

    public RebootDirectionBody(String right) {
        this.right = right;
    }

    public String getRight() {
        return right;
    }
}
