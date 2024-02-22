package client.scenes;

import client.utilities.AnswerCommunication;
import client.utilities.QuestionCommunication;
import client.utilities.QuestionUtils;
import client.utilities.ServerCommunication;
import client.utilities.UpdateViewUtils;
import client.utilities.UserCommunication;
import commons.Answer;
import commons.AnswerResponse;
import commons.SingleEntry;
import commons.questions.ComparisonQuestion;
import commons.questions.InsteadOfQuestion;
import commons.questions.Question;
import commons.questions.SimpleQuestionMCQ;
import commons.questions.SimpleQuestionOpen;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.net.URL;

public class QuestionController implements FrontEndController {

    protected final MainCtrl mainCtrl;
    protected final AnswerCommunication answerCommunication;
    protected final QuestionCommunication questionCommunication;
    protected final UserCommunication userCommunication;
    protected QuestionUtils questionUtils;
    protected UpdateViewUtils updateViewUtils;

    protected Question questionFromServer;

    protected int counter;
    protected boolean answered;

    @FXML
    protected Label timer;
    protected double timeLeft;

    @FXML
    protected Label score;

    @FXML
    protected Label question;

    @FXML
    protected ImageView image;

    @FXML
    protected ImageView image1;

    @FXML
    protected ImageView image2;

    @FXML
    protected Button answer1;

    @FXML
    protected Button answer2;

    @FXML
    protected Button answer3;

    @FXML
    protected TextField openAnswer;

    @FXML
    protected Group openQuestionGroup;

    @FXML
    protected VBox MCQuestionGroup;

    @FXML
    protected Label questionNumber;

    @FXML
    protected Label alert;

    /**
     * The constructor for the Question controller
     *
     * @param mainCtrl              An instance of the MainCtrl
     * @param answerCommunication   An instance of AnswerCommunication
     * @param questionCommunication An instance of QuestionCommunication
     * @param userCommunication     An instance of UserCommunication
     */
    @Inject
    public QuestionController(MainCtrl mainCtrl, AnswerCommunication answerCommunication, QuestionCommunication questionCommunication,
                              UserCommunication userCommunication) {
        this.mainCtrl = mainCtrl;
        this.answerCommunication = answerCommunication;
        this.questionCommunication = questionCommunication;
        this.userCommunication = userCommunication;
    }

    /**
     * Method that sets up everything in a question round.
     */
    public void setup() {
        questionUtils = new QuestionUtils(image, image1, image2, answer1, answer2, answer3);
        updateViewUtils = new UpdateViewUtils(mainCtrl, this, timer);
        setQuestionNumber();
        setQuestion();
        setScore();
    }

    /**
     * return user to the second half of the game
     */
    public void reset() {
        questionUtils = new QuestionUtils(image, image1, image2, answer1, answer2, answer3);
        updateViewUtils = new UpdateViewUtils(mainCtrl, this, timer);
        resetQuestionNumber();
        setQuestion();
        setScore();
    }

    /**
     * Sets the number of the question that appears on the scene to 1.
     */
    public void resetQuestionNumber() {
        counter = 10;
        incrementCounter();
    }

    /**
     * Setter for the score.
     */
    public void setScore() {
        score.setText("Score: " + mainCtrl.user.getScore());
    }

    /**
     * Increments the question number that appears on the scene.
     */
    public void incrementCounter() {
        counter++;
        questionNumber.setText(counter + "/20");
    }

    /**
     * Sets the number of the question that appears on the scene to 1.
     */
    public void setQuestionNumber() {
        counter = 0;
        incrementCounter();
    }

    /**
     * Sets up the question screen for a multiple-choice question by removing the text field and adding possible answers.
     */
    public void setMCQ() {
        openQuestionGroup.setDisable(true);
        openQuestionGroup.setVisible(false);
        MCQuestionGroup.setDisable(false);
        MCQuestionGroup.setVisible(true);
    }

    /**
     * Sets up the question screen for an open question by removing possible answers and adding text field.
     */
    public void setOpen() {
        image1.setVisible(false);
        image2.setVisible(false);
        openQuestionGroup.setDisable(false);
        openQuestionGroup.setVisible(true);
        MCQuestionGroup.setDisable(true);
        MCQuestionGroup.setVisible(false);
        openAnswer.setText("");
        openAnswer.setStyle("-fx-background-color: white");
        openAnswer.setStyle("-fx-border-color: white");
        openAnswer.requestFocus();
    }

    /**
     * Sends the user to the main screen.
     */
    public void exitGame() {
        userCommunication.delete(mainCtrl.user);
        mainCtrl.user = userCommunication.resetUser(mainCtrl.user.getUuid());
        mainCtrl.showSplashScene();
        //mainCtrl.user.setScore(0);
    }

