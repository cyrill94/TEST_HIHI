package messages;

import java.io.Serializable;
import java.net.SocketAddress;

public class PasswordAnswerMessage extends Message implements Serializable {
	private int errorCode;
	
	public PasswordAnswerMessage(int errorCode) {
		this.errorCode = errorCode;
		this.setMessageType(EnumMessageType.passwordAnswerMessage);
	}

	public int getErrorCode() {
		return this.errorCode;
	}
	
}
