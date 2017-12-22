package messages;

import java.io.Serializable;
import java.net.SocketAddress;

public class StartGameMessage extends Message implements Serializable {
	
	private GameMessage gameMessage;
	
	public StartGameMessage(GameMessage gameMessage) {
		this.setMessageType(EnumMessageType.startGame);
		this.gameMessage = gameMessage;
	}
	
	public GameMessage getGameMessage () {
		return this.gameMessage;
	}
	
}
