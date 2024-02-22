package commons;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;



import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long uuid;

    public String name;
    public int score;

    @JoinColumn(name = "sessionId", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private UserSession userSession;

    @Column(name = "sessionId")
    private UUID sessionId;

    /**
     * Default constructor for Object Mappers
     */
    @SuppressWarnings("unused")
    public User() {}

    /**
     * Constructor for User, sets score to 0
     * @param name The name of the user
     */
    public User(String name) {
        this.name = name;
        this.score = 0;
    }

    /**
     * Constructor for User, with a sessionId
     * @param name The name of the user
     * @param sessionId The UUID of the UserSession the user belongs to
     */
    public User(String name, UUID sessionId) {
        this(name);
        this.sessionId = sessionId;
    }

    /**
     * Getter for the name field
     * @return The name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the User
     * @param name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for User score
     * @return The score of the user
     */
    public int getScore() { return this.score; }

    /**
     * Setter for User score
     * @param score The new score of the user
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Returns uuid of user
     * @return The uuid of the user
     */
    public long getUuid() {
        return uuid;
    }

    /**
     * Sets userId of user
     * @param uuid The new uuis of the user
     */
    public void setUuid(long uuid) {
        this.uuid = uuid;
    }

    /**
     * Allows program to access sessionID
     * @return The sessionId of the user
     */
    public UUID getSessionId() {
        return sessionId;
    }

    /**
     * Sets the session ID of a user to something different
     * @param sessionId The new sessionId of the user
     */
    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * Equals method to compare an instance of User to another object
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
