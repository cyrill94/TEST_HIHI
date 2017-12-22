package messages;

/** @author Cyrill **/

import java.io.Serializable;
import java.net.SocketAddress;

public class UpdateLogMessage extends Message implements Serializable {
	
private String strLog;

	public UpdateLogMessage(String strLog) {
		this.setMessageType(EnumMessageType.updateLogMessage);
		this.strLog = strLog;
	}
	
	public String getStrLog() {
		return this.strLog;
	}
	
	public void setStrLog(String strLog) {
		this.strLog = strLog;
	}
}
