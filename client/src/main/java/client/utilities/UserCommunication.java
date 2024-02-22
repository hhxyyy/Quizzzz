package client.utilities;

import commons.SingleEntry;
import commons.User;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Utility class for communication with the UserController REST API
 */
public class UserCommunication extends ServerCommunication {

    /**
     * The constructor for the UserUtils class
     */
    public UserCommunication() {super(); }


    /**
     * Saved the newUser to the database
     * @param newUser The new User to be added to the database
     * @return The user that was added to the database
     */
    public User addUser(User newUser) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("api/users")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(newUser, APPLICATION_JSON), User.class);
    }

    /**
     * Saved the Single player to the database
     * @param newUser The new User to be added to the database
     * @return The user that was added to the database
     */
    public SingleEntry addSingle(SingleEntry newUser) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("api/users/addSingle")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(newUser, APPLICATION_JSON), SingleEntry.class);
    }

    /**
     * Retrieves a user based on a unique UUID sent to the server
     * @param uuid The uuid of the user
     * @return The user with the given uuid
     */
    public User getUser(long uuid) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("api/users/" + uuid)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {});
    }

    /**
     * deletes a user from the database if the exit the game
     * @param user
     */
    public void delete(User user) {
        ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("api/users/delete/" + user.getUuid())
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get();
    }

    /**
     * Renames given user with the given string.
     * @param uuid - uuid of the user being renamed
     * @param newUsername - username to rename the user to
     * @return - renamed user
     */
    public User renameUser(long uuid, String newUsername) {
       return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("api/users/" + uuid + "/" + newUsername)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {});
    }

    /**
     * Renames given user with the given string.
     * @param uuid - uuid of the user being renamed
     * @return - renamed user
     */
    public User resetUser(long uuid) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("api/users/reset/" + uuid)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {});
    }
}
