package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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