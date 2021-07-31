package ai.database.fieldelements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import server.feldobjects.FeldObject;
import server.game.Game;
import server.network.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Laser extends ElementGeneral {

    private String isOnBoard;
    private List<String> orientations;
    private int count;

    public Laser() {
    }

    public Laser(String isOnBoard, List<String> orientations, int count) {

        this.isOnBoard = isOnBoard;
        this.orientations = orientations;
        this.count = count;
    }

    @Override
    public String getIsOnBoard() {
        return isOnBoard;
    }

    @Override
    public List<String> getOrientations() {
        return orientations;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public void doBoardFunction(int clientID, ElementGeneral obj) throws IOException {
        int laserNum = obj.getCount();
        List<String> damageCards = new ArrayList<>();

        for (int i = 0; i < laserNum; i++) {
            String cardName = drawOneDamageCard(clientID);
            damageCards.add(cardName);
        }
        System.out.println("got damages : " + damageCards);
        Server.getServer().handleDrawDamage(clientID, damageCards);
    }

    /**
     * draw one damage card from damage card piles
     *
     * @param clientID the client id
     * @return string
     */
    public static String drawOneDamageCard(int clientID){
        String damageCardName = "";

        if(!Game.spamPile.isEmpty()){
            Game.getInstance().discardedCards.get(clientID).add(Game.spamPile.pop());
            damageCardName = "Spam";
        }else if(!Game.wormPile.isEmpty()){
            Game.getInstance().discardedCards.get(clientID).add(Game.wormPile.pop());
            damageCardName = "Worm";
        }else if(!Game.virusPile.isEmpty()){
            Game.getInstance().discardedCards.get(clientID).add(Game.virusPile.pop());
            damageCardName = "Virus";
        }else if(!Game.trojanHorsePile.isEmpty()){
            Game.getInstance().discardedCards.get(clientID).add(Game.trojanHorsePile.pop());
            damageCardName = "Trojan";
        }
        return damageCardName;
    }
}
