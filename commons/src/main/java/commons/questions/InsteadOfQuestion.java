package commons.questions;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import commons.Activity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static java.lang.Integer.MAX_VALUE;
import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "class")
public class InsteadOfQuestion extends Question {
    public String[] answers;
    public int correctIndex;

    /**
     * Default constructor for Object Mappers
     */
    @SuppressWarnings("unused")
    public InsteadOfQuestion() {}

    /**
     * Constructor for Child class InsteadOfQuestion
     * @param questionText The text of this InsteadOfQuestion
     * @param questionId The question id
     * @param image_path The path to the corresponding image
     * @param answers The answers to this InsteadOfQuestion
     * @param correctIndex The index of the correct answer
     */
    public InsteadOfQuestion(String questionText, UUID questionId, String image_path, String[] answers, int correctIndex) {
        super(questionText, questionId, image_path);
        this.answers = answers;
        this.correctIndex = correctIndex;
    }

    /**
     * Generator for questions of type InsteadOfQuestion
     * @param activities Array of activities.
     * @return Question of type InsteadOfQuestion with a randomized array of options to choose from and the index
     * of element of the array which is the correct answer.
     */
    public static InsteadOfQuestion questionGenerator(Activity[] activities) {
        String[] answersGen = new String[4];
        int index = 0;
        int index2 = 0;
        long min = MAX_VALUE;

        // Get the activity with the lowest energy consumption
        for (int i = 0; i < 4; i++) {
            if (activities[i].getConsumption() < min) {
                min = activities[i].getConsumption();
                index = i; // correct answer
            }
        }

        activities[index].setConsumption(MAX_VALUE);
        long min2 = MAX_VALUE;

        // Get the activity with the second-lowest energy consumption
        for (int i = 0; i < 4; i++) {
            if (activities[i].getConsumption() < min2) {
                min2 = activities[i].getConsumption();
                index2 = i; // title on the question
            }
        }

        Activity[] newActivities = new Activity[3];
        List<Activity> activityList = new LinkedList<Activity>(Arrays.asList(activities));
        activityList.remove(activities[index2]);
        Collections.shuffle(activityList);
        index = activityList.indexOf(activities[index]);
        activityList.toArray(newActivities);
        for (int j = 0; j < 3; j++) {
            answersGen[j] = newActivities[j].getTitle();
        }
        return new InsteadOfQuestion("Instead of " + activities[index2].getTitle() + " you could save energy by...", UUID.randomUUID(), activities[index2].getImage(), answersGen, index);
    }

    /**
     * Getter for answers
     * @return All the answers
     */
    public String[] getAnswers() {
        return answers;
    }

    /**
     * Setter for answers
     * @param answers The new answers
     */
    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    /**
     * Getter for correctIndex
     * @return Index of the correct answer in String array answers.
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
     * Equals method to compare an instance of InsteadOfQuestion to another object.
     * @param obj Object to be compared against.
     * @return Boolean value indicating whether the objects are identical or not.
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
