package client.utilities;

import client.scenes.HalftimeController;
import client.scenes.MainCtrl;
import client.scenes.QuestionController;
import client.scenes.WaitingController;
import javafx.scene.control.Label;

public class UpdateViewUtils {
    private MainCtrl mainCtrl;
    private QuestionController questionController;
    private HalftimeController halftimeController;
    private WaitingController waitingController;
    private TimerUtils timerUtils;
    private final Label timer;

    /**
     * Constructor for UpdateViewUtils for use in QuestionControllers
     *
     * @param mainCtrl           - MainCtrl to use methods from.
     * @param questionController - Controller being used.
     * @param timer              - Label to fill in.
     */
    public UpdateViewUtils(MainCtrl mainCtrl, QuestionController questionController, Label timer) {
        this.mainCtrl = mainCtrl;
        this.questionController = questionController;
        this.timer = timer;
        setupQuestionTime();
    }

    /**
     * Constructor for UpdateViewUtils for use in QuestionControllers
     *
     * @param mainCtrl            - MainCtrl to use methods from.
     * @param halftimeController1 - Controller being used.
     * @param timer               - Label to fill in.
     */
    public UpdateViewUtils(MainCtrl mainCtrl, HalftimeController halftimeController1, Label timer) {
        this.mainCtrl = mainCtrl;
        this.halftimeController = halftimeController1;
        this.timer = timer;
        setupHalftimeTime();
    }


    /**
     * Constructor for UpdateViewUtils for use in WaitingControllers
     *
     * @param mainCtrl          - MainCtrl to use methods from.
     * @param waitingController - Controller being used.
     * @param timer             - Label to fill in.
     */
    public UpdateViewUtils(MainCtrl mainCtrl, WaitingController waitingController, Label timer) {
        this.mainCtrl = mainCtrl;
        this.waitingController = waitingController;
        this.timer = timer;
        setupLobbyTime();
    }

    /**
     * Sets up the timer by resetting the time text in the scene and initialising and running our TimerUtils class.
     */
    public void setupQuestionTime() {
        if (timerUtils != null) {
            timerUtils.cancel();
        }
        timerUtils = new TimerUtils(mainCtrl, this, 10);
        timerUtils.startQuestionTimer();
    }

    /**
     * Sets up the timer by resetting the time text in the scene and initialising and running our TimerUtils class.
     */
    public void setupWaitingTime() {
        if (timerUtils != null) {
            timerUtils.cancel();
        }
        timerUtils = new TimerUtils(mainCtrl, this, 2);
        timerUtils.startWaitingTimer();
    }

    /**
     * Sets up the timer by resetting the time text in the scene and initialising and running our TimerUtils class.
     */
    public void setupHalftimeTime() {
        if (timerUtils != null) {
            timerUtils.cancel();
        }
        timerUtils = new TimerUtils(mainCtrl, this, 5);
        timerUtils.startHalftimeTimer();
    }

    /**
     * Sets up the timer by resetting the time text in the scene and initialising and running our TimerUtils class.
     */
    public void setupPreHalftimeTime() {
        if (timerUtils != null) {
            timerUtils.cancel();
        }
        timerUtils = new TimerUtils(mainCtrl, this, 3);
        timerUtils.startPreHalftimeTimer();
    }

    /**
     * Sets up the timer by resetting the time text in the scene and initialising and running our TimerUtils class.
     */
    public void setupFinishedGameTime() {
        if (timerUtils != null) {
            timerUtils.cancel();
        }
        timerUtils = new TimerUtils(mainCtrl, this, 2);
        timerUtils.startFinishedGameTimer();
    }

    /**
     * Sets up the timer by resetting the time text in the scene and initialising and running our TimerUtils class.
     */
    public void setupFinalLeaderboardTime() {
        if (timerUtils != null) {
            timerUtils.cancel();
        }
        timerUtils = new TimerUtils(mainCtrl, this, 2);
        timerUtils.startFinalLeaderboardTimer();
    }

    /**
     * Sets up the timer by resetting the time text in the scene and initialising and running our TimerUtils class.
     */
    public void setupLobbyTime() {
        if (timerUtils != null) {
            timerUtils.cancel();
        }
        timerUtils = new TimerUtils(mainCtrl, this, 5);
        timerUtils.startLobbyTimer();
    }

    /**
     * Setter for the timer, changes to timer to the one sent as a parameter.
     *
     * @param seconds Time left which will be displayed at the top of the scene.
     */
    public void setQuestionTimer(double seconds) {
        timer.setText("TIME REMAINING: " + seconds + "s");
        questionController.setTimeLeft(seconds);
    }

    /**
     * Setter for the waiting timer, changes to timer to the one sent as a parameter.
     *
     * @param seconds Time left which will be displayed at the top of the scene.
     */
    public void setWaitingTimer(double seconds) {
        timer.setText("NEXT QUESTION IN... " + seconds + "s");
    }

    /**
     * Setter for the finished game timer, changes to timer to the one sent as a parameter.
     *
     * @param seconds Time left which will be displayed at the top of the scene.
     */
    public void setFinishedGameTimer(double seconds) {
        timer.setText("RETURNING TO MAIN MENU IN... " + seconds + "s");
    }

    /**
     * Setter for the final leaderboard timer, changes to timer to the one sent as a parameter.
     *
     * @param seconds Time left which will be displayed at the top of the scene.
     */
    public void setFinalLeaderboardTimer(double seconds) {
        timer.setText("FINAL LEADERBOARD IN ... " + seconds + "s");
    }

    /**
     * Setter for the halftime controller, changes the timer
     *
     * @param seconds Time left
     */
    public void setHalftimeTimer(double seconds) {
        timer.setText(seconds + " s");
    }

    /**
     * Setter for the halftime controller, changes the timer
     *
     * @param seconds Time left
     */
    public void setPreHalftimeTimer(double seconds) {
        timer.setText("HALF TIME LEADERBOARD IN... " + seconds + " s");
    }

    /**
     * Setter for the lobby timer, changes to timer to the one sent as a parameter.
     *
     * @param seconds Time left which will be displayed at the top of the scene.
     */
    public void setLobbyTimer(double seconds) {
        timer.setText("GAME STARTS IN... " + seconds);
    }

    /**
     * Method called when the timer is done.
     */
    public void timerFinished() {
        questionController.questionFinished();
    }

    /**
     * Method called when the waiting timer is done.
     */
    public void waitingTimerFinished() {
        questionController.nextQuestion();
    }

    /**
     * Method called when the halftime timer is done.
     */
    public void halftimeTimerFinished() {
        mainCtrl.returnQuestionScreenMP();
    }

    /**
     * Method called when the pre-halftime timer is done.
     */
    public void preHalftimeTimerFinished() {
        mainCtrl.showHalftimeLeaderboard();
    }

    /**
     * Method called when the finished game timer is done.
     */
    public void finishedGameTimerFinished() {
        //questionController.exitGame();
        mainCtrl.showSoloWaiting();
    }

    /**
     * Method called when the finished game timer is done.
     */
    public void finalLeaderboardFinished() {
        mainCtrl.showFinalLeaderboard();
    }

    /**
     * Method called when the lobby waiting stage is over.
     */
    public void lobbyTimerFinished() {
        mainCtrl.showQuestionScreenMP();
    }

    /**
     * Getter for the TimerUtils field
     *
     * @return The TimerUtils field
     */
    public TimerUtils getTimerUtils() {
        return timerUtils;
    }
}
