package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Map selected body.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MapSelectedBody {
    private String map;

    /**
     * Instantiates a new Map selected body.
     */
    public MapSelectedBody() {
    }

    /**
     * Instantiates a new Map selected body.
     *
     * @param map the map
     */
    public MapSelectedBody(String map) {
        this.map = map;
    }

    /**
     * Gets map.
     *
     * @return the map
     */
    public String getMap() {
        return map;
    }

}
