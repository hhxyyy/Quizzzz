package client.utilities;

import commons.Answer;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import commons.AnswerResponse;
import org.glassfish.jersey.client.ClientConfig;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;

/**
 * Utility class for communication with the AnswerController REST API
 */
public class AnswerCommunication extends ServerCommunication {

    /**
     * The constructor for the AnswerUtils class
     */
    public AnswerCommunication() {super(); }

    /**
     * Send the chosen answer to the server.
     * @param answer - The chosen answer.
     * @return The Answer with updated information.
     */
    public AnswerResponse sendAnswer(Answer answer) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/answer") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(answer, APPLICATION_JSON), AnswerResponse.class);
    }
}
