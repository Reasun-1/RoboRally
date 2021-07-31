package ai.database.cards;

import server.feldobjects.Laser;
import server.game.Game;
import server.network.Server;
import server.registercards.MoveI;
import server.registercards.RegisterCard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Trojan extends CardGeneral {

    String cardType; // PROGRAMME DAMAGE SPECIAL

    String cardName; // detailed name of each card

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
