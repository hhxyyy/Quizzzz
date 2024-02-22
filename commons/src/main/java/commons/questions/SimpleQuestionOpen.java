package commons.questions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import commons.Activity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.UUID;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "class")
public class SimpleQuestionOpen extends Question {
    double correctMin;
    double correctMax;

    /**
     * Default constructor for Object Mappers
     */
    @SuppressWarnings("unused")
    public SimpleQuestionOpen() {}

    /**
     * The constructor for SimpleQuestionOpen with all attributes
     * @param questionText The text of this SimpleQuestionMCQ
     * @param questionId The question id
     * @param image_path The path to the corresponding image
     * @param correctMin The minimum correct value
     * @param correctMax The maximum correct value
     */
    public SimpleQuestionOpen(String questionText, UUID questionId, String image_path, double correctMin, double correctMax) {
        super(questionText, questionId, image_path);
        this.correctMin = correctMin;
        this.correctMax = correctMax;
    }

    /**
     * Generator for question type Open
     * @param activities The activities used to generate the SimpleQuestionOpen
     * @return The new Question
     */
    public static Question questionGenerator(Activity[] activities) {
        if (activities.length != 1) {
            throw new IndexOutOfBoundsException();
        }
        Activity activity = activities[0];
        double min = activity.getConsumption() * .75;
        double max = activity.getConsumption() * 1.25;
        return new SimpleQuestionOpen("About how much energy does " + activity.getTitle() + " take?", UUID.randomUUID(), activities[0].getImage(), min, max);
    }

    /**
     * Getter for correctMin
     * @return The minimum value that is correct
     */
    public double getCorrectMin() {
        return correctMin;
    }

    /**
     * Setter for correctMin
     * @param correctMin The new minimum correct value
     */
    public void setCorrectMin(double correctMin) {
        this.correctMin = correctMin;
    }

    /**
     * Getter for correctMax
     * @return The maximum value that is correct
     */
    public double getCorrectMax() {
        return correctMax;
    }

    /**
     * Setter for correctMax
     * @param correctMax The new maximum correct value
     */
    public void setCorrectMax(double correctMax) {
        this.correctMax = correctMax;
    }

    /**
     * Getter for the exact correct answer
     * @return The correct answer
     */
    public int getPreciseAnswer() {
        return (int) (correctMax + correctMin) / 2;
    }

    /**
     * Equals method to compare an instance of UserSession to another object
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
