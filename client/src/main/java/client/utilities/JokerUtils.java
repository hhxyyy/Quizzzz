package client.utilities;

import commons.jokers.DoublePointJoker;
import commons.jokers.RemoveAnswerJoker;
import commons.jokers.TimeJoker;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class JokerUtils {

    private final String redJoker = "-fx-border-color: red;" + "-fx-text-fill: red;";
    private final String whiteJoker = "-fx-border-color: white;" + "-fx-text-fill: white;";

    private final Image doublePointWhite = new Image("images/doubleWhite.png");
    private final Image doublePointRed = new Image("images/doubleRed.png");
    private final Image removeWhite = new Image("images/removeWhite.png");
    private final Image removeRed = new Image("images/removeRed.png");
    private final Image timeWhite = new Image("images/timeWhite.png");
    private final Image timeRed = new Image("images/timeRed.png");

    private DoublePointJoker dpJoker;
    private RemoveAnswerJoker rmJoker;
    private TimeJoker timeJoker;

    /**
     * The constructor for the JokerUtils class.
     * Generate three different jokers for each game.
     */
    public JokerUtils() {
        this.dpJoker = new DoublePointJoker();
        this.rmJoker = new RemoveAnswerJoker();
        this.timeJoker = new TimeJoker();
    }

    /**
     * Setter for the Joker button, which change the colour style of the button.
     * for example, turn red after click the joker button.
     *
     * @param joker The joker button that need to be set.
     */
    public void setJokerRed(Button joker) {
        joker.setStyle(redJoker);
    }

    /**
     * Setter for the Joker button, which change the colour style of the button.
     * for example, turn white when a new game starts.
     *
     * @param joker The joker button that need to be set.
     */
    public void setJokerWhite(Button joker) {
        joker.setStyle(whiteJoker);
    }

    /**
     * Setter for the button, which change the ability of the button.
     * for example, the button is disabled after clicking.
     *
     * @param button The button that need to be set.
     * @param disable The status that need to be set to the button.
     */
    public void setButtonDisable(Button button, boolean disable) {
        button.setDisable(disable);
    }

    /**
     * Setter for the button, which change the visibility of the button.
     * for example, the button is invisible after the remove-answer joker removed the button.
     *
     * @param button The button that need to be set.
     * @param disable The status that need to be set to the button.
     */
    public void setButtonVisible(Button button, boolean disable) {
        button.setVisible(disable);
    }

    /**
     * Setter that reset the default status of all jokers.
     * Setter that reset the default style of all jokers.
     */
    public void resetAllJoker(Button joker1, Button joker2, Button joker3, ImageView jokerImage1, ImageView jokerImage2, ImageView jokerImage3) {
        setButtonDisable(joker1, false);
        setButtonDisable(joker2, false);
        setButtonDisable(joker3, false);
        jokerImage1.setImage(doublePointWhite);
        jokerImage2.setImage(removeWhite);
        jokerImage3.setImage(timeWhite);

    }

    /**
     * Setter that reset the default visible style of the answer button.
     * Especially after using the remove-answer joker.
     */
    public void resetAnswerVisible(Button answer1, Button answer2, Button answer3) {
        setButtonVisible(answer1, true);
        setButtonVisible(answer2, true);
        setButtonVisible(answer3, true);
    }

    /**
     * Method that enables the remove-answer joker, so the user can remove one incorrect answer from the current question.
     * The second joker button is associated with the remove-answer function.
     * The joker is disabled and turn red after it has been used once.
     *
     * @param index The index of the button which need to be removed.
     * @param answer1 The button that might be removed.
     * @param answer2 The button that might be removed.
     * @param answer3 The button that might be removed.
     * @param joker2 The joker button that should be disabled.
     * @param jokerImage2 The image set to the jokerbutton.
     */
    public void removeSpecificAnswer(int index, Button answer1, Button answer2, Button answer3, Button joker2, ImageView jokerImage2) {
        if (index < 0) { return; }
        switch (index) {
            case 0 -> {
                setButtonDisable(answer1, true);
                setButtonVisible(answer1, false);
                System.out.println("Remove 1");
            }
            case 1 -> {
                setButtonDisable(answer2, true);
                setButtonVisible(answer2, false);
                System.out.println("Remove 2");
            }
            case 2 -> {
                setButtonDisable(answer3, true);
                setButtonVisible(answer3, false);
                System.out.println("Remove 3");
            }
        }

        setButtonDisable(joker2, true);
        jokerImage2.setImage(removeRed);
        this.rmJoker.setRemoveAnswer(true);
        this.rmJoker.setUsed(true);
    }

    /**
     * Method that enables the double point joker, so the user can get double points for the current question.
     * The first joker button is associated with the double point function.
     * The joker is disabled and turn red after it has been used once.
     *
     * @param joker1 The joker button that should be disabled.
     * @param pointDouble The status of whether the point should be doubled.
     * @param disable The status of whether the button should be disabled.
     * @param jokerImage1 The image set to the jokerbutton.
     */
    public void setPointDouble(Button joker1, boolean pointDouble, boolean disable, ImageView jokerImage1) {
        this.dpJoker.setDoublePoint(pointDouble);
        setButtonDisable(joker1, disable);
        jokerImage1.setImage(doublePointRed);
        this.dpJoker.setUsed(true);
    }

    /**
     * Method that decrease the current time of the current question.
     * For example, the time-decrease joker is used by other player while you didn't.
     *
     * @param decreaseTime The status of whether the time should be decreased.
     * @param timerUtils The timerUtils of the current question.
     */
    public void decreaseTime(boolean decreaseTime, TimerUtils timerUtils) {
        if (decreaseTime && !this.timeJoker.isDecreaseTime()) {
            timerUtils.setDecreaseTime(true);
        }
    }

    /**
     * Method that enables the time-decrease joker, so the user can get decrease the time of other player.
     * The third joker button is associated with the time-decrease function.
     * The joker is disabled and turn red after it has been used once.
     *
     * @param joker3 The joker button that should be disabled.
     * @param jokerImage3 The image set to the jokerbutton.
     */
    public void setTimeDecrease(Button joker3, ImageView jokerImage3) {
        setButtonDisable(joker3, true);
        jokerImage3.setImage(timeRed);
        this.timeJoker.setUsed(true);
        this.timeJoker.setDecreaseTime(true);


    }

    /**
     * Getter for the DoublePointJoker field
     * @return The DoublePointJoker field
     */
    public DoublePointJoker getDpJoker() {
        return dpJoker;
    }

    /**
     * Getter for the RemoveAnswerJoker field
     * @return The RemoveAnswerJoker field
     */
    public RemoveAnswerJoker getRmJoker() {
        return rmJoker;
    }

    /**
     * Getter for the TimeJoker field
     * @return The TimeJoker field
     */
    public TimeJoker getTimeJoker() {
        return timeJoker;
    }
}
