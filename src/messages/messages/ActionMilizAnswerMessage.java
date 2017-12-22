package messages;

import java.io.Serializable;
import java.net.SocketAddress;
import java.util.ArrayList;

import cards.Card;

public class ActionMilizAnswerMessage extends Message implements Serializable {
	
	private ArrayList<Card> cardsToDrop;
	
	
	public ActionMilizAnswerMessage(SocketAddress socketAddress, ArrayList<Card> cardsToDrop) {
		this.setMessageType(EnumMessageType.milizAnswer);
		this.setSocketAddress(socketAddress);
		this.cardsToDrop = cardsToDrop;
	}

	public ArrayList<Card> getCardsToDrop() {
		return cardsToDrop;
	}
}