package commons.questions;

import java.net.MalformedURLException;
import java.net.URL;

import commons.Activity;
import commons.questions.SimpleQuestionMCQ;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SimpleQuestionMCQTest {
    String questionText;
    UUID id;
    String path;
    long[] answers;
    int correctIndex;
    SimpleQuestionMCQ mcq;

    @BeforeEach
    void setup() {
        questionText = "Asking?";
        id = UUID.randomUUID();
        path = "path";
        answers = new long[]{1, 2, 3};
        correctIndex = 0;
        mcq = new SimpleQuestionMCQ(questionText, id, path, answers, correctIndex);
    }

    @Test
    void testConstructor() {
        assertNotNull(mcq);
    }

    @Test
    void testGetAnswers() {
        assertEquals(answers, mcq.getAnswers());
    }

    @Test
    void testGetWrongAnswers() {
        long[] newAnswers = new long[]{4L, 5L, 6L};
        assertNotEquals(newAnswers, mcq.getAnswers());
    }

    @Test
    void testSetAnswers() {
        long[] newAnswers = new long[]{4L, 5L, 6L};
        mcq.setAnswers(newAnswers);
        assertEquals(newAnswers, mcq.getAnswers());
    }

    @Test
    void testGetCorrectIndex() {
        assertEquals(correctIndex, mcq.getCorrectIndex());
    }

    @Test
    void testSetCorrectIndex() {
        int newCorrectIndex = 2;
        mcq.setCorrectIndex(newCorrectIndex);
        assertEquals(newCorrectIndex, mcq.getCorrectIndex());
    }

    @Test
    void testEqualsSameObject() {
        assertEquals(mcq, mcq);
    }

    @Test
    void testNotEqualsId() {
        UUID newId = UUID.randomUUID();
        while (newId.equals(id)) {
            newId = UUID.randomUUID();
        }
        SimpleQuestionMCQ mcq2 = new SimpleQuestionMCQ(questionText, newId, path, answers, correctIndex);
        assertNotEquals(mcq, mcq2);
    }

    @Test
    void testNotEqualsQuestionText() {
        SimpleQuestionMCQ mcq2 = new SimpleQuestionMCQ("Question", id, path, answers, correctIndex);
        assertNotEquals(mcq, mcq2);
    }

    @Test
    void testNotEqualsImagePath() {
        SimpleQuestionMCQ mcq2 = new SimpleQuestionMCQ(questionText, id, "image_path", answers, correctIndex);
        assertNotEquals(mcq, mcq2);
    }

    @Test
    void testNotEqualsAnswers() {
        long[] newAnswers = new long[]{4, 5, 6};
        SimpleQuestionMCQ mcq2 = new SimpleQuestionMCQ(questionText, id, path, newAnswers, correctIndex);
        assertNotEquals(mcq, mcq2);
    }

    @Test
    void testNotEqualsCorrectIndex() {
        SimpleQuestionMCQ mcq2 = new SimpleQuestionMCQ(questionText, id, path, answers, 1);
        assertNotEquals(mcq, mcq2);
    }

    @Test
    void testHashCode() {
        SimpleQuestionMCQ mcq2 = new SimpleQuestionMCQ(questionText, id, path, answers, correctIndex);
        assertEquals(mcq.hashCode(), mcq2.hashCode());
    }

    @Test
    void testHashCodeSameObject() {
        assertEquals(mcq.hashCode(), mcq.hashCode());
    }

    @Test
    void testNotEqualsHashCodeQuestion() {
        SimpleQuestionMCQ mcq2 = new SimpleQuestionMCQ("Question", id, path, answers, correctIndex);
        assertNotEquals(mcq.hashCode(), mcq2.hashCode());
    }

    @Test
    void testNotEqualsHashId() {
        UUID newId = UUID.randomUUID();
        while (newId.equals(id)) {
            newId = UUID.randomUUID();
        }
        SimpleQuestionMCQ mcq2 = new SimpleQuestionMCQ(questionText, newId, path, answers, correctIndex);
        assertNotEquals(mcq.hashCode(), mcq2.hashCode());
    }

    @Test
    void testNotEqualsHashImagePath() {
        SimpleQuestionMCQ mcq2 = new SimpleQuestionMCQ(questionText, id, "image_path", answers, correctIndex);
        assertNotEquals(mcq.hashCode(), mcq2.hashCode());
    }

    @Test
    void testNotEqualsHashAnswers() {
        long[] newAnswers = new long[]{4, 5, 6};
        SimpleQuestionMCQ mcq2 = new SimpleQuestionMCQ(questionText, id, path, newAnswers, correctIndex);
        assertNotEquals(mcq.hashCode(), mcq2.hashCode());
    }

    @Test
    void testNotEqualsHashCorrectIndex() {
        SimpleQuestionMCQ mcq2 = new SimpleQuestionMCQ(questionText, id, path, answers, 1);
        assertNotEquals(mcq.hashCode(), mcq2.hashCode());
    }

    @Test
    void testToString() {
        String expected = mcq.toString();
        assertTrue(expected.contains("Asking"));
        assertTrue(expected.contains("questionId"));
        assertTrue(expected.contains("1"));
        assertTrue(expected.contains("2"));
    }

    @Test
    void testToStringSameObject() {
        assertEquals(mcq.toString(), mcq.toString());
    }

    @Test
    void testFisherYatesShuffle() {
        long[] testArray = new long[]{1, 2, 3};
        SimpleQuestionMCQ.fisherYatesShuffle(testArray);
        assertTrue(
                Arrays.equals(new long[]{1, 2, 3}, testArray) ||
                        Arrays.equals(new long[]{1, 3, 2}, testArray) ||
                        Arrays.equals(new long[]{2, 1, 3}, testArray) ||
                        Arrays.equals(new long[]{2, 3, 1}, testArray) ||
                        Arrays.equals(new long[]{3, 1, 2}, testArray) ||
                        Arrays.equals(new long[]{3, 2, 1}, testArray)
        );
    }

    @Test
    void testTrailingZeros() {
        assertEquals(SimpleQuestionMCQ.countTrailingZeros(100000), 5);
    }

    @Test
    void testIncorrectAnswerGeneration() {
        long correctAnswer = 11480000000L;
        assertNotEquals(correctAnswer, SimpleQuestionMCQ.incorrectAnswerGenerator(correctAnswer));
    }

    @Nested
    class GeneratedSimpleMCQTests {

        Activity[] activities;
        Activity activity1;
        SimpleQuestionMCQ simpleQ;
        URL source;
        long consumption_in_wh;

        @BeforeEach
        void setUp() throws MalformedURLException {
            source = new URL("https://www.example.com/");
            consumption_in_wh = 180;
            activity1 = new Activity("id1", "path/to/image1.jpeg", "title1", consumption_in_wh, source);
            activities = new Activity[]{activity1};
            simpleQ = SimpleQuestionMCQ.questionGenerator(activities);
        }

        @Test
        void testIncorrectAnswerRandomization() {
            long[] answers1 = simpleQ.getAnswers();
            long[] answers2 = SimpleQuestionMCQ.questionGenerator(activities).getAnswers();
            boolean equals = true;

            for (int i = 0; i < answers.length; i++) {
                if (answers1[i] != answers2[i]) {
                    equals = false;
                    break;
                }
            }

            assertFalse(equals);
        }

        @Test
        void testIncorrectAnswerSize() {
            long[] answers1 = simpleQ.getAnswers();
            boolean withinRange = true;

            for (int i = 0; i < answers.length; i++) {
                if (answers1[i] > 7 * consumption_in_wh) {
                    withinRange = false;
                    break;
                }
            }

            assertTrue(withinRange);
        }


    }
}