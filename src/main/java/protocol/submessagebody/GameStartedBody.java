package protocol.submessagebody;

import server.feldobjects.FeldObject;

import java.util.List;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
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
