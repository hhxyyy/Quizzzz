package server.services;

import commons.Activity;
import org.springframework.stereotype.Service;
import server.database.ActivityRepository;

import java.util.List;

@Service
public class ActivityService {
    private ActivityRepository activityRepository;

    /**
     * Constructor for the ActivityService
     * @param activityRepository an instance of the ActivityRepository
     */
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /**
     * Returns all te activities in the database
     * @return list of all activities in the database
     */
    public List<Activity> list() {
        return activityRepository.findAll();
    }

    /**
     * Saves all the activities in the database
     * @param activities list of all activities to be saved
     * @return the original list of activities
     */
    public List<Activity> save(List<Activity> activities) {
        return activityRepository.saveAll(activities);
    }

    /**
     * Deletes all activities from the database
     */
    public void delete() {
        activityRepository.deleteAll();
    }
}
