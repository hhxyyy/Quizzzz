package client.scenes;

import client.utilities.ServerCommunication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;

import javax.inject.Inject;

public class ChangeServerController implements FrontEndController {

    private final MainCtrl mainCtrl;
    private final ServerCommunication serverCommunication;

    @FXML
    private Label alert;
    private boolean exitActive = true;

    @FXML
    private TextField serverText;

    /**
     * The constructor for the ChangeServerController
     * @param mainCtrl an instance of the MainCtrl
     */
    @Inject
    public ChangeServerController(MainCtrl mainCtrl, ServerCommunication serverCommunication) {
        this.mainCtrl = mainCtrl;
        this.serverCommunication = serverCommunication;
    }

    /**
     * Method called when this scene is displayed.
     * Sets up necessary functionality.
     */
    public void setup() {
        serverText.requestFocus();
    }

    /**
     * Method called when the exit button is pressed. Sends the user back to splash screen if the server is working.
     */
    public void exitGame() {
        if (!exitActive) {
            alert.setText("Submit your server !");
            return;
        }
        mainCtrl.showSplashScene();
    }

    /**
     * Method used to display an alert.
     */
    public void setAlert() {
        alert.setText("Server not active, try again !");
        alert.setTextFill(Paint.valueOf("#ff0000"));
        exitActive = false;
    }

    /**
     * Method used to clear the alert.
     */
    public void clearAlert() {
        alert.setText("Connection succesful !");
        alert.setTextFill(Paint.valueOf("#00FF00"));
        exitActive = true;
    }

    /**
     * Method used to call ChangeServer on enter press
     * @param event The KeyEvent of a key press
     */
    public void changeServerEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            changeServer();
        }
    }

    /**
     * Method called when the submit button is pressed. Saves the server that the user typed and tests if it works.
     */
    public void changeServer() {
        serverCommunication.setServer(serverText.getText());
        if (!serverCommunication.checkServer()) {
            setAlert();
        } else {
            clearAlert();
        }
    }

}
