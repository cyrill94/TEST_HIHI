package messages;

import java.io.Serializable;
import java.net.SocketAddress;
import java.util.ArrayList;

import cards.Card;

public class ActionWerkstattAnswerMessage extends Message implements Serializable {
	
	private Card cardToTake;
	
	
	public ActionWerkstattAnswerMessage(SocketAddress socketAddress, Card cardToTake) {
		this.setMessageType(EnumMessageType.werkstattAnswer);
		this.setSocketAddress(socketAddress);
		this.cardToTake = cardToTake;
	}

	public Card getCardToTake() {
		return cardToTake;
	}
}