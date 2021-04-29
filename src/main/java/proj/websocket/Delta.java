package proj.websocket;

import io.micronaut.websocket.WebSocketBroadcaster;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Delta implements Runnable {
	private String message;
	private String source;
	private String timestamp;
	private WebSocketBroadcaster broadcaster;
	
	@Override
	public void run() {
		broadcaster.broadcastSync(String.format("%s : %s", source, message));
	}
	

}
