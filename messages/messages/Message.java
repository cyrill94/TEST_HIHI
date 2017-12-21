package messages;

import java.io.Serializable;
import java.net.*;

public class Message implements Serializable{

	private EnumMessageType messageType;
	private SocketAddress socketAddress;
	
	
	public EnumMessageType getMessageType () {
		return this.messageType;
	}
	
	public void setMessageType(EnumMessageType type) {
		this.messageType = type;
	}
	
	public String toString() {
		return "This Message is from Type: "+this.messageType.toString() + " from: " + this.socketAddress.toString();
	}
	
	public SocketAddress getSocketAddress(){
		return this.socketAddress;
	}
	
	public void setSocketAddress(SocketAddress socketAddress){
		this.socketAddress = socketAddress;
	}

}
