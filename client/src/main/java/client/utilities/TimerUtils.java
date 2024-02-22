package client.utilities;


import client.scenes.MainCtrl;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Utility class for working with the in-game timer
 */
public class TimerUtils {

    private MainCtrl mainCtrl;
    private UpdateViewUtils ctrl;
    private double time;
    private Timer timer;
    private boolean decreaseTime;

    /**
     * The constructor for the TimerUtils class
     *
     * @param mainCtrl Main controller of the application to access the primary stage
     * @param ctrl Question controller to update the timer on the scene
     * @param start The number of seconds of the timer
     */
    public TimerUtils(MainCtrl mainCtrl, UpdateViewUtils ctrl, double start) {
        this.mainCtrl = mainCtrl;
        this.ctrl = ctrl;
        this.time = start;
        this.decreaseTime = false;
    }

    /**
     * Setter for the current time, can be changed while the timer is running,
     * for example, decreasing time when using the reduce-time joker
     *
     * @param time The new time to set the current one to
     */
    public void setTime(double time) {
        this.time = time;
    }

    /**
     * The main method that initializes the timer and starts it.
     * It starts a timer in a new thread which will display the time left each 100ms to 1 decimal point precision.
     */
    public void startQuestionTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                final double current = time;
                Platform.runLater(() -> ctrl.setQuestionTimer((double) Math.round(current * 100) / 100));

                Stage stage = mainCtrl.getPrimaryStage();
                if (!stage.getTitle().equals("Quizzzz!: Question") || !stage.isShowing()) {
                    timer.cancel();
                }

                if (time <= 0.0) {
                    timer.cancel();
                    Platform.runLater(() -> ctrl.timerFinished());
                }

                if (decreaseTime) {
                    time = time - time * 0.1;
                    decreaseTime = false;
                } else {
                    time = time - 0.1;
                }

            }
        }, 0, 100);

    }

    /**
     * The main method that initializes the lobby (waiting room) timer and starts it.
     * It starts a timer in a new thread which will display the time left each 100ms to 1 decimal point precision.
     */
    public void startLobbyTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                final double current = time;
                Platform.runLater(() -> ctrl.setLobbyTimer((double) Math.round(current * 100) / 100));

                Stage stage = mainCtrl.getPrimaryStage();
                if (!stage.getTitle().equals("Quizzzz!: Waiting Room") || !stage.isShowing()) {
                    timer.cancel();
                }

                if (time <= 0.0) {
                    timer.cancel();
                    Platform.runLater(() -> ctrl.lobbyTimerFinished());
                    System.out.println("Game Started");
                }

                time = time - 0.1;
            }
        }, 0, 100);

    }

    /**
     * The main method that initializes the waiting timer and starts it.
     * It starts a timer in a new thread which will display the time left each 100ms to 1 decimal point precision.
     */
    public void startWaitingTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                final double current = time;
                Platform.runLater(() -> ctrl.setWaitingTimer((double) Math.round(current * 100) / 100));

                Stage stage = mainCtrl.getPrimaryStage();
                if (!stage.getTitle().equals("Quizzzz!: Question") || !stage.isShowing()) {
                    timer.cancel();
                }

                if (time <= 0.0) {
                    timer.cancel();
                    Platform.runLater(() -> ctrl.waitingTimerFinished());
                }

                time = time - 0.1;
            }
        }, 0, 100);
    }

    /**
     * The main method that initializes the finished game timer and starts it.
     * It starts a timer in a new thread which will display the time left each 100ms to 1 decimal point precision.
     */
    public void startFinishedGameTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                final double current = time;
                Platform.runLater(() -> ctrl.setFinishedGameTimer((double) Math.round(current * 100) / 100));

                Stage stage = mainCtrl.getPrimaryStage();
                if (!stage.getTitle().equals("Quizzzz!: Question") || !stage.isShowing()) {
                    timer.cancel();
                }

                if (time <= 0.0) {
                    timer.cancel();
                    Platform.runLater(() -> ctrl.finishedGameTimerFinished());
                }

                time = time - 0.1;
            }
        }, 0, 100);
    }

    /**
     * The main method that initializes the finished game timer and starts it.
     * It starts a timer in a new thread which will display the time left each 100ms to 1 decimal point precision.
     */
    public void startFinalLeaderboardTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                final double current = time;
                Platform.runLater(() -> ctrl.setFinalLeaderboardTimer((double) Math.round(current * 100) / 100));

                if (time <= 0.0) {
                    timer.cancel();
                    Platform.runLater(() -> ctrl.finalLeaderboardFinished());
                }

                time = time - 0.1;
            }
        }, 0, 100);
    }


    /**
     * The main method that initializes the halftime game timer and starts it.
     * It starts a timer in a new thread which will display the time left each 100ms to 1 decimal point precision.
     */
    public void startHalftimeTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

          @Override
          public void run() {
              final double current = time;
              Platform.runLater(() -> ctrl.setHalftimeTimer((double) Math.round(current * 100) / 100));

              if (time <= 0.0) {
                  timer.cancel();
                  Platform.runLater(() -> ctrl.halftimeTimerFinished());
              }

              time = time - 0.1;
          }
        }, 0, 100);
    }

    /**
     * The main method that initializes the pre-halftime game timer and starts it.
     * It starts a timer in a new thread which will display the time left each 100ms to 1 decimal point precision.
     */
    public void startPreHalftimeTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                final double current = time;
                Platform.runLater(() -> ctrl.setPreHalftimeTimer((double) Math.round(current * 100) / 100));

                if (time <= 0.0) {
                    timer.cancel();
                    Platform.runLater(() -> ctrl.preHalftimeTimerFinished());
                }

                time = time - 0.1;
            }
        }, 0, 100);
    }

    /**
     * Cancels the timer
     */
    public void cancel() {
        timer.cancel();
    }

    /**
     * Setter for the decreaseTime, which decide whether the time should be decreased,
     * for example, decreasing time when using the reduce-time joker
     *
     * @param decreaseTime The status which decides the time decrease.
     */
    public void setDecreaseTime(boolean decreaseTime) {
        this.decreaseTime = decreaseTime;
    }
}