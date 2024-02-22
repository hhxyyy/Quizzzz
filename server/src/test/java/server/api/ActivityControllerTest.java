package server.api;

import commons.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import server.database.ActivityRepository;
import server.services.ActivityService;
import server.stubs.TestActivityRepository;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActivityControllerTest {

    private ActivityRepository activityRepository;
    private ActivityService activityService;
    private ActivityController activityController;
    private List<Activity> dbActivities;

    @BeforeEach
    void setup() throws MalformedURLException {
        activityRepository = new TestActivityRepository();
        Activity activity1 = new Activity("activity-1", "path/to/image1.png", "activity1", 100, new URL("https://www.google.com"));
        Activity activity2 = new Activity("activity-2", "path/to/image2.png", "activity2", 200, new URL("https://www.yahoo.com"));
        dbActivities = new ArrayList<>();
        dbActivities.add(activity1);
        dbActivities.add(activity2);
        activityRepository.save(activity1);
        activityRepository.save(activity2);

        activityService = new ActivityService(activityRepository);
        activityController = new ActivityController(activityService);
    }

    @Test
    void testGetActivities() {
        ResponseEntity<List<Activity>> activitiesResponse = activityController.getActivities();
        List<Activity> activities = activitiesResponse.getBody();
        assertEquals(activities, dbActivities);
    }

    @Test
    void testSaveActivities() throws MalformedURLException {
        Activity activity3 = new Activity("activity-3", "path/to/image3.png", "activity3", 300, new URL("https://www.google.com"));
        Activity activity4 = new Activity("activity-4", "path/to/image4.png", "activity4", 400, new URL("https://www.yahoo.com"));
        List<Activity> newActivities = new ArrayList<>();
        newActivities.add(activity3);
        newActivities.add(activity4);

        ResponseEntity<List<Activity>> activitiesResponse = activityController.saveActivities(newActivities);
        List<Activity> activities = activitiesResponse.getBody();
        assertEquals(activities, newActivities);

        ResponseEntity<List<Activity>> getActivitiesResponse = activityController.getActivities();
        List<Activity> getActivities = getActivitiesResponse.getBody();
        assertEquals(getActivities, newActivities);
    }
}