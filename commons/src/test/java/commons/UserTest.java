package commons;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class UserTest {
    UUID id = UUID.randomUUID();

    @Test
    void testConstructor() {
        User x = new User("test", id);
        assertEquals("test", x.name);
        assertEquals(0, x.score);
    }

    @Test
    void testEquals() {
        User x = new User("a", id);
        User y = new User("a", id);
        assertEquals(x, y);
    }

    @Test
    void testEqualsSame() {
        User x = new User("a", id);
        assertEquals(x, x);
    }

    @Test
    void testEqualsHashCode() {
        User x = new User("a", id);
        User y = new User("a", id);
        assertEquals(x.hashCode(), y.hashCode());
    }

    @Test
    void testEqualsHashCodeSame() {
        User x = new User("a", id);
        assertEquals(x.hashCode(), x.hashCode());
    }

    @Test
    void testNotEquals() {
        User x = new User("a", id);
        User y = new User("b", id);
        assertNotEquals(x, y);
    }

    @Test
    void testNotEqualsSessionID() {
        User x = new User("a", id);
        UUID newId = UUID.randomUUID();
        while (newId.equals(id)) {
            newId = UUID.randomUUID();
        }
        User y = new User("a", newId);
        assertNotEquals(x, y);
    }

    @Test
    void testNotEqualsHashCode() {
        User x = new User("a", id);
        User y = new User("b", id);
        assertNotEquals(x.hashCode(), y.hashCode());
    }

    @Test
    void testNotEqualsHashCodeSessionId() {
        User x = new User("a", id);
        UUID newId = UUID.randomUUID();
        while (newId.equals(id)) {
            newId = UUID.randomUUID();
        }
        User y = new User("a", newId);
        assertNotEquals(x.hashCode(), y.hashCode());
    }

    @Test
    void testGetScore() {
        User x = new User("a", id);
        x.setScore(42);
        assertEquals(42, x.getScore());
    }

    @Test
    void testGetScoreWrong() {
        User x = new User("a", id);
        x.setScore(42);
        assertNotEquals(1, x.getScore());
    }

    @Test
    void testSetScore() {
        User x = new User("test", id);
        assertEquals(0, x.score);
        x.setScore(42);
        assertEquals(42, x.score);
    }

    @Test
    void testGetName() {
        User x = new User("a", id);
        assertEquals("a", x.getName());
    }

    @Test
    void testGetNameWrong() {
        User x = new User("a", id);
        x.setName("b");
        assertNotEquals("a", x.getName());
    }

    @Test
    void testSetName() {
        User x = new User("test", id);
        assertEquals("test", x.name);
        x.setName("a");
        assertEquals("a", x.name);
    }

    @Test
    void testGetSessionId() {
        User x = new User("a", id);
        assertEquals(id, x.getSessionId());
    }

    @Test
    void testGetSessionIdWrong() {
        User x = new User("a", id);
        UUID newId = UUID.randomUUID();
        while (newId.equals(id)) {
            newId = UUID.randomUUID();
        }
        assertNotEquals(newId, x.getSessionId());
    }

    @Test
    void testSetSessionId() {
        User x = new User("test", id);
        assertEquals(id, x.getSessionId());
        UUID u = UUID.randomUUID();
        x.setSessionId(u);
        assertEquals(u, x.getSessionId());
    }

    @Test
    void testToString() {
        String actual = new User("a", id).toString();
        assertTrue(actual.contains(User.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("name"));
    }
}