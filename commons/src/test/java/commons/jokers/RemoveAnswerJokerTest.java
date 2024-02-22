package commons.jokers;

import commons.questions.SimpleQuestionMCQ;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RemoveAnswerJokerTest {

    @Test
    void testConstructor() {
        RemoveAnswerJoker joker = new RemoveAnswerJoker();
        assertNotNull(joker);
    }

    @Test
    void getRemoveIndex() {
        RemoveAnswerJoker joker = new RemoveAnswerJoker();
        String questionText = "Asking?";
        UUID id = UUID.randomUUID();
        String path = "path";
        long[] answers = new long[]{1, 2, 3};
        int correctIndex = 0;
        SimpleQuestionMCQ mcq = new SimpleQuestionMCQ(questionText, id, path, answers, correctIndex);
        int index = joker.getRemoveIndex(mcq);
        assertTrue(index < 3);
        assertTrue(index > 0);
    }

    @Test
    void isRemoveAnswer() {
        RemoveAnswerJoker joker = new RemoveAnswerJoker();
        assertFalse(joker.isRemoveAnswer());
    }

    @Test
    void setRemoveAnswer() {
        RemoveAnswerJoker joker = new RemoveAnswerJoker();
        joker.setRemoveAnswer(true);
        assertTrue(joker.isRemoveAnswer());
    }

    @Test
    void testEquals() {
        RemoveAnswerJoker joker = new RemoveAnswerJoker();
        RemoveAnswerJoker joker1 = new RemoveAnswerJoker();
        assertTrue(joker.equals(joker1));
    }

    @Test
    void testHashCode() {
        RemoveAnswerJoker joker = new RemoveAnswerJoker();
        RemoveAnswerJoker joker1 = new RemoveAnswerJoker();
        assertEquals(joker.hashCode(), joker1.hashCode());
    }

    @Test
    void testToString() {
        RemoveAnswerJoker joker = new RemoveAnswerJoker();
        String e = joker.toString();
        assertTrue(e.contains("RemoveAnswerJoker"));
        assertTrue(e.contains("removeAnswer=false"));
    }
}