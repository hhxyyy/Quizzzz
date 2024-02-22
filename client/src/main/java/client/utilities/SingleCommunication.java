package client.utilities;

import commons.SingleEntry;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.glassfish.jersey.client.ClientConfig;

import java.util.List;

import static client.utilities.ServerCommunication.SERVER;
import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

public class SingleCommunication {
    /**
     * Saved the newUser to the database
     * @param newUser The new User to be added to the database
     * @return The user that was added to the database
     */
    public SingleEntry addUser(SingleEntry newUser) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("api/singles/add")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(newUser, APPLICATION_JSON), SingleEntry.class);
    }

    public List<SingleEntry> getSingles() {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER)
                .path("api/singles/getUsers")
                .queryParam("status", "open")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {
                });
    }
}
