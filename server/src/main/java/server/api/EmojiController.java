package server.api;

import commons.Emoji;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class EmojiController {

    /**
     * WebSocket mapping for the emoji communication
      * @param emoji Emoji received
     * @return Emoji sent back
     */
    @MessageMapping("/emojis/{id}") // /app/emojis
    @SendTo("/topic/emojis/{id}")
    public Emoji receiveEmoji(Emoji emoji) {
        return emoji;
    }

}
