package messages;

import java.io.Serializable;

import player.EnumCardSet;

/** @author Cyrill **/

public class CardsetMismatchMessage extends Message implements Serializable {
	private EnumCardSet yourCardSet;
	private EnumCardSet opponentCardSet;
	private String yourCardSetName;
	private String opponentCardSetName;
	
	public CardsetMismatchMessage(EnumCardSet yourCardSet, EnumCardSet opponentCardSet){
		this.yourCardSet = yourCardSet;
		this.opponentCardSet = opponentCardSet;
		this.yourCardSetName = yourCardSet.toString();
		this.opponentCardSetName = opponentCardSet.toString();
		this.setMessageType(EnumMessageType.cardsetMismatchMessage);
	}
	
	
	public EnumCardSet getYourCardSet() {
		return this.yourCardSet;
	}
	
	public EnumCardSet getOpponentCardSet() {
		return this.opponentCardSet;
	}
	
	public String getyourCardSetName () {
		return this.yourCardSetName;
	}
	
	public String getopponentCardSetName () {
		return this.opponentCardSetName;
	}
	
}
