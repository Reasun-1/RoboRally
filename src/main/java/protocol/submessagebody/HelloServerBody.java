package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Hello server body.
 *
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HelloServerBody {


    private String group;

    private @JsonProperty(value="isAI") boolean isAI;

    private String protocol;

    /**
     * Instantiates a new Hello server body.
     */
    public HelloServerBody() {
    }

    /**
     * Instantiates a new Hello server body.
     *
     * @param group    the group
     * @param isAI     the is ai
     * @param protocol the protocol
     */
    public HelloServerBody(String group, boolean isAI, String protocol) {
        this.group = group;
        this.isAI = isAI;
        this.protocol = protocol;
    }

    /**
     * Gets group.
     *
     * @return the group
     */
    public String getGroup() {
        return group;
    }

    /**
     * Is ai boolean.
     *
     * @return the boolean
     */
    @JsonProperty(value="isAI")
    public boolean isAI() {
        return isAI;
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
