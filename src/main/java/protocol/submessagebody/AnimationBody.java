package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Animation body.
 *
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnimationBody {
    private String type;

    /**
     * Instantiates a new Animation body.
     */
    public AnimationBody() {
    }

    /**
     * Instantiates a new Animation body.
     *
     * @param type the type
     */
    public AnimationBody(String type) {
        this.type = type;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }
}
