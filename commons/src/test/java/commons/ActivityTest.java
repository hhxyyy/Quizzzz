package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ActivityTest {

    private final String id1 = "id";
    private final String id2 = "test";
    private final String image1 = "path/to/image.jpg";
    private final String image2 = "test/test/test.png";
    private final String title1 = "Using an Apple Watch for a week.";
    private final String title2 = "Thinking really hard for an hour.";
    private final int consumption1 = 11;
    private final int consumption2 = 20;
    private URL source1;
    private URL source2;

    private Activity activityOne;
    private Activity activityTwo;

    @BeforeEach
    void setUp() throws IOException {
        source1 = new URL("https://www.apple.com/watch/battery/");
        source2  = new URL("https://www.munichre.com/topics-online/en/digitalisation/interview-henning-beck.html#:~:text=%E2%80%9CThe%20brain%20works%20with%2020,.%E2%80%9D%20%7C%20Munich%20Re%20Topics%20Online");
        activityOne = new Activity(id1, image1, title1, consumption1, source1);
    }

    @Test
    void testConstructorNotNullTest(){
        assertNotNull(activityOne);
    }

    @Test
    void testGetId() { assertEquals(activityOne.getId(), id1); }

    @Test
    void testGetIdFalse() { assertNotEquals(activityOne.getId(), id2); }

    @Test
    void testSetId() {
        activityOne.setId(id2);
        assertEquals(activityOne.getId(), id2);
    }

    @Test
    void testGetImage() { assertEquals(activityOne.getImage(), image1); }

    @Test
    void testGetImageFalse() { assertNotEquals(activityOne.getImage(), image2); }

    @Test
    void testSetImage() {
        activityOne.setImage(image2);
        assertEquals(activityOne.getImage(), image2);
    }

    @Test
    void testGetSource() {
        assertEquals(activityOne.getSource(), source1);
    }

    @Test
    void testGetSourceFalse(){
        assertNotEquals(activityOne.getSource(), source2);
    }

    @Test
    void testSetSource() {
        activityOne.setSource(source2);
        assertEquals(activityOne.getSource(), source2);
    }

    @Test
    void testGetTitle() {
        assertEquals(activityOne.getTitle(), title1);
    }

    @Test
    void testGetTitleFalse() {
        assertNotEquals(activityOne.getTitle(), title2);
    }

    @Test
    void testSetTitle() {
        activityOne.setTitle(title2);
        assertEquals(activityOne.getTitle(), title2);
    }

    @Test
    void testGetConsumption() {
        assertEquals(activityOne.getConsumption(), consumption1);
    }

    @Test
    void testGetConsumptionFalse() {
        assertNotEquals(activityOne.getConsumption(), consumption2);
    }

    @Test
    void testSetConsumption() {
        activityOne.setConsumption(consumption2);
        assertEquals(activityOne.getConsumption(), consumption2);
    }

    @Test
    void testEqualsExact() {
        assertEquals(activityOne, activityOne);
    }

    @Test
    void testEqualsNormal() {
        activityTwo = new Activity(id1, image1, title1, consumption1, source1);
        assertEquals(activityOne, activityTwo);
    }

    @Test
    void testNotEquals() {
        activityTwo = new Activity(id2, image2, title2, consumption2, source2);
        assertNotEquals(activityOne, activityTwo);
    }

    @Test
    void testToString() {
        String toString = activityOne.toString();
        assertTrue(toString.contains(Activity.class.getSimpleName()));
        assertTrue(toString.contains("\n"));
        activityTwo = new Activity(id2, image2, title2, consumption2, source2);
        assertNotEquals(activityTwo.toString(), toString);
    }
}