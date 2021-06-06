package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivePhaseBody {
    private int phase;

    public ActivePhaseBody() {
    }

    public ActivePhaseBody(int phase) {
        this.phase = phase;
    }

    public int getPhase() {
        return phase;
    }
}
