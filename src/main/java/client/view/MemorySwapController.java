package client.view;

import client.model.Client;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


/**
 * The controller for  Memory swap.
 *
 * @author Rajna Fani
 * @author Chiara Welz
 * @author Yuliia Shaparenko
 */
public class MemorySwapController {

    private Client client;

    private int countTotolClick = 0;

    @FXML
    private Button card1Button, card2Button, card3Button, card4Button, card5Button, card6Button,
            card7Button, card8Button, card9Button, card10Button, card11Button, card12Button;

    @FXML
    private ImageView card1, card2, card3, card4, card5, card6, card7,
    card8, card9, card10, card11, card12;

    @FXML
    private Label chooseCards;



//====================DrawnCardsBindings===================================
    Image imageAgain = new Image(getClass().getResource("/images/Cards/C-Again.jpg").toExternalForm());
    Image imageDiscard = new Image(getClass().getResource("/images/Cards/C-Discard.jpg").toExternalForm());
    Image imageMove1 = new Image(getClass().getResource("/images/Cards/C-Move1.jpg").toExternalForm());
    Image imageMove2 = new Image(getClass().getResource("/images/Cards/C-Move2.jpg").toExternalForm());
    Image imageMove3 = new Image(getClass().getResource("/images/Cards/C-Move3.jpg").toExternalForm());
    Image imageMoveBack = new Image(getClass().getResource("/images/Cards/C-MoveBack.jpg").toExternalForm());
    Image imagePowerUp = new Image(getClass().getResource("/images/Cards/C-PowerUp.jpg").toExternalForm());
    Image imageTurnL = new Image(getClass().getResource("/images/Cards/C-TurnL.jpg").toExternalForm());
    Image imageTurnR = new Image(getClass().getResource("/images/Cards/C-TurnR.jpg").toExternalForm());
    Image imageTurnU = new Image(getClass().getResource("/images/Cards/C-TurnU.jpg").toExternalForm());
    Image imageSpam = new Image(getClass().getResource("/images/Cards/DamageCards/D-Spam.jpg").toExternalForm());
    Image imageTrojan = new Image(getClass().getResource("/images/Cards/DamageCards/D-TrojanHorse.jpg").toExternalForm());
    Image imageVirus = new Image(getClass().getResource("/images/Cards/DamageCards/D-Virus.jpg").toExternalForm());
    Image imageWorm = new Image(getClass().getResource("/images/Cards/DamageCards/D-Worm.jpg").toExternalForm());

