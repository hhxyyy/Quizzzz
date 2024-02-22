/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.scenes;

import client.utilities.ServerCommunication;
import com.google.inject.Inject;
import commons.User;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * The Main controller for all the scenes
 */
public class MainCtrl {

    private final ServerCommunication serverCommunication;

    private Stage primaryStage;

    public User user;

    private SplashController splashCtrl;
    private Parent splashScene;

    private FinalController finalCtrl;
    private Parent finalScene;

    private HalftimeController halftimeCtrl;
    private Parent halftimeScene;

    private QuestionController questionCtrlSP;
    private Parent questionSceneSP;

    private QuestionControllerMP questionCtrlMP;
    private Parent questionSceneMP;

    private SoloWaitingController soloWaitingCtrl;
    private Parent soloWaitingScene;

    private TutorialController tutorialCtrl;
    private Parent tutorialScene;

    private ChangeServerController changeServerCtrl;
    private Parent changeServerScene;

    private AdminPanelController adminPanelCtrl;
    private Parent adminPanelScene;

    private WaitingController waitingCtrl;
    private Parent waitingScene;

    private LustrumController lustrumCtrl;
    private Parent lustrumScene;

    private boolean inBoot = true;

    /**
     * Constructor for the MainCtrl class
     */
    @Inject
    public MainCtrl(ServerCommunication serverCommunication) {
        this.serverCommunication = serverCommunication;
    }

    /**
     * Creates all the scenes
     * @param primaryStage the stage which everything is added too
     * @param splash the splashscreen view
     * @param questionSP the question single player view
     * @param questionMP the question multi player view
     * @param waiting the waiting room view
     * @param tutorial the tutorial view
     * @param changeServer the change server view
     * @param soloWaitingRoom the solo waiting room view
     * @param finalLeaderboard the final leaderboard view
     * @param halftime the halftime leaderboard view
     * @param adminPanel the admin panel view
     */
    public void initialize(Stage primaryStage,
                           Pair<SplashController, Parent> splash,
                           Pair<QuestionController, Parent> questionSP,
                           Pair<QuestionControllerMP, Parent> questionMP,
                           Pair<WaitingController, Parent> waiting,
                           Pair<TutorialController, Parent> tutorial,
                           Pair<ChangeServerController, Parent> changeServer,
                           Pair<SoloWaitingController, Parent> soloWaitingRoom,
                           Pair<FinalController, Parent> finalLeaderboard,
                           Pair<HalftimeController, Parent> halftime,
                           Pair<AdminPanelController, Parent> adminPanel,
                           Pair<LustrumController, Parent> lustrum) {

        this.primaryStage = primaryStage;

        this.splashCtrl = splash.getKey();
        this.splashScene = splash.getValue();

        this.questionCtrlSP = questionSP.getKey();
        this.questionSceneSP = questionSP.getValue();

        this.questionCtrlMP = questionMP.getKey();
        this.questionSceneMP = questionMP.getValue();

        this.waitingCtrl = waiting.getKey();
        this.waitingScene = waiting.getValue();

        this.tutorialCtrl = tutorial.getKey();
        this.tutorialScene = tutorial.getValue();

        this.changeServerCtrl = changeServer.getKey();
        this.changeServerScene = changeServer.getValue();

        this.soloWaitingCtrl = soloWaitingRoom.getKey();
        this.soloWaitingScene = soloWaitingRoom.getValue();

        this.halftimeCtrl = halftime.getKey();
        this.halftimeScene = halftime.getValue();

        this.finalCtrl = finalLeaderboard.getKey();
        this.finalScene = finalLeaderboard.getValue();

        this.adminPanelCtrl = adminPanel.getKey();
        this.adminPanelScene = adminPanel.getValue();

        this.lustrumCtrl = lustrum.getKey();
        this.lustrumScene = lustrum.getValue();

        primaryStage.show();
        showLustrumScene();

        delay(3700, () -> {
            lustrumCtrl.fadeOut();
        });
    }

