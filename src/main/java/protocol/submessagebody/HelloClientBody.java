package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Hello client body.
 *
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HelloClientBody {


    private String protocol;

    /**
     * Instantiates a new Hello client body.
     */
    public HelloClientBody() {
    }

    /**
     * Instantiates a new Hello client body.
     *
     * @param protocol the protocol
     */
    public HelloClientBody(String protocol) {
        this.protocol = protocol;
    }

    /**
     * Gets protocol.
     *
     * @return the protocol
     */
    public String getProtocol() {
        return protocol;
    }
}
