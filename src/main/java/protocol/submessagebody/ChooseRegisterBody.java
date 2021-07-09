package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
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
