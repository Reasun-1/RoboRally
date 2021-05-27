package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import server.feldobjects.FeldObject;

import java.util.List;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameStartedBody {
    private List<List<List<FeldObject>>> gameMap;

    public GameStartedBody() {
    }

    public GameStartedBody(List<List<List<FeldObject>>> gameMap) {
        this.gameMap = gameMap;
    }

    public List<List<List<FeldObject>>> getGameMap() {
        return gameMap;
    }
}
