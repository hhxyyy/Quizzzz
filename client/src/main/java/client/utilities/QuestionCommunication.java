package client.utilities;

import commons.questions.Question;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;

import java.util.UUID;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Utility class for communication with the QuestionController REST API
 */
public class QuestionCommunication extends ServerCommunication {

    /**
     * The constructor for the QuestionUtils class
     */
    public QuestionCommunication() {super(); }

    /**
     * Gets a question from the server.
     * @return A random question from the server.
     */
    public Question getQuestion() {
        return ClientBuilder
                .newClient(new ClientConfig())
                .target(SERVER)
                .path("api/question")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {
                });
    }

    /**
     * Gets a question from the server for a specific session
     * @param sessionId the session for the question
     * @param questionNumber the number of the question in the game
     * @return a Question that is the same for all users in session
     */
    public Question getQuestion(UUID sessionId, int questionNumber) {
        return ClientBuilder
                .newClient(new ClientConfig())
                .target(SERVER)
                .path("api/question/" + sessionId + "/" + questionNumber)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {
                });
    }
}


