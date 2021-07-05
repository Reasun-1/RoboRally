package protocol.submessagebody;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import server.feldobjects.FeldObject;

/**
 * The type Test body.
 *
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestBody {
    /**
     * The Fo.
     */
    FeldObject fo;

    /**
     * Instantiates a new Test body.
     */
    public TestBody() {
    }

    /**
     * Instantiates a new Test body.
     *
     * @param fo the fo
     */
    public TestBody(FeldObject fo) {
        this.fo = fo;
    }

    /**
     * Gets fo.
     *
     * @return the fo
     */
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
