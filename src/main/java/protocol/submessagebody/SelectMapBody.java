package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectMapBody {
    private List<String> availableMaps;

    public SelectMapBody() {
    }

    public SelectMapBody(List<String> availableMaps) {
        this.availableMaps = availableMaps;
    }

    public List<String> getAvailableMaps() {
        return availableMaps;
    }
}
