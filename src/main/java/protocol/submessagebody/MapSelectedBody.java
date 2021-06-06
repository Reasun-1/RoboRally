package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MapSelectedBody {
    private String map;

    public MapSelectedBody() {
    }

    public MapSelectedBody(String map) {
        this.map = map;
    }

    public String getMap() {
        return map;
    }

}
