package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RebootDirectionBody {
    private String direction;

    public RebootDirectionBody() {
    }

    public RebootDirectionBody(String right) {
        this.direction = right;
    }

    public String getDirection() {
        return direction;
    }
}
