package server.api;

import commons.SingleEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.SingleRepository;

import java.util.List;

/**
 * A REST API for creating and getting users
 */
@RestController
@RequestMapping("/api/singles")
public class SingleController {

    private final SingleRepository singleRepository;

    /**
     * The constructor for the SingleController class
     * @param singleRepository An instance of the UserRepository
     * @param singleRepository
     */
    public SingleController(SingleRepository singleRepository) {
        this.singleRepository = singleRepository;
    }

    /**
     * A POST endpoint to save a single into the database
     * @param user a user to insert into the database
     * @return a ResponseEntity containing the user
     */
    @PostMapping("/add")
    public ResponseEntity<SingleEntry> addUser(@RequestBody SingleEntry user) {

//        if (user.uuid < 0 || user.name == null || user.score != 0 || this.singleRepository.existsById(user.uuid)) {
//            return ResponseEntity.badRequest().build();
//        }

        SingleEntry saved = singleRepository.save(user);
        return ResponseEntity.ok(saved);
    }

    /**
     * A GET endpoint for getting all users
     * @return list of all users in the database
     */
    @GetMapping("/getUsers")
    public List<SingleEntry> getAll() {
        return singleRepository.findAll();
    }
}
