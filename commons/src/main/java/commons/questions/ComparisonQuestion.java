package commons.questions;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import commons.Activity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "class")
public class ComparisonQuestion extends Question {

    public String[] answers;
    public int correctIndex;
    public List<String> allImagePaths;

    /**
     * Default constructor for Object Mappers
     */
    @SuppressWarnings("unused")
    public ComparisonQuestion() {}

    /**
     * Constructor for Comparison question, containing list will all images from the answers
     * @param questionText The text of this ComparisonQuestion
     * @param questionText The text of this ComparisonQuestion
     * @param questionId The question id
     * @param answers The answers
     * @param correctIndex The index of the correct answer
     * @param allImagePaths All the paths to the images corresponding to the answers
     */
    public ComparisonQuestion(String questionText, UUID questionId, String image_path, String[] answers,
                              int correctIndex, List<String> allImagePaths) {
        super(questionText, questionId, image_path);
        this.answers = answers;
        this.correctIndex = correctIndex;
        this.allImagePaths = allImagePaths;
    }

    /**
     * Generator for questions of type Comparison
     * @param activities Array of activities.
     * @return Question of type ComparisonQuestion with a randomized array of options to choose from and the index
     * of element of the array which is the correct answer.
     */
    public static ComparisonQuestion questionGenerator(Activity[] activities) {
        String[] answersGen = new String[3];
        String ml;
        Random rand = new Random(); //instance of random class
        int upperbound = 2;
        int index = 0;
        //generate random values from 0-1
        int ran = rand.nextInt(upperbound);
        if (ran == 0) {
            ml = "least";
            long min = MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                if (activities[i].getConsumption() < min) {
                    min = activities[i].getConsumption();
                    index = i;
                }
            }
        } else {
            ml = "most";
            long max = MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                if (activities[i].getConsumption() > max) {
                    max = activities[i].getConsumption();
                    index = i;
                }
            }
        }

        Activity correctAnswer = activities[index];
        List<Activity> activityList = Arrays.asList(activities);
        Collections.shuffle(activityList);
        index = activityList.indexOf(correctAnswer);

        for (int j = 0; j < 3; j++) {
            answersGen[j] = activities[j].getTitle();
        }
        List<String> allImagePaths = new ArrayList<>();
        for (int j = 0; j < 3; j++) {
            allImagePaths.add(activities[j].getImage());
        }
        return new ComparisonQuestion("Which takes the " + ml + " energy?", UUID.randomUUID(),
                activities[index].getImage(), answersGen, index, allImagePaths);
    }

    /**
     * Getter for answers
     * @return An array of answers
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
     * @return The index of the correct answer
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
     * Getter for all images of the answers
     * @return list with all images
     */
    public List<String> getAllImagePaths() {
        return allImagePaths;
    }

    /**
     * Setter for all images of the answers
     * @param allImagePaths The new ImagePaths
     */
    public void setAllImagePaths(List<String> allImagePaths) {
        this.allImagePaths = allImagePaths;
    }

    /**
     * Equals method to compare an instance of ComparisonQuestion to another object
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

