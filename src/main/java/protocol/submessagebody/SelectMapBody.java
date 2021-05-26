package protocol.submessagebody;

import java.util.List;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
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
