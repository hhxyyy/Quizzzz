package client.scenes;

import javax.inject.Inject;

public class TutorialController implements FrontEndController {

    private final MainCtrl mainCtrl;

    /**
     * The constructor of the TutorialController
     * @param mainCtrl An instance of the MainCtrl
     */
    @Inject
    public TutorialController(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    public void setup() {
    }

    /**
     * Method called when the exit button is pressed.
     */
    public void exitGame(){
        mainCtrl.showSplashScene();
    }

}
