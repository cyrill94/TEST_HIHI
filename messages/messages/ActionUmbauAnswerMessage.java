package messages;

import java.io.Serializable;
import java.net.SocketAddress;
import java.util.ArrayList;

import cards.Card;

public class ActionUmbauAnswerMessage extends Message implements Serializable {
	
	private Card cardToDrop;
	private Card cardToTake;
	
	
	public ActionUmbauAnswerMessage(SocketAddress socketAddress, Card cardToDrop, Card cardToTake) {
		this.setMessageType(EnumMessageType.umbauAnswer);
		this.setSocketAddress(socketAddress);
		this.cardToDrop = cardToDrop;
		this.cardToTake = cardToTake;
	}


	public Card getCardToDrop() {
		return cardToDrop;
	}


	public Card getCardToTake() {
		return cardToTake;
	}
}