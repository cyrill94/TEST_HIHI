package messages;

import java.io.Serializable;
import java.net.SocketAddress;
import java.util.ArrayList;

import cards.Card;
import cards.MoneyCard;

public class BuyCardsMessage extends Message implements Serializable {

	private ArrayList<MoneyCard> moneyCards;
	private ArrayList<Card> cardsToBuy;
	
	public BuyCardsMessage(SocketAddress socketAddress, ArrayList<MoneyCard> moneyCards, ArrayList<Card> cardsToBuy) {
		this.setMessageType(EnumMessageType.buyCardsMessage);
		this.setSocketAddress(socketAddress);
		this.moneyCards = moneyCards;
		this.cardsToBuy = cardsToBuy;
	}
	
	public ArrayList<MoneyCard> getMoneyCards() {
		return moneyCards;
	}

	public ArrayList<Card> getCardsToBuy() {
		return cardsToBuy;
	}
}
