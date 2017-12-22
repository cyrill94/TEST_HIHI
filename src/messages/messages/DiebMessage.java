package messages;

import java.io.Serializable;
import java.net.SocketAddress;
import java.util.ArrayList;

import cards.Card;

public class DiebMessage extends Message implements Serializable {

	private ArrayList<Card> cards;
	
	public DiebMessage(ArrayList<Card> cards) {
		this.setMessageType(EnumMessageType.dieb);
		this.cards = cards;
	}
	
	public ArrayList<Card> getCards() {
		return this.cards;
	}
}
