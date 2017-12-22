package messages;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.SocketAddress;

import player.Player;


public class LoginAnswerMessage extends Message implements Serializable {
	private boolean loginCheck;
	private Player player;
	
	public LoginAnswerMessage(boolean loginCheck) {
		this.loginCheck = loginCheck;
		this.setMessageType(EnumMessageType.loginAnswer);
	}
	
	public LoginAnswerMessage(boolean loginCheck, Player player) {
		this.player = player;
		this.loginCheck = loginCheck;
		this.setMessageType(EnumMessageType.loginAnswer);
	}
	
	public boolean getLoginCheck() {
		return this.loginCheck;		
	}
	
	public Player getPlayer () {
		return this.player;
	}
}
