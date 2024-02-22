package commons;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AnswerTest {

    @Test
    void testConstructor() {
        Answer x = new Answer(1, 1);
        assertNotNull(x);
        assertEquals(1, x.getUuid());
        assertEquals(1, x.getAnswer());
    }

    @Test
    void testEquals() {
        Answer x = new Answer(1, 1);
        Answer y = new Answer(1, 1);
        assertEquals(x, y);
    }

    @Test
    void testEqualsHashCode() {
        Answer x = new Answer(1, 1);
        Answer y = new Answer(1, 1);
        assertEquals(x.hashCode(), y.hashCode());
    }

    @Test
    void testEqualsHashCode2() {
        Answer x = new Answer(1, 1, 5.0f, false);
        Answer y = new Answer(1, 1, 5.0f, false);
        assertEquals(x.hashCode(), y.hashCode());
    }

    @Test
    void testNotEquals() {
        Answer x = new Answer(1, 1);
        Answer y = new Answer(1, 2);
        assertNotEquals(x, y);
    }

    @Test
    void testNotEquals2() {
        Answer x = new Answer(1, 1);
        Answer y = new Answer(1, 2, 5.0f, false);
        assertNotEquals(x, y);
    }

    @Test
    void testNotEqualsHashCode() {
        UUID uuid = UUID.randomUUID();
        User x = new User("a", uuid);
        User y = new User("b", uuid);
        assertNotEquals(x.hashCode(), y.hashCode());
    }

    @Test
    void testToString() {
        Answer ans = new Answer(1, 1, 5.0f, false);
        String actual = ans.toString();
        assertTrue(actual.contains(Answer.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
    }

    @Test
    void testConstructor2() {
        Answer x = new Answer(1, 1, 6.0f, false);
        assertNotNull(x);
        assertEquals(1, x.getUuid());
        assertEquals(1, x.getAnswer());
        assertEquals(6.0, x.getTimeLeft());
    }

    @Test
    void testGetPointDoubleTest() {
        Answer x = new Answer(1, 1, 6.0f, false);
        assertFalse(x.getPointDouble());
    }

    @Test
    void testSetPointDoubleTest() {
        Answer x = new Answer(1, 1, 6.0f, false);
        x.setPointDouble(true);
        assertTrue(x.getPointDouble());
    }
}