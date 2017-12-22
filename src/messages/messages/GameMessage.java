package messages;

import java.io.Serializable;
import java.net.SocketAddress;

import game.Game;

public class GameMessage extends Message implements Serializable {

	private Game game;
	
	public Game getGame() {
		return game;
	}

	public GameMessage(Game game) {
		this.setMessageType(EnumMessageType.game);
		this.game = game;
	
	}
}
