package client.network;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.network.ProtocolServer;

import java.io.IOException;

public class ProtocolClient {

    private String type;
    private String content;

    public ProtocolClient(String type, String content) {
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
        return "ProtocolClient{" +
                "type='" + type + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String writeJson(ProtocolClient pc) throws JsonProcessingException {
        ObjectMapper obejectMapper = new ObjectMapper();

        String json = obejectMapper.writeValueAsString(pc);

        return json;
    }

    public ProtocolClient readJson(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        ProtocolClient protocolClient = objectMapper.readValue(json, ProtocolClient.class);

        return protocolClient;
    }
}
