package messages;

import java.io.Serializable;
import java.net.SocketAddress;
import java.util.ArrayList;

import player.Player;

public class SettingsCustomCardSetsMessage  extends Message implements Serializable {
	private Player player;
	private ArrayList<String> strings;
	
	
	public SettingsCustomCardSetsMessage(Player player, SocketAddress socketAddress, ArrayList<String> strings) {
		this.player = player;
		this.setMessageType(EnumMessageType.settingsCustomCards);
		this.setSocketAddress(socketAddress);
		this.strings = strings;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public ArrayList<String> getCardStrings(){
		return this.strings;
	}
}
