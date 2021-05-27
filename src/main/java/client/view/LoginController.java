package client.view;
import client.model.Client;
import client.viewmodel.LoginViewModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author can ren
 * @author yuliia shaparenko
 * @author chiara welz
 * @create $(YEAR)-$(MONTH)-$(DAY)
 */
public class LoginController {

    @FXML
    private TextField name;

    @FXML
    private Button startButton;

    @FXML
    private Label clientID;

    @FXML
    private TextField clientIIDD;

    @FXML
    private TextField robotFigure;

    private Client client;

    /**
     * Method to be called from WindowLauncher to check the entered name.
     * @param client
     */
    public void init(Client client) {
        this.client = client;
        //connect the viewModel
        //new LoginViewModel();
        /*With bidirectional binding, the two property values are synchronized so that if either
         property changes, the other property is automatically changed as well */
        //name.textProperty().bindBidirectional(LoginViewModel.heroNameProperty());

        clientID.textProperty().bindBidirectional(client.getCLIENTIDASSTRINGPROPERTY());

        //heroNameProperty() is a method declared on the LogInViewModel that returns the username required on the TextField
        //startButton.disableProperty().bind(LoginViewModel.loginPossibleProperty().not());
    }

    @FXML
    /**
     * Method creates a new event where another scene(window)
     * is opened after pressing the "start" Button on login window
     * @param event
     */
    private void loginButton(ActionEvent event) throws JsonProcessingException {
        Stage stage = (Stage) startButton.getScene().getWindow();
        //client.checkName(name.getText());
        //client.setName(name.getText());
        //client.setRobotFigureAllClients(client.getClientID(), Integer.valueOf(robotFigure.getText()));
        client.setPlayerValues(name.getText(), Integer.valueOf(robotFigure.getText()));
        stage.close();
    }
}
