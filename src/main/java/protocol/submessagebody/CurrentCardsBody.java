package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import server.game.Register;

import java.util.List;

/**
 * The type Current cards body.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentCardsBody {

    /**
     * The Current registers all clients.
     */
    List<Register> currentRegistersAllClients;

    /**
     * Instantiates a new Current cards body.
     */
    public CurrentCardsBody() {
    }

    /**
     * Instantiates a new Current cards body.
     *
     * @param currentRegistersAllClients the current registers all clients
     */
    public CurrentCardsBody(List<Register> currentRegistersAllClients) {
        this.currentRegistersAllClients = currentRegistersAllClients;
    }

    /**
     * Gets current registers all clients.
     *
     * @return the current registers all clients
     */
    public List<Register> getCurrentRegistersAllClients() {
        return currentRegistersAllClients;
    }
}
