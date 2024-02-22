package client.scenes;

import client.utilities.EmojiCommunication;
import client.utilities.GridUtils;
import client.utilities.UpdateViewUtils;
import client.utilities.UserCommunication;
import client.utilities.UserSessionCommunication;
import commons.Emoji;
import commons.UserSession;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import javax.inject.Inject;
import java.util.Timer;
import java.util.TimerTask;

public class HalftimeController implements FrontEndController {

    private final MainCtrl mainCtrl;
    private final UserSessionCommunication userSessionCommunication;
    private final EmojiCommunication emojiCommunication;
    private final GridUtils gridUtils;
    private UserSession userSession;
    private UpdateViewUtils updateViewUtils;
    private final UserCommunication userCommunication;

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
    private Label timer;

    private Timer timerEmoji;

    /**
     * The constructor for the HalftimeController
     * @param mainCtrl An instance of the MainCtrl
     * @param userSessionCommunication An instance of UserSessionCommunication
     * @param emojiCommunication An instance for EmojiCommunication
     * @param gridUtils An instance of GridUtils
     * @param userCommunication An instance of UserCommunication
     */
    @Inject
    public HalftimeController(MainCtrl mainCtrl, UserSessionCommunication userSessionCommunication, EmojiCommunication emojiCommunication, GridUtils gridUtils, UserCommunication userCommunication) {
        this.mainCtrl = mainCtrl;
        this.userSessionCommunication = userSessionCommunication;
        this.emojiCommunication = emojiCommunication;
        this.gridUtils = gridUtils;
        this.userCommunication = userCommunication;
    }

    /**
     * Method used to set up the HalfTimeController
     */
    public void setup() {
        userSession = SplashController.userSession;
        updateViewUtils = new UpdateViewUtils(mainCtrl, this, timer);
        createLeaderBoard();
        emojiCommunication.registerForMessages("/topic/emojis/" + mainCtrl.user.getSessionId(), Emoji.class, this::displayEmoji);
    }

    /**
     * Method that creates a leaderboard of all top players who are part of the userSession
     */
    public void createLeaderBoard() {
        try {
            gridUtils.fillLeaderboard(userSessionCommunication, userSession, scoreGrid);
            updateViewUtils.setupHalftimeTime();
//            mainCtrl.returnQuestionScreenMP();
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
     * Method which is run to exit the game
     */
    public void exitGame() {
        userCommunication.delete(mainCtrl.user);
        mainCtrl.user = userCommunication.resetUser(mainCtrl.user.getUuid());
        mainCtrl.showSplashScene();
        //mainCtrl.user.setScore(0);
    }
}
