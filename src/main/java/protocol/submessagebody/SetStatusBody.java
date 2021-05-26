package protocol.submessagebody;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
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
