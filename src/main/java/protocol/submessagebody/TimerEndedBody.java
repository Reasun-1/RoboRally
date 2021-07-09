package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * The type Timer ended body.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimerEndedBody {
    private List<Integer> clientIDs;

    /**
     * Instantiates a new Timer ended body.
     */
    public TimerEndedBody() {
    }

    /**
     * Instantiates a new Timer ended body.
     *
     * @param clientIDs the client i ds
     */
    public TimerEndedBody(List<Integer> clientIDs) {
        this.clientIDs = clientIDs;
    }

    /**
     * Gets client i ds.
     *
     * @return the client i ds
     */
    public List<Integer> getClientIDs() {
        return clientIDs;
    }
}
