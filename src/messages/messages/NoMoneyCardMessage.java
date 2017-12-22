package messages;

/** @author Cyrill **/

import java.io.Serializable;
import java.net.SocketAddress;

public class NoMoneyCardMessage extends Message implements Serializable {

	public NoMoneyCardMessage(SocketAddress socketAddress) {
		this.setMessageType(EnumMessageType.noMoneyCard);
		this.setSocketAddress(socketAddress);
	}
}
