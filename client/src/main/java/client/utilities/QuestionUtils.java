package client.utilities;

import commons.questions.ComparisonQuestion;
import commons.questions.InsteadOfQuestion;
import commons.questions.Question;
import commons.questions.SimpleQuestionMCQ;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class QuestionUtils {

    private final ImageView image1;
    private final ImageView image2;
    private final ImageView image3;
    private final Button answer1;
    private final Button answer2;
    private final Button answer3;

    /**
     * Constructor for QuestionUtils
     * @param image1 The middle image
     * @param image2 The left image
     * @param image3 The right image
     * @param answer1 The top answer button
     * @param answer2 The middle answer button
     * @param answer3 The bottom answer button
     */
    public QuestionUtils(ImageView image1, ImageView image2, ImageView image3, Button answer1, Button answer2, Button answer3) {
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
    }

    /**
     * Method that sets the view based on the type of question
     * @param q A Question
     */
    public void setQuestionTypeMCQ(Question q) {
        String defaultStyle = "-fx-background-color: white;" +
                "  -fx-background-radius: 20px;" +
                "  -fx-font-family: \"Open Sans\";" +
                "  -fx-font-size: 20px;" +
                "  -fx-text-fill: #13293D;";
        answer1.setStyle(defaultStyle);
        answer2.setStyle(defaultStyle);
        answer3.setStyle(defaultStyle);

        if (q instanceof InsteadOfQuestion) {
            InsteadOfQuestion question = (InsteadOfQuestion) q;
            image2.setVisible(false);
            image3.setVisible(false);
            answer1.setText(question.getAnswers()[0]);
            answer2.setText(question.getAnswers()[1]);
            answer3.setText(question.getAnswers()[2]);
        } else if (q instanceof SimpleQuestionMCQ) {
            SimpleQuestionMCQ question = (SimpleQuestionMCQ) q;
            image2.setVisible(false);
            image3.setVisible(false);
            answer1.setText(question.getAnswers()[0] + "Wh");
            answer2.setText(question.getAnswers()[1] + "Wh");
            answer3.setText(question.getAnswers()[2] + "Wh");
        } else {
            ComparisonQuestion question = (ComparisonQuestion) q;
            try {
                this.image1.setImage(imageFromUrl(new URL(ServerCommunication.SERVER + "images/" + question.getAllImagePaths().get(0))));
                this.image1.fitWidthProperty();
                this.image2.setImage(imageFromUrl(new URL(ServerCommunication.SERVER + "images/" + question.getAllImagePaths().get(1))));
                this.image2.fitWidthProperty();
                this.image3.setImage(imageFromUrl(new URL(ServerCommunication.SERVER + "images/" + question.getAllImagePaths().get(2))));
                this.image3.fitWidthProperty();
                image2.setVisible(true);
                image3.setVisible(true);
                answer1.setText(String.valueOf(question.getAnswers()[0]));
                answer2.setText(String.valueOf(question.getAnswers()[1]));
                answer3.setText(String.valueOf(question.getAnswers()[2]));
            } catch (MalformedURLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Sets the color of the buttons showing which one is correct and which ones are incorrect.
     *
     * @param correctIndex The index of the correct answer
     */
    public void setColorAnswer(int correctIndex) {
        String incorrectStyle = "-fx-background-color: red;" +
                "  -fx-background-radius: 20px;" +
                "  -fx-font-family: \"Open Sans\";" +
                "  -fx-font-size: 20px;" +
                "  -fx-text-fill: #13293D;";
        answer1.setStyle(incorrectStyle);
        answer2.setStyle(incorrectStyle);
        answer3.setStyle(incorrectStyle);
        String correctStyle = "-fx-background-color: #66e1b0;" +
                "  -fx-background-radius: 20px;" +
                "  -fx-font-family: \"Open Sans\";" +
                "  -fx-font-size: 20px;" +
                "  -fx-text-fill: #13293D;";
        if (correctIndex == 0) {
            answer1.setStyle(correctStyle);
        } else if (correctIndex == 1) {
            answer2.setStyle(correctStyle);
        } else {
            answer3.setStyle(correctStyle);
        }
    }

    /**
     * Converts a BufferedImage to a JavafxImage
     *
     * @param url URL to image
     * @return A JavaFX image
     */
    public Image imageFromUrl(URL url) {
        try {
            BufferedImage image = ImageIO.read(url);
            WritableImage wr = null;
            if (image != null) {
                wr = new WritableImage(image.getWidth(), image.getHeight());
                PixelWriter pw = wr.getPixelWriter();
                for (int x = 0; x < image.getWidth(); x++) {
                    for (int y = 0; y < image.getHeight(); y++) {
                        pw.setArgb(x, y, image.getRGB(x, y));
                    }
                }
            }

            return new ImageView(wr).getImage();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public void setChosenStyle(Button chosenButton) {
        String chosenStyle = "-fx-background-color: #89cff0;" +
            "  -fx-background-radius: 20px;" +
            "  -fx-font-family: \"Open Sans\";" +
            "  -fx-font-size: 20px;" +
            "  -fx-text-fill: #13293D;";

        if (chosenButton.equals(answer1)) {
            answer1.setStyle(chosenStyle);
            answer1.setOpacity(1);
        } else if (chosenButton.equals(answer2)) {
            answer2.setStyle(chosenStyle);
            answer2.setOpacity(1);
        } else {
            answer3.setStyle(chosenStyle);
            answer3.setOpacity(1);
        }
    }
}
