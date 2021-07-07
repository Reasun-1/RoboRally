package server.upgradecards;

import server.game.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Admin privilege.
 *
 * @author Can Ren
 * @author Megzon Mehmedali
 */
public class AdminPrivilege extends UpgradeCard {

    /**
     * The Card type.
     */
    String cardType; // PROGRAMME DAMAGE SPECIAL UPGRADE
    /**
     * The Card name.
     */
    String cardName; // detailed name of each card
    /**
     * The Sub type.
     */
    String subType; // detailed type of card

    /**
     * Instantiates a new Admin privilege.
     */
    public AdminPrivilege() {
        this.cardType = "UPGRADE";
        this.cardName = "AdminPrivilege";
        this.subType = "Passiv";
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
    public String getSubType() { return subType; }

    @Override
    public void doCardFunction(int clientID) throws IOException {
        System.out.println("DO UPGRADE ADMIN FUNCTION");
        // set at which register the priority should be changed.
        Game.changePriorityRegPointer = Game.registerPointer;
        Game.changedPriorityList.add(clientID);
    }

    public void doAdminPrivilege(int client, int regNr){
        System.out.println("DO UPGRADE ADMIN FUNCTION");
        // set at which register the priority should be changed.
        Game.changePriorityRegPointer = regNr-1;
        //Game.changedPriorityList.add(client);

        if(Game.hashMapPriority.get(Game.changePriorityRegPointer) != null){
            List<Integer> curList = Game.hashMapPriority.get(Game.changePriorityRegPointer);
            curList.add(client);
            Game.hashMapPriority.put(Game.changePriorityRegPointer, curList);
        }else{
            List<Integer> newList = new ArrayList<>();
            newList.add(client);
            Game.hashMapPriority.put(Game.changePriorityRegPointer, newList);
        }

    }
}
