package protocol.submessagebody;

/**
 * this class is for detailed messageBody
 *
 * @author Can Ren
 */
//@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorBody {

    private String error;

    /**
     * Instantiates a new Error body.
     */
// empty constructor is required for json
    public ErrorBody() {
    }

    /**
     * Instantiates a new Error body.
     *
     * @param error the error
     */
    public ErrorBody(String error) {
        this.error = error;
    }

    /**
     * Gets error.
     *
     * @return the error
     */
    public String getError() {
        return error;
    }
}
