package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserSessionTest {

    UUID sessionId;
    User user1;
    User user2;
    UserSession userSession;

    @BeforeEach
    void setup() {
        sessionId = UUID.randomUUID();
        user1 = new User("Yoan", sessionId);
        user2 = new User("Okan", sessionId);
        userSession = new UserSession();
        userSession.setId(sessionId);
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(userSession);
    }

    @Test
    void testGetId() {
        assertEquals(sessionId, userSession.getId());
    }

    @Test
    void testGetWrongId() {
        assertNotEquals(UUID.randomUUID(), userSession.getId());
    }

    @Test
    void testSetId() {
        UUID uuid1 = UUID.randomUUID();
        userSession.setId(uuid1);
        assertEquals(uuid1, userSession.getId());
    }

    @Test
    void testGetStatus() {
        assertEquals(1, userSession.getStatus());
    }

    @Test
    void testGetWrongStatus() {
        assertNotEquals(0, userSession.getStatus());
    }

    @Test
    void testSetStatus() {
        userSession.setStatus(1);
        assertEquals(1, userSession.getStatus());
    }

    @Test
    void testGetQuestionNumber() {
        assertEquals(0, userSession.getQuestionNumber());
    }

    @Test
    void testWrongQuestionNumber() {
        assertNotEquals(1, userSession.getQuestionNumber());
    }

    @Test
    void testSetQuestionNumber() {
        userSession.setQuestionNumber(1);
        assertEquals(1, userSession.getQuestionNumber());
    }

    @Test
    void testIncrementQuestionNumber() {
        userSession.incrementQuestionNumber();
        assertEquals(1, userSession.getQuestionNumber());
    }

    @Test
    void testEqualsSame() {
        assertEquals(userSession, userSession);
    }

    @Test
    void testEquals() {
        UserSession userSession1 = new UserSession();
        userSession1.setId(sessionId);
        assertEquals(userSession, userSession1);
    }

    @Test
    void testNotEqualsUsers() {
        UserSession userSession1 = new UserSession();
        assertNotEquals(userSession1, userSession);
    }

    @Test
    void testNotEqualsId() {
        UserSession userSession1 = new UserSession();
        userSession1.setId(UUID.randomUUID());
        assertNotEquals(userSession1, userSession);
    }

    @Test
    void testHashCode() {
        UserSession userSession1 = new UserSession();
        userSession1.setId(UUID.randomUUID());
        assertNotEquals(userSession1.hashCode(), userSession.hashCode());
    }

    @Test
    void testEqualHashCode() {
        assertEquals(userSession.hashCode(), userSession.hashCode());
    }

    @Test
    void testToStringNotNull() {
        assertNotNull(userSession.toString());
    }

}