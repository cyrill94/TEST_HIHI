package cards;

import java.io.IOException;
import java.net.SocketAddress;

import com.sun.org.apache.bcel.internal.generic.ISUB;

import game.EnumPhase;
import game.Game;

import player.Player;
import server.ServerModel;

/** @author Cyrill **/

public class ActionCard extends Card {

	public ActionCard(int intCost, String strName) {
		super(intCost, strName);
		this.cardType = EnumCardType.ActionCard;

	}
	
	/** @author Thomas / Cyrill**/
	public Game run(Game game, Player player, ServerModel servermodel, SocketAddress socketaddress, boolean defendedWithBurggraben) throws IOException {
		
		//Auf allen ActionCards sind sämtliche Aktionen jeglicher Karten definiert. Mittels Switch Funktion werden nur die Aktionen des jeweiligen Kartennamens ausgeführt
		//in run Methode darf nichts direkt auf dem Objekt servermodel etwas angepasst werden, lediglich read Abfragen
		switch(this.getName()){		

			case "Abenteurer":
				for (int counter = 0; counter<2;) {
					Card c = game.drawCardFromDeck(player);
					if(c.cardType == EnumCardType.MoneyCard) {
						counter++;
					}
					else {
						game.removeCardFromHandAndPutToGraveyard(player, c);
					}
				}
			
				break;
		
			case "Burggraben":
				game.drawCardFromDeck(player);
				game.drawCardFromDeck(player);
	
				break;
			
			case "Dieb":
				//dieb läuft über separate messages und nicht über die aktionskarte selbst
				break;
			
			case "Dorf":
				game.drawCardFromDeck(player);
				game.setActionCount(player, (game.getActionCount(player)+2));

				break;
				
			
			case "Festmahl":
				game.setPhase(EnumPhase.actionFestmahl);
				break;
			
			
			case "Gaerten":
				//Gaerten macht nichts, da keine aktionskarte
				//berechnung der punkte in Game.getdeckPoints()
				break;
				
			case "Hexe":
				game.drawCardFromDeck(player);
				game.drawCardFromDeck(player);
				if (!defendedWithBurggraben) {
					game.opponentDrawSpellCard(player);
				}
				break;
				
				
			case "Holzfaeller":
				game.setBuyCount(player, (game.getBuyCount(player)+1));
				game.setAdditionalMoney(player, game.getAdditionalMoney(player)+2);

				break;
				
			case "Jahrmarkt":
				game.setActionCount(player, game.getActionCount(player)+2);
				game.setBuyCount(player, game.getBuyCount(player)+1);
				game.setAdditionalMoney(player, game.getAdditionalMoney(player)+2);
				break;
				
				
			case "Kapelle":
				game.setPhase(EnumPhase.actionKapelle);
				break;
				
			
			case "Keller":
				game.setActionCount(player, (game.getActionCount(player)+1));
				game.setPhase(EnumPhase.actionKeller);
				
				break;
				
			
			case "Laboratorium":
				game.drawCardFromDeck(player);
				game.drawCardFromDeck(player);
				game.setActionCount(player, (game.getActionCount(player)+1));
				
				break;
			
				
			case "Markt":
				game.drawCardFromDeck(player);
				game.setActionCount(player, (game.getActionCount(player)+1));
				game.setBuyCount(player, (game.getBuyCount(player)+1));
				game.setAdditionalMoney(player, game.getAdditionalMoney(player)+1);
				
				break;
				

			case "Mine":
				game.setPhase(EnumPhase.actionMine); 
				
				break;
				
				
			case "Miliz":
				if (!defendedWithBurggraben) {
					game.setPhase(EnumPhase.actionMiliz);
				}
				game.setAdditionalMoney(player, game.getAdditionalMoney(player)+2);
				
				break;
				

			case "Schmiede":
				game.drawCardFromDeck(player);
				game.drawCardFromDeck(player);
				game.drawCardFromDeck(player);
				
				break;
				
				
			case "Umbau":
				game.setPhase(EnumPhase.actionUmbau);
				
				break;
				
				
			case "Werkstatt":
				game.setPhase(EnumPhase.actionWerkstatt);
				
				break;

		}
		
	return game;
	
	}
	
	public boolean isAttackCard() {
		if (this.getName().equals("Miliz") || this.getName().equals("Hexe") || this.getName().equals("Dieb")){
				return true;
		}
		
		else {
			return false;
		}
	}
	
}
