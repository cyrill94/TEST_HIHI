package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

import cards.Card;
import player.Player;
import stacks.CardStack;

/** @author Thomas / Cyrill **/

public class GamePlayer implements Serializable{
	private int points;
	private int actionCount;
	private int buyCount;
	private int additionalMoney;
	private boolean turn;
	private CardStack handCards;
	private CardStack deckStack;
	private CardStack graveyardCards;
	private final Player player;
	
// this.points = 3 weil Standardmässig am Anfang vom Spiel 3 Karten mit 1 Punkt verteilt werden
	public GamePlayer(Player player, boolean turn) {
		this.points = 3;
		this.actionCount = 1;
		this.buyCount = 1;
		this.additionalMoney = 0;
		this.turn = turn;
		this.player = player;
		this.handCards = new CardStack(new ArrayList<Card>());
		this.deckStack = new CardStack(new ArrayList<Card>());
		this.graveyardCards = new CardStack(new ArrayList<Card>());
	}
	
	public int getAdditionalMoney() {
		return additionalMoney;
	}

	public void setAdditionalMoney(int additionalMoney) {
		this.additionalMoney = additionalMoney;
	}

	public int getActionCount() {
		return actionCount;
	}

	public void setActionCount(int actionCount) {
		this.actionCount = actionCount;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public int getPoints() {
		return this.points;
	}
	
	public void setTurn(boolean turn) {
		this.turn = turn;
	}
	
	public boolean getTurn() {
		return this.turn;
	}
	
	public void sethandCards(CardStack handCards) {
		this.handCards = handCards;
	}
	
	public void setdeckStack(CardStack deckStack) {
		this.deckStack = deckStack;
	}
	
	public void setgraveyardCards(CardStack graveyardCards) {
		this.graveyardCards = graveyardCards;
	}
	
	public CardStack gethandCards() {
		return this.handCards;
	}
	
	public CardStack getdeckStack() {
		return this.deckStack;
	}
	
	public CardStack getgraveyardCards() {
		return this.graveyardCards;
	}

	public Player getPlayer() {
		return player;
	}
	
	public Card drawCardFromDeck() {
		return this.deckStack.popCard();
	}
	
	public void addCardToHand(Card card) {
		this.handCards.add(card);
	}

	
	public int getBuyCount() {
		return this.buyCount;
	}

	
	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}
	
	public void setDeckStack(CardStack cardList) {
		for (Card c:cardList.getArrCards()) {
			this.deckStack.add(c);
		}
	}
	
	public void mixDeckStack() {
		this.deckStack.mixCards();
	}

	public void removeCardFromHand(Card card) {
		int position = -1;
		for (int counter = 0; counter < handCards.getSize(); counter++) { 
	          if (card.getName().equals(handCards.getCard(counter).getName())){
	        	  position = counter;
	          }
	    }  
		if (position > -1) {
			handCards.removeCard(position);
		}
	}

	//needs to be tested
	public void putAllHandCardsToGraveyard() {
		while (handCards.isEmpty()==false) {
			this.addCardToGraveyard(handCards.popCard());
		} 
	}

	
	public void addCardToGraveyard(Card card) {
		this.graveyardCards.add(card);
	}
	
	public void clearGraveyard() {
		this.graveyardCards.getArrCards().clear();
	}
	
}
