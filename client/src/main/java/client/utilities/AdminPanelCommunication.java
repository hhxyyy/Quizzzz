package client.utilities;

import commons.Activity;
import commons.ActivityImage;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

import java.util.List;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Utility class for communication with the AdminController REST API
 */
public class AdminPanelCommunication extends ServerCommunication {

    /**
     * The constructor for the AdminPanelUtils class
     */
    public AdminPanelCommunication() {super(); }

    /**
     * Gets and returns all the activities from the REST API
     * @return
     */
    public List<Activity> getActivities() {
       return ClientBuilder
                .newClient(new ClientConfig())
                .target(SERVER)
                .path("api/activity")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .get(new GenericType<>() {});
    }

    /**
     * Sends all the activities to the REST API to be saved
     * @param activities list of activities to be saved
     * @return boolean on whether the saving was successful
     */
    public boolean saveActivities(List<Activity> activities) {
        var res = ClientBuilder
            .newClient(new ClientConfig())
            .target(SERVER)
            .path("api/activity")
            .request(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .post(Entity.entity(activities, APPLICATION_JSON));
        return res.getStatus() == 200;
    }

    public boolean saveImage(ActivityImage image) {
        Response res = null;
        res = ClientBuilder
                .newClient(new ClientConfig())
                .target(SERVER)
                .path("api/activity/image")
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(image, APPLICATION_JSON));
        return res.getStatus() == 200;
    }
}
