package messages;

import java.io.Serializable;
import java.net.SocketAddress;


/** @author Cyrill **/

public class CreateUserMessage extends Message implements Serializable  {
	private String username, password;

public CreateUserMessage(String username, String password, SocketAddress socketAddress) {
		this.username = username;
		this.password = password;
		this.setMessageType(EnumMessageType.createUser);
		this.setSocketAddress(socketAddress);
	}

	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}

	
}