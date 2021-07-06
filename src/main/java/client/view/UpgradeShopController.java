package client.view;


import client.model.Client;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author rajna fani
 * @author chiara welz
 * @author yuliia shaparenko
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */

public class UpgradeShopController {

    private Client client;

    @FXML
    private Button noSelection;
    @FXML
    private Button finishButton;
    @FXML
    private Button upgradeButton1,upgradeButton2,upgradeButton3,upgradeButton4,upgradeButton5,upgradeButton6;
    @FXML
    private ImageView upgradeCard1, upgradeCard2, upgradeCard3, upgradeCard4, upgradeCard5, upgradeCard6;
    @FXML
    private Label upgradeLabel1, upgradeLabel2, upgradeLabel3, upgradeLabel4, upgradeLabel5, upgradeLabel6;
    @FXML
    private Label energyNow, chosenUpgrade;


    //====================CardsBindings===================================
    Image imageDiscard = new Image(getClass().getResource("/images/Cards/C-Discard.jpg").toExternalForm());
    Image adminUpCard = new Image(getClass().getResource("/images/Cards/UpgradeCards/UpGradeAdmin.jpg").toExternalForm());
    Image laserUpCard = new Image(getClass().getResource("/images/Cards/UpgradeCards/UpGradeLaser.jpg").toExternalForm());
    Image spamUpCard = new Image(getClass().getResource("/images/Cards/UpgradeCards/UpGradeBlocker.jpg").toExternalForm());
    Image memoryUpCard = new Image(getClass().getResource("/images/Cards/UpgradeCards/UpGradeMemory.jpg").toExternalForm());

    //====================LabelCostBindings===================================
    String adminLabel = "Cost: 3";
    String laserLabel = "Cost: 2";
    String spamLabel = "Cost: 3";
    String memoryLabel = "Cost: 1";


    //adding sound effects when the window gets opened
    MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass().getResource("/soundEffects/upgradeSound.mp3").toString()));


    /**
     * Method to be called from WindowLauncher to check the entered name.
     *
     * @param
     */
    public void init(Client client) {

        this.client = client;

        mediaPlayer.play();
        mediaPlayer.setVolume(0.15);
        mediaPlayer.seek(Duration.ZERO);

        //binds count of energy cubes
        energyNow.textProperty().bindBidirectional(client.energyCountProperty());

        //binds finish-Button with textfield
        finishButton.disableProperty().bind(chosenUpgrade.textProperty().isEmpty());


        client.flagRefreshUpdateShopProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                for (int i = 0; i < client.availableUpgradesCards.size(); i++) {

                    String cardName = client.availableUpgradesCards.get(i);

                    Image upgradeImage = null;
                    //Label upgradeLabel = null;
                    String label = null;

                    switch (cardName) {
                        case "AdminPrivilege":
                            upgradeImage = adminUpCard;
                            label = adminLabel;
                            break;
                        case "SpamBlocker":
                            upgradeImage = spamUpCard;
                            label = spamLabel;
                            break;
                        case "RealLaser":
                            upgradeImage = laserUpCard;
                            label = laserLabel;
                            break;
                        case "MemorySwap":
                            upgradeImage = memoryUpCard;
                            label = memoryLabel;
                            break;
                    }

                    switch (i + 1) {
                        case 1:
                            upgradeCard1.setImage(upgradeImage);
                            upgradeLabel1.setText(label);
                            break;
                        case 2:
                            upgradeCard2.setImage(upgradeImage);
                            upgradeLabel2.setText(label);
                            break;
                        case 3:
                            upgradeCard3.setImage(upgradeImage);
                            upgradeLabel3.setText(label);
                            break;
                        case 4:
                            upgradeCard4.setImage(upgradeImage);
                            upgradeLabel4.setText(label);
                            break;
                        case 5:
                            upgradeCard5.setImage(upgradeImage);
                            upgradeLabel5.setText(label);
                            break;
                        case 6:
                            upgradeCard6.setImage(upgradeImage);
                            upgradeLabel6.setText(label);
                            break;
                    }
                }

                for (int i = client.availableUpgradesCards.size(); i < 6; i++) {
                    switch (i + 1) {
                        case 3:
                            upgradeButton3.setOpacity(0.0);
                            upgradeCard3.setOpacity(0.0);
                            break;
                        case 4:
                            upgradeButton4.setOpacity(0.0);
                            upgradeCard4.setOpacity(0.0);
                            break;
                        case 5:
                            upgradeButton5.setOpacity(0.0);
                            upgradeCard5.setOpacity(0.0);
                            break;
                        case 6:
                            upgradeButton6.setOpacity(0.0);
                            upgradeCard6.setOpacity(0.0);
                            break;
                    }
                }
            }
        });

    }


    /**
     * Methods set the text in the textfield by press the button of each upgradecard.
     *
     * @param event
     * @throws JsonProcessingException
     */
    @FXML
    private void btnAction1(ActionEvent event) throws JsonProcessingException {
        chosenUpgrade.setText("");
        chosenUpgrade.setText(client.availableUpgradesCards.get(0));
    }

    @FXML
    private void btnAction2(ActionEvent event) throws JsonProcessingException {
        chosenUpgrade.setText("");

        if (client.availableUpgradesCards.size() > 1) {
            chosenUpgrade.setText(client.availableUpgradesCards.get(1));
        }
    }

    @FXML
    private void btnAction3(ActionEvent event) throws JsonProcessingException {
        chosenUpgrade.setText("");

        if (client.availableUpgradesCards.size() > 2) {
            chosenUpgrade.setText(client.availableUpgradesCards.get(2));
        }
    }

    @FXML
    private void btnAction4(ActionEvent event) throws JsonProcessingException {
        chosenUpgrade.setText("");

        if (client.availableUpgradesCards.size() > 3) {
            chosenUpgrade.setText(client.availableUpgradesCards.get(3));
        }
    }

    @FXML
    private void btnAction5(ActionEvent event) throws JsonProcessingException {
        chosenUpgrade.setText("");

        if (client.availableUpgradesCards.size() > 4) {
            chosenUpgrade.setText(client.availableUpgradesCards.get(4));
        }
    }

    @FXML
    private void btnAction6(ActionEvent event) throws JsonProcessingException {
        chosenUpgrade.setText("");

        if (client.availableUpgradesCards.size() > 5) {
            chosenUpgrade.setText(client.availableUpgradesCards.get(5));
        }
    }

    /**
     * Method creates a new event where another scene(window)
     * is opened after pressing the "finish"-Button on Upgrade Shop
     *
     * @param event
     * @throws JsonProcessingException
     */
    @FXML
    private void finishAction(ActionEvent event) throws JsonProcessingException {
        Stage stage = (Stage) finishButton.getScene().getWindow();
        client.handleBuyUpgrade(chosenUpgrade.getText());
        stage.close();
    }

    /**
     * Method creates a new event where another scene(window)
     * is opened after pressing the "noSelection"-Button on Upgrade Shop
     *
     * @param event
     * @throws JsonProcessingException
     */
    @FXML
    private void noSelectionAction(ActionEvent event) throws JsonProcessingException {
        Stage stage = (Stage) noSelection.getScene().getWindow();
        client.handleBuyUpgrade(null);
        //client.setPlayerValues(name.getText(), Integer.valueOf(robotFigure.getText()));
        //client.s
        stage.close();
    }

}
