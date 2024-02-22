package commons.questions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import commons.Activity;
import commons.questions.InsteadOfQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class InsteadOfQuestionTest {
    String path;
    public String[] answers;
    public int correctIndex;
    String questionText;
    UUID id;
    InsteadOfQuestion instead;

    @BeforeEach
    void setup() {
        questionText = "Instead of a a you could save energy by...";
        id = UUID.randomUUID();
        path = "path";
        answers = new String[]{"a", "b", "c", "d"};
        correctIndex = 1;
        instead = new InsteadOfQuestion(questionText, id, path, answers, correctIndex);
    }

    @Test
    void testConstructor() {
        assertNotNull(instead);
    }

    @Test
    void testGetAnswers() {
        assertEquals(answers, instead.getAnswers());
    }

    @Test
    void testGetWrongAnswers() {
        String[] newAnswer = new String[]{"e", "f", "g", "h"};
        assertNotEquals(newAnswer, instead.getAnswers());
    }

    @Test
    void testSetAnswers() {
        String[] tester = new String[]{"e", "f", "g", "h"};
        instead.setAnswers(tester);
        assertEquals(tester, instead.getAnswers());
    }

    @Test
    void testGetCorrectIndex() {
        assertEquals(correctIndex, instead.getCorrectIndex());
    }

    @Test
    void testSetCorrectIndex() {
        int newCorrectIndex = 2;
        instead.setCorrectIndex(newCorrectIndex);
        assertEquals(newCorrectIndex, instead.getCorrectIndex());
    }

    @Test
    void testEquals() {
        InsteadOfQuestion instead1 = new InsteadOfQuestion(questionText, id, path, answers, correctIndex);
        assertEquals(instead, instead1);
    }

    @Test
    void testEqualsSameObject() {
        assertEquals(instead, instead);
    }

    @Test
    void testNotEqualsId() {
        UUID newId = UUID.randomUUID();
        while (newId.equals(id)) {
            newId = UUID.randomUUID();
        }
        InsteadOfQuestion instead1 = new InsteadOfQuestion(questionText, newId, path, answers, correctIndex);
        assertNotEquals(instead, instead1);
    }

    @Test
    void testNotEqualsCorrectIndex() {
        InsteadOfQuestion instead1 = new InsteadOfQuestion(questionText, id, path, answers, 2);
        assertNotEquals(instead, instead1);
    }


    @Test
    void testNotEqualsQuestionText() {
        InsteadOfQuestion instead1 = new InsteadOfQuestion("Question", id, path, answers, correctIndex);
        assertNotEquals(instead, instead1);
    }

    @Test
    void testNotEqualsAnswers() {
        String[] newAnswer = new String[]{"e", "f", "g", "h"};
        InsteadOfQuestion instead1 = new InsteadOfQuestion(questionText, id, path, newAnswer, correctIndex);
        assertNotEquals(instead, instead1);
    }

    @Test
    void testNotEqualsImagePath() {
        InsteadOfQuestion instead1 = new InsteadOfQuestion(questionText, id, "image_path", answers, correctIndex);
        assertNotEquals(instead, instead1);
    }

    @Test
    void testHashCode() {
        InsteadOfQuestion instead1 = new InsteadOfQuestion(questionText, id, path, answers, correctIndex);
        assertEquals(instead.hashCode(), instead1.hashCode());
    }

    @Test
    void testHashCodeSameObject() {
        assertEquals(instead.hashCode(), instead.hashCode());
    }

    @Test
    void testNotEqualsHashCodeQuestionText() {
        InsteadOfQuestion instead1 = new InsteadOfQuestion("Question", id, path, answers, correctIndex);
        assertNotEquals(instead.hashCode(), instead1.hashCode());
    }

    @Test
    void testNotEqualsHashCodeId() {
        UUID newId = UUID.randomUUID();
        while (newId.equals(id)) {
            newId = UUID.randomUUID();
        }
        InsteadOfQuestion instead1 = new InsteadOfQuestion(questionText, newId, path, answers, correctIndex);
        assertNotEquals(instead.hashCode(), instead1.hashCode());
    }

    @Test
    void testNotEqualsHashCodeAnswers() {
        String[] newAnswer = new String[]{"e", "f", "g", "h"};
        InsteadOfQuestion instead1 = new InsteadOfQuestion(questionText, id, path, newAnswer, correctIndex);
        assertNotEquals(instead.hashCode(), instead1.hashCode());
    }

    @Test
    void testNotEqualsHashCodeCorrectIndex() {
        InsteadOfQuestion instead1 = new InsteadOfQuestion(questionText, id, path, answers, 3);
        assertNotEquals(instead.hashCode(), instead1.hashCode());
    }

    @Test
    void testNotEqualsHashCodeImagePath() {
        InsteadOfQuestion instead1 = new InsteadOfQuestion(questionText, id, "image_path", answers, correctIndex);
        assertNotEquals(instead.hashCode(), instead1.hashCode());
    }

    @Test
    void testToString() {
        System.out.println(instead);
        String expected = instead.toString();
        assertTrue(expected.contains("Instead of a a you could save energy by..."));
        assertTrue(expected.contains("questionId"));
        assertTrue(expected.contains("1"));
    }

    @Test
    void testToStringSameObject() {
        assertEquals(instead.toString(), instead.toString());
    }

    @Nested
    class GeneratedInsteadOfQuestionTests {

        URL source;
        Activity testActivity1;
        Activity testActivity2;
        Activity testActivity3;
        Activity testActivity4;
        Activity[] activities;
        InsteadOfQuestion generatedInstead;
        List<String> generatedInsteadAnswers;

        @BeforeEach
        void setup() throws MalformedURLException {
            source = new URL("https://www.example.com/");
            testActivity1 = new Activity("id1", "path/to/image1.jpeg", "title1", 20, source);
            testActivity2 = new Activity("id2", "path/to/image2.jpeg", "title2", 40, source);
            testActivity3 = new Activity("id3", "path/to/image3.jpeg", "title3", 30, source);
            testActivity4 = new Activity("id4", "path/to/image4.jpeg", "title4", 60, source);

            activities = new Activity[]{testActivity1, testActivity2, testActivity3, testActivity4};

            generatedInstead = InsteadOfQuestion.questionGenerator(activities);
            generatedInsteadAnswers = Arrays.asList(generatedInstead.getAnswers());
        }

        @Test
        void testAnswersExclusion() {
            assertFalse(generatedInsteadAnswers.contains("title3"));
        }

        @Test
        void testCorrectAnswerIndex() {
            assertEquals(generatedInstead.getCorrectIndex(), generatedInsteadAnswers.indexOf("title1"));
        }
    }
}