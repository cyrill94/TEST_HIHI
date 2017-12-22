package messages;

import java.io.Serializable;
import java.net.SocketAddress;

import player.EnumCardSet;
import player.EnumLanguage;
import player.Player;

/** @author Cyrill **/
public class SettingsMessage  extends Message implements Serializable {
	private Player player;
	
	
	public SettingsMessage(Player player, SocketAddress socketAddress) {
		this.player = player;
		this.setMessageType(EnumMessageType.settings);
		this.setSocketAddress(socketAddress);
	}
	
	public Player getPlayer() {
		return this.player;
	}
}
