package commons.jokers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TimeJokerTest {

    @Test
    void testConstructor() {
        TimeJoker joker = new TimeJoker();
        assertNotNull(joker);
    }

    @Test
    void isDecreaseTime() {
        TimeJoker joker = new TimeJoker();
        assertFalse(joker.isDecreaseTime());
    }

    @Test
    void setDecreaseTime() {
        TimeJoker joker = new TimeJoker();
        joker.setDecreaseTime(true);
        assertTrue(joker.isDecreaseTime());
    }

    @Test
    void testEquals() {
        TimeJoker joker = new TimeJoker();
        TimeJoker joker1 = new TimeJoker();
        assertTrue(joker.equals(joker1));
    }

    @Test
    void testHashCode() {
        TimeJoker joker = new TimeJoker();
        TimeJoker joker1 = new TimeJoker();
        assertEquals(joker.hashCode(), joker1.hashCode());
    }

    @Test
    void testToString() {
        TimeJoker joker = new TimeJoker();
        String e = joker.toString();
        assertTrue(e.contains("TimeJoker"));
        assertTrue(e.contains("decreaseTime=false"));
    }
}