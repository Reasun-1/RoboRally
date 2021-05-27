package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnimationBody {
    private String type;

    public AnimationBody() {
    }

    public AnimationBody(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
