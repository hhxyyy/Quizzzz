package server.api;

import commons.AnswerResponse;
import commons.Answer;
import commons.questions.ComparisonQuestion;
import commons.questions.InsteadOfQuestion;
import commons.questions.Question;
import commons.questions.SimpleQuestionOpen;
import commons.questions.SimpleQuestionMCQ;

import commons.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.UserRepository;

import java.util.Optional;

/**
 * A REST Controller for checking whether an answer is correct and assigning points
 */
@RestController
@RequestMapping("/api/answer")
public class AnswerController {

    private final UserRepository users;

    /**
     * Constructor for the AnswerController
     * @param users an instance of the UserRepository
     */
    public AnswerController(UserRepository users) {
        this.users = users;
    }

    /**
     * A POST endpoint to check whether an answer is correct and assign points
     * @param answer an Answer instance
     * @return an instance of the AnswerResponse class
     */
    @PostMapping(path = {"", "/"})
    public ResponseEntity<AnswerResponse> answer(@RequestBody Answer answer) {
        if (answer == null) {
            return ResponseEntity.badRequest().build();
        }

        boolean isCorrect = false; // answer status
        
        //Process answer here
        int choice = answer.getAnswer();
        long user = answer.getUuid();
        boolean isPointDouble = answer.getPointDouble();

        //Check whether choice is correct according to the question type
        Question q = QuestionController.get();
        if (q == null) {
            return ResponseEntity.badRequest().build();
        }
        if (q instanceof SimpleQuestionOpen) {
            double[] correctRange = new double[2];
            correctRange[0] = ((SimpleQuestionOpen) q).getCorrectMin();
            correctRange[1] = ((SimpleQuestionOpen) q).getCorrectMax();
            if (choice >= correctRange[0] && choice <= correctRange[1]) {
                isCorrect = true;
            }
        } else if (q instanceof SimpleQuestionMCQ) {
            if (choice == ((SimpleQuestionMCQ) q).getCorrectIndex()) {
                isCorrect = true;
            }
        } else if (q instanceof InsteadOfQuestion) {
            if (choice == ((InsteadOfQuestion) q).getCorrectIndex()) {
                isCorrect = true;
            }
        } else {
            if (choice == ((ComparisonQuestion) q).getCorrectIndex()) {
                isCorrect = true;
            }
        }
        // check the question type. If the question is not an estimation question, the timeLeft field should be present
        double timeLeftOptional = answer.getTimeLeft();
        //-------------------

//        if (timeLeftOptional.isEmpty() /*And question type is not estimation type*/) {
//            // return bad request
//            return ResponseEntity.badRequest().build();
//        }

        double timeLeft = timeLeftOptional;

        Optional<User> userOptional = users.findById(user);
        if (!users.existsById(user)) {
            System.err.println("USER UUID IS: " + user + " AND IS NOT FOUND.");
            return ResponseEntity.badRequest().build();
        }

        User u = userOptional.get();
        // 10 seconds per question for now. This should be a static variable set in a Game class

        int newScore = u.getScore();

        if (isCorrect) {
            //If the user use double point joker, then the score * 2
            if (isPointDouble) {
                newScore += (int) timeLeft * 5 * 2;
            } else {
                newScore += (int) timeLeft * 5;
            }
            u.setScore(newScore);
            users.save(u);
        }




        return ResponseEntity.ok(new AnswerResponse(answer, isCorrect, newScore, isPointDouble));
    }
}
