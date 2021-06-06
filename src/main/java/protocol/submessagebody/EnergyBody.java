package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnergyBody {
    private int clientID;
    private int count;
    private String source;

    public EnergyBody() {
    }

    public EnergyBody(int clientID, int count, String source) {
        this.clientID = clientID;
        this.count = count;
        this.source = source;
    }

    public int getClientID() {
        return clientID;
    }

    public int getCount() {
        return count;
    }

    public String getSource() {
        return source;
    }
}
