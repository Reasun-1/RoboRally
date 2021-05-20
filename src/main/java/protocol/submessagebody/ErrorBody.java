package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorBody {

    private String error;

    public ErrorBody() {
    }

    public ErrorBody(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
