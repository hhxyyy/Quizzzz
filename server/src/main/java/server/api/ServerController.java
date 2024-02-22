package server.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A REST API for checking the connection between client and server
 */
@RestController
@RequestMapping("/api/connect")
public class ServerController {

    /**
     * A GET endpoint for checking whether the server can be reached
     * @return true
     */
    @GetMapping(path = {"", "/"})
    public boolean test() {
        return true;
    }
}
