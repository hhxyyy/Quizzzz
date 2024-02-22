package commons.jokers;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class TimeJoker extends Joker{

    private boolean decreaseTime;

    /**
     * Constructor for class TimeJoker
     * Constructor for Object Mappers
     */
    public TimeJoker() {
        super();
        this.decreaseTime = false;
    }

    /**
     * Getter for decreaseTime attribute
     *
     * @return The status of decreaseTime attribute
     */
    public boolean isDecreaseTime() {
        return decreaseTime;
    }

    /**
     * Setter for decreaseTime attribute
     * @param decreaseTime The status set to decreaseTime attribute
     */
    public void setDecreaseTime(boolean decreaseTime) {
        this.decreaseTime = decreaseTime;
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
