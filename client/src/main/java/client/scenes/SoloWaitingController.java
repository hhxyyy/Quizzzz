package client.scenes;

import client.utilities.GridUtils;
import client.utilities.SingleCommunication;
import client.utilities.UserCommunication;
import client.utilities.UserSessionCommunication;
import commons.SingleEntry;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SoloWaitingController implements FrontEndController {

    private final MainCtrl mainCtrl;
    private final UserSessionCommunication userSessionCommunication;
    private final GridUtils gridUtils;
    private final SingleCommunication singleCommunication;
    private final UserCommunication userCommunication;

    @FXML
    private GridPane soloGrid;

    /**
     * Constructor for the SoloWaitingController.
     * @param mainCtrl instance of MainCtrl to be used to switch scenes.
     * @param userSessionCommunication Instance of UserSessionCommunication
     * @param gridUtils Instance of GridUtils
     * @param singleCommunication
     * @param userCommunication
     * @param soloGrid
     */
    @Inject
    public SoloWaitingController(MainCtrl mainCtrl, UserSessionCommunication userSessionCommunication, GridUtils gridUtils, SingleCommunication singleCommunication, UserCommunication userCommunication, GridPane soloGrid) {
        this.mainCtrl = mainCtrl;
        this.userSessionCommunication = userSessionCommunication;
        this.gridUtils = gridUtils;
        this.singleCommunication = singleCommunication;
        this.userCommunication = userCommunication;
        this.soloGrid = soloGrid;
    }

    public void setup() {
        soloGrid.getChildren().clear();
        createSingleLeaderBoard();
    }

    /**
     * gets all single players from the database and lists them by score
     */
    public void createSingleLeaderBoard() {
        try {
            List<SingleEntry> users = singleCommunication.getSingles();
            List<SingleEntry> sorted = new ArrayList<>();
            List<Integer> scores = new ArrayList<>();
            int size = users.size();

            for (SingleEntry user : users) {
                scores.add(user.getScore());
            }

            for (int i = 0; i < size; i++) {
                int max = scores.indexOf(Collections.max(scores));
                if (!users.get(max).equals(mainCtrl.user)) {
                    sorted.add(users.get(max));
                }
                users.remove(max);
                scores.remove(max);
            }

            gridUtils.addToSingleGrid(sorted, soloGrid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method called when the exit button is pressed.
     */
    public void exitGame() {
        mainCtrl.user = userCommunication.resetUser(mainCtrl.user.getUuid());
        mainCtrl.showSplashScene();
    }

    /**
     * Method called when the "Play Game" button is pressed.
     */
    public void showQuestion() {
        mainCtrl.showQuestionScreenSP();
    }

    /**
     * Method called when the ? button is pressed.
     */
    public void showTutorial() { mainCtrl.showTutorial(); }
}
