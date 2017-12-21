package messages;

import java.io.Serializable;
import java.net.SocketAddress;

public class ReadyMessage extends Message implements Serializable{

		public ReadyMessage(SocketAddress socketAddress) {
			this.setMessageType(EnumMessageType.readyGame);
			this.setSocketAddress(socketAddress);
		}
}