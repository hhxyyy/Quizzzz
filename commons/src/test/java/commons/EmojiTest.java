package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmojiTest {

    private Emoji e;
    private Emoji f;
    private Emoji g;

    @BeforeEach
    void setup() {
        e = new Emoji("test");
        f = new Emoji("test");
        g = new Emoji("test2");
    }

    @Test
    void testConstructor() {
        assertNotNull(e);
    }

    @Test
    void testGetImgPath() {
        assertEquals("test", e.getImgPath());
    }

    @Test
    void setImgPath() {
        e.setImgPath("test2");
        assertEquals("test2", e.getImgPath());
    }

    @Test
    void testEquals() {
        assertEquals(e, f);
    }

    @Test
    void testNotEquals() {
        assertNotEquals(e, g);
    }

    @Test
    void testHashCode() {
        assertEquals(e, f);
        assertEquals(e.hashCode(), f.hashCode());
    }

    @Test
    void testFailHashCode() {
        assertNotEquals(e, g);
        assertNotEquals(e.hashCode(), g.hashCode());
    }

    @Test
    void testToString() {
        assertNotNull(e.toString());
        assertTrue(e.toString().contains("imgPath=test"));
    }
}