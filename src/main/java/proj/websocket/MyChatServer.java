package proj.websocket;

import java.time.Duration;
import java.time.Instant;

import io.micronaut.scheduling.TaskScheduler;
import io.micronaut.websocket.WebSocketBroadcaster;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ServerWebSocket("/chat/{username}")
public class MyChatServer {
    private final WebSocketBroadcaster broadcaster;
    private final TaskScheduler taskScheduler;

    @OnOpen
    public void onOpen(String username, WebSocketSession session) {
    	String msg = String.format("%s has joined the chat", username);
    	broadcaster.broadcastSync(msg);
    	taskScheduler.scheduleAtFixedRate(
    			Duration.ZERO, 
    			Duration.ofSeconds(20), 
    			Delta.builder()
    				.broadcaster(broadcaster)
    				.source("delta")
    				.timestamp(Instant.now().toString())
    				.message(String.valueOf(Math.random() * 100))
    				.build()
		);
    }

    @OnMessage
    public void onMessage(String message, String username, WebSocketSession session) {
    	String msg = String.format("%s: %s", username, message);
    	broadcaster.broadcastSync(msg);
    }
    

    @OnClose
    public void onClose(String username, WebSocketSession session) {
    	broadcaster.broadcastSync(username + " has left the chat");
    	session.close();
    }

}