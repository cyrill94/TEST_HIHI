package messages;

import java.io.Serializable;
import java.net.SocketAddress;

public class ChangeTurnMessage extends Message implements Serializable {

	public ChangeTurnMessage(SocketAddress socketAddress) {
		this.setMessageType(EnumMessageType.nextTurn);
		this.setSocketAddress(socketAddress);
	}
}
