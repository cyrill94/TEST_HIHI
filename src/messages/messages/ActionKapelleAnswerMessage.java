package messages;

/** @author Cyrill **/

import java.io.Serializable;
import java.net.SocketAddress;
import java.util.ArrayList;

import cards.Card;

public class ActionKapelleAnswerMessage extends Message implements Serializable {
	
	private ArrayList<Card> cardsToDrop;
	
	public ActionKapelleAnswerMessage(SocketAddress socketAddress, ArrayList<Card> cardsToDrop) {
		this.setMessageType(EnumMessageType.kapelleAnswer);
		this.setSocketAddress(socketAddress);
		this.cardsToDrop = cardsToDrop;
	}


	public ArrayList<Card> getCardsToDrop(){
		return this.cardsToDrop;
	}
}