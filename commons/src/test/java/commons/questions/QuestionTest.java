package commons.questions;

import commons.questions.Question;
import commons.questions.SimpleQuestionOpen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class QuestionTest {
    String questionText;
    UUID id;
    String path;
    double correctMin;
    double correctMax;
    Question question;

    @BeforeEach
    void setup() {
        questionText = "About how much energy does b take?";
        id = UUID.randomUUID();
        path = "path";
        correctMin = 10;
        correctMax = 20;
        question = new SimpleQuestionOpen(questionText, id, path, correctMin, correctMax);
    }

    @Test
    void testConstructor() {
        assertNotNull(question);
    }

    @Test
    void testGetQuestionId() {
        assertEquals(id, question.getQuestionId());
    }

    @Test
    void testGetWrongQuestionId() {
        UUID newId = UUID.randomUUID();
        while (newId.equals(id)) {
            newId = UUID.randomUUID();
        }
        assertNotEquals(newId, question.getQuestionId());
    }

    @Test
    void testSetQuestionId() {
        UUID id1 = UUID.randomUUID();
        question.setQuestionId(id1);
        assertEquals(id1, question.getQuestionId());
    }

    @Test
    void testGetQuestionText() {
        assertEquals(questionText, question.getQuestionText());
    }

    @Test
    void testGetWrongQuestionText() {
        assertNotEquals("Question", question.getQuestionText());
    }

    @Test
    void testSetQuestionText() {
        question.setQuestionText("Replace");
        assertEquals("Replace", question.getQuestionText());
    }

    @Test
    void testGetImagePath() {
        assertEquals("path", question.getImage_path());
    }

    @Test
    void testGetWrongImagePath() {
        assertNotEquals("image_path", question.getImage_path());
    }

    @Test
    void testImagePathNotNull() {
        assertNotNull(question.getImage_path());
    }

    @Test
    void testSetImagePath() {
        question.setImage_path("image_path");
        assertEquals("image_path", question.getImage_path());
    }

    @Test
    void testEquals() {
        Question question1 = new SimpleQuestionOpen(questionText, id, path, correctMin, correctMax);
        assertTrue(question1.equals(question));
    }

    @Test
    void testEqualsSameObject() {
        assertEquals(question, question);
    }

    @Test
    void testNotEqualsQuestionText() {
        Question question1 = new SimpleQuestionOpen("Question", id, path, correctMin, correctMax);
        assertNotEquals(question1, question);
    }

    @Test
    void testNotEqualsId() {
        UUID newId = UUID.randomUUID();
        while (newId.equals(id)) {
            newId = UUID.randomUUID();
        }
        Question question1 = new SimpleQuestionOpen(questionText, newId, path, correctMin, correctMax);
        assertNotEquals(question1, question);
    }

    @Test
    void testNotEqualsCorrectMin() {
        Question question1 = new SimpleQuestionOpen(questionText, id, path, 0, correctMax);
        assertNotEquals(question1, question);
    }

    @Test
    void testNotEqualsCorrectMax() {
        Question question1 = new SimpleQuestionOpen(questionText, id, path, correctMin, 0);
        assertNotEquals(question1, question);
    }

    @Test
    void testNotEqualsImagePath() {
        Question question1 = new SimpleQuestionOpen(questionText, id, "image_path", correctMin, 0);
        assertNotEquals(question1, question);
    }

    @Test
    void testHashCode() {
        Question question1 = new SimpleQuestionOpen(questionText, id, path, correctMin, correctMax);
        assertEquals(question1.hashCode(), question.hashCode());
    }

    @Test
    void testHashCodeSameObject() {
        assertEquals(question.hashCode(), question.hashCode());
    }

    @Test
    void testNotEqualsHashCodeQuestionText() {
        Question question1 = new SimpleQuestionOpen("Question", id, path, correctMin, correctMax);
        assertNotEquals(question1.hashCode(), question.hashCode());
    }

    @Test
    void testNotEqualsHashCodeId() {
        UUID newId = UUID.randomUUID();
        while (newId.equals(id)) {
            newId = UUID.randomUUID();
        }
        Question question1 = new SimpleQuestionOpen(questionText, newId, path, correctMin, correctMax);
        assertNotEquals(question1.hashCode(), question.hashCode());
    }

    @Test
    void testNotEqualsHashCorrectMin() {
        Question question1 = new SimpleQuestionOpen(questionText, id, path, 0, correctMax);
        assertNotEquals(question1.hashCode(), question.hashCode());
    }

    @Test
    void testNotEqualsHashCorrectMax() {
        Question question1 = new SimpleQuestionOpen(questionText, id, path, correctMin, 0);
        assertNotEquals(question1.hashCode(), question.hashCode());
    }

    @Test
    void testNotEqualsHashCodeImagePath() {
        Question question1 = new SimpleQuestionOpen(questionText, id, "image_path", correctMin, correctMax);
        assertNotEquals(question1.hashCode(), question.hashCode());
    }

    @Test
    void testToString() {
        String expected = question.toString();
        assertTrue(expected.contains("SimpleQuestionOpen"));
        assertTrue(expected.contains("correctMax=20"));
        assertTrue(expected.contains("correctMin=10"));
        assertTrue(expected.contains("questionId"));
        assertTrue(expected.contains("About how much energy does b take"));
    }

    @Test
    void testToStringSameObject() {
        assertEquals(question.toString(), question.toString());
    }
}