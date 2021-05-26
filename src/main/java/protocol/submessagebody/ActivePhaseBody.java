package protocol.submessagebody;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
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
