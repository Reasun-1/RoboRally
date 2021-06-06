package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SetStatusBody {
    private boolean ready;

    public SetStatusBody() {
    }

    public SetStatusBody(boolean ready) {
        this.ready = ready;
    }

    public boolean isReady() {
        return ready;
    }
}
