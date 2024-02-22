package commons.questions;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import commons.Activity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Random;
import java.util.UUID;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "class")
public class SimpleQuestionMCQ extends Question {

    public long[] answers;
    public int correctIndex;

    /**
     * Default constructor for Object Mappers
     */
    @SuppressWarnings("unused")
    public SimpleQuestionMCQ() {}

    /**
     * The constructor for SimpleQuestionMCQ with all attributes
     * @param questionText The text of this SimpleQuestionMCQ
     * @param questionId The question id
     * @param image_path The path to the corresponding image
     * @param answers The answers to this SimpleQuestionMCQ
     * @param correctIndex The index of the correct answer
     */
    public SimpleQuestionMCQ(String questionText, UUID questionId, String image_path, long[] answers, int correctIndex) {
        super(questionText, questionId, image_path);
        this.answers = answers;
        this.correctIndex = correctIndex;
    }

    /**
     * generates new questions for SimpleQuestionMCQ
     * @param activities The activities used for making a SimpleQuestionMCQ
     */
    public static SimpleQuestionMCQ questionGenerator(Activity[] activities) {
        if (activities.length != 1) {
            throw new IndexOutOfBoundsException();
        }
        Activity activity = activities[0];

        long nextRandomLong;
        long[] options = new long[3];
        long correctAnswer = activity.getConsumption();

        // Perhaps implement normalization here?

        options[0] = correctAnswer;

        for (int i = 1; i < 3; i++) {
            nextRandomLong = incorrectAnswerGenerator(correctAnswer);
            while (nextRandomLong == correctAnswer || nextRandomLong == options[i - 1] || nextRandomLong == 0) {
                nextRandomLong = incorrectAnswerGenerator(correctAnswer);
            }
            options[i] = nextRandomLong;
        }

        fisherYatesShuffle(options);

        int correctAnswerIndex = 0;
        // Set correct index
        for (int i = 0; i < 3; i++) {
            if (options[i] == activities[0].getConsumption()) {
                correctAnswerIndex = i;
            }
        }

        return new SimpleQuestionMCQ("How much energy does " + activity.getTitle() + " take?", UUID.randomUUID(), activities[0].getImage(), options, correctAnswerIndex);
    }

    /**
     * Getter method for Question Text to be displayed
     * @return questionText of String type
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Setter method for questionText to be displayed.
     * @param questionText The new questionText
     */
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }


    /**
     * Getter method for array of incorrect answers to display
     * @return The answers of the question
     */
    public long[] getAnswers() {
        return answers;
    }

    /**
     * Setter for answers
     * @param answers The new answers
     */
    public void setAnswers(long[] answers) {
        this.answers = answers;
    }

    /**
     * Getter for the correctIndex
     * @return The index of the correctIndex
     */
    public int getCorrectIndex() {
        return correctIndex;
    }

    /**
     * Setter for correctIndex
     * @param correctIndex The new correctIndex
     */
    public void setCorrectIndex(int correctIndex) {
        this.correctIndex = correctIndex;
    }

    /**
     * Fisher-Yates shuffler for arrays of primitive types.
     * @param inputArray Long array to be shuffled.
     */
    public static void fisherYatesShuffle(long[] inputArray) {
        Random randomizer = new Random();
        int index;
        long swap;

        for (int i = inputArray.length - 1; i > 0; i--) {
            index = randomizer.nextInt(i + 1);
            swap = inputArray[index];
            inputArray[index] = inputArray[i];
            inputArray[i] = swap;
        }
    }

    /**
     * Work in progress random incorrect answer generator based on normal distribution.
     * @param correctAnswer Correct answer / consumption_in_wh of type long.
     * @return Randomly generated answer of type long, with the same amount of
     * trailing zeros as the correct answer for added obscurity.
     */
    public static long incorrectAnswerGenerator(long correctAnswer) {
        Random randomizer = new Random();
        long exponent = countTrailingZeros(correctAnswer);
        long incorrectAnswer;

        double nextGauss = randomizer.nextGaussian();
        while (nextGauss < 0.1) {
            nextGauss = randomizer.nextGaussian();
        }

        if (nextGauss >= 1 && randomizer.nextInt() % 3 == 0) {
            nextGauss *= 2; // Increase the range to 0-6x The correct answer
        }

        incorrectAnswer = Math.round(correctAnswer / Math.pow(10, exponent) * nextGauss);

        return Math.round(incorrectAnswer * Math.pow(10, exponent));
    }

    /**
     * Equals method to compare an instance of SimpleQuestionMCQ to another object
     * @param obj Object to be compared against
     * @return Boolean value indicating whether the objects are identical or not
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * HashCode generator method
     * @return hashCode of type int
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * toString method generating human-readable string
     * @return Human-readable string
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}
