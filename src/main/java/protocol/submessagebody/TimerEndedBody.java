package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TimerEndedBody {
    private List<Integer> clientIDs;

    public TimerEndedBody() {
    }

    public TimerEndedBody(List<Integer> clientIDs) {
        this.clientIDs = clientIDs;
    }

    public List<Integer> getClientIDs() {
        return clientIDs;
    }
}
