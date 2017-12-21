package messages;

import java.io.Serializable;
import java.net.SocketAddress;
import game.GamePlayer;

/** @author Cyrill **/

public class EndGameMessage extends Message implements Serializable {
	
private String title;
private String message;

	
	public EndGameMessage(String title, String message) {
		this.setMessageType(EnumMessageType.endGameMessage);
		this.message = message;
		this.title = title;
		
	}
		
	public String getTitle() {
		return this.title;
	}
	
	public String getMessage() {
		return this.message;
	}
	
}
