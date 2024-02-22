package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class Answer {

    private long uuid;
    private int answer;
    private double timeLeft;
    private boolean pointDouble;

    /**
     * Default constructor for Jackson OM
     */
    protected Answer() {}

    /**
     * Constructor for Answer class
     * @param uuid of the user
     * @param answer the user submitted
     */
    public Answer(long uuid, int answer) {
        this.uuid = uuid;
        this.answer = answer;
        this.timeLeft = 0;
        this.pointDouble = false;
    }

    /**
     * Constructor for Answer class
     * @param uuid of the user
     * @param answer the user submitted
     * @param timeLeft the time left of the current question
     * @param pointDouble whether the user use the double point joker
     */
    public Answer(long uuid, int answer, double timeLeft, boolean pointDouble) {
        this.uuid = uuid;
        this.answer = answer;
        this.timeLeft = timeLeft;
        this.pointDouble = pointDouble;
    }

    /**
     * Getter method for the answer attribute
     * @return The answer in wh
     */
    public int getAnswer() {
        return answer;
    }

    /**
     * Getter method for the uuid attribute
     * @return The uuid field
     */
    public long getUuid() {
        return uuid;
    }

    /**
     * Getter method for the timeLeft attribute
     * @return The timeLeft field
     */
    public double getTimeLeft() {
        return timeLeft;
    }

    /**
     * Equals method to compare an object to an instance of Answer
     *
     * @param o Object to be compared against.
     * @return boolean value for whether the object equals this instance of Answer or not
     */
    @Override
    public boolean equals(Object o) { return EqualsBuilder.reflectionEquals(this, o); }

    /**
     * HashCode generator method
     * @return hashCode of type int
     */
    @Override
    public int hashCode() { return HashCodeBuilder.reflectionHashCode(this); }

    /**
     * toString method generating human-readable string
     * @return Human-readable string
     */
    @Override
    public String toString() { return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE); }

    /**
     * Getter method for the pointDouble attribute
     * @return boolean
     */
    public boolean getPointDouble() { return pointDouble; }

    /**
     * Setter method for the pointDouble attribute
     * @param pointDouble The required status of the pointDouble
     */
    public void setPointDouble(boolean pointDouble) {
        this.pointDouble = pointDouble;
    }


}
