package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChooseRegisterBody {

    private int register;

    public ChooseRegisterBody() {
    }

    public ChooseRegisterBody(int register) {
        this.register = register;
    }

    public int getRegister() {
        return register;
    }
}
