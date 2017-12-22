package stacks;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import cards.*;

public class TestCardMix {

	public static void main(String[] args) {

		CardStack stack = new CardStack(new ArrayList<Card>());
		
		stack.add(new ActionCard(3, "Abenteurer"));
		stack.add(new PointCard(5, 3, "Anwesen"));
		stack.add(new ActionCard(5, "Geist"));
		stack.add(new PointCard(8, 5, "Provinz"));
		stack.add(new ActionCard(5, "Hexe"));
		stack.add(new MoneyCard(6, 3, "Gold"));
				
		
		for(int i = 0; i < stack.size(); i++){
			System.out.println(stack.get(i).toString());
		}		
				
		//stack.mixCards();
		System.out.println();
		
		for(int i = 0; i < stack.size(); i++){
			System.out.println(stack.get(i).toString());
		}
		


	}


}