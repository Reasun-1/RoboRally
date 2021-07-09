package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import server.feldobjects.FeldObject;

import java.util.List;

/**
 * The type Game started body.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameStartedBody {

    //@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
    private List<List<List<FeldObject>>> gameMap;

    /**
     * Instantiates a new Game started body.
     */
    public GameStartedBody() {
    }

    /**
     * Instantiates a new Game started body.
     *
     * @param gameMap the game map
     */
    public GameStartedBody(List<List<List<FeldObject>>> gameMap) {
        this.gameMap = gameMap;
    }

    /**
     * Gets game map.
     *
     * @return the game map
     */
    public List<List<List<FeldObject>>> getGameMap() {
        return gameMap;
    }

    /*
    public static void main(String[] args) throws JsonProcessingException {
        List<List<List<FeldObject>>> threeDListAsMap = Arrays.asList(Arrays.asList(Arrays.asList(new Pit("5B"))));
        String js = Protocol.writeJson(new Protocol("GameStarted", new GameStartedBody(threeDListAsMap)));
        System.out.println(js);
    }
     */
}
