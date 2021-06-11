package server.registercards;

import server.game.Game;
import server.network.Server;

import java.io.IOException;
import java.util.List;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class Spam extends RegisterCard{

    String cardType; // PROGRAMME DAMAGE SPECIAL
    String cardName; // detailed name of each card
    public static int cardCount = 38; // only as info for shuffle the cards

    public Spam() {
        this.cardType = "DAMAGE";
        this.cardName = "Spam";
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