    /**
     * Method that sets the scene for when the client either submits an answer or the timer is up.
     * It disables answers and start the waiting timer.
     */
    public void questionFinished() {
        if (questionFromServer instanceof SimpleQuestionMCQ) {
            questionUtils.setColorAnswer(((SimpleQuestionMCQ) questionFromServer).getCorrectIndex());
        } else if (questionFromServer instanceof ComparisonQuestion) {
            questionUtils.setColorAnswer(((ComparisonQuestion) questionFromServer).getCorrectIndex());
        } else if (questionFromServer instanceof InsteadOfQuestion) {
            questionUtils.setColorAnswer(((InsteadOfQuestion) questionFromServer).getCorrectIndex());
        } else if (questionFromServer instanceof SimpleQuestionOpen) {
            setCorrectAnswerOpen();
        }

        if (!answered) {
            enableAnswers(false);
        }
        answered = false;

        setScore();
        updateViewUtils.getTimerUtils().cancel();
        if (counter == 20) {
            SingleEntry singleEntry = new SingleEntry(mainCtrl.user.getUuid(), mainCtrl.user.getName(), mainCtrl.user.getScore());
            userCommunication.addSingle(singleEntry);
            updateViewUtils.setupFinishedGameTime();
        } else {
            updateViewUtils.setupWaitingTime();
        }
    }

    /**
     * Method that is used to display the next question, it sets up the image, text, answers, timer and counter.
     */
    public void nextQuestion() {
        incrementCounter();
        updateViewUtils.setupQuestionTime();
        setQuestion();
    }

    /**
     * Setter for the question. Displays the question text, image, and possible answers.
     */
    public void setQuestion() {
        questionFromServer = questionCommunication.getQuestion();
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
     * Send the chosen answer to the AnswerController REST API
     *
     * @param evt The click event
     */
    public void sendAnswer(Event evt) {
        answered = true;
        enableAnswers(false);
        questionUtils.setChosenStyle((Button) evt.getSource());

        Answer answer;

        if (evt.getSource().equals(answer1)) {
            answer = new Answer(mainCtrl.user.getUuid(), 0, timeLeft, false);
        } else if (evt.getSource().equals(answer2)) {
            answer = new Answer(mainCtrl.user.getUuid(), 1, timeLeft, false);
        } else {
            answer = new Answer(mainCtrl.user.getUuid(), 2, timeLeft, false);
        }
        AnswerResponse response = answerCommunication.sendAnswer(answer);
        mainCtrl.user.setScore(response.getNewScore());
        questionFinished();
    }

    /**
     * Method that is called when the user submits the open answer.
     */
    public void sendOpenAnswer() {
        String answerInput = openAnswer.getText();
        boolean isNumber = answerInput.matches("[0-9]+");
        if (!isNumber) {
            setAlert();
        } else {
            answered = true;
            enableAnswers(false);
            clearAlert();

            Answer answer = new Answer(mainCtrl.user.getUuid(), Integer.parseInt(openAnswer.getText()), timeLeft, false);
            AnswerResponse response = answerCommunication.sendAnswer(answer);
            mainCtrl.user.setScore(response.getNewScore());
            questionFinished();
            setOpenColorAnswer(response.isCorrect());
        }
    }

    /**
     * Method that is called when the user presses enter in the open answer box
     */
    public void sendOpenAnswerEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            sendOpenAnswer();
        }
    }

    /**
     * Method that enables the answers, so they can be submitted.
     */
    public void enableAnswers(boolean enable) {
        double opacity;
        if (enable) {
            opacity = 1;
        } else {
            opacity = 0.5;
        }
        answer1.setOpacity(opacity);
        answer2.setOpacity(opacity);
        answer3.setOpacity(opacity);
        answer1.setDisable(!enable);
        answer2.setDisable(!enable);
        answer3.setDisable(!enable);
        openQuestionGroup.setDisable(!enable);
    }

    /**
     * Method used to display an alert when there is wrong input.
     */
    public void setAlert() {
        alert.setText("Enter a number!");
        alert.setTextFill(Paint.valueOf("#ff0000"));
    }

    /**
     * Method used to clear the alert.
     */
    public void clearAlert() {
        alert.setText("");
    }

    /**
     * Method used to display the correct answer of an open-ended question.
     */
    public void setCorrectAnswerOpen() {
        SimpleQuestionOpen simpleQuestionOpen = (SimpleQuestionOpen) questionFromServer;
        String text = "The answer is ";
        alert.setText(text + simpleQuestionOpen.getPreciseAnswer());
        alert.setTextFill(Paint.valueOf("#ffffff"));
    }

    /**
     * Sets the color of the open answers showing if the user responded correctly or not.
     *
     * @param isCorrect Whether the answer is correct
     */
    public void setOpenColorAnswer(boolean isCorrect) {
        String color = (isCorrect) ? "#66e1b0" : "#ff0033";
        openAnswer.setStyle("-fx-background-color: " + color + ";-fx-border-color: " + color);
    }

    /**
     * Setter for the timeLeft field
     *
     * @param timeLeft The new timeLeft value
     */
    public void setTimeLeft(double timeLeft) {
        this.timeLeft = timeLeft;
    }
}