    /**
     * Loads either the splash scene or the change server scene.
     */
    public void loadGame(){
        if (serverCommunication.checkServer()) {
            showSplashScene();
        } else {
            showChangeServer();
            changeServerCtrl.setAlert();
        }
        inBoot = false;
    }

    public boolean getInBoot(){
        return this.inBoot;
    }

    /**
     * Method that delays provided runnable after a certain amount of delay.
     * @param millis - Miliseconds to delay
     * @param method - Method to run after delay
     */
    public static void delay(long millis, Runnable method) {
        Task<Void> delayer = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try { Thread.sleep(millis);
                } catch (InterruptedException e) { }
                return null;
            }
        };
        delayer.setOnSucceeded(event -> method.run());
        new Thread(delayer).start();
    }

    /**
     * Show the intro view
     */
    public void showLustrumScene(){
        primaryStage.setTitle("Quizzzz!");
        primaryStage.setScene(new Scene(lustrumScene));
        lustrumCtrl.setup();
    }

    /**
     * Show the splash screen view
     */
    public void showSplashScene() {
        primaryStage.setTitle("Quizzzz!: Welcome");
        primaryStage.getScene().setRoot(splashScene);
        splashCtrl.setup();
    }

    /**
     * Show and set up the final leaderboard view
     */
    public void showFinalLeaderboard() {
        primaryStage.setTitle("Quizzzz!: Podium");
        primaryStage.getScene().setRoot(finalScene);
        finalCtrl.setup();
    }

    /**
     * Show and set yp the halftime leaderboard view
     */
    public void showHalftimeLeaderboard() {
        primaryStage.setTitle("Quizzzz!: Half Time!");
        primaryStage.getScene().setRoot(halftimeScene);
        halftimeCtrl.setup();
    }

    /**
     * Shows the question screen view (singleplayer) and sets it up
     */
    public void showQuestionScreenSP() {
        primaryStage.setTitle("Quizzzz!: Question");
        primaryStage.getScene().setRoot(questionSceneSP);
        questionCtrlSP.setup();
    }

    /**
     * Show the question screen view (multiplayer) and sets it up
     */
    public void showQuestionScreenMP() {
        primaryStage.setTitle("Quizzzz!: Question");
        primaryStage.getScene().setRoot(questionSceneMP);
        questionCtrlMP.setup();
    }

    /**
     * Show the question screen view (multiplayer) after having visited the leaderboard
     */
    public void returnQuestionScreenMP() {
        primaryStage.setTitle("Quizzzz!: Question");
        primaryStage.getScene().setRoot(questionSceneMP);
        questionCtrlMP.reset();
    }

    /**
     * Show the solo waiting room view
     */
    public void showSoloWaiting() {
        primaryStage.setTitle("Quizzzz!: Single Player Game");
        primaryStage.getScene().setRoot(soloWaitingScene);
        soloWaitingCtrl.setup();
    }

    /**
     * Show the waiting room view
     */
    public void showWaitingRoom() {
        primaryStage.setTitle("Quizzzz!: Waiting Room");
        primaryStage.getScene().setRoot(waitingScene);
        waitingCtrl.setup();
    }

    /**
     * Shows the tutorial view
     */
    public void showTutorial() {
        primaryStage.setTitle("Quizzzz!: Tutorial");
        primaryStage.getScene().setRoot(tutorialScene);
        tutorialCtrl.setup();
    }

    /**
     * Shows the change server view and sets it up
     */
    public void showChangeServer() {
        primaryStage.setTitle("Change Server");
        primaryStage.getScene().setRoot(changeServerScene);
        changeServerCtrl.setup();
    }

    /**
     * Shows the admin panel view and sets it up
     */
    public void showAdminPanel() {
        primaryStage.setTitle("Admin Panel");
        primaryStage.getScene().setRoot(adminPanelScene);
        adminPanelCtrl.setup();
    }

    /**
     * Getter for the primary stage
     * @return the primary stage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
}