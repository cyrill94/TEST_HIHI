package messages;

import java.io.Serializable;

/** @author Cyrill **/

public class DropCardDrawCardMessage extends Message implements Serializable {

	private int cardsToDraw;
	
	public DropCardDrawCardMessage(int cardsToDraw) {
		this.setMessageType(EnumMessageType.dropCardDrawCard);
		this.cardsToDraw = cardsToDraw;
	}
	
	public int getCardsToDraw() {
		return this.cardsToDraw;
	}
}
