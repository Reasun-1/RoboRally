package server.registercards;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.IOException;

/**
 * The type Register card.
 *
 * @author Can Ren
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public abstract class RegisterCard {

    /**
     * The Card type.
     */
    String cardType; // PROGRAMME DAMAGE SPECIAL
    /**
     * The Card name.
     */
    String cardName; // detailed name of each card
    /**
     * The Card count.
     */
    int cardCount; // total count of this card

    /**
     * Gets card type.
     *
     * @return the card type
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * Gets card name.
     *
     * @return the card name
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * Gets card count.
     *
     * @return the card count
     */
    public int getCardCount() {
        return cardCount;
    }

    /**
     * Do card function.
     *
     * @param clientID the client id
     * @throws IOException the io exception
     */
    public void doCardFunction(int clientID) throws IOException {}
}
