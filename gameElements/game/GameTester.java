package game;

import java.util.ArrayList;

import cards.ActionCard;
import cards.Card;
import cards.MoneyCard;
import player.EnumCardSet;
import player.Player;
import server.Slot;
import stacks.CardStack;

public class GameTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Player player1 = new Player("p1", "p1");
			Player player2 = new Player("p2", "p2");
			
			Slot slot1 = new Slot(player1);
			Slot slot2 = new Slot(player2);
			
			Game game = new Game(EnumCardSet.BigMoney, slot1.getPlayer(), slot2.getPlayer());			
			
			
			
			//
			//TEST FROM HERE ON
			//
			//
			//
			
			if (game.getPointStack6().size() == 8){
				System.out.println(game.getPointStack3().size());
				System.out.println("HIHI");
			}
			
	}

}
