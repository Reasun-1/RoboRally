package ai.database.cards;

import server.game.Game;
import server.network.Server;
import server.registercards.MoveI;
import server.registercards.RegisterCard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Virus extends CardGeneral {

    String cardType; // PROGRAMME DAMAGE SPECIAL
    String cardName; // detailed name of each card

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
     *
     * @param clientID the client id
     * @return list
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
