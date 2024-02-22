package commons.jokers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DoublePointJokerTest {

    @Test
    void testConstructor() {
        DoublePointJoker joker = new DoublePointJoker();
        assertNotNull(joker);
    }

    @Test
    void setDoublePoint() {
        DoublePointJoker joker = new DoublePointJoker();
        joker.setDoublePoint(true);
        assertTrue(joker.isDoublePoint());
    }

    @Test
    void isDoublePoint() {
        DoublePointJoker joker = new DoublePointJoker();
        assertFalse(joker.isDoublePoint());
    }

    @Test
    void testEquals() {
        DoublePointJoker joker = new DoublePointJoker();
        DoublePointJoker joker1 = new DoublePointJoker();
        assertTrue(joker.equals(joker1));
    }

    @Test
    void testHashCode() {
        DoublePointJoker joker = new DoublePointJoker();
        DoublePointJoker joker1 = new DoublePointJoker();
        assertEquals(joker.hashCode(), joker1.hashCode());
    }

    @Test
    void testToString() {
        DoublePointJoker joker = new DoublePointJoker();
        String e = joker.toString();
        assertTrue(e.contains("DoublePointJoker"));
        assertTrue(e.contains("doublePoint=false"));
    }
}