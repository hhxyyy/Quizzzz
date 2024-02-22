package commons.jokers;

import commons.questions.Question;
import commons.questions.SimpleQuestionMCQ;
import commons.questions.InsteadOfQuestion;
import commons.questions.ComparisonQuestion;
import commons.questions.SimpleQuestionOpen;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Random;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class RemoveAnswerJoker extends Joker{

    private boolean removeAnswer;

    /**
     * Constructor for class RemoveAnswerJoker
     * Constructor for Object Mappers
     */
    public RemoveAnswerJoker() {
        super();
        this.removeAnswer = false;
    }

    /**
     * Getter for index of the answer that should be removed
     *
     * @param questionFromServer The current question received from the server
     * @return The index of the answer that should be removed
     */
    public int getRemoveIndex(Question questionFromServer) {
        int correctIndex = Integer.MIN_VALUE;

        if (questionFromServer instanceof SimpleQuestionOpen) {
            return -1;
        } else if (questionFromServer instanceof InsteadOfQuestion) {
            correctIndex = ((InsteadOfQuestion) questionFromServer).getCorrectIndex();
        } else if (questionFromServer instanceof SimpleQuestionMCQ) {
            correctIndex = ((SimpleQuestionMCQ) questionFromServer).getCorrectIndex();
        } else if (questionFromServer instanceof ComparisonQuestion) {
            correctIndex = ((ComparisonQuestion) questionFromServer).getCorrectIndex();
        }

        Random random = new Random();
        int hideIndex = Integer.MIN_VALUE;
        if (correctIndex == 0) {
            hideIndex = random.nextInt(2) + 1;
        } else if (correctIndex == 2) {
            hideIndex = random.nextInt(2);
        } else if (correctIndex == 1) {
            int[] index = new int[2];
            index[1] = 2;
            hideIndex = index[random.nextInt(2)];
        }


        return hideIndex;

    }

    /**
     * Getter for removeAnswer attribute
     *
     * @return The status of removeAnswer attribute
     */
    public boolean isRemoveAnswer() {
        return removeAnswer;
    }

    /**
     * Setter for removeAnswer attribute
     * @param removeAnswer The status set to removeAnswer attribute
     */
    public void setRemoveAnswer(boolean removeAnswer) {
        this.removeAnswer = removeAnswer;
    }

    /**
     * Equals method to test the equality of two RemoveAnswerJokers.
     * @param o Object to be compared against
     * @return Boolean value indicating whether the objects are identical or not
     */
    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
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
