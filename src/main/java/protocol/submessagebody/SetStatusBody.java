package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Set status body.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SetStatusBody {
    private boolean ready;

    /**
     * Instantiates a new Set status body.
     */
    public SetStatusBody() {
    }

    /**
     * Instantiates a new Set status body.
     *
     * @param ready the ready
     */
    public SetStatusBody(boolean ready) {
        this.ready = ready;
    }

    /**
     * Is ready boolean.
     *
     * @return the boolean
     */
    public boolean isReady() {
        return ready;
    }
}
