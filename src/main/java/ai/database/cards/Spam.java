package ai.database.cards;

import server.game.Game;
import server.network.Server;
import server.registercards.MoveI;
import server.registercards.RegisterCard;

import java.io.IOException;

public class Spam extends CardGeneral {

    String cardType; // PROGRAMME DAMAGE SPECIAL
    String cardName; // detailed name of each card

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
