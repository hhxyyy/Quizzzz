package server.api;

import commons.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.SingleRepository;
import server.database.UserRepository;
import server.stubs.TestUserRepository;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {

    private User user1;
    private User user2;
    private UserRepository users;
    private UserController userController;
    private SingleRepository singleRepository;

    @BeforeEach
    void setup() {
        user1 = new User("a");
        user2 = new User("b");
        users = new TestUserRepository();
        userController = new UserController(users, singleRepository);
    }

    @Test
    void testAddUser() {
        userController.addUser(user1);
        assertEquals(userController.getUserById(user1.getUuid()).getBody(), user1);
    }

    @Test
    void testGetAllUsers() {
        userController.addUser(user1);
        user2.setUuid(3L);
        userController.addUser(user2);
        List<User> usersList1 = new ArrayList<>();
        usersList1.add(user1);
        usersList1.add(user2);
        List<User> usersList2 = userController.getAll();
        assertEquals(usersList2, usersList1);

    }

//    @Test
//    void testAddUserBadResponse() {
//        userController.addUser(user1);
//        assertNull(userController.addUser(user2).getBody()); // Will have the same UUID of 0, hence return 400
//    }

    @Test
    void testGetUserById() {
        userController.addUser(user1);
        User testUser = userController.getUserById(user1.getUuid()).getBody();
        assertEquals(testUser, user1);
    }

    @Test
    void renameUser() {
        userController.addUser(user1);
        User user3 = userController.renameUser(user1.getUuid(), "newUsername").getBody();
        assertEquals(user3.getName(), "newUsername");
    }
}