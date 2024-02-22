package server.api;

import commons.jokers.TimeJoker;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class JokerController {

    /**
     * A websocket mapping to receive a timeJoker from one client and sent it to other clients.
     * @param timeJoker The received timeJoker.
     */
    @MessageMapping("/joker/{sessionId}") // /app/joker
    @SendTo("/topic/joker/{sessionId}")
    public TimeJoker SendTimeJoker(TimeJoker timeJoker) {
        System.out.println("receive!");
        return timeJoker;
    }

}
