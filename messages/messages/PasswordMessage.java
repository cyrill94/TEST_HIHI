package messages;

import java.io.Serializable;
import java.net.SocketAddress;

import player.Player;

public class PasswordMessage extends Message implements Serializable {
	private Player player;
	private String currentpassword;
	private String newpassword;
	
	public PasswordMessage(Player player, String currentpassword, String newpassword) {
		this.player = player;
		this.newpassword = newpassword;
		this.currentpassword = currentpassword;
		this.setMessageType(EnumMessageType.passwordMessage);
	}
	
	
	public String getNewPassword() {
		return this.newpassword;
	}
	
	public String getCurrentPassword() {
		return this.currentpassword;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
}
