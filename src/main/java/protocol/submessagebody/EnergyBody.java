package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Energy body.
 *
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnergyBody {
    private int clientID;
    private int count;
    private String source;

    /**
     * Instantiates a new Energy body.
     */
    public EnergyBody() {
    }

    /**
     * Instantiates a new Energy body.
     *
     * @param clientID the client id
     * @param count    the count
     * @param source   the source
     */
    public EnergyBody(int clientID, int count, String source) {
        this.clientID = clientID;
        this.count = count;
        this.source = source;
    }

    /**
     * Gets client id.
     *
     * @return the client id
     */
    public int getClientID() {
        return clientID;
    }

    /**
     * Gets count.
     *
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * Gets source.
     *
     * @return the source
     */
    public String getSource() {
        return source;
    }
}
