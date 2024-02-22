package client.scenes;

import client.utilities.AnswerCommunication;
import client.utilities.EmojiCommunication;
import client.utilities.JokerCommunication;
import client.utilities.JokerUtils;
import client.utilities.QuestionCommunication;
import client.utilities.ServerCommunication;
import client.utilities.UserCommunication;
import commons.Answer;
import commons.AnswerResponse;
import commons.Emoji;
import commons.jokers.TimeJoker;
import commons.questions.ComparisonQuestion;
import commons.questions.InsteadOfQuestion;
import commons.questions.Question;
import commons.questions.SimpleQuestionMCQ;
import commons.questions.SimpleQuestionOpen;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.util.Duration;
import javax.inject.Inject;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class QuestionControllerMP extends QuestionController implements FrontEndController {

    private final EmojiCommunication emojiCommunication;
    private final JokerUtils jokerUtils;
    private final JokerCommunication jokerCommunication;

    @FXML
    private Button joker1;

    @FXML
    private Button joker2;

    @FXML
    private Button joker3;

    @FXML
    private ImageView jokerImage1;

    @FXML
    private ImageView jokerImage2;

    @FXML
    private ImageView jokerImage3;

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
    private Timer timerEmoji;

    private boolean openAnswerCorrect;

    /**
     * The constructor for the Question controller
     *
     * @param mainCtrl              An instance of the MainCtrl
     * @param answerCommunication   An instance of AnswerCommunication
     * @param questionCommunication An instance of QuestionCommunication
     * @param userCommunication     An instance of UserCommunication
     * @param emojiCommunication    An instance of EmojiCommunication
     * @param jokerUtils            An instance of JokerUtils
     * @param jokerCommunication    An instance of JokerCommunication
     */
    @Inject
    public QuestionControllerMP(MainCtrl mainCtrl, AnswerCommunication answerCommunication, QuestionCommunication questionCommunication,
                                UserCommunication userCommunication, EmojiCommunication emojiCommunication, JokerUtils jokerUtils,
                                JokerCommunication jokerCommunication) {
        super(mainCtrl, answerCommunication, questionCommunication, userCommunication);
        this.emojiCommunication = emojiCommunication;
        this.jokerUtils = jokerUtils;
        this.jokerCommunication = jokerCommunication;
    }


    /**
     * Method that sets up everything in a question round.
     */
    @Override
    public void setup() {
        super.setup();
        emojiWindow.setImage(null);
        emojiCommunication.registerForMessages("/topic/emojis/" + mainCtrl.user.getSessionId(), Emoji.class, this::displayEmoji);
        jokerCommunication.registerForMessages("/topic/joker/" + mainCtrl.user.getSessionId(), TimeJoker.class, timeJoker ->
                jokerUtils.decreaseTime(timeJoker.isDecreaseTime(), updateViewUtils.getTimerUtils()));
        jokerUtils.resetAllJoker(joker1, joker2, joker3, jokerImage1, jokerImage2, jokerImage3);
    }

    /**
     * Sets up the question screen for a multiple-choice question by removing the text field and adding possible answers.
     */
    @Override
    public void setMCQ() {
        super.setMCQ();
        jokerUtils.resetAnswerVisible(answer1, answer2, answer3);
        setJokers();
    }

    /**
     * Sets up the question screen for an open question by removing possible answers and adding text field.
     */
    @Override
    public void setOpen() {
        super.setOpen();
        setJokers();
    }

    /**
     * Sets up the question screen to show the jokers correctly
     */
    private void setJokers() {
        jokerUtils.getDpJoker().setDoublePoint(false);
        jokerUtils.getTimeJoker().setDecreaseTime(false);
        if (!jokerUtils.getRmJoker().isUsed()) {
            jokerUtils.setButtonDisable(joker2, false);
        }
        if (!jokerUtils.getDpJoker().isUsed()) {
            jokerUtils.setButtonDisable(joker1, false);
        }
        if (!jokerUtils.getTimeJoker().isUsed()) {
            jokerUtils.setButtonDisable(joker3, false);
        }
    }

    /**
     * Sends the user to the main screen.
     */
    @Override
    public void exitGame() {
        userCommunication.delete(mainCtrl.user);
        mainCtrl.user = userCommunication.resetUser(mainCtrl.user.getUuid());
        mainCtrl.showSplashScene();
    }

    /**
     * Setter for the question. Displays the question text, image, and possible answers.
     */
    @Override
    public void setQuestion() {
        questionFromServer = questionCommunication.getQuestion(mainCtrl.user.getSessionId(), counter);
        Question question = questionFromServer;

        this.image1.setImage(null);
        this.image2.setImage(null);

        enableAnswers(true);
        this.question.setText(question.getQuestionText());
        try {
            Image img = questionUtils.imageFromUrl(new URL(ServerCommunication.SERVER + "images/" + question.getImage_path()));
            this.image.setImage(img);
        } catch (MalformedURLException e) {
            System.err.println(e.getMessage());
        }
        this.image.fitWidthProperty();

        if (question instanceof SimpleQuestionOpen) {
            clearAlert();
            setOpen();
        } else {
            setMCQ();
            questionUtils.setQuestionTypeMCQ(question);
        }
    }

    /**
     * Method that sets the scene for when the client either submits an answer or the timer is up.
     * It disables answers and start the waiting timer.
     */
    @Override
    public void questionFinished() {
        if (questionFromServer instanceof SimpleQuestionMCQ) {
            questionUtils.setColorAnswer(((SimpleQuestionMCQ) questionFromServer).getCorrectIndex());
        } else if (questionFromServer instanceof ComparisonQuestion) {
            questionUtils.setColorAnswer(((ComparisonQuestion) questionFromServer).getCorrectIndex());
        } else if (questionFromServer instanceof InsteadOfQuestion) {
            questionUtils.setColorAnswer(((InsteadOfQuestion) questionFromServer).getCorrectIndex());
        } else if (questionFromServer instanceof SimpleQuestionOpen) {
            setCorrectAnswerOpen();
            setOpenColorAnswer(openAnswerCorrect);
        }

        if (!answered) {
            enableAnswers(false);
        }
        answered = false;

        setScore();
        updateViewUtils.getTimerUtils().cancel();
        if (counter == 20) {
            updateViewUtils.setupFinalLeaderboardTime();
        } else if (counter == 10) {
            updateViewUtils.setupPreHalftimeTime();
        } else {
            updateViewUtils.setupWaitingTime();
        }
    }

    /**
     * Send the chosen answer to the AnswerController REST API
     *
     * @param evt The click event
     */
    @Override
    public void sendAnswer(Event evt) {
        answered = true;
        enableAnswers(false);
        questionUtils.setChosenStyle((Button) evt.getSource());

        if (!joker2.isDisabled()) {
            jokerUtils.setButtonDisable(joker2, true);
        }

        Answer answer;
        if (evt.getSource().equals(answer1)) {
            answer = new Answer(mainCtrl.user.getUuid(), 0, timeLeft, jokerUtils.getDpJoker().isDoublePoint());
        } else if (evt.getSource().equals(answer2)) {
            answer = new Answer(mainCtrl.user.getUuid(), 1, timeLeft, jokerUtils.getDpJoker().isDoublePoint());
        } else {
            answer = new Answer(mainCtrl.user.getUuid(), 2, timeLeft, jokerUtils.getDpJoker().isDoublePoint());
        }

        AnswerResponse response = answerCommunication.sendAnswer(answer);
        mainCtrl.user.setScore(response.getNewScore());
    }

    /**
     * Method that is called when the user submits the open answer.
     */
    @Override
    public void sendOpenAnswer() {
        String answerInput = openAnswer.getText();
        boolean isNumber = answerInput.matches("[0-9]+");
        if (!isNumber) {
            setAlert();
        } else {
            answered = true;
            enableAnswers(false);
            clearAlert();

            Answer answer = new Answer(mainCtrl.user.getUuid(), Integer.parseInt(openAnswer.getText()), timeLeft, jokerUtils.getDpJoker().isDoublePoint());
            AnswerResponse response = answerCommunication.sendAnswer(answer);
            mainCtrl.user.setScore(response.getNewScore());
            openAnswerCorrect = response.isCorrect();
        }
    }

    /**
     * Method that enables the double point joker, so the user can get double points for the current question.
     * The first joker button is associated with the double point function.
     * The joker is disabled after it has been used once.
     */
    public void setPointDouble() {
        jokerUtils.setPointDouble(joker1, true, true, jokerImage1);
    }

    /**
     * Method that enables the remove-answer joker, so the user can remove one incorrect answer from the current question.
     * The second joker button is associated with the remove-answer function.
     * The joker is disabled after it has been used once.
     */
    public void removeOneAnswer() {
        int index = jokerUtils.getRmJoker().getRemoveIndex(questionFromServer);
        jokerUtils.removeSpecificAnswer(index, answer1, answer2, answer3, joker2, jokerImage2);
    }

    /**
     * Setter for the timeLeft field
     *
     * @param timeLeft The new timeLeft value
     */
    public void setTimeLeft(double timeLeft) {
        this.timeLeft = timeLeft;
    }

    /**
     * Method called when user presses on an emoji.
     *
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
     * Method that enables the answers, so they can be submitted.
     */
    @Override
    public void enableAnswers(boolean enable) {
        super.enableAnswers(enable);
        if (!enable) {
            joker1.setDisable(true);
            joker2.setDisable(true);
            joker3.setDisable(true);
        }
    }

    /**
     * Method called when user receives an emoji.
     *
     * @param emoji Emoji instance to display on the scene
     */
    public void displayEmoji(Emoji emoji) {
        Image img = new Image("images/" + emoji.getImgPath());

        emojiWindow.setOpacity(1);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(200));
        fadeTransition.setNode(emojiWindow);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);

        this.emojiWindow.setImage(img);
        this.emojiWindow.fitWidthProperty();
        if (timerEmoji != null) {
            timerEmoji.cancel();
        }
        timerEmoji = new Timer();
        timerEmoji.schedule(new TimerTask() {
            @Override
            public void run() {
                fadeTransition.play();
                fadeTransition.setOnFinished(event -> {
                    Platform.runLater(() -> emojiWindow.setImage(null));
                    timerEmoji.cancel();
            });

            }
        }, 1000);
    }

    /**
     * Method that enables the time-decrease joker, so the time of the user who didn't use the joker will be decreased.
     * The third joker button is associated with the time-decrease function.
     * The joker is disabled after it has been used once.
     */
    public void useTimeJoker() {
        System.out.println("TimeJoker IS USING!");
        jokerUtils.setTimeDecrease(joker3, jokerImage3);
        jokerCommunication.send("/app/joker/" + mainCtrl.user.getSessionId(), jokerUtils.getTimeJoker());
        System.out.println("Send!");
    }

}
