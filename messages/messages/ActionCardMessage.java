package messages;

import java.io.Serializable;
import java.net.SocketAddress;

import cards.Card;

public class ActionCardMessage  extends Message implements Serializable{
	private EnumMessageType messageType;
	private Card card;
	
	public ActionCardMessage(SocketAddress socketAddress, Card card) {
		this.messageType = EnumMessageType.actionCard;
		this.card = card;
		this.setSocketAddress(socketAddress);
	}
	public EnumMessageType getMessageType () {
		return this.messageType;
	}
	public Card getCard() {
		return card;
	}
	
	
}
