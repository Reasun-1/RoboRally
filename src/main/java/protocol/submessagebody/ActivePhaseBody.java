package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Active phase body.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivePhaseBody {
    private int phase;

    /**
     * Instantiates a new Active phase body.
     */
    public ActivePhaseBody() {
    }

    /**
     * Instantiates a new Active phase body.
     *
     * @param phase the phase
     */
    public ActivePhaseBody(int phase) {
        this.phase = phase;
    }

    /**
     * Gets phase.
     *
     * @return the phase
     */
    public int getPhase() {
        return phase;
    }
}
