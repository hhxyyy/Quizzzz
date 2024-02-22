package commons.jokers;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class DoublePointJoker extends Joker{

    private boolean doublePoint;

    /**
     * Constructor for class DoublePointJoker
     * Constructor for Object Mappers
     */
    public DoublePointJoker() {
        super();
        this.doublePoint = false;
    }

    /**
     * Setter for doublePoint attribute
     * @param doublePoint The status set to doublePoint attribute
     */
    public void setDoublePoint(boolean doublePoint) {
        this.doublePoint = doublePoint;
    }

    /**
     * Getter for doublePoint attribute
     * @return The status of doublePoint attribute
     */
    public boolean isDoublePoint() {
        return doublePoint;
    }

    /**
     * Equals method to test the equality of two DoublePointJokers.
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
