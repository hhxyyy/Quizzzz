package commons.jokers;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public abstract class Joker {

    private boolean used;

    /**
     * Constructor for abstract class Joker
     * Constructor for Object Mappers
     */
    public Joker(){
        this.used = false;
    }

    /**
     * Getter for used attribute
     * @return The status of used
     */
    public boolean isUsed() {
        return used;
    }

    /**
     * Setter for used attribute
     * @param used The status set to used
     */
    public void setUsed(boolean used) {
        this.used = used;
    }

    /**
     * Equals method to test the equality of two jokers.
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
