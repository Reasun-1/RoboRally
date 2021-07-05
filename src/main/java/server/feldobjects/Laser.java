package server.feldobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import server.game.Game;
import server.network.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The FeldObject Board laser: Board Lasers fire, hitting any robots in their line of sight.
 * Board lasers cannot fire through walls, the priority antenna, or hit more than one robot,
 * and they shoot from the red and white pointer.
 * (Take a SPAM damage card for each laser that hits you.)
 *
 * @author Jonas Gottal
 * @author Can Ren
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Laser extends FeldObject{

   // private String type;
    private String isOnBoard;
    private List<String> orientations;
    private int count;

    /**
     * Instantiates a new Laser.
     */
    public Laser() {
    }

    /**
     * Instantiates a new Laser.
     *
     * @param isOnBoard    the is on board
     * @param orientations the orientations
     * @param count        the count
     */
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
    public void doBoardFunction(int clientID, FeldObject obj) throws IOException {
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
