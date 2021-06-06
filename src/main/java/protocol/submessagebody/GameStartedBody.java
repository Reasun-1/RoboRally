package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import protocol.Protocol;
import server.feldobjects.FeldObject;
import server.feldobjects.Pit;

import java.util.Arrays;
import java.util.List;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameStartedBody {

    //@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS)
    private List<List<List<FeldObject>>> gameMap;

    public GameStartedBody() {
    }

    public GameStartedBody(List<List<List<FeldObject>>> gameMap) {
        this.gameMap = gameMap;
    }

    public List<List<List<FeldObject>>> getGameMap() {
        return gameMap;
    }

    public static void main(String[] args) throws JsonProcessingException {
        List<List<List<FeldObject>>> threeDListAsMap = Arrays.asList(Arrays.asList(Arrays.asList(new Pit("5B"))));
        String js = Protocol.writeJson(new Protocol("GameStarted", new GameStartedBody(threeDListAsMap)));
        System.out.println(js);
    }
}
