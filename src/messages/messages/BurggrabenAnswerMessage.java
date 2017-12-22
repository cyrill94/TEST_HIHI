package messages;

import java.io.Serializable;
import java.net.SocketAddress;

import cards.Card;

public class BurggrabenAnswerMessage extends Message implements Serializable {

	private boolean burggrabenPlayed;
	private Card actionCard;
	
	public BurggrabenAnswerMessage(SocketAddress socketAddress, boolean burggrabenPlayed, Card actionCard) {
		this.setMessageType(EnumMessageType.burggrabenAnswer);
		this.setSocketAddress(socketAddress);
		this.burggrabenPlayed = burggrabenPlayed;
		this.actionCard = actionCard;
	}
	
	public boolean isBurggrabenPlayed() {
		return this.burggrabenPlayed;
	}
	
	public Card getCard() {
		return this.actionCard;
	}
	
}