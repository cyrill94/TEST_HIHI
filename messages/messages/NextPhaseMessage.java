package messages;

import java.io.Serializable;
import java.net.SocketAddress;

public class NextPhaseMessage extends Message implements Serializable {

	public NextPhaseMessage(SocketAddress socketAddress) {
		this.setMessageType(EnumMessageType.nextPhase);
		this.setSocketAddress(socketAddress);
	}
	
	public NextPhaseMessage() {
		this.setMessageType(EnumMessageType.nextPhase);
	}
}
