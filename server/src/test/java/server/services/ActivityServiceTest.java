package server.services;

import commons.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.ActivityRepository;
import server.stubs.TestActivityRepository;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;



class ActivityServiceTest {
    private ActivityRepository activityRepository;
    private ActivityService activityService;
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
    }

    @Test
    void testConstructor() {
        ActivityService testService = new ActivityService(activityRepository);
        assertNotNull(testService);
    }

    @Test
    void testList() {
        assertEquals(dbActivities, activityService.list());
    }

    @Test
    void testSave() throws MalformedURLException {
        Activity activity3 = new Activity("activity-3", "path/to/image3.png", "activity3", 300, new URL("https://www.google.com"));
        Activity activity4 = new Activity("activity-4", "path/to/image4.png", "activity4", 400, new URL("https://www.yahoo.com"));
        List<Activity> newActivities = new ArrayList<>();
        newActivities.add(activity3);
        newActivities.add(activity4);

        assertEquals(newActivities, activityService.save(newActivities));

        List<Activity> mergedList = Stream.concat(dbActivities.stream(), newActivities.stream()).collect(Collectors.toList());
        assertEquals(mergedList, activityService.list());
    }

    @Test
    void testDelete() {
        activityService.delete();
        assertEquals(Collections.emptyList(), activityService.list());
    }
}