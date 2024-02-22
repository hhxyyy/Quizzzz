package commons.jokers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JokerTest {

    @Test
    void testConstructor() {
        Joker joker = new DoublePointJoker();
        assertNotNull(joker);
    }

    @Test
    void isUsed() {
        Joker joker = new DoublePointJoker();
        assertFalse(joker.isUsed());
    }

    @Test
    void setUsed() {
        Joker joker = new DoublePointJoker();
        joker.setUsed(true);
        assertTrue(joker.isUsed());
    }

    @Test
    void testEquals() {
        Joker joker = new DoublePointJoker();
        Joker joker1 = new DoublePointJoker();
        assertTrue(joker.equals(joker1));
    }

    @Test
    void testHashCode() {
        Joker joker = new DoublePointJoker();
        Joker joker1 = new DoublePointJoker();
        assertEquals(joker.hashCode(), joker1.hashCode());
    }

    @Test
    void testToString() {
        Joker joker = new DoublePointJoker();
        String e = joker.toString();
        assertTrue(e.contains("DoublePointJoker"));
        assertTrue(e.contains("used=false"));
    }
}