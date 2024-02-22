package client.scenes;

import client.utilities.UpdateViewUtils;
import client.utilities.UserCommunication;
import client.utilities.UserSessionCommunication;
import commons.User;
import commons.UserSession;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class WaitingController implements FrontEndController {

    private final MainCtrl mainCtrl;
    private final UserSessionCommunication userSessionCommunication;
    private final UserCommunication userCommunication;
    private UserSession session;
    private UpdateViewUtils updateViewUtils;

    @FXML
    private GridPane playerGrid;

    @FXML
    private Label roomTitle;

    @FXML
    Button startGame;

    /**
     * The constructor for the WaitingController
     * @param mainCtrl An instance of the MainCtrl
     * @param userCommunication
     */
    @Inject
    public WaitingController(MainCtrl mainCtrl, UserSessionCommunication userSessionCommunication, UserCommunication userCommunication) {
        this.mainCtrl = mainCtrl;
        this.userSessionCommunication = userSessionCommunication;
        this.userCommunication = userCommunication;
    }

    /**
     * Method used to set up the WaitingController
     */
    public void setup() {
        clearGrid();
        session = userSessionCommunication.getOpenSession();
        startGame.setDisable(false);
        setTitle(session.getStatus());
        userSessionCommunication.registerForMessages("/topic/session/" + session.getId(), UserSession.class, u -> {
            session =  u;
            Platform.runLater(() -> setTitle(u.getStatus()));
        });
        addPlayers(session);
    }

    /**
     * Method to set the title of the waiting room according to the current userSession's status.
     * @param status - integer type status obtained from received UserSession instance.
     */
    private void setTitle(int status) {
        if (status == 1) {
            roomTitle.setText("Waiting for players");
        } else if (status == 2) {
            updateViewUtils = new UpdateViewUtils(mainCtrl, this, roomTitle);
            startGame.setDisable(true);
            updateViewUtils.setupLobbyTime();
            clearGrid();

        } else {
            roomTitle.setText("Congratulations, you broke the game. Proud of yourself?");
        }
    }

    /**
     * Method called when the exit button is pressed.
     */
    public void exitGame() {
        clearGrid();
        userCommunication.delete(mainCtrl.user);
        mainCtrl.user = userCommunication.resetUser(mainCtrl.user.getUuid());
        mainCtrl.showSplashScene();
    }

    /**
     * Method called when the "Play Game" button is pressed.
     */
    public void startGame() {
        session.setStatus(2);
        userSessionCommunication.send("/app/session/" + session.getId(), session);
    }

    /**
     * Method called when the ? button is pressed.
     */
    public void showTutorial() { mainCtrl.showTutorial(); }

    public void clearGrid() {
        playerGrid.getChildren().clear();
    }

    /**
     * adds all players that have entered the game to the WaitingRoom
     * They are displayed on each section of the grid pane
     * the system is refreshed every second
     *
     * @param session - "Open" UserSession to fetch usernames from.
     */
    public void addPlayers(UserSession session) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            clearGrid();
            try {
                if (session == null) {
                    return;
                }
                List<User> users = userSessionCommunication
                        .getAllUsers(session.getId());
                List<String> usernames = new ArrayList<>();

                for (User user : users) {
                    usernames.add(user.getName());
                }
                Label[][] label = new Label[4][3];
                for (int i = 0; i <= 3; i++) {
                    for (int j = 0; j <= 2; j++) {
                        if (4 * i  + j  >= usernames.size()){
                            label[i][j] = new Label("");
                        } else {
                            label[i][j] = new Label(usernames.get(4 * i + j));
                        }
                        playerGrid.add(label[i][j], i, j);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Method used to refresh the WaitingRoom
     */
    public void refresh() {
        try {
            session = userSessionCommunication
                    .getOpenSession();
            if (session == null) {
                return;
            }

            List<User> users = userSessionCommunication
                    .getAllUsers(session.getId());
            List<String> usernames = new ArrayList<>();

            for (User user : users) {
                usernames.add(user.getName());
            }
            Label[][] label = new Label[4][3];
            for (int i = 0; i <= 3; i++) {
                for (int j = 0; j <= 2; j++) {
                    label[i][j] = new Label(usernames.get(4 * i  + j));
                    playerGrid.add(label[i][j], i, j);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

