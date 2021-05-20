package protocol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import protocol.submessagebody.ErrorBody;
import protocol.submessagebody.ReceivedChatBody;
import protocol.submessagebody.SendChatBody;


import java.io.IOException;
import java.util.List;
import java.util.logging.Filter;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class Protocol {

    private String messageType;
    private Object messageBody;


    public Protocol() {
    }

    public Protocol(String messageType, Object messageBody) {
        this.messageType = messageType;
        this.messageBody = messageBody;
    }

    public String getMessageType() {
        return messageType;
    }

    public Object getMessageBody() {
        return messageBody;
    }

    public static String writeJson(Protocol protokoll) throws JsonProcessingException {

        ObjectMapper obejectMapper = new ObjectMapper();

        String json = obejectMapper.writeValueAsString(protokoll);

        return json;
    }

   /* public static Protocol readJson(String json) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        return protocol;
    }

    */

    public static String readJsonMessageType(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        return protocol.getMessageType();
    }

    public static SendChatBody readJsonSendChatBody(String json) throws IOException, ClassNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        SendChatBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<SendChatBody>(){});
        return messageBodyDetail;
    }

    public static ReceivedChatBody readJsonReceivedChatBody(String json) throws IOException, ClassNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        ReceivedChatBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<ReceivedChatBody>(){});
        return messageBodyDetail;
    }

    public static ErrorBody readJsonErrorBody(String json) throws IOException, ClassNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        ErrorBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<ErrorBody>(){});
        return messageBodyDetail;
    }

    // only for test
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Protocol protocol = new Protocol("SendChat", new SendChatBody("alice","hi"));
        String json = Protocol.writeJson(protocol);
        System.out.println(json);
        String type = Protocol.readJsonMessageType(json);
        System.out.println(type);
        SendChatBody sendChatBody = Protocol.readJsonSendChatBody(json);
        System.out.println(sendChatBody.getMessage());


    }


}
