package server.api;

import commons.User;
import commons.UserSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.UserRepository;
import server.database.UserSessionRepository;
import server.stubs.TestUserRepository;
import server.stubs.TestUserSessionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

class UserSessionControllerTest {

    private UserRepository userRepository;
    private UserSessionRepository userSessionRepository;
    private UserSession userSession;
    private UserSessionController userSessionController;

    @BeforeEach
    void setup() {
        userRepository = new TestUserRepository();
        userSessionRepository = new TestUserSessionRepository();
        userSession = new UserSession();
        userSessionController = new UserSessionController(userSessionRepository, userRepository);
    }


    @Test
    void testCreateRoomNull() {
        var code = userSessionController.createUserSession(null);
        assertEquals(BAD_REQUEST, code.getStatusCode());
    }

    @Test
    void testCreateRoomAlreadyExisting() {
        userSessionRepository.save(userSession);
        var code = userSessionController.createUserSession(userSession);
        assertEquals(BAD_REQUEST, code.getStatusCode());
    }

    @Test
    void testCreateRoom() {
        var code = userSessionController.createUserSession(userSession);
        assertEquals(OK, code.getStatusCode());
    }

    @Test
    void testGetUserSessionStatus1Code() {
        userSessionRepository.save(userSession);
        var code = userSessionController.getUserSession("1");
        assertEquals(OK, code.getStatusCode());
    }

    @Test
    void testGetUserSessionStatusOpenCode() {
        userSessionRepository.save(userSession);
        var code = userSessionController.getUserSession("open");
        assertEquals(OK, code.getStatusCode());
    }

    @Test
    void testGetUserSessionStatus1List() {
        userSessionRepository.save(userSession);
        var result = userSessionController.getUserSession("1");
        List<UserSession> userSessionList = new ArrayList<>();
        userSessionList.add(userSession);
        assertEquals(userSessionList, result.getBody());
    }

    @Test
    void testGetUserSessionStatusOpenList() {
        userSessionRepository.save(userSession);
        var result = userSessionController.getUserSession("open");
        List<UserSession> userSessionList = new ArrayList<>();
        userSessionList.add(userSession);
        assertEquals(userSessionList, result.getBody());
    }

    @Test
    void testGetUserSessionStatus0Code() {
        userSession.setStatus(0);
        userSessionRepository.save(userSession);
        var code = userSessionController.getUserSession("0");
        assertEquals(OK, code.getStatusCode());
    }

    @Test
    void testGetUserSessionStatusClosedCode() {
        userSession.setStatus(0);
        userSessionRepository.save(userSession);
        var code = userSessionController.getUserSession("closed");
        assertEquals(OK, code.getStatusCode());
    }

    @Test
    void testGetUserSessionStatus0List() {
        userSession.setStatus(0);
        userSessionRepository.save(userSession);
        var result = userSessionController.getUserSession("0");
        List<UserSession> userSessionList = new ArrayList<>();
        userSessionList.add(userSession);
        assertEquals(userSessionList, result.getBody());
    }

    @Test
    void testGetUserSessionStatusClosedList() {
        userSession.setStatus(0);
        userSessionRepository.save(userSession);
        var result = userSessionController.getUserSession("closed");
        List<UserSession> userSessionList = new ArrayList<>();
        userSessionList.add(userSession);
        assertEquals(userSessionList, result.getBody());
    }

