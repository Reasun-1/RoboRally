package server.registercards;

import server.feldobjects.Laser;
import server.game.Game;
import server.network.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Trojan.
 *
 * @author Megzon Mehmedali
 * @author Can Ren
 * @author Jonas Gottal
 */
public class Trojan extends RegisterCard{
    /**
     * The Card type.
     */
    String cardType; // PROGRAMME DAMAGE SPECIAL
    /**
     * The Card name.
     */
    String cardName; // detailed name of each card
    /**
     * The constant cardCount.
     */
    public static int cardCount = 12; // only as info for shuffle the cards

    /**
     * Instantiates a new Trojan.
     */
    public Trojan() {
        this.cardType = "DAMAGE";
        this.cardName = "Trojan";
    }

    @Override
    public String getCardType() {
        return cardType;
    }

    @Override
    public String getCardName() {
        return cardName;
    }

    @Override
    public int getCardCount() {
        return cardCount;
    }

    @Override
    public void doCardFunction(int clientID) throws IOException {
        List<String> damageCards = new ArrayList<>();
        // draw two damage cards
        for (int i = 0; i < 2; i++) {
            String cardString = Laser.drawOneDamageCard(clientID);
            damageCards.add(cardString);
        }
        Server.getServer().handleDrawDamage(clientID, damageCards);

        // replace with a random card
        RegisterCard replaceCard = null;

        if(!Game.undrawnCards.get(clientID).isEmpty()){
            replaceCard= Game.undrawnCards.get(clientID).get(0);
            Game.undrawnCards.get(clientID).remove(0);
        }else{
            replaceCard = new MoveI();
        }

        Game.discardedCards.get(clientID).add(replaceCard);
        Server.getServer().handleReplaceCard(Game.registerPointer, clientID, replaceCard.getCardName());

    }
}
