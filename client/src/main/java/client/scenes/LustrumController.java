package client.scenes;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javax.inject.Inject;

public class LustrumController implements FrontEndController {

    private MainCtrl mainCtrl;

    @FXML
    private ImageView lustrumView;

    @FXML
    private StackPane rootPane;

    private Image img = new Image("images/lustrum.gif");

    @Inject
    public LustrumController(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    @Override
    public void setup() {
       this.lustrumView.setImage(img);
    }

    /**
     * Method to fade out the stackpane at the root.
     */
    public void fadeOut() {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(800));
        fadeTransition.setNode(rootPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        fadeTransition.setOnFinished(event -> mainCtrl.loadGame());
    }
}
