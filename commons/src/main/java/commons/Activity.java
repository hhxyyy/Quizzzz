package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.net.URL;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
public class Activity {

    @Id
    public String id;

    @Column(name = "image")
    public String image_path;

    public String title;

    @Column(name = "consumption", columnDefinition = "BIGINT(19)")
    public long consumption_in_wh; // Has to be a long as some values are larger than an int

    @Column(name = "source", columnDefinition = "VARCHAR(500)", length = 500)
    public URL source;

    /**
     * The constructor for the object mapper
     */
    @SuppressWarnings("unused")
    private Activity() {
        // for object mapper
    }

    /**
     * The constructor for the Activity class
     * @param id The id of the activity
     * @param image_path The image path of the activity
     * @param title The title of the activity
     * @param consumption_in_wh The consumption in wh of the activity
     * @param source The source of the activity
     */
    public Activity(String id, String image_path, String title, long consumption_in_wh, URL source) {
        this.id = id;
        this.image_path = image_path;
        this.title = title;
        this.consumption_in_wh = consumption_in_wh;
        this.source = source;
    }

    /**
     * Getter method for id field of type String
     * @return id of activity
     */
    public String getId() { return id; }

    /**
     * Setter method for id field of type String
     * @param id The new ID
     */
    public void setId(String id) { this.id = id; }

    /**
     * Getter method for image_path field of type String
     * @return String of path to image
     */
    public String getImage() { return image_path; }

    /**
     * Setter method for the image_path field of type String
     * @param image_path New image path
     */
    public void setImage(String image_path) { this.image_path = image_path; }

    /**
     * Getter method for field source of URL type.
     * @return Source URL pointing to origin website.
     */
    public URL getSource() {
        return source;
    }

    /**
     * Setter method for field source of URL type.
     * @param source New source URL pointing to origin website.
     */
    public void setSource(URL source) { this.source = source; }

    /**
     * Getter method for the field get title of type String.
     * @return title of the activity
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter method for the field get title of type String.
     * @param title String describing the activity.
     */
    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * Getter method for the field consumption_in_wh of type long.
     * @return The consumption_in_wh field of the activity
     */
    public long getConsumption() {
        return consumption_in_wh;
   }

    /**
     * Setter method for the field consumption_in_wh of type long.
     * @param consumption_in_wh New consumption of energy in Wh.
     */
    public void setConsumption(long consumption_in_wh) {
        this.consumption_in_wh = consumption_in_wh;
    }


    /**
     * Equals method to compare an object to an instance of Activity
     * @param o Object to be compared against.
     * @return Boolean value for whether the object equals this instance of Activity or not.
     */
    @Override
    public boolean equals(Object o) { return EqualsBuilder.reflectionEquals(this, o); }

    /**
     * HashCode generator method.
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
}
