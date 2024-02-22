package server.api;

import commons.UserSession;
import commons.questions.Question;
import commons.Activity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import server.database.ActivityRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.UserSessionRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

/**
 * A REST API for getting questions
 */
@RestController
@RequestMapping("/api/question")
public class QuestionController {
    private final ActivityRepository activityRepository;
    private final UserSessionRepository userSessionRepository;
    private static Question question;
    private static Map<UUID, Question> map;

    public QuestionController(ActivityRepository activityRepository, UserSessionRepository userSessionRepository) {
        this.activityRepository = activityRepository;
        this.userSessionRepository = userSessionRepository;
        map = new HashMap<>();
    }

    /**
     * A GET endpoint which returns a random question
     *
     * @return a question
     */
    @GetMapping(path = {"", "/"})
    public ResponseEntity<Question> getQuestion() {
        Random randomNumbers = new Random();

        int nextRandomNumber;
        List<Activity> allActivities = activityRepository.findAll();

        int numberOfActivities = allActivities.size();
        Activity[] questionActivities = new Activity[4];

        List<Integer> generatedNumbers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            nextRandomNumber = randomNumbers.nextInt(numberOfActivities);

            while (nextRandomNumber < 0 || nextRandomNumber >= numberOfActivities ||
                    generatedNumbers.contains(nextRandomNumber)) {
                nextRandomNumber = randomNumbers.nextInt(numberOfActivities);
            }
            generatedNumbers.add(nextRandomNumber);
            questionActivities[i] = allActivities.get(nextRandomNumber);
        }

        int type = randomNumbers.nextInt(4);

        while (type < 0 || type >= 4) {
            type = randomNumbers.nextInt(4);
        }

        switch (type) {
            case 0:
                question = Question.generator(new Activity[]{questionActivities[0]}, 0);
                break;
            case 1:
                question = Question.generator(new Activity[]{questionActivities[0]}, 1);
                break;
            case 2:
                question = Question.generator(new Activity[]{questionActivities[0],
                        questionActivities[1], questionActivities[2]}, 2);
                break;
            default:
                question = Question.generator(questionActivities, 3);
                break;
        }
        return ResponseEntity.ok(question);
    }

    /**
     * GET endpoint which returns a question for a specific user session
     *
     * @param uuid id of the user session
     * @param qn   question number of that session
     * @return question for every player in that session
     */
    @GetMapping("/{uuid}/{qn}")
    public synchronized ResponseEntity<Question> getUserSessionQuestion(@PathVariable("uuid") UUID uuid, @PathVariable("qn") int qn) {
        Optional<UserSession> optionalSession = userSessionRepository.findById(uuid);
        if (optionalSession.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        UserSession session = optionalSession.get();
        Question question = null;
        if (qn == session.getQuestionNumber()) {
            question = map.get(session.getId());
        } else if (qn > session.getQuestionNumber()) {
            session.incrementQuestionNumber();
            userSessionRepository.save(session);
            question = getQuestion().getBody();
            map.put(session.getId(), question);
        }
        if (question == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(question);
    }


    /**
     * Set a specific question for testing
     *
     * @param question a specific question for testing
     */
    public static void setQuestion(Question question) {
        QuestionController.question = question;
    }

    /**
     * Getter method for the generated Question
     *
     * @return Question
     */
    public static Question get() {
        return question;
    }
}
