package messages;

import java.io.Serializable;
import java.net.SocketAddress;
/** @author Thomas **/
public class AbortReadyMessage extends Message implements Serializable{

		public AbortReadyMessage(SocketAddress socketAddress) {
			this.setMessageType(EnumMessageType.abortReadyGame);
			this.setSocketAddress(socketAddress);
		}

}

