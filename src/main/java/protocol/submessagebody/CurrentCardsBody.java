package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import server.game.Register;

import java.util.List;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentCardsBody {

    List<Register> currentRegistersAllClients;

    public CurrentCardsBody() {
    }

    public CurrentCardsBody(List<Register> currentRegistersAllClients) {
        this.currentRegistersAllClients = currentRegistersAllClients;
    }

    public List<Register> getCurrentRegistersAllClients() {
        return currentRegistersAllClients;
    }
}
