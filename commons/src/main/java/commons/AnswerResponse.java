package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;


public class AnswerResponse extends Answer {

    private boolean isCorrect;
    private int newScore;

    /**
     * Constructor for the Object Mapper
     */
    @SuppressWarnings("unused")
    private AnswerResponse() {
        super();
    }

    /**
     * Constructor for the AnswerResponse class
     * @param uuid The uuid of the AnswerResponse
     * @param answer The answer of the AnswerResponse
     * @param timeLeft The time left of the AnswerResponse
     * @param isCorrect Whether the Answer is correct
     * @param newScore The new score of the user whose AnswerResponse this is
     * @param pointDouble Whether the points are doubled for this answer
     */
    public AnswerResponse(long uuid, int answer, double timeLeft, boolean isCorrect, int newScore, boolean pointDouble) {
        super(uuid, answer, timeLeft, pointDouble);
        this.isCorrect = isCorrect;
        this.newScore = newScore;

    }

    /**
     * Different constructor for the AnswerResponse class
     * @param ans The corresponding Answer object
     * @param isCorrect Whether the Answer given is correct
     * @param newScore The new score of the user whose AnswerResponse this is
     * @param pointDouble Whether the points are doubled for this answer
     */
    public AnswerResponse(Answer ans, boolean isCorrect, int newScore, boolean pointDouble) {
        this(ans.getUuid(), ans.getAnswer(), ans.getTimeLeft(), isCorrect, newScore, pointDouble);
    }

    /**
     * Getter for the isCorrect field
     * @return Whether the answer is correct
     */
    public boolean isCorrect() {
        return isCorrect;
    }

    /**
     * Setter for the isCorrect field
     * @param correct Whether this AnswerResponse should be correct
     */
    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    /**
     * Getter for the NewScore field
     * @return The new score of the user this AnswerResponse belongs to
     */
    public int getNewScore() {
        return newScore;
    }

    /**
     * Setter for the NewScore field
     * @param newScore The new newscore
     */
    public void setNewScore(int newScore) {
        this.newScore = newScore;
    }

    /**
     * Tests whether this is equal to obj
     * @param obj object to be compared to this
     * @return true if and only if this is equal to obj
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * Generates hashCode for the User class
     * @return hashCode for user
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Created a human-readable String for the User class
     * @return String containing information about this instance
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}
