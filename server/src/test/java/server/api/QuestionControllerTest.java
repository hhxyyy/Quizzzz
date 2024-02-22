package server.api;

import commons.Activity;
import commons.questions.Question;
import commons.questions.SimpleQuestionOpen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.database.UserSessionRepository;
import server.stubs.TestActivityRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.springframework.http.HttpStatus.OK;

class QuestionControllerTest {

    private TestActivityRepository activityRepository;
    private QuestionController questionController;
    private UserSessionRepository sessionRepository;

    ResponseEntity<Question> question;

    @BeforeEach
    void setup() {
        activityRepository = new TestActivityRepository();
        Activity one = new Activity("activity1", "path.png", "One", 100, null);
        Activity two = new Activity("activity2", "path.png", "Two", 200, null);
        Activity three = new Activity("activity3", "path.png", "Three", 300, null);
        Activity four = new Activity("activity4", "path.png", "Four", 400, null);

        activityRepository.save(one);
        activityRepository.save(two);
        activityRepository.save(three);
        activityRepository.save(four);

        questionController = new QuestionController(activityRepository, sessionRepository);
    }

    @Test
    void testGetQuestionStatusCode() {
        question = questionController.getQuestion();
        assertEquals(OK, question.getStatusCode());
    }

    @Test
    void testGetQuestion() {
        question = questionController.getQuestion();
        Question realQuestion;
        realQuestion = question.getBody();
        assertNotNull(realQuestion);
    }

    @Test
    void testGetQuestionString() {
        question = questionController.getQuestion();
        Question realQuestion;
        realQuestion = question.getBody();
        String expected = realQuestion.toString();
        assertTrue(expected.contains("path.png"));
    }

    @Test
    void testSetQuestion() {
        question = questionController.getQuestion();
        Question realQuestion;
        realQuestion = question.getBody();
        UUID uuid = UUID.randomUUID();
        SimpleQuestionOpen openQuestion = new SimpleQuestionOpen("How much energy does One take?",
                uuid, "path", 1000, 2000);
        questionController.setQuestion(openQuestion);
        assertNotEquals(realQuestion, questionController.get());
    }

    @Test
    void testGet() {
        question = questionController.getQuestion();
        Question realQuestion;
        realQuestion = question.getBody();
        Question getQuestion = questionController.get();
        assertEquals(realQuestion, getQuestion);
    }

    @Test
    void testGetWrong() {
        question = questionController.getQuestion();
        Question realQuestion;
        realQuestion = question.getBody();
        UUID uuid = UUID.randomUUID();
        SimpleQuestionOpen openQuestion = new SimpleQuestionOpen("How much energy does One take?",
                uuid, "path", 1000, 2000);
        assertNotEquals(realQuestion, openQuestion);
    }
}