package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
@Table(name = "UserSession")
public class UserSession {
    @Id
    @Column(name = "Id")
    public UUID uuid;

    @Column(name = "Status")
    private int status;

    @Column(name = "Question")
    private int questionNumber;

    /*
        Status:
        > 0: Closed
        > 1: Open
     */

    /**
     * The constructor for the UserSession, sets the status to 1
     */
    public UserSession() {
        this.uuid = UUID.randomUUID();
        this.status = 1;
        this.questionNumber = 0;
    }

    /**
     * Getter for the id of the UserSession
     * @return The id of the room
     */
    public UUID getId() {
        return uuid;
    }

    /**
     * Setter for the id of the UserSession
     * @param uuid to be assigned to the room
     */
    public void setId(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Getter for the status of the UserSession
     * @return 0 if the room has not started and 1 otherwise
     */
    public int getStatus() {
        return status;
    }

    /**
     * Change the status of a UserSession
     * @param status The new status of the UserSession
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Getter for the question number of the UserSession
     * @return the number of the question the session is currently on
     */
    public int getQuestionNumber() {
        return questionNumber;
    }

    /**
     * Setter for the question number
     * @param questionNumber the new question number
     */
    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    /**
     * Increments the question number by 1.
     */
    public void incrementQuestionNumber() {
        this.questionNumber++;
    }

    /**
     * compares an object to aUserSession
     * @param obj is the object to be compared with the waiting room
     * @return true if the object is from the same tye as the waiting room and has the same parameters
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * generates hash code for a waiting room
     *
     * @return the hash code of the waiting room
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * generate a human-readable string containing the information about a waiting room
     *
     * @return string in a human-readable format
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}
