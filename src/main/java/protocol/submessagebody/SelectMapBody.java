package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * The type Select map body.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectMapBody {
    private List<String> availableMaps;

    /**
     * Instantiates a new Select map body.
     */
    public SelectMapBody() {
    }

    /**
     * Instantiates a new Select map body.
     *
     * @param availableMaps the available maps
     */
    public SelectMapBody(List<String> availableMaps) {
        this.availableMaps = availableMaps;
    }

    /**
     * Gets available maps.
     *
     * @return the available maps
     */
    public List<String> getAvailableMaps() {
        return availableMaps;
    }
}
