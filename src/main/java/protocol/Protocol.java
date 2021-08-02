package protocol;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import protocol.submessagebody.*;
import server.feldobjects.*;


import java.io.IOException;

/**
 * The type Protocol.
 *
 * @author Can Ren
 * @author Jonas Gottal
 * @author Megzon Mehmedali
 */
public class Protocol {

    // Object messageBody will be reset by subMessageBody and by decoding separately in subMessageBody transformed
    private String messageType;
    private Object messageBody;


    /**
     * Instantiates a new Protocol.
     */
    public Protocol() {
    }

    /**
     * Instantiates a new Protocol.
     *
     * @param messageType the message type
     * @param messageBody the message body
     */
    public Protocol(String messageType, Object messageBody) {
        this.messageType = messageType;
        this.messageBody = messageBody;
    }

    /**
     * Gets message type.
     *
     * @return the message type
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * Gets message body.
     *
     * @return the message body
     */
    public Object getMessageBody() {
        return messageBody;
    }

    /**
     * Write json string.
     *
     * @param protokoll the protokoll
     * @return the string
     * @throws JsonProcessingException the json processing exception
     */
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

    /**
     * Read json message type string.
     *
     * @param json the json
     * @return the string
     * @throws IOException the io exception
     */
    public static String readJsonMessageType(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        return protocol.getMessageType();
    }

