package messages;

import java.io.Serializable;
import java.net.SocketAddress;

import cards.Card;

public class BurggrabenMessage extends Message implements Serializable {

	private Card actionCard;
	
	public BurggrabenMessage(Card actionCard) {
		this.setMessageType(EnumMessageType.burggraben);
		this.actionCard = actionCard;
	}
	
	public Card getCard() {
		return this.actionCard;
	}
}
