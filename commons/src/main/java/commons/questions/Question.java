package commons.questions;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import commons.Activity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.UUID;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;


@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "class")
public abstract class Question {
    public UUID questionId;
    public String questionText;
    public String image_path;

    /**
     * Default constructor for Object Mappers
     */
    @SuppressWarnings("unused")
    public Question() {}

    /**
     * Constructor for abstract class Question
     * @param questionText The text of the Question
     * @param questionId The uuid of the question
     * @param image_path The path to the corresponding image
     */
    public Question(String questionText, UUID questionId, String image_path) {
        this.questionText = questionText;
        this.questionId = questionId;
        this.image_path = image_path;
    }

    /**
     * Generator for all types of questions
     * @param activities List of activities for generating the Question
     * @param type The type of the question
     * @return A new Question
     */
    public static Question generator(Activity[] activities, int type) {
        if (type == 0) {
            return SimpleQuestionMCQ.questionGenerator(activities);
        } else if (type == 1) {
            return SimpleQuestionOpen.questionGenerator(activities);
        } else if (type == 2) {
            return ComparisonQuestion.questionGenerator(activities);
        } else {
            return InsteadOfQuestion.questionGenerator(activities);
        }
    }

    /**
     * Getter for UUID
     * @return The uuid of thq question
     */
    public UUID getQuestionId() {
        return questionId;
    }

    /**
     * Setter for UUID
     * @param questionId The new id of the question
     */
    public void setQuestionId(UUID questionId) {
        this.questionId = questionId;
    }

    /**
     * Getter for question text
     * @return The text of the question
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Setter for question text
     * @param questionText The new text of the question
     */
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    /**
     * Getter for image path
     * @return The path to the image
     */
    public String getImage_path() {
        return image_path;
    }

    /**
     * Setter for image path
     * @param image_path The new path to the image
     */
    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    /**
     * Equals method to test the equality of two questions.
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

    /**
     * Trailing zero counter.
     * @param num Integer to count the zeros from.
     * @return The number of zeros at the end of the input parameter.
     */
    public static int countTrailingZeros(long num) {
        int zeros = 0;
        while (num % 10 == 0) {
            zeros++;
            num /= 10;
        }
        return zeros;
    }
}