    /**
     * Read json send chat body send chat body.
     *
     * @param json the json
     * @return the send chat body
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    public static SendChatBody readJsonSendChatBody(String json) throws IOException, ClassNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        SendChatBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<SendChatBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json received chat body received chat body.
     *
     * @param json the json
     * @return the received chat body
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    public static ReceivedChatBody readJsonReceivedChatBody(String json) throws IOException, ClassNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        ReceivedChatBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<ReceivedChatBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json error body error body.
     *
     * @param json the json
     * @return the error body
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    public static ErrorBody readJsonErrorBody(String json) throws IOException, ClassNotFoundException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        ErrorBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<ErrorBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json hello client body hello client body.
     *
     * @param json the json
     * @return the hello client body
     * @throws IOException the io exception
     */
    public static HelloClientBody readJsonHelloClientBody(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        HelloClientBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<HelloClientBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json hello server body hello server body.
     *
     * @param json the json
     * @return the hello server body
     * @throws IOException the io exception
     */
    public static HelloServerBody readJsonHelloServerBody(String json) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        HelloServerBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<HelloServerBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json welcome body welcome body.
     *
     * @param json the json
     * @return the welcome body
     * @throws IOException the io exception
     */
    public static WelcomeBody readJsonWelcomeBody(String json) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        WelcomeBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<WelcomeBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json player values player values body.
     *
     * @param json the json
     * @return the player values body
     * @throws IOException the io exception
     */
    public static PlayerValuesBody readJsonPlayerValues(String json) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        PlayerValuesBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<PlayerValuesBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json player added player added body.
     *
     * @param json the json
     * @return the player added body
     * @throws IOException the io exception
     */
    public static PlayerAddedBody readJsonPlayerAdded(String json) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        PlayerAddedBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<PlayerAddedBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json set status set status body.
     *
     * @param json the json
     * @return the set status body
     * @throws IOException the io exception
     */
    public static SetStatusBody readJsonSetStatus(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        SetStatusBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<SetStatusBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json player status player status body.
     *
     * @param json the json
     * @return the player status body
     * @throws IOException the io exception
     */
    public static PlayerStatusBody readJsonPlayerStatus(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        PlayerStatusBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<PlayerStatusBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json map selected map selected body.
     *
     * @param json the json
     * @return the map selected body
     * @throws IOException the io exception
     */
    public static MapSelectedBody readJsonMapSelected(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        MapSelectedBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<MapSelectedBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json select map select map body.
     *
     * @param json the json
     * @return the select map body
     * @throws IOException the io exception
     */
    public static SelectMapBody readJsonSelectMap(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        SelectMapBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<SelectMapBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json game started game started body.
     *
     * @param json the json
     * @return the game started body
     * @throws IOException the io exception
     */
    public static GameStartedBody readJsonGameStarted(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        //objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.registerSubtypes(Antenna.class, CheckPoint.class, ConveyorBelt.class, Empty.class, EnergySpace.class, Gear.class, Laser.class, Pit.class, PushPanel.class, RestartPoint.class, StartPoint.class, Wall.class);

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        GameStartedBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<GameStartedBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json active phase active phase body.
     *
     * @param json the json
     * @return the active phase body
     * @throws IOException the io exception
     */
    public static ActivePhaseBody readJsonActivePhase(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        ActivePhaseBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<ActivePhaseBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json current player current player body.
     *
     * @param json the json
     * @return the current player body
     * @throws IOException the io exception
     */
    public static CurrentPlayerBody readJsonCurrentPlayer(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        CurrentPlayerBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<CurrentPlayerBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json set starting point set starting point body.
     *
     * @param json the json
     * @return the set starting point body
     * @throws IOException the io exception
     */
    public static SetStartingPointBody readJsonSetStartingPoint(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        SetStartingPointBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<SetStartingPointBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json starting point taken starting point taken body.
     *
     * @param json the json
     * @return the starting point taken body
     * @throws IOException the io exception
     */
    public static StartingPointTakenBody readJsonStartingPointTaken(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        StartingPointTakenBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<StartingPointTakenBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json your cards your cards body.
     *
     * @param json the json
     * @return the your cards body
     * @throws IOException the io exception
     */
    public static YourCardsBody readJsonYourCards(String json) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        YourCardsBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<YourCardsBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json not your cards not your cards body.
     *
     * @param json the json
     * @return the not your cards body
     * @throws IOException the io exception
     */
    public static NotYourCardsBody readJsonNotYourCards(String json) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        NotYourCardsBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<NotYourCardsBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json shuffle coding shuffle coding body.
     *
     * @param json the json
     * @return the shuffle coding body
     * @throws IOException the io exception
     */
    public static ShuffleCodingBody readJsonShuffleCoding(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        ShuffleCodingBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<ShuffleCodingBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json selected card selected card body.
     *
     * @param json the json
     * @return the selected card body
     * @throws IOException the io exception
     */
    public static SelectedCardBody readJsonSelectedCard(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        SelectedCardBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<SelectedCardBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json card selected card selected body.
     *
     * @param json the json
     * @return the card selected body
     * @throws IOException the io exception
     */
    public static CardSelectedBody readJsonCardSelected(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        CardSelectedBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<CardSelectedBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json selection finished selection finished body.
     *
     * @param json the json
     * @return the selection finished body
     * @throws IOException the io exception
     */
    public static SelectionFinishedBody readJsonSelectionFinished(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        SelectionFinishedBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<SelectionFinishedBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json timer ended timer ended body.
     *
     * @param json the json
     * @return the timer ended body
     * @throws IOException the io exception
     */
    public static TimerEndedBody readJsonTimerEnded(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        TimerEndedBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<TimerEndedBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json cards you got now cards you got now body.
     *
     * @param json the json
     * @return the cards you got now body
     * @throws IOException the io exception
     */
    public static CardsYouGotNowBody readJsonCardsYouGotNow(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        CardsYouGotNowBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<CardsYouGotNowBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json current cards current cards body.
     *
     * @param json the json
     * @return the current cards body
     * @throws IOException the io exception
     */
    public static CurrentCardsBody readJsonCurrentCards(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        CurrentCardsBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<CurrentCardsBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json play card play card body.
     *
     * @param json the json
     * @return the play card body
     * @throws IOException the io exception
     */
    public static PlayCardBody readJsonPlayCard(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        PlayCardBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<PlayCardBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json card played card played body.
     *
     * @param json the json
     * @return the card played body
     * @throws IOException the io exception
     */
    public static CardPlayedBody readJsonCardPlayed(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        CardPlayedBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<CardPlayedBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json reboot reboot body.
     *
     * @param json the json
     * @return the reboot body
     * @throws IOException the io exception
     */
    public static RebootBody readJsonReboot(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        RebootBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<RebootBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json reboot direction reboot direction body.
     *
     * @param json the json
     * @return the reboot direction body
     * @throws IOException the io exception
     */
    public static RebootDirectionBody readJsonRebootDirection(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        RebootDirectionBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<RebootDirectionBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json game finished game finished body.
     *
     * @param json the json
     * @return the game finished body
     * @throws IOException the io exception
     */
    public static GameFinishedBody readJsonGameFinished(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        GameFinishedBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<GameFinishedBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json movement movement body.
     *
     * @param json the json
     * @return the movement body
     * @throws IOException the io exception
     */
    public static MovementBody readJsonMovement(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        MovementBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<MovementBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json player turning player turning body.
     *
     * @param json the json
     * @return the player turning body
     * @throws IOException the io exception
     */
    public static PlayerTurningBody readJsonPlayerTurning(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        PlayerTurningBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<PlayerTurningBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json draw damage draw damage body.
     *
     * @param json the json
     * @return the draw damage body
     * @throws IOException the io exception
     */
    public static DrawDamageBody readJsonDrawDamage(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        DrawDamageBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<DrawDamageBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json replace card replace card body.
     *
     * @param json the json
     * @return the replace card body
     * @throws IOException the io exception
     */
    public static ReplaceCardBody readJsonReplaceCard(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        ReplaceCardBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<ReplaceCardBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json connection update connection update body.
     *
     * @param json the json
     * @return the connection update body
     * @throws IOException the io exception
     */
    public static ConnectionUpdateBody readJsonConnectionUpdate(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        ConnectionUpdateBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<ConnectionUpdateBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json energy energy body.
     *
     * @param json the json
     * @return the energy body
     * @throws IOException the io exception
     */
    public static EnergyBody readJsonEnergy(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        EnergyBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<EnergyBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json refill shop refill shop body.
     *
     * @param json the json
     * @return the refill shop body
     * @throws IOException the io exception
     */
    public static RefillShopBody readJsonRefillShop(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        RefillShopBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<RefillShopBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json buy upgrade buy upgrade body.
     *
     * @param json the json
     * @return the buy upgrade body
     * @throws IOException the io exception
     */
    public static BuyUpgradeBody readJsonBuyUpgrade(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        BuyUpgradeBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<BuyUpgradeBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json upgrade bought upgrade bought body.
     *
     * @param json the json
     * @return the upgrade bought body
     * @throws IOException the io exception
     */
    public static UpgradeBoughtBody readJsonUpgradeBought(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        UpgradeBoughtBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<UpgradeBoughtBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json checkpoint moved checkpoint moved body.
     *
     * @param json the json
     * @return the checkpoint moved body
     * @throws IOException the io exception
     */
    public static CheckpointMovedBody readJsonCheckpointMoved(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        CheckpointMovedBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<CheckpointMovedBody>() {
        });

        return messageBodyDetail;
    }

    public static ChooseRegisterBody readJsonChooseRegister(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        ChooseRegisterBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<ChooseRegisterBody>() {
        });

        return messageBodyDetail;
    }

    public static RegisterChosenBody readJsonRegisterChosen(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        RegisterChosenBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<RegisterChosenBody>() {
        });

        return messageBodyDetail;
    }

    public static CheckPointReachedBody readJsonCheckPointReached(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        CheckPointReachedBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<CheckPointReachedBody>() {
        });

        return messageBodyDetail;
    }

    /**
     * Read json test test body.
     *
     * @param json the json
     * @return the test body
     * @throws IOException the io exception
     */
    public static TestBody readJsonTest(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Protocol protocol = objectMapper.readValue(json, Protocol.class);

        Object messageBody = protocol.getMessageBody();

        TestBody messageBodyDetail = objectMapper.convertValue(messageBody, new TypeReference<TestBody>() {
        });

        return messageBodyDetail;
    }

    // only for test
    /*
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Protocol protocol = new Protocol("SendChat", new SendChatBody("hi", 2));
        String json = Protocol.writeJson(protocol);
        System.out.println(json);
        String type = Protocol.readJsonMessageType(json);
        System.out.println(type);
        SendChatBody sendChatBody = Protocol.readJsonSendChatBody(json);
        System.out.println(sendChatBody.getMessage());
    }
     */


}
