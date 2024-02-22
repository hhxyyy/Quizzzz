package commons.questions;

import commons.Activity;
import commons.questions.ComparisonQuestion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ComparisonQuestionTest {
    String questionText;
    UUID id;
    String[] answers;
    String path;
    int correctIndex;
    ComparisonQuestion comp;
    URL myURL = new URL("http://example.com/");
    Activity act1 = new Activity("this", "path", "title1", 20, myURL);
    Activity act2 = new Activity("that", "path", "title2", 200, myURL);
    Activity act3 = new Activity("them", "path", "title3", 100, myURL);
    Activity[] activities;
    List<String> allImagePaths = new ArrayList<>();

    ComparisonQuestionTest() throws MalformedURLException {
    }

    @BeforeEach
    void setup() {
        String ml = "most";
        questionText = "Which takes the " + ml + " energy";
        id = UUID.randomUUID();
        answers = new String[]{"this", "that", "them"};
        correctIndex = 1;
        path = "path";
        allImagePaths.add("path");
        allImagePaths.add("path");
        allImagePaths.add("path");
        comp = new ComparisonQuestion(questionText, id, path, answers, correctIndex, allImagePaths);
        activities = new Activity[]{act1, act2, act3};

    }

    @Test
    void testConstructor() {
        assertNotNull(comp);
    }

    @Test
    void testGetAnswers() {
        assertEquals(answers, comp.getAnswers());
    }

    @Test
    void testSetAnswers() {
        String[] newAnswers = new String[]{"a", "b", "c"};
        comp.setAnswers(newAnswers);
        assertEquals(newAnswers, comp.getAnswers());
    }

    @Test
    void testGetWrongAnswers() {
        String[] newAnswers = new String[]{"a", "b", "c"};
        assertNotEquals(newAnswers, comp.getAnswers());
    }

    @Test
    void testGetCorrectIndex() {
        assertEquals(correctIndex, comp.getCorrectIndex());
    }

    @Test
    void testSetCorrectIndex() {
        int newCorrectIndex = 2;
        comp.setCorrectIndex(newCorrectIndex);
        assertEquals(newCorrectIndex, comp.getCorrectIndex());
    }

    @Test
    void testGetAllImagePath() {
        ComparisonQuestion comp1 = new ComparisonQuestion(questionText, id, path, answers, correctIndex, allImagePaths);
        assertEquals(allImagePaths, comp1.getAllImagePaths());
    }

    @Test
    void testGetAllImagePathGenerated() {
        ComparisonQuestion comp1 = ComparisonQuestion.questionGenerator(activities);
        assertEquals(allImagePaths, comp1.getAllImagePaths());
    }

    @Test
    void testGetAllImagePathsNotNull() {
        ComparisonQuestion comp1 = new ComparisonQuestion(questionText, id, path, answers, correctIndex, allImagePaths);
        assertNotNull(comp1.getAllImagePaths());
    }

    @Test
    void testSetAllImagePaths() {
        ComparisonQuestion comp1 = new ComparisonQuestion(questionText, id, path, answers, correctIndex, allImagePaths);
        allImagePaths.add("new_path");
        comp1.setAllImagePaths(allImagePaths);
        assertEquals(allImagePaths, comp1.getAllImagePaths());
    }

    @Test
    void testEquals() {
        ComparisonQuestion comp3 = new ComparisonQuestion(questionText, id, path, answers, correctIndex, allImagePaths);
        assertEquals(comp, comp3);
    }

    @Test
    void testEqualsSameObject() {
        assertEquals(comp, comp);
    }

    @Test
    void testNotEqualsQId() {
        UUID newId = UUID.randomUUID();
        while (newId.equals(id)) {
            newId = UUID.randomUUID();
        }
        ComparisonQuestion comp3 = new ComparisonQuestion(questionText, newId, path, answers, correctIndex, allImagePaths);
        assertNotEquals(comp, comp3);
    }

    @Test
    void testNotEqualsQuestionText() {
        ComparisonQuestion comp3 = new ComparisonQuestion("Question", id, path, answers, correctIndex, allImagePaths);
        assertNotEquals(comp, comp3);
    }

    @Test
    void testNotEqualsIndex() {
        ComparisonQuestion comp3 = new ComparisonQuestion(questionText, id, path, answers, 3, allImagePaths);
        assertNotEquals(comp, comp3);
    }

    @Test
    void testNotEqualsAnswers() {
        String[] newAnswers = new String[]{"a", "b", "c"};
        ComparisonQuestion comp3 = new ComparisonQuestion(questionText, id, path, newAnswers,
                correctIndex, allImagePaths);
        assertNotEquals(comp, comp3);
    }

    @Test
    void testNotEqualsImagePath() {
        ComparisonQuestion comp3 = new ComparisonQuestion(questionText, id, "image_path",
                answers, correctIndex, allImagePaths);
        assertNotEquals(comp, comp3);
    }

    @Test
    void testNotEqualsAllImagePaths() {
        List<String> newImages = new ArrayList<>();
        newImages.add("path1");
        newImages.add("path2");
        ComparisonQuestion comp3 = new ComparisonQuestion(questionText, id, path,
                answers, correctIndex, newImages);
        assertNotEquals(comp, comp3);
    }

    @Test
    void testHashCode() {
        ComparisonQuestion comp3 = new ComparisonQuestion(questionText, id, path, answers, correctIndex, allImagePaths);
        assertEquals(comp.hashCode(), comp3.hashCode());
    }

    @Test
    void testHashCodeSameObject() {
        assertEquals(comp.hashCode(), comp.hashCode());
    }

    @Test
    void testNotEqualsHashCodeQuestionText() {
        ComparisonQuestion comp3 = new ComparisonQuestion("Question", id, path, answers,
                3, allImagePaths);
        assertNotEquals(comp.hashCode(), comp3.hashCode());
    }

    @Test
    void testNotEqualsHashCodeId() {
        UUID newId = UUID.randomUUID();
        while (newId.equals(id)) {
            newId = UUID.randomUUID();
        }
        ComparisonQuestion comp3 = new ComparisonQuestion(questionText, newId, path, answers, correctIndex, allImagePaths);
        assertNotEquals(comp.hashCode(), comp3.hashCode());
    }

    @Test
    void testNotEqualsHashCodeAnswers() {
        String[] newAnswers = new String[]{"a", "b", "c"};
        ComparisonQuestion comp3 = new ComparisonQuestion(questionText, id, path, newAnswers, correctIndex, allImagePaths);
        assertNotEquals(comp.hashCode(), comp3.hashCode());
    }

    @Test
    void testNotEqualsHashCodeCorrectIndex() {
        ComparisonQuestion comp3 = new ComparisonQuestion(questionText, id, path, answers, 3, allImagePaths);
        assertNotEquals(comp.hashCode(), comp3.hashCode());
    }

    @Test
    void testNotEqualsHashCodeImagePath() {
        ComparisonQuestion comp3 = new ComparisonQuestion(questionText, id, "image_path", answers,
                correctIndex, allImagePaths);
        assertNotEquals(comp.hashCode(), comp3.hashCode());
    }

    @Test
    void testNotEqualsHashCodeAllImagePaths() {
        List<String> newImages = new ArrayList<>();
        newImages.add("path1");
        newImages.add("path2");
        ComparisonQuestion comp3 = new ComparisonQuestion(questionText, id, path,
                answers, correctIndex, newImages);
        assertNotEquals(comp.hashCode(), comp3.hashCode());
    }

    @Test
    void testToString() {
        String expected = comp.toString();
        assertTrue(expected.contains("Which takes the"));
        assertTrue(expected.contains("questionId"));
        assertTrue(expected.contains("this"));
        assertTrue(expected.contains("that"));
        assertTrue(expected.contains("them"));
    }

    @Test
    void testToStringSameObject() {
        assertEquals(comp.toString(), comp.toString());
    }

    @Test
    void testCorrectAnswerIndex() {
        ComparisonQuestion comp3 = ComparisonQuestion.questionGenerator(activities);
        List<String> actualAnswers = Arrays.asList(comp3.getAnswers());
        int correctIndex = comp3.getCorrectIndex();
        if (comp3.toString().contains("less")) {
            assertEquals(correctIndex, actualAnswers.indexOf("title1"));
        } else if (comp3.toString().contains("more")) {
            assertEquals(correctIndex, actualAnswers.indexOf("title2"));
        }
    }
}