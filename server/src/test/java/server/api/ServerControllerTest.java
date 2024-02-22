package server.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


class ServerControllerTest {

    @Test
    void testCheckServer() {
        ServerController server = new ServerController();
        assertTrue(server.test());
    }
}