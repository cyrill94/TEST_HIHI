package messages;

import java.io.Serializable;

import cards.Card;

public class CardMessage  extends Message implements Serializable{
	private EnumMessageType messageType;
	private Card card;
	
	public CardMessage(Card card) {
		this.messageType = EnumMessageType.card;
		this.card = card;
	}
	public EnumMessageType getMessageType () {
		return this.messageType;
	}
}
