package messages;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.SocketAddress;

public class LoginMessage extends Message implements Serializable{
	private String username;
	private String password;
	
	public LoginMessage(String username, String password, SocketAddress socketAddress) {
		this.username = username;
		this.password = password;
		this.setMessageType(EnumMessageType.login);
		this.setSocketAddress(socketAddress);
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
