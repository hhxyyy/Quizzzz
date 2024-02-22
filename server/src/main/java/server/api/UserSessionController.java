package server.api;

import commons.User;
import commons.UserSession;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


import server.database.UserRepository;
import server.database.UserSessionRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/usersession")
public class UserSessionController {
    private final UserSessionRepository userSessionRepository;
    private final UserRepository userRepository;

    public UserSessionController(UserSessionRepository userSessionRepository, UserRepository userRepository) {
        this.userSessionRepository = userSessionRepository;
        this.userRepository = userRepository;
    }

    /**
     * A POST endpoint for saving a UserSession to the database
     * @param userSession the UserSession to be saved
     * @return a ResponseEntity containing the UserSession
     */
    @PostMapping(path = {"", "/"})
    public ResponseEntity<UserSession> createUserSession(@RequestBody UserSession userSession) {
        if (userSession == null || this.userSessionRepository.existsById(userSession.getId())) {
            return ResponseEntity.badRequest().build();
        }
        userSessionRepository.save(userSession);
        return ResponseEntity.ok(userSession);
    }

    /**
     * A GET endpoint for getting all UserSessions from the database
     * @return a list of all UserSessions
     */
    @GetMapping(path = {"", "/"})
    public ResponseEntity<List<UserSession>> getUserSession(@RequestParam(required = false) String status) {
        List<UserSession>  userSessions = userSessionRepository.findAll();
        if (status == null) {
            return ResponseEntity.ok(userSessions);
        } else if (status.equals("0") || status.equals("closed")) {
            List<UserSession> closedSession = new ArrayList<>();
            for (UserSession userSession: userSessions) {
                if (userSession.getStatus() == 0){
                    closedSession.add(userSession);
                }
            }
            return ResponseEntity.ok(closedSession);
        } else if (status.equals("1") || status.equals("open")) {
            UserSession openSession = getOpen();
            if (openSession == null) {
                return ResponseEntity.ok(Collections.emptyList());
            }
            return ResponseEntity.ok(List.of(openSession));
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * A GET endpoint for getting the UserSession based on the sessionId
     * @param sessionId The sessionId of the UserSession
     * @return The UserSession
     */
    @GetMapping("/{sessionId}")
    public ResponseEntity<UserSession> getUserSessionById(@PathVariable("sessionId") UUID sessionId) {
        List<UserSession> userSessions = userSessionRepository.findAll();
        for (UserSession userSession : userSessions) {
            if (userSession.getId() != null) {
                if (userSession.getId().compareTo(sessionId) == 0) {
                    return ResponseEntity.ok(userSession);
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * A DELETE endpoint for deleting a specified userSession
     * @param sessionId The sessionId of the UserSession
     * @return Whether the userSession was successfully deleted
     */
    @DeleteMapping("/{sessionId}")
    public boolean deleteUserSession(@PathVariable("sessionId") UUID sessionId) {
        if (!userSessionRepository.existsById(sessionId)) {
            return false;
        }
        userSessionRepository.deleteById(sessionId);
        return true;
    }

    /**
     * A GET endpoint for getting the users belonging to a sessionId
     * @param sessionId The uuid of the UserSession
     * @return The list of users in the specified UserSession
     */
    @GetMapping("/users/{sessionId}")
    public ResponseEntity<List<User>> getUsersInSession(@PathVariable("sessionId") UUID sessionId) {
        List<User>  users = userRepository.findAll();
        List<User> inSession = new ArrayList<>();
        for (User user: users) {
            if (user.getSessionId() != null) {
                if (user.getSessionId().compareTo(sessionId) == 0) {
                    inSession.add(user);
                }
            }
        }
        return ResponseEntity.ok(inSession);
    }

    /**
     * A GET endpoint for getting the users belonging to a sessionId
     * @return The list of users in the specified UserSession
     */
    @GetMapping("/getSingles")
    public ResponseEntity<List<User>> getSingleSessionUsers() {
        List<User>  users = userRepository.findAll();
        List<User> singles = new ArrayList<>();
        for (User user: users) {
            if (user.getSessionId() != null) {
                if (userSessionRepository.findById(user.getSessionId()).get().getStatus() == 0) {
                    singles.add(user);
                }
            }
        }
        return ResponseEntity.ok(singles);
    }

    /**
     * A PUT endpoint for changing the UserSession of the specified user to the open session
     * @param userId The userId of the user
     * @return The specified user
     */
    @PutMapping("/users/{userId}")
    public ResponseEntity<User> setSessionId(@PathVariable("userId") long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return null;
        }
        User res = user.get();
        UserSession userSession = getOpen();
        if (userSession == null) {
            return ResponseEntity.badRequest().build();
        }
        UUID sessionId = userSession.getId();
        res.setSessionId(sessionId);
        userRepository.save(res);
        return ResponseEntity.ok(res);
    }

    /**
     * Method to get a UserSession with the status 1
     * @return - UserSession with the status 1
     */
    public UserSession getOpen() {
        List<UserSession>  userSessions = userSessionRepository.findAll();
        for (UserSession userSession: userSessions) {
            if (userSession.getStatus() == 1){
                return userSession;
            }
        }
        return null;
    }

    /**
     * A PUT endpoint for changing the UserSession of the specified user to the open session
     * @return The specified userSession
     */
    @PutMapping("/setSessionSingle/{sessionId}/")
    public ResponseEntity<UserSession> setSessionSingle(@PathVariable("sessionId") UUID id) {
        Optional<UserSession> userSession = userSessionRepository.findById(id);
        if (userSession.isEmpty()) {
            return null;
        }
        UserSession res = userSession.get();
        res.setStatus(0);
        if (userSession == null) {
            return ResponseEntity.badRequest().build();
        }
        userSessionRepository.save(res);
        return ResponseEntity.ok(res);
    }

    /**
     * A PUT endpoint for changing the UserSession of the specified user to the open session
     * @return The specified userSession
     */
    @PutMapping("/setSessionPlaying/{sessionId}/")
    public ResponseEntity<UserSession> setSessionPlaying(@PathVariable("sessionId") UUID id) {
        Optional<UserSession> userSession = userSessionRepository.findById(id);
        if (userSession.isEmpty()) {
            return null;
        }
        UserSession res = userSession.get();
        res.setStatus(2);
        if (userSession == null) {
            return ResponseEntity.badRequest().build();
        }
        userSessionRepository.save(res);
        return ResponseEntity.ok(res);
    }

    /**
     * WebSocket communication endpoints for sending and receiving UserSessions
     * @param userSession - UserSession to converse and save (if edited)
     * @return - Edited UserSession.
     */
    @MessageMapping("/session/{sessionID}")
    @SendTo("/topic/session/{sessionID}")
    public UserSession converseSession(UserSession userSession) {
        userSessionRepository.save(userSession);
        return userSession;
    }
}