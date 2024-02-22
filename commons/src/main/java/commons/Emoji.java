package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Emoji {

    private String imgPath;

    /**
     * Default constructor for OM
     */
    public Emoji() {
    }

    /**
     * Constructor for Emoji
     *
     * @param imgPath
     */
    public Emoji(String imgPath) {
        this.imgPath = imgPath;
    }

    /**
     * Getter for image path
     *
     * @return
     */
    public String getImgPath() {
        return imgPath;
    }

    /**
     * Setter for image path
     *
     * @param imgPath
     */
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    /**
     * Method that test whether this is equal to object o
     *
     * @param o object to compare this with
     * @return true iff this and o are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Emoji emoji = (Emoji) o;

        return new EqualsBuilder().append(imgPath, emoji.imgPath).isEquals();
    }

    /**
     * Generates hashCode for Emoji
     *
     * @return
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(imgPath).toHashCode();
    }

    /**
     * Generates String with all information about Emoji
     *
     * @return
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("imgPath", imgPath)
                .toString();
    }
}
