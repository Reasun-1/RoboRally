package protocol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class Protocol {

    private String messageType;
    private MessageBody messageBody;


    public Protocol() {
    }

    public Protocol(String messageType, MessageBody messageBody) {
        this.messageType = messageType;
        this.messageBody = messageBody;
    }

    public String getMessageType() {
        return messageType;
    }

    public MessageBody getMessageBody() {
        return messageBody;
    }

    public static String writeJson(Protocol protokoll) throws JsonProcessingException {

        ObjectMapper obejectMapper = new ObjectMapper();

        String json = obejectMapper.writeValueAsString(protokoll);

        return json;
    }

    public static Protocol readJson(String json) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        return protocol;
    }


}
