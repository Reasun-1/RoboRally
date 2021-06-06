package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerValuesBody {
    private String name;
    private int figure;

    public PlayerValuesBody() {
    }

    public PlayerValuesBody(String name, int figure) {
        this.name = name;
        this.figure = figure;
    }

    public String getName() {
        return name;
    }

    public int getFigure() {
        return figure;
    }
}
