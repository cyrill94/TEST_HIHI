package messages;

import java.io.Serializable;
import java.util.ArrayList;

import player.EnumCardSet;

public class CustomCardSetMismatchMessage extends Message implements Serializable {
	
	private ArrayList<String> yourCardSetCards;
	private ArrayList<String> opponentCardSetCards;
	
	public CustomCardSetMismatchMessage(ArrayList<String> yourCardSetCards, ArrayList<String> opponentCardSetCards){
		this.yourCardSetCards = yourCardSetCards;
		this.opponentCardSetCards = opponentCardSetCards;
		this.setMessageType(EnumMessageType.customCardSetMismatchMessage);
	}

	public ArrayList<String> getYourCardSetCards() {
		return yourCardSetCards;
	}

	public ArrayList<String> getOpponentCardSetCards() {
		return opponentCardSetCards;
	}
}
