package client.scenes;

import client.utilities.UserCommunication;
import client.utilities.UserSessionCommunication;
import commons.User;
import commons.UserSession;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javax.inject.Inject;

public class SplashController implements FrontEndController{

    private final MainCtrl mainCtrl;
    private final UserCommunication userCommunication;
    private final UserSessionCommunication userSessionCommunication;

    public static UserSession userSession = new UserSession();
    public static UserSession singleSession;

    @FXML
    private StackPane rootPane;

    @FXML
    private Label alert;
    private final String alertText = "Username required !";

    @FXML
    private TextField usernameBox;

    @FXML
    private Button userButton;

    /**
     * The constructor of the SplashController
     * @param mainCtrl an instance of the MainCtrl
     */
    @Inject
    public SplashController(MainCtrl mainCtrl, UserCommunication userCommunication, UserSessionCommunication userSessionCommunication) {
        this.mainCtrl = mainCtrl;
        this.userCommunication = userCommunication;
        this.userSessionCommunication = userSessionCommunication;
    }

    /**
     * Method used to set up the SplashController
     */
    public void setup() {

        if (mainCtrl.getInBoot()) {
            rootPane.setOpacity(0);
            fadeIn();
        }

        userSession = userSessionCommunication.getOpenSession();
        if (userSession == null) {
            userSession = new UserSession();
            try {
                userSessionCommunication.makeSession(userSession);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (mainCtrl.user == null) {
            userButton.setText("Create User");
        } else {
            // Reset user score with API call
            mainCtrl.user.setScore(0);
        }
    }
    
    /**
     * Method to fade in the pane when loading scene.
     */
    public void fadeIn() {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    /**
     * Method called when the submit button is pressed. Saves the username and sends it to the database. Checks if the field is empty.
     */
    public void addUsername() {
        if (mainCtrl.user == null) {
            String nameContent = usernameBox.getText();
            if (!nameContent.isEmpty()) {
                mainCtrl.user = userCommunication.addUser(new User(nameContent));
                alert.setText("");
                usernameBox.setText("");
                userButton.setText("Rename");
            } else {
                mainCtrl.user = null;
                alert.setText(alertText);
            }
        } else {
            String newUsername = usernameBox.getText();
            if (!newUsername.isEmpty()) {
                mainCtrl.user = userCommunication.renameUser(mainCtrl.user.getUuid(), newUsername);
                alert.setText("");
                usernameBox.setText("");
            } else {
                mainCtrl.user = null;
                alert.setText(alertText);
            }
        }
    }

    /**
     * Method called when the Single Player button is pressed.
     */
    public void showSolo() {
        if (mainCtrl.user == null) {
            alert.setText(alertText);
            return;
        }
        singleSession = new UserSession();
        singleSession.setStatus(0);
        userSessionCommunication.makeSession(singleSession);
        mainCtrl.user.setSessionId(singleSession.getId());
        //userCommunication.addUser(mainCtrl.user);
        mainCtrl.showSoloWaiting();
    }

    /**
     * Method called when the MultiPlayer button is pressed.
     */
    public void showWaitingRoom() {
        if (mainCtrl.user == null) {
            alert.setText(alertText);
            return;
        }

        userSession = userSessionCommunication.getSession(userSession.getId());

        if (userSession == null || userSession.getStatus() != 1) {
            userSession = new UserSession();
            userSessionCommunication.makeSession(userSession);
        }
        userSessionCommunication.setSessionId(mainCtrl.user);
        mainCtrl.user.setSessionId(userSession.getId());
        mainCtrl.showWaitingRoom();
    }

    /**
     * Method called when the ? button is pressed.
     */
    public void showTutorial() {
        mainCtrl.showTutorial();
    }

    /**
     * Method called when the Change Server button is pressed.
     */
    public void showChangeServer() {
        mainCtrl.showChangeServer();
    }

    /**
     * Method called when the Admin Panel button is pressed.
     */
    public void showAdminPanel() {
        mainCtrl.showAdminPanel();
    }
}