    /**
     * Init.
     *
     * @param client the client
     */
    public void init(Client client) {

        this.client = client;

        client.flagCardsInMemorySwapWindowProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                for (int i = 0; i < client.cards12ForMemory.size(); i++) {
                    String cardName = client.cards12ForMemory.get(i);

                    Image curImage = null;

                    switch (cardName) {
                        case "Again":
                            curImage = imageAgain;
                            break;
                        case "BackUp":
                            curImage = imageMoveBack;
                            break;
                        case "MoveI":
                            curImage = imageMove1;
                            break;
                        case "MoveII":
                            curImage = imageMove2;
                            break;
                        case "MoveIII":
                            curImage = imageMove3;
                            break;
                        case "PowerUp":
                            curImage = imagePowerUp;
                            break;
                        case "TurnLeft":
                            curImage = imageTurnL;
                            break;
                        case "TurnRight":
                            curImage = imageTurnR;
                            break;
                        case "UTurn":
                            curImage = imageTurnU;
                            break;
                        case "Spam":
                            curImage = imageSpam;
                            break;
                        case "Trojan":
                            curImage = imageTrojan;
                            break;
                        case "Virus":
                            curImage = imageVirus;
                            break;
                        case "Worm":
                            curImage = imageWorm;
                            break;
                    }

                    switch (i + 1) {
                        case 1:
                            card1.setImage(curImage);
                            break;
                        case 2:
                            card2.setImage(curImage);
                            break;
                        case 3:
                            card3.setImage(curImage);
                            break;
                        case 4:
                            card4.setImage(curImage);
                            break;
                        case 5:
                            card5.setImage(curImage);
                            break;
                        case 6:
                            card6.setImage(curImage);
                            break;
                        case 7:
                            card7.setImage(curImage);
                            break;
                        case 8:
                            card8.setImage(curImage);
                            break;
                        case 9:
                            card9.setImage(curImage);
                            break;
                        case 10:
                            card10.setImage(curImage);
                            break;
                        case 11:
                            card11.setImage(curImage);
                            break;
                        case 12:
                            card12.setImage(curImage);
                            break;
                    }
                }
            }
        });
    }

    /**
     * Click button 1.
     */
    public void clickButton1() {

        card1Button.setDisable(true);

        countTotolClick++;

        String url = card1.getImage().getUrl();

        String cardName = findCardName(url);

        client.cards12ForMemory.remove(cardName);

        if(countTotolClick == 3){

            client.MYCARDS.clear();

            for (int i = 0; i < 9; i++) {
                client.MYCARDS.add(client.cards12ForMemory.get(i));
            }

            client.cards12ForMemory.clear();

            Stage stage = (Stage) card1Button.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Click button 2.
     */
    public void clickButton2() {

        card2Button.setDisable(true);

        countTotolClick++;

        String url = card2.getImage().getUrl();

        String cardName = findCardName(url);

        client.cards12ForMemory.remove(cardName);

        if(countTotolClick == 3){

            client.MYCARDS.clear();

            for (int i = 0; i < 9; i++) {
                client.MYCARDS.add(client.cards12ForMemory.get(i));
            }

            client.cards12ForMemory.clear();

            Stage stage = (Stage) card2Button.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Click button 3.
     */
    public void clickButton3() {

        card3Button.setDisable(true);

        countTotolClick++;

        String url = card3.getImage().getUrl();

        String cardName = findCardName(url);

        client.cards12ForMemory.remove(cardName);

        if(countTotolClick == 3){

            client.MYCARDS.clear();

            for (int i = 0; i < 9; i++) {
                client.MYCARDS.add(client.cards12ForMemory.get(i));
            }

            client.cards12ForMemory.clear();

            Stage stage = (Stage) card3Button.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Click button 4.
     */
    public void clickButton4() {

        card4Button.setDisable(true);

        countTotolClick++;

        String url = card4.getImage().getUrl();

        String cardName = findCardName(url);

        client.cards12ForMemory.remove(cardName);

        if(countTotolClick == 3){

            client.MYCARDS.clear();

            for (int i = 0; i < 9; i++) {
                client.MYCARDS.add(client.cards12ForMemory.get(i));
            }

            client.cards12ForMemory.clear();

            Stage stage = (Stage) card4Button.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Click button 5.
     */
    public void clickButton5() {

        card5Button.setDisable(true);

        countTotolClick++;

        String url = card5.getImage().getUrl();

        String cardName = findCardName(url);

        client.cards12ForMemory.remove(cardName);

        if(countTotolClick == 3){

            client.MYCARDS.clear();

            for (int i = 0; i < 9; i++) {
                client.MYCARDS.add(client.cards12ForMemory.get(i));
            }

            client.cards12ForMemory.clear();

            Stage stage = (Stage) card5Button.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Click button 6.
     */
    public void clickButton6() {

        card6Button.setDisable(true);

        countTotolClick++;

        String url = card6.getImage().getUrl();

        String cardName = findCardName(url);

        client.cards12ForMemory.remove(cardName);

        if(countTotolClick == 3){

            client.MYCARDS.clear();

            for (int i = 0; i < 9; i++) {
                client.MYCARDS.add(client.cards12ForMemory.get(i));
            }

            client.cards12ForMemory.clear();

            Stage stage = (Stage) card6Button.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Click button 7.
     */
    public void clickButton7() {

        card7Button.setDisable(true);

        countTotolClick++;

        String url = card7.getImage().getUrl();

        String cardName = findCardName(url);

        client.cards12ForMemory.remove(cardName);

        if(countTotolClick == 3){

            client.MYCARDS.clear();

            for (int i = 0; i < 9; i++) {
                client.MYCARDS.add(client.cards12ForMemory.get(i));
            }

            client.cards12ForMemory.clear();

            Stage stage = (Stage) card7Button.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Click button 8.
     */
    public void clickButton8() {

        card8Button.setDisable(true);

        countTotolClick++;

        String url = card8.getImage().getUrl();

        String cardName = findCardName(url);

        client.cards12ForMemory.remove(cardName);

        if(countTotolClick == 3){

            client.MYCARDS.clear();

            for (int i = 0; i < 9; i++) {
                client.MYCARDS.add(client.cards12ForMemory.get(i));
            }

            client.cards12ForMemory.clear();

            Stage stage = (Stage) card8Button.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Click button 9.
     */
    public void clickButton9() {

        card9Button.setDisable(true);

        countTotolClick++;

        String url = card9.getImage().getUrl();

        String cardName = findCardName(url);

        client.cards12ForMemory.remove(cardName);

        if(countTotolClick == 3){

            client.MYCARDS.clear();

            for (int i = 0; i < 9; i++) {
                client.MYCARDS.add(client.cards12ForMemory.get(i));
            }

            client.cards12ForMemory.clear();

            Stage stage = (Stage) card9Button.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Click button 10.
     */
    public void clickButton10() {

        card10Button.setDisable(true);

        countTotolClick++;

        String url = card10.getImage().getUrl();

        String cardName = findCardName(url);

        client.cards12ForMemory.remove(cardName);

        if(countTotolClick == 3){

            client.MYCARDS.clear();

            for (int i = 0; i < 9; i++) {
                client.MYCARDS.add(client.cards12ForMemory.get(i));
            }

            client.cards12ForMemory.clear();

            Stage stage = (Stage) card10Button.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Click button 11.
     */
    public void clickButton11() {

        card11Button.setDisable(true);

        countTotolClick++;

        String url = card11.getImage().getUrl();

        String cardName = findCardName(url);

        client.cards12ForMemory.remove(cardName);

        if(countTotolClick == 3){

            client.MYCARDS.clear();

            for (int i = 0; i < 9; i++) {
                client.MYCARDS.add(client.cards12ForMemory.get(i));
            }

            client.cards12ForMemory.clear();

            Stage stage = (Stage) card11Button.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Click button 12.
     */
    public void clickButton12() {

        card12Button.setDisable(true);

        countTotolClick++;

        String url = card12.getImage().getUrl();

        String cardName = findCardName(url);

        client.cards12ForMemory.remove(cardName);

        if(countTotolClick == 3){

            client.MYCARDS.clear();

            for (int i = 0; i < 9; i++) {
                client.MYCARDS.add(client.cards12ForMemory.get(i));
            }

            client.cards12ForMemory.clear();

            Stage stage = (Stage) card12Button.getScene().getWindow();
            stage.close();
        }
    }


    /**
     * Find card name string.
     *
     * @param url the url
     * @return the string
     */
    public String findCardName(String url) {
        String cardName = null;

        if(url.contains("Again")){
            cardName = "Again";
        }else if(url.contains("Move1")){
            cardName = "MoveI";
        }else if(url.contains("Move2")){
            cardName = "MoveII";
        }else if(url.contains("Move3")){
            cardName = "MoveIII";
        }else if(url.contains("MoveBack")){
            cardName = "BackUp";
        }else if(url.contains("PowerUp")){
            cardName = "PowerUp";
        }else if(url.contains("TurnL")){
            cardName = "TurnLeft";
        }else if(url.contains("TurnR")){
            cardName = "TurnRight";
        }else if(url.contains("TurnU")){
            cardName = "UTurn";
        }else if(url.contains("Spam")){
            cardName = "Spam";
        }else if(url.contains("Trojan")){
            cardName = "Trojan";
        }else if(url.contains("Virus")){
            cardName = "Virus";
        }else if(url.contains("Worm")){
            cardName = "Worm";
        }

        return cardName;
    }
}
