package server.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Register {

    private int clientID;
    private String cardName;

    public Register() {
    }

    public Register(int clientID, String cardName) {
        this.clientID = clientID;
        this.cardName = cardName;
    }

    public int getClientID() {
        return clientID;
    }

    public String getCardName() {
        return cardName;
    }
}
