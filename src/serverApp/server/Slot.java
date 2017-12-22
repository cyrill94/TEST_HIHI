package server;

import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;

import player.Player;

/** @author Thomas **/
public class Slot implements Serializable{
	private Player player;
	private Socket socket;
	private ObjectOutputStream messageSender;
	private boolean isReadyForGame;
	
	//DIESER KONSTRUKTOR IST NUR ZUM TESTEN OHNE SOCKET!
	public Slot (Player player) {
		this.player = player;
	}
	
	public Slot (Socket socket, ObjectOutputStream messageSender) {
		this.socket = socket;
		this.messageSender = messageSender;
		this.isReadyForGame = false;
		this.player = null;
	}
	
	public void setPlayer (Player player) {
		this.player = player;
	}
	
	public SocketAddress getClientSocketAddress () {
		return socket.getRemoteSocketAddress();
	}
	
	public ObjectOutputStream getmessageSender() {
		return this.messageSender;
	}
	
	public boolean getReadyStatus() {
		return this.isReadyForGame;
	}
	
	public void setReady() {
		this.isReadyForGame = true;
	}
	
	public void setNotReady() {
		this.isReadyForGame = false;
	}
	
	public Player getPlayer() throws FileNotFoundException {
		if (this.player == null) {
			return new Player("dummy");
		}
		return this.player;
	}
	
	public Socket getSocket() {
		return this.socket;
	}
	
}
