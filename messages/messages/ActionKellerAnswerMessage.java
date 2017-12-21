package messages;

import java.io.Serializable;
import java.net.SocketAddress;
import java.util.ArrayList;

import cards.Card;

public class ActionKellerAnswerMessage extends Message implements Serializable {
	
	private ArrayList<Card> cardsToDrop;
	
	
	public ActionKellerAnswerMessage(SocketAddress socketAddress, ArrayList<Card> cardsToDrop) {
		this.setMessageType(EnumMessageType.kellerAnswer);
		this.setSocketAddress(socketAddress);
		this.cardsToDrop = cardsToDrop;
	}

	public ArrayList<Card> getCardsToDrop() {
		return cardsToDrop;
	}
}