package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * this class is for detailed messageBody
 * @author can ren
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorBody {

    private String error;

    // empty constructor is required for json
    public ErrorBody() {
    }

    public ErrorBody(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
