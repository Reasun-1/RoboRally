package server.game;

import server.feldobjects.FeldObject;
import server.registercards.RegisterCard;

import java.util.HashSet;
import java.util.List;

public class Game {

    private final static Game game = new Game();

    int countPlayer; // total count of players
    String mapName; // von players chosen map

    List<String> playerNames; // index of List = index of player = anchor for the game!!!
    List<String> robotFigure; // each player has a robot figure
    List<Integer> priorityEachRound; // e.g. [2,0,1] means player number 2 has first priority in this round

    List<List<RegisterCard>> undrawnCards; // decks of undrawn cards of all players
    List<List<RegisterCard>> discardedCards; // decks of discarded cards of all players
    List<List<RegisterCard>> registers; // registers of all players
    List<List<UpgradeCard>> upgradeCards; // deck of upgrade cards of all players

    List<UpgradeCard> upgradeShop; // total common deck of all upgrade cards
    List<Integer> energyCubes; // who has how many cubes
    int energyBank; // total common energy cubes for the game
    List<HashSet<Integer>> arrivedCheckpoints; // who has arrived which checkpoints;

    List<String> playerStatus; // OUTOFBOARD INPLAY
    List<Position> playerPositions; //

    List<List<List<FeldObject>>> board;

    boolean isGameOver; // true for game over

    /**
     * constructor Game:
     * @return only one instance of the Game
     */
    public static Game getInstance() {
        return game;
    }

    /**
     * initial game and player info, every player draws cards for first round
     */
    public void initAndStartGame(){

        // TODO init all the lists in Game in respect of player number

        for (int i = 0; i < playerNames.size(); i++) {

            shuffleUndrawnCardDeck(i);
            drawRegiCards(i);

            // TODO SEND INFO VIA SERVER TO EACH CLIENT: which cards have been drawn
        }
    }

    /**
     * invoked from Game: initAndStartGame
     * init and shuffle undrawn card deck
     */
    public void shuffleUndrawnCardDeck(int PlayerIndex){
    }

    /**
     * invoked from Game: initAndStartGame
     */
    public void drawRegiCards(int PlayerIndex){
    }

    /**
     * invoked from client side: set robot figure to a client (index as anchor in list)
     */
    public void setPlayerRobots(String clientName){

        // TODO SEND INFO VIA SERVER TO ALL CLIENTS: who has which robot
    }

    /**
     * transform json-play-map to list as board
     * @param json
     */
    public void initBoard(String json){

        // TODO SEND INFO VIA SERVER TO ALL CLIENTS: which board has been chosen
    }

    /**
     * if the client is offline, should be removed from game
     */
    public void removePlayer(String clientName){


        // TODO SEND INFO VIA SERVER TO ALL CLIENTS: who was removed
    }

    /**
     * when the client finished programming, will send thd infos of register cards to server
     * then the list of register cards will be set
     * @param clientName
     */
    public void setRegisterCards(String clientName){

    }

    /**
     * invoked from client by button(only when all finished programming, button can be activated)
     * TODO button from GUI
     */
    public void activatePhase(){

        checkAndSetPriority();

        for (int i = 0; i < playerNames.size(); i++) {
            int playerIndex = priorityEachRound.get(i);
            String clientName = playerNames.get(playerIndex);

            // do RegiCard function

            // do Tile function in board (incl. damage)

            // remove RegiCard from register list

            // TODO SEND INFO VIA SERVER TO EACH CLIENT: the current position of robot
        }

        shootLaser();

        // TODO SEND INFO VIA SERVER TO ALL CLIENTS:
        // send a message to all clients to tell the RegiCard is played, so that one card will disappears in GUI

        checkGameOver();

        if(!isGameOver){
            for (int i = 0; i < playerNames.size(); i++) {
                drawRegiCards(i);

                // TODO TODO SEND INFO VIA SERVER TO ALL CLIENTS: who has drawn which cards
            }
        }
    }

    /**
     * invoked from Game: activatePhase
     * analog game rules, priority list will be reset
     */
    public void checkAndSetPriority(){

    }

    /**
     * invoked from Game: activatePhase
     */
    public void shootLaser(){

        setRobotLaserLine();

        // check damage for each robot
        for (int i = 0; i < playerNames.size(); i++) {
        }

        clearRobotLaseLine();
    }


    /**
     * invoked from Game: shootLaser
     * calculate the line of robot laser, set the line into board list (3D)
     */
    public void setRobotLaserLine(){
    }

    /**
     * invoked from Game: shootLaser
     * erase the line of robot laser from board list (3D), ready for next turn
     */
    public void clearRobotLaseLine(){
    }

    /**
     * if all checkpoints are reached, game is over
     */
    public void checkGameOver(){
        // TODO SEND INFO VIA SERVER TO EACH CLIENT: game is over and who is the winner
    }
}
