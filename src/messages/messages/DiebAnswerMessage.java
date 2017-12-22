package messages;

import java.io.Serializable;
import java.net.SocketAddress;

import cards.Card;

public class DiebAnswerMessage extends Message implements Serializable {

	private Card actionCard;
	private boolean stehlen;
	
	public DiebAnswerMessage(SocketAddress socketAddress, Card actionCard, boolean stehlen) {
		this.setMessageType(EnumMessageType.diebanswer);
		this.setSocketAddress(socketAddress);
		this.actionCard = actionCard;
		this.stehlen = stehlen;
	}
	
	public DiebAnswerMessage(SocketAddress socketAddress) {
		this.setMessageType(EnumMessageType.diebanswer);
		this.setSocketAddress(socketAddress);
		this.actionCard = null;
	}
	
	public Card getCard() {
		return this.actionCard;
	}
	
	public boolean getStehlen() {
		return this.stehlen;
	}
	
}