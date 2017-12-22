package messages;

import java.io.Serializable;
import java.net.SocketAddress;
import cards.Card;

/* @author Cyrill */

public class ActionFestmahlAnswerMessage extends Message implements Serializable {
	
	private Card cardToTake;
	
	
	public ActionFestmahlAnswerMessage(SocketAddress socketAddress, Card cardToTake) {
		this.setMessageType(EnumMessageType.festmahlAnswer);
		this.setSocketAddress(socketAddress);
		this.cardToTake = cardToTake;
	}

	public Card getCardToTake() {
		return cardToTake;
	}
}
