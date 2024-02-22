package client.scenes;

import client.utilities.EmojiCommunication;
import client.utilities.GridUtils;
import client.utilities.SingleCommunication;
import client.utilities.UserCommunication;
import client.utilities.UserSessionCommunication;
import commons.Emoji;
import commons.User;
import commons.UserSession;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FinalController implements FrontEndController {

    private final MainCtrl mainCtrl;
    private final UserSessionCommunication userSessionCommunication;
    private final EmojiCommunication emojiCommunication;
    private final GridUtils gridUtils;
    private final UserCommunication userCommunication;
    private final SingleCommunication singleCommunication;
    private UserSession userSession;

    @FXML
    private GridPane scoreGrid;

    @FXML
    private Button emoji1;

    @FXML
    private Button emoji2;

    @FXML
    private Button emoji3;

    @FXML
    private Button emoji4;

    @FXML
    private Button emoji5;

    @FXML
    private Button emoji6;

    @FXML
    private ImageView emojiWindow;

    @FXML
    private Text winnerBox;

    private Timer timerEmoji;

    /**
     * The constructor for the FinalController
     * @param mainCtrl An instance of the MainCtrl
     * @param userSessionCommunication An instance of UserSessionCommunication
     * @param emojiCommunication An instance for EmojiCommunication
     * @param gridUtils An instance of GridUtils
     * @param userCommunication An instance of UserCommunication
     * @param singleCommunication
     */
    @Inject
    public FinalController(MainCtrl mainCtrl, UserSessionCommunication userSessionCommunication, EmojiCommunication emojiCommunication, GridUtils gridUtils, UserCommunication userCommunication, SingleCommunication singleCommunication) {
        this.mainCtrl = mainCtrl;
        this.userSessionCommunication = userSessionCommunication;
        this.emojiCommunication = emojiCommunication;
        this.gridUtils = gridUtils;
        this.userCommunication = userCommunication;
        this.singleCommunication = singleCommunication;
    }

    /**
     * Sets up all necessary steps for this scene to run
     */
    public void setup() {
        userSession = SplashController.userSession;
        if (mainCtrl.user.getSessionId() == null) {
            //SingleEntry single = new SingleEntry(mainCtrl.user.getUuid(), mainCtrl.user.getName(), mainCtrl.user.getScore());
            //SingleEntry singleEntry = new SingleEntry(2L, "test", 100);
            //singleCommunication.addUser(singleEntry);
            createSingleLeaderBoard();
        } else {
            createLeaderBoard();
        }
        emojiCommunication.registerForMessages("/topic/emojis/" + mainCtrl.user.getSessionId(), Emoji.class, this::displayEmoji);
    }

    /**
     * gets all users in single session and adds them to the leaderboard
     */
    public void createSingleLeaderBoard() {
        try {
            List<User> users = userSessionCommunication.getAllSingles();
            List<User> sorted = new ArrayList<>();
            List<Integer> scores = new ArrayList<>();
            int size = users.size();

            for (User user : users) {
                scores.add(user.getScore());
            }

            for (int i = 0; i < size; i++) {
                int max = scores.indexOf(Collections.max(scores));
                sorted.add(users.get(max));
                users.remove(max);
                scores.remove(max);
            }

            gridUtils.addToGrid(sorted, scoreGrid);
            winnerBox.setText("And the winner is " + sorted.get(0).getName() + "!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Uses the usersession to create a leaderboard of all top players
     */
    public void createLeaderBoard() {
        try {
            gridUtils.fillLeaderboard(userSessionCommunication, userSession, scoreGrid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method called when user presses on an emoji.
     * @param evt The click event
     */
    public void sendEmoji(Event evt) {
        String dest = "/app/emojis/" + mainCtrl.user.getSessionId();
        String file = null;
        if (evt.getSource().equals(emoji1)) {
            file = "emoji1.png";
        } else if (evt.getSource().equals(emoji2)) {
            file = "emoji2.png";
        } else if (evt.getSource().equals(emoji3)) {
            file = "emoji3.png";
        } else if (evt.getSource().equals(emoji4)) {
            file = "emoji4.png";
        } else if (evt.getSource().equals(emoji5)) {
            file = "emoji5.png";
        } else if (evt.getSource().equals(emoji6)) {
            file = "emoji6.png";
        }
        emojiCommunication.send(dest, file);
    }

    /**
     * Method called when user receives an emoji.
     *
     * @param emoji Emoji instance to display on the scene
     */
    public void displayEmoji(Emoji emoji) {
        Image img = new Image("images/" + emoji.getImgPath());
        this.emojiWindow.setImage(img);
        this.emojiWindow.fitWidthProperty();
        if (timerEmoji != null) {
            timerEmoji.cancel();
        }
        timerEmoji = new Timer();
        timerEmoji.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> emojiWindow.setImage(null));
                timerEmoji.cancel();
            }
        }, 1000);
    }

    /**
     * Method which is called to exit the game
     */
    public void exitGame() {
        if (SplashController.singleSession == null) {
            userCommunication.delete(mainCtrl.user);
        }
        mainCtrl.user = userCommunication.resetUser(mainCtrl.user.getUuid());
        mainCtrl.showSplashScene();
    }
}
