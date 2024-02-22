package client.utilities;

import commons.User;
import commons.UserSession;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
import org.glassfish.jersey.client.ClientConfig;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.List;
import java.util.UUID;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Utility class for communication with the UserSessionController REST API
 */
public class UserSessionCommunication extends ServerCommunication {

    /**
     * The constructor for the UserSessionUtils class
     */
    public UserSessionCommunication() {super(); }

    /**
     * Get a UserSession by its ID
     * @param id The ID to look for
     * @return The UserSession with the given ID
     */
    public UserSession getSession(UUID id) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("api/usersession/" + id)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {});
    }

    /**
     * Stores a new UserSession in the database
     * @param session The UserSession to be stored
     */
    public boolean makeSession(UserSession session) {
        var res = ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("api/usersession/")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(session, APPLICATION_JSON));
        return res.getStatus() == 200;
    }

    /**
     * Gets all users in a UserSession
     * @param sessionId The ID of the UserSession
     * @return The list of users belonging to the UserSession
     */
    public List<User> getAllUsers(UUID sessionId) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("api/usersession/users/" + sessionId)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {});
    }

    /**
     * Sets a players SessionId to the one of the open UserSession
     * @param user The User to change the SessionId of
     */
    public User setSessionId(User user) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("api/usersession/users/" +  user.getUuid())
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(user, APPLICATION_JSON), User.class);
    }

    /**
     * Gets the UserSession that is currently open
     * @return The UserSession object corresponding to the open UserSession
     */
    public UserSession getOpenSession() {
        List<UserSession> res = ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("api/usersession")
                .queryParam("status", "open")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {});
        if (res.isEmpty()) {
            return null;
        }
        return res.get(0);
    }

    /**
     * Sets a sessions status to the one of the options
     * @param userSession The User to change the SessionId of
     */
    public UserSession setSessionStatus(UserSession userSession) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("api/usersession/setSessionPlaying/" +  userSession.getId())
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .put(Entity.entity(userSession, APPLICATION_JSON), UserSession.class);
    }

    /**
     * Sets a sessions status to the one of the options
     */
    public List<User> getAllSingles() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("api/usersession/getSingles")
                .queryParam("status", "open")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {});
    }

    /**
     * Start a StompSession connection (for WebSocket communication) to the server
     * using the "upgraded" WebSocket protocol (ws://...)
     */
    private StompSession session;

    /**
     * Start a StompSession connection (for WebSocket communication) to the server
     * on the provided destination URL.
     *
     * @param url - Connection destination.
     * @return - StompSession
     */
    private StompSession connect(String url) {
        var client = new StandardWebSocketClient();
        var stomp = new WebSocketStompClient(client);

        stomp.setMessageConverter(new MappingJackson2MessageConverter());

        try {
            return stomp.connect(url, new StompSessionHandlerAdapter() {
            }).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException();
    }

    /**
     * Generic message registration method for use with WebSockets.
     *
     * @param destination - Destination to "subscribe" to.
     * @param consumer    - Consumer of correct object type.
     */
    public <T> void registerForMessages(String destination, Class<T> type, Consumer<T> consumer) {
        session = connect("ws://" + RAW_SERVER + "websocket");
        session.subscribe(destination, new StompFrameHandler() {

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return type;
            }

            @SuppressWarnings("unchecked")
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((T) payload);
            }
        });
    }

    /**
     * Method that sends object o to destination dest
     * @param dest destination
     * @param o object to send
     */
    public void send(String dest, Object o) {
        session.send(dest, o);
    }

}