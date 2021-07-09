package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Reboot direction body.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RebootDirectionBody {
    private String direction;

    /**
     * Instantiates a new Reboot direction body.
     */
    public RebootDirectionBody() {
    }

    /**
     * Instantiates a new Reboot direction body.
     *
     * @param right the right
     */
    public RebootDirectionBody(String right) {
        this.direction = right;
    }

    /**
     * Gets direction.
     *
     * @return the direction
     */
    public String getDirection() {
        return direction;
    }
}
