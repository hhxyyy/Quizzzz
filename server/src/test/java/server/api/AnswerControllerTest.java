package server.api;

import commons.Activity;
import commons.Answer;
import commons.AnswerResponse;
import commons.User;
import commons.questions.ComparisonQuestion;
import commons.questions.InsteadOfQuestion;
import commons.questions.SimpleQuestionMCQ;
import commons.questions.SimpleQuestionOpen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.UserSessionRepository;
import server.stubs.TestActivityRepository;
import server.stubs.TestUserRepository;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

class AnswerControllerTest {

    private AnswerController answerController;
    private TestUserRepository users;
    private TestActivityRepository activityRepository;
    private UserSessionRepository sessionRepository;
    private QuestionController questionController;
    private ComparisonQuestion cmpQuestion;
    private InsteadOfQuestion ioQuestion;
    private SimpleQuestionMCQ mcQuestion;
    private SimpleQuestionOpen openQuestion;

    @BeforeEach
    void setup() {
        UUID uuid = UUID.randomUUID();
        users  = new TestUserRepository();
        users.save(new User("X", uuid));

        answerController = new AnswerController(users);

        activityRepository = new TestActivityRepository();
        Activity a = new Activity("activity0", null, "Activity0", 15, null);
        Activity b = new Activity("activity1", null, "Activity1", 100, null);
        Activity c = new Activity("activity2", null, "Activity2", 1800, null);
        Activity d = new Activity("activity3", null, "Activity3", 2000, null);
        activityRepository.save(a);

        questionController = new QuestionController(activityRepository, sessionRepository);
        Activity[] activities4 = new Activity[]{a, b, c, d};

        String imgPath = "/path/img.png";
        List<String> allImgPaths = List.of(imgPath, imgPath, imgPath);

        cmpQuestion = new ComparisonQuestion("Which takes more energy?", uuid, imgPath, new String[]{a.getTitle(), b.getTitle(), c.getTitle()}, 0, allImgPaths);
        ioQuestion = new InsteadOfQuestion("Instead of " + b.getTitle() + ", you can save energy by:", uuid, imgPath, new String[]{a.getTitle(), b.getTitle(), c.getTitle()}, 0);
        // ioQuestion = (InsteadOfQuestion) InsteadOfQuestion.questionGenerator(activities4);
        mcQuestion = new SimpleQuestionMCQ("How much energy does Activity1 take?", uuid, imgPath, new long[]{100, 80, 120}, 0);
        openQuestion = new SimpleQuestionOpen("How much energy does Activity4 take?", uuid, imgPath, 1500d, 2500d);

        QuestionController.setQuestion(mcQuestion);
    }

    @Test
    void testCannotSubmitNullAnswer() {
        var actual = answerController.answer(null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    void testOkStatusCode() {
        var actual = answerController.answer(new Answer(0, 1, 5.0f, false));
        assertEquals(OK, actual.getStatusCode());
    }


    @Test
    void testReturnAnswer1() {
        QuestionController.setQuestion(openQuestion);
        var actual = answerController.answer(new Answer(0, 1, 5.0f, false));
        var expected = new AnswerResponse(new Answer(0, 1, 5.0f, false), false, 0, false);
        assertEquals(expected, actual.getBody());
    }

    @Test
    void testReturnAnswer2() {
        QuestionController.setQuestion(openQuestion);
        var actual = answerController.answer(new Answer(0, 2000, 5.0f, false));
        var expected = new AnswerResponse(new Answer(0, 2000, 5.0f, false), true, 25, false);
        assertEquals(expected, actual.getBody());
    }

    @Test
    void testRightAnswerComparisonQuestion() {
        QuestionController.setQuestion(cmpQuestion);
        answerController.answer(new Answer(0, 0, 5.0f, false));
        User u = users.getById(1L);
        assertEquals(u.getScore(), (int) (5.0f * 5) );
    }

    @Test
    void testWrongAnswerComparisonQuestion() {
        QuestionController.setQuestion(cmpQuestion);
        answerController.answer(new Answer(0, 1, 5.0f, false));
        User u = users.getById(0L);
        assertEquals(u.getScore(), 0);
    }

    @Test
    void testRightAnswerInsteadOfQuestion() {
        QuestionController.setQuestion(ioQuestion);
        answerController.answer(new Answer(0, 0, 5.0f, false));
        User u = users.getById(1L);
        assertEquals(u.getScore(), (int) (5.0f * 5) );
    }

    @Test
    void testWrongAnswerInsteadOfQuestion() {
        QuestionController.setQuestion(ioQuestion);
        answerController.answer(new Answer(0, 1, 5.0f, false));
        User u = users.getById(0L);
        assertEquals(u.getScore(), 0 );
    }

    @Test
    void testRightAnswerMCQuestion() {
        QuestionController.setQuestion(mcQuestion);
        answerController.answer(new Answer(0, 0, 5.0f, false));
        User u = users.getById(1L);
        assertEquals(u.getScore(), (int) (5.0f * 5) );
    }

    @Test
    void testWrongAnswerMCQuestion() {
        QuestionController.setQuestion(mcQuestion);
        answerController.answer(new Answer(0, 1, 5.0f, false));
        User u = users.getById(0L);
        assertEquals(u.getScore(), 0 );
    }

    @Test
    void testRightAnswerOpenQuestion() {
        QuestionController.setQuestion(openQuestion);
        answerController.answer(new Answer(0, 2000, 5.0f, false));
        User u = users.getById(1L);
        assertEquals(u.getScore(), (int) (5.0f * 5) );
    }

    @Test
    void testWrongAnswerOpenQuestion() {
        QuestionController.setQuestion(openQuestion);
        answerController.answer(new Answer(0, 1000, 5.0f, false));
        User u = users.getById(0L);
        assertEquals(u.getScore(), 0 );
    }

    @Test
    void testDoublePointJokerTest() {
        QuestionController.setQuestion(openQuestion);
        Answer answer = new Answer(0, 1600, 5.0f, true);
        answer.setPointDouble(true);
        answerController.answer(answer);
        User u = users.getById(1L);
        assertEquals(u.getScore(), (int) (5.0f * 5 * 2)  );
    }
}