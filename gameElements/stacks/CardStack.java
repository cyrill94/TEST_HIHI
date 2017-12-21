package stacks;

import java.util.ArrayList;
import java.util.Random;
import cards.*;

public class CardStack extends ArrayList{
	
	private ArrayList<Card> arrCards;

	public CardStack(ArrayList<Card> cardStack) {
		this.arrCards = cardStack;
	}
	
	public void mixCards(){
		Random rand = new Random();
		int size = this.arrCards.size();
		for(int y = 0; y < 5; y++){
			for(int i = 0; i < size; i++){
				Card temp = (Card) this.arrCards.get(i);
				this.arrCards.remove(i);
				this.arrCards.add(rand.nextInt(size), temp);
			}
		}	
		
	}
	
	public Card popCard() {
		return this.arrCards.remove(this.arrCards.size()-1);
	}
	
	public String toString() {
		String string = "";
		
		for (int counter = 0; counter < arrCards.size(); counter++) { 		      
	          string += arrCards.get(counter).toString() + ", ";
	    }   
		return string;
	}
	
	public boolean isEmpty() {
		return this.arrCards.isEmpty();
	}
	
	
	public void add(Card card) {
		this.arrCards.add(card);
	}
	
	public int getSize() {
		return this.arrCards.size();
	}

	public Card getCard(int i) {
		return this.arrCards.get(i);
	}
	
	public Card removeCard(int i) {
		return this.arrCards.remove(i);
	}
	
	//if there is an Action Card in Hand, return true, else return false
	public boolean checkForActionCard() {
		boolean check = false;
		for (Card card:this.arrCards) {
			if (card.getCardType() == EnumCardType.ActionCard) {
				if (!(card.getName().equals("Gaerten")))
					check = true;
			}
		}
		return check;
	}
	
	//if there is an Money Card in Hand, return true, else return false
	public boolean checkForMoneyCard() {
		boolean check = false;
		for (Card card:this.arrCards) {
			if (card.getCardType() == EnumCardType.MoneyCard) {
				check = true;
			}
		}
		return check;
	}
	
	public ArrayList<Card> getArrCards() {
		return arrCards;
	}
	
	public boolean containsBurggraben() {
		boolean containsBurggraben = false;
		for (Card c:this.arrCards) {
			if (c.getName().equals("Burggraben")) {
				containsBurggraben = true;
			}
		}
		return containsBurggraben;
	}

}