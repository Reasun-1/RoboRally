package server.registercards;

import server.game.Game;
import server.game.Position;
import server.network.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author can ren
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class Virus extends RegisterCard{
    String cardType; // PROGRAMME DAMAGE SPECIAL
    String cardName; // detailed name of each card
    public static int cardCount =18; // only as info for shuffle the cards

    public Virus() {
        this.cardType = "DAMAGE";
        this.cardName = "Virus";
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

        List<Integer> infectedClients = searchClientsInTheNear(clientID);
        for(int clt : infectedClients){
            // each near client gets a virus
            List<String> damageCards = new ArrayList<>();
            Game.getInstance().discardedCards.get(clientID).add(Game.virusPile.pop());
            damageCards.add("Virus");
            Server.getServer().handleDrawDamage(clientID, damageCards);
        }

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

    /**
     * check which clients are in the infection´s distance
     * @param clientID
     * @return
     */
    public List<Integer> searchClientsInTheNear(int clientID){
        List<Integer> clientsWithDistance = new ArrayList<>();
        int myX = Game.playerPositions.get(clientID).getX();
        int myY = Game.playerPositions.get(clientID).getY();

        for(int client : Game.clientIDs){
            int cltX = Game.playerPositions.get(client).getX();
            int cltY = Game.playerPositions.get(client).getY();
            if(Math.abs(myX-cltX) + Math.abs(myY-cltY) <= 6 && client != clientID){
                clientsWithDistance.add(client);
            }
        }
        return clientsWithDistance;
    }
}
