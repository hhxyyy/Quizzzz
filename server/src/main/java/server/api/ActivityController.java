package server.api;

import commons.Activity;
import commons.ActivityImage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.services.ActivityService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * A REST controller for getting and saving activities in the database
 */
@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    private ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    /**
     * Gets and returns activities from the database
     * @return A ResponseEntity containing all the activities
     */
    @GetMapping(path = {"", "/"})
    public ResponseEntity<List<Activity>> getActivities() {
        List<Activity> activities = activityService.list();
        return ResponseEntity.ok(activities);
    }

    /**
     * A POST endpoint for saving activities to the database
     * @param activities the activities to be saved
     * @return a ResponseEntity
     */
    @PostMapping(path = {"", "/"})
    public ResponseEntity<List<Activity>> saveActivities(@RequestBody List<Activity> activities) {
        activityService.delete();
        activityService.save(activities);
        return ResponseEntity.ok(activities);
    }

    @PostMapping("/image")
    public void saveImage(@RequestBody ActivityImage image) {
        byte[] actualImage = image.getImage();
        Path path = Paths.get("server/src/main/resources/static/images/" + image.getName());
        File newFile = new File(path + "/..");
        if (!Files.exists(path)) {
            newFile.mkdir();
        }

        try {
            Files.write(path, actualImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(path);
    }
}
