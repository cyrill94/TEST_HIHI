package messages;

import java.io.Serializable;
import java.net.SocketAddress;

public class NoActionCardMessage extends Message implements Serializable {

	public NoActionCardMessage(SocketAddress socketAddress) {
		this.setMessageType(EnumMessageType.noActionCard);
		this.setSocketAddress(socketAddress);
	}
}
