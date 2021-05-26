package protocol.submessagebody;

import java.util.List;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
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
