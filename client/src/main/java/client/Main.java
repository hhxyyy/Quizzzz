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
package client;

import static com.google.inject.Guice.createInjector;
import client.scenes.LustrumController;
import client.scenes.QuestionControllerMP;
import com.google.inject.Injector;

import client.scenes.MainCtrl;
import client.scenes.FinalController;
import client.scenes.HalftimeController;
import client.scenes.SoloWaitingController;
import client.scenes.AdminPanelController;
import client.scenes.QuestionController;
import client.scenes.SplashController;
import client.scenes.WaitingController;
import client.scenes.TutorialController;
import client.scenes.ChangeServerController;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    /**
     * Method that is run to start the program
     * @param args Arguments entered using the commandline
     */
    public static void main(String[] args){
        launch();
    }

    /**
     * Loads all the FXML files and initializes them
     * @param primaryStage The stage to add all the scenes too
     */
    @Override
    public void start(Stage primaryStage) {

        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);

        var splash = FXML.load(SplashController.class, "quiz_splash.fxml");
        var questionSP = FXML.load(QuestionController.class, "question_screen_SP.fxml");
        var questionMP = FXML.load(QuestionControllerMP.class, "question_screen_MP.fxml");
        var waiting = FXML.load(WaitingController.class, "waiting_room.fxml");
        var tutorial = FXML.load(TutorialController.class, "help_screen.fxml");
        var changeServer = FXML.load(ChangeServerController.class, "server_select.fxml");
        var adminPanel = FXML.load(AdminPanelController.class, "admin_panel.fxml");
        var soloWaiting = FXML.load(SoloWaitingController.class, "solo_waiting.fxml");
        var halftime = FXML.load(HalftimeController.class, "halftime_leaderboard.fxml");
        var finalLeaderboard = FXML.load(FinalController.class, "final_leaderboard.fxml");
        var lustrum = FXML.load(LustrumController.class, "intro.fxml");

        mainCtrl.initialize(primaryStage, splash, questionSP, questionMP, waiting, tutorial, changeServer, soloWaiting,
                finalLeaderboard, halftime, adminPanel, lustrum);

    }
}