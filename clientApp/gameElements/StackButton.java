package gameElements;

import java.util.Stack;

import cards.ActionCard;
import cards.Card;
import cards.MoneyCard;

public class StackButton extends CardButton{

	private Stack<Card> cardStack;
	
	public StackButton(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public StackButton(Stack<Card> stack, String name){
		super(name);
		this.cardStack = stack;
	}
	
	public void setCardStack(Stack<Card> stack) {
		this.cardStack = stack;
	}

	public Stack<Card> getCardStack() {
		return cardStack;
	}

	

}
