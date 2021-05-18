package server.network;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ProtocolServer {

    private String type;
    private String content;

    public ProtocolServer(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "ProtocolServer{" +
                "type='" + type + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String writeJson(ProtocolServer ps) throws JsonProcessingException {

        ObjectMapper obejectMapper = new ObjectMapper();

        String json = obejectMapper.writeValueAsString(ps);

        return json;
    }

    public ProtocolServer readJson(String json) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        ProtocolServer protocolServer = objectMapper.readValue(json, ProtocolServer.class);

        return protocolServer;
    }

}
