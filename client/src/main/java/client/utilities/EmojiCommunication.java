package client.utilities;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class EmojiCommunication extends ServerCommunication {

    /**
     * Start a StompSession connection (for WebSocket communication) to the server
     * using the "upgraded" WebSocket protocol (ws://...)
     */
    private StompSession session;

    /**
     * Start a StompSession connection (for WebSocket communication) to the server
     * on the provided destination URL.
     * @param url Connection destination.
     * @return StompSession
     */
    private StompSession connect(String url) {
        var client = new StandardWebSocketClient();
        var stomp = new WebSocketStompClient(client);

        stomp.setMessageConverter(new MappingJackson2MessageConverter());

        try {
            return stomp.connect(url, new StompSessionHandlerAdapter() {
            }).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException();
    }

    /**
     * Generic message registration method for use with WebSockets.
     * @param destination Destination to "subscribe" to.
     * @param consumer Consumer of correct object type.
     */
    public <T> void registerForMessages(String destination, Class<T> type, Consumer<T> consumer) {
        session = connect("ws://" + RAW_SERVER + "websocket");
        session.subscribe(destination, new StompFrameHandler() {

            @Override
            public Type getPayloadType(StompHeaders headers) {
                return type;
            }

            @SuppressWarnings("unchecked")
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((T) payload);
            }
        });
    }

    /**
     * Method that sends object o to destination dest
     * @param dest destination
     * @param o object to send
     */
    public void send(String dest, Object o) {
        session.send(dest, o);
    }
}