    @Test
    void testGetUserSessionStatusWrongStatus() {
        var result = userSessionController.getUserSession("session");
        assertEquals(BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void testGetUserSessionStatusNull() {
        var result = userSessionController.getUserSession(null);
        assertEquals(OK, result.getStatusCode());
    }

    @Test
    void testGetUserSessionByIdCode() {
        userSessionRepository.save(userSession);
        UUID uuid = userSession.getId();
        var code = userSessionController.getUserSessionById(uuid);
        assertEquals(OK, code.getStatusCode());
    }

    @Test
    void testGetUserSessionByIdCodeNotExisting() {
        userSessionRepository.save(userSession);
        UUID uuid = UUID.randomUUID();
        while (uuid.equals(userSession.getId())) {
            uuid = UUID.randomUUID();
        }
        var code = userSessionController.getUserSessionById(uuid);
        assertEquals(BAD_REQUEST, code.getStatusCode());
    }

    @Test
    void testGetUserSessionById() {
        userSessionRepository.save(userSession);
        UUID uuid = userSession.getId();
        var code = userSessionController.getUserSessionById(uuid);
        assertEquals(userSession, code.getBody());
    }

    @Test
    void testGetUserSessionByIdNull() {
        var code = userSessionController.getUserSessionById(null);
        assertEquals(BAD_REQUEST, code.getStatusCode());
    }

    @Test
    void testDeleteUserSessionNull() {
        var code = userSessionController.deleteUserSession(null);
        assertFalse(code);
    }

    @Test
    void testDeleteUserSessionNotExisting() {
        UUID uuid = UUID.randomUUID();
        while (uuid.equals(userSession.getId())) {
            uuid = UUID.randomUUID();
        }
        var code = userSessionController.deleteUserSession(uuid);
        assertFalse(code);
    }

    @Test
    void testDeleteUserSession() {
        UUID uuid = userSession.getId();
        userSessionRepository.save(userSession);
        var code = userSessionController.deleteUserSession(uuid);
        assertTrue(code);
    }

    @Test
    void testGetUsersInSessionCode() {
        User userOne = new User("Yoan", userSession.getId());
        User userTwo = new User("Joris", userSession.getId());
        User userThree = new User("Lucian", userSession.getId());
        userRepository.save(userOne);
        userRepository.save(userTwo);
        userRepository.save(userThree);
        var listUsers = userSessionController.getUsersInSession(userSession.getId());
        assertEquals(OK, listUsers.getStatusCode());
    }


    @Test
    void testGetUsersInSessionNotExistingCode() {
        User userOne = new User("Yoan", userSession.getId());
        User userTwo = new User("Joris", userSession.getId());
        User userThree = new User("Lucian", userSession.getId());
        userRepository.save(userOne);
        userRepository.save(userTwo);
        userRepository.save(userThree);
        UUID uuid = UUID.randomUUID();
        while (uuid.equals(userSession.getId())) {
            uuid = UUID.randomUUID();
        }
        var listUsers = userSessionController.getUsersInSession(uuid);
        assertEquals(OK, listUsers.getStatusCode());
    }

    @Test
    void testGetUsersInSession() {
        User userOne = new User("Yoan", userSession.getId());
        User userTwo = new User("Joris", userSession.getId());
        User userThree = new User("Lucian", userSession.getId());
        userRepository.save(userOne);
        userRepository.save(userTwo);
        userRepository.save(userThree);
        var listUsers = userSessionController.getUsersInSession(userSession.getId());
        List<User> users = new ArrayList<>();
        users.add(userOne);
        users.add(userTwo);
        users.add(userThree);
        assertEquals(users, listUsers.getBody());
    }

    @Test
    void testGetUsersInSessionEmpty() {
        User userOne = new User("Yoan", userSession.getId());
        User userTwo = new User("Joris", userSession.getId());
        User userThree = new User("Lucian", userSession.getId());
        userRepository.save(userOne);
        userRepository.save(userTwo);
        userRepository.save(userThree);
        UUID uuid = UUID.randomUUID();
        while (uuid.equals(userSession.getId())) {
            uuid = UUID.randomUUID();
        }
        var listUsers = userSessionController.getUsersInSession(uuid);
        assertEquals(new ArrayList<>(), listUsers.getBody());
    }

    @Test
    void testSetSessionIdNotExisting() {
        User userOne = new User("Yoan", userSession.getId());
        userRepository.save(userOne);
        var code = userSessionController.setSessionId(userOne.getUuid());
        assertEquals(BAD_REQUEST, code.getStatusCode());
    }

    @Test
    void testSetSessionIdNull() {
        User userOne = new User("Yoan", userSession.getId());
        userRepository.save(userOne);
        var code = userSessionController.setSessionId(userOne.getUuid() + 1);
        assertNull(code);
    }

    @Test
    void testSetSessionIdCode() {
        User userOne = new User("Yoan");
        userRepository.save(userOne);
        userSessionRepository.save(userSession);
        var code = userSessionController.setSessionId(userOne.getUuid());
        assertEquals(OK, code.getStatusCode());
    }

    @Test
    void testSetSessionId() {
        User userOne = new User("Yoan");
        userRepository.save(userOne);
        userSessionRepository.save(userSession);
        var code = userSessionController.setSessionId(userOne.getUuid());
        User userEqual = new User("Yoan", userSession.getId());
        userEqual.setUuid(userOne.getUuid());
        assertEquals(userEqual, code.getBody());
    }

    @Test
    void testGetOpen() {
        userSessionRepository.save(userSession);
        var result = userSessionController.getOpen();
        assertEquals(userSession, result);
    }

    @Test
    void testGetOpenNull() {
        userSession.setStatus(0);
        userSessionRepository.save(userSession);
        var result = userSessionController.getOpen();
        assertNull(result);
    }

    @Test
    void testGetOpenEmpty() {
        var result = userSessionController.getOpen();
        assertNull(result);
    }
}