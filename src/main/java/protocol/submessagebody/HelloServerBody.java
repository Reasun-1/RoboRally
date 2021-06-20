package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class HelloServerBody {


    private String group;

    private @JsonProperty(value="isAI") boolean isAI;

    private String protocol;

    public HelloServerBody() {
    }

    public HelloServerBody(String group, boolean isAI, String protocol) {
        this.group = group;
        this.isAI = isAI;
        this.protocol = protocol;
    }

    public String getGroup() {
        return group;
    }

    @JsonProperty(value="isAI")
    public boolean isAI() {
        return isAI;
    }

    public String getProtocol() {
        return protocol;
    }
}
