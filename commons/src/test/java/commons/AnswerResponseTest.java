package commons;

import org.junit.jupiter.api.Test;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AnswerResponseTest {

    @Test
    void testConstructor1() {
        long uuid = UUID.randomUUID().getLeastSignificantBits();
        Answer ans = new Answer(uuid, 5);
        assertNotNull(new AnswerResponse(ans, true, 0, false));

    }

    @Test
    void testConstructor2() {
        long uuid = UUID.randomUUID().getLeastSignificantBits();
        Answer ans = new Answer(uuid, 5, 5.0f, false);
        AnswerResponse answerResponse = new AnswerResponse(ans, true, 0, false);
        assertEquals(answerResponse.getUuid(), uuid);
        assertEquals(answerResponse.getAnswer(), 5);
        assertEquals(answerResponse.getTimeLeft(), 5.0);
        assertTrue(answerResponse.isCorrect());
        assertEquals(answerResponse.getNewScore(), 0);
    }

    @Test
    void testConstructor3() {
        long uuid = UUID.randomUUID().getLeastSignificantBits();
        AnswerResponse answerResponse = new AnswerResponse(uuid, 5, 5.0f, true, 0, false);
        assertEquals(answerResponse.getUuid(), uuid);
        assertEquals(answerResponse.getAnswer(), 5);
        assertEquals(answerResponse.getTimeLeft(), 5.0);
        assertTrue(answerResponse.isCorrect());
        assertEquals(answerResponse.getNewScore(), 0);
    }

    @Test
    void testIsCorrect() {
        long uuid = UUID.randomUUID().getLeastSignificantBits();
        AnswerResponse answerResponse1 = new AnswerResponse(uuid, 5, 5.0f, true, 0, false);
        AnswerResponse answerResponse2 = new AnswerResponse(uuid, 5, 5.0f, false, 0, false);
        assertTrue(answerResponse1.isCorrect());
        assertFalse(answerResponse2.isCorrect());
    }

    @Test
    void testSetCorrect() {
        long uuid = UUID.randomUUID().getLeastSignificantBits();
        AnswerResponse answerResponse = new AnswerResponse(uuid, 5, 5.0f, true, 0, false);
        assertTrue(answerResponse.isCorrect());
        answerResponse.setCorrect(false);
        assertFalse(answerResponse.isCorrect());
    }

    @Test
    void testGetNewScore() {
        long uuid = UUID.randomUUID().getLeastSignificantBits();
        AnswerResponse answerResponse1 = new AnswerResponse(uuid, 5, 5.0f, true, 0, false);
        AnswerResponse answerResponse2 = new AnswerResponse(uuid, 5, 5.0f, false, 100, false);
        assertEquals(answerResponse1.getNewScore(), 0);
        assertEquals(answerResponse2.getNewScore(), 100);
    }

    @Test
    void testSetNewScore() {
        long uuid = UUID.randomUUID().getLeastSignificantBits();
        AnswerResponse answerResponse = new AnswerResponse(uuid, 5, 5.0f, true, 0, false);
        assertEquals(answerResponse.getNewScore(), 0);
        answerResponse.setNewScore(100);
        assertEquals(answerResponse.getNewScore(), 100);
    }
}