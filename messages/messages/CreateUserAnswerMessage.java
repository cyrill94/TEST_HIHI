package messages;

import java.io.Serializable;
import java.net.SocketAddress;

/** @author Cyrill **/

public class CreateUserAnswerMessage extends Message implements Serializable {
	private int errorCode;

	public CreateUserAnswerMessage(int errorCode) {
		this.errorCode = errorCode;
		this.setMessageType(EnumMessageType.createUserAnswer);
	}
	
	public int getErrorCode() {
		return this.errorCode;
	}
	
}


