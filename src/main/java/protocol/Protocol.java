package protocol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import protocol.submessagebody.*;


import java.io.IOException;
import java.util.List;
import java.util.logging.Filter;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class Protocol {

    // Object messageBody will be reset by subMessageBody and by decoding separately in subMessageBody transformed
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

    // optional readJson as Protocol, maybe we need this function later
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

    public static HelloClientBody readJsonHelloClientBody(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        HelloClientBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<HelloClientBody>(){});

        return messageBodyDetail;
    }

    public static HelloServerBody readJsonHelloServerBody(String json) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        HelloServerBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<HelloServerBody>(){});

        return messageBodyDetail;
    }

    public static WelcomeBody readJsonWelcomeBody(String json) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        WelcomeBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<WelcomeBody>(){});

        return messageBodyDetail;
    }

    public static PlayerValuesBody readJsonPlayerValues(String json) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        PlayerValuesBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<PlayerValuesBody>(){});

        return messageBodyDetail;
    }

    public static PlayerAddedBody readJsonPlayerAdded(String json) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        PlayerAddedBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<PlayerAddedBody>(){});

        return messageBodyDetail;
    }

    public static SetStatusBody readJsonSetStatus(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        SetStatusBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<SetStatusBody>(){});

        return messageBodyDetail;
    }

    public static PlayerStatusBody readJsonPlayerStatus(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        PlayerStatusBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<PlayerStatusBody>(){});

        return messageBodyDetail;
    }

    public static MapSelectedBody readJsonMapSelected(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        MapSelectedBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<MapSelectedBody>(){});

        return messageBodyDetail;
    }

    public static SelectMapBody readJsonSelectMap(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        SelectMapBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<SelectMapBody>(){});

        return messageBodyDetail;
    }

    public static GameStartedBody readJsonGameStarted(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        GameStartedBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<GameStartedBody>(){});

        return messageBodyDetail;
    }

    public static ActivePhaseBody readJsonActivePhase(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        ActivePhaseBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<ActivePhaseBody>(){});

        return messageBodyDetail;
    }
    
    public static CurrentPlayerBody readJsonCurrentPlayer(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        CurrentPlayerBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<CurrentPlayerBody>(){});

        return messageBodyDetail;
    }

    public static SetStartingPointBody readJsonSetStartingPoint(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        SetStartingPointBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<SetStartingPointBody>(){});

        return messageBodyDetail;
    }

    public static StartingPointTakenBody readJsonStartingPointTaken(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        StartingPointTakenBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<StartingPointTakenBody>(){});

        return messageBodyDetail;
    }

    public static YourCardsBody readJsonYourCards(String json) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        YourCardsBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<YourCardsBody>(){});

        return messageBodyDetail;
    }

    public static NotYourCardsBody readJsonNotYourCards(String json) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        NotYourCardsBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<NotYourCardsBody>(){});

        return messageBodyDetail;
    }

    public static ShuffleCodingBody readJsonShuffleCoding(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        ShuffleCodingBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<ShuffleCodingBody>(){});

        return messageBodyDetail;
    }

    public static SelectedCardBody readJsonSelectedCard(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        SelectedCardBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<SelectedCardBody>(){});

        return messageBodyDetail;
    }

    public static CardSelectedBody readJsonCardSelected(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        CardSelectedBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<CardSelectedBody>(){});

        return messageBodyDetail;
    }

    public static SelectionFinishedBody readJsonSelectionFinished(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        SelectionFinishedBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<SelectionFinishedBody>(){});

        return messageBodyDetail;
    }

    public static TimerEndedBody readJsonTimerEnded(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        TimerEndedBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<TimerEndedBody>(){});

        return messageBodyDetail;
    }

    public static CardsYouGotNowBody readJsonCardsYouGotNow(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        CardsYouGotNowBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<CardsYouGotNowBody>(){});

        return messageBodyDetail;
    }

    public static CurrentCardsBody readJsonCurrentCards(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        CurrentCardsBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<CurrentCardsBody>(){});

        return messageBodyDetail;
    }

    public static TestBody readJsonTest(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        TestBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<TestBody>(){});

        return messageBodyDetail;
    }

    // only for test
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Protocol protocol = new Protocol("SendChat", new SendChatBody("hi", 2));
        String json = Protocol.writeJson(protocol);
        System.out.println(json);
        String type = Protocol.readJsonMessageType(json);
        System.out.println(type);
        SendChatBody sendChatBody = Protocol.readJsonSendChatBody(json);
        System.out.println(sendChatBody.getMessage());
    }


}
