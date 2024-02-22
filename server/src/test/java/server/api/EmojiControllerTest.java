package server.api;

import commons.Emoji;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmojiControllerTest {

    @Test
    void receiveEmoji() {
        Emoji emoji = new Emoji("test");
        EmojiController ctrl = new EmojiController();
        assertEquals(emoji, ctrl.receiveEmoji(emoji));
    }
}