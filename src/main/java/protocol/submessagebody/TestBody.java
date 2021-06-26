package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import protocol.Protocol;
import server.feldobjects.FeldObject;
import server.feldobjects.Pit;

import java.io.IOException;
import java.util.List;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestBody {
    FeldObject fo;

    public TestBody() {
    }

    public TestBody(FeldObject fo) {
        this.fo = fo;
    }

    public FeldObject getFo() {
        return fo;
    }

    /*
    public static void main(String[] args) throws IOException {
        Protocol protocol = new Protocol("TestBody", new TestBody((FeldObject) new Pit("5B")));
        String json = Protocol.writeJson(protocol);
        System.out.println(json);

        TestBody testBody = Protocol.readJsonTest(json);
        Pit fo = (Pit)testBody.getFo();


    }

     */
}
