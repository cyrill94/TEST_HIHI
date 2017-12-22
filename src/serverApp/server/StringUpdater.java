package server;
/** @author Thomas / Cyrill **/
import java.util.ArrayList;

import cards.Card;
import messages.EnumMessageType;
import player.EnumLanguage;
import player.Player;

public class StringUpdater {

	public StringUpdater() {
		
	}
	
	public String template(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			
		}
		else {
			
		}
		
		return str;
	}
	
	public String getNextPhaseString(EnumLanguage lang, Player player, EnumMessageType messageType) {
		String str = "";
		if (lang == EnumLanguage.English) {
			if (messageType == EnumMessageType.noActionCard) {
				str = "There is no action card on player " + player.getName() + "'s Hand. Next Phase.";
			}
			if (messageType == EnumMessageType.noMoneyCard) {
				str = "There is no money card on player " + player.getName() + "'s Hand. Next Phase.";
			}
			if (messageType == EnumMessageType.nextPhase) {
				str = "Next Phase.";
			}
		}
		else {
			if (messageType == EnumMessageType.noActionCard) {
				str = player.getName() + " hat keine Aktionskarte auf der Hand. N�chste Phase";
			}
			if (messageType == EnumMessageType.noMoneyCard) {
				str = player.getName() + " hat keine Geldkarte auf der Hand. N�chste Phase";
			}
			if (messageType == EnumMessageType.nextPhase) {
				str = "N�chste Phase.";
			}
		}
		
		return str;
	}
	
	public String getNextTurnString(EnumLanguage lang, Player player) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "Next players turn: " + player.getName();
		}
		else {
			str = "N�chster Spieler am Zug: " + player.getName();
		}
		
		return str;
	}
	
	public String getBuyCardsString(EnumLanguage lang, Player player, ArrayList<Card> cardsToBuy) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "Player: " + player.getName() + " has bought ";
			for (Card c:cardsToBuy) {
				str += c.getName() + ", ";
			}
			str = str.substring(0, str.length()-2);
		}
		else {
			str = "Spieler: " + player.getName() + " hat ";
			for (Card c:cardsToBuy) {
				str += c.getName() + ", ";
			}
			str = str.substring(0, str.length()-2);
			str += " gekauft";
		}
		
		return str;
	}

	public String getEndOfGameTitleString(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "Game finished!";
		}
		else {
			str = "Spiel beendet!";
		}
		
		return str;
	}
	
	public String getEndOfGameDrawString(EnumLanguage lang, int points) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "Draw! Both players have got " + points + " Points. \nThanks for playing the game!";
		}
		else {
			str = "Unentschieden! Beide Spieler haben " + points + " Punkte. \nDanke f�rs Spiel!";
		}
		
		return str;
	}
	
	public String getEndOfGameWinnerString(EnumLanguage lang, int winnerPoints, int loserPoint, Player winner, Player loser) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "Player " + winner.getName() + " has won with " + winnerPoints + " Points. \n" + 
					loser.getName() + " only has " + loserPoint + " Points. \nThanks for playing the game!";
		}
		else {
			str = "Spieler " + winner.getName() + " hat mit " + winnerPoints + " Punkten gewonnen. \n" + 
					loser.getName() + " hat nur " + loserPoint + " Punkte. \nDanke f�rs Spiel!";
		}
		
		return str;
	}

	public String getPlayActionCardString(EnumLanguage lang, Player player, Card playedCard, boolean defendedWithBurggraben) {
		String str = "";
		if (lang == EnumLanguage.English) {
			if (defendedWithBurggraben) {
				str = "Player: " + player.getName() + " has played " + playedCard.getName() + " but it was defended with Burggraben!";
			}
			else {
				str = "Player: " + player.getName() + " has played " + playedCard.getName();
			}
		}
		else {
			if (defendedWithBurggraben) {
				str = "Spieler: " + player.getName() + " hat " + playedCard.getName() + " gespielt, aber sie wurde mit dem Burggraben verteidigt!";
			}
			else {
				str = "Spieler: " + player.getName() + " hat " + playedCard.getName() + " gespielt";
			}
		}
		
		return str;
	}
	
	public String getMineAnswerString(EnumLanguage lang, Player player, Card cardToDrop, Card cardToTake) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "Player " + player.getName() + " has dropped " + cardToDrop.getName() + 
					" and taken " + cardToTake.getName();
		}
		else {
			str = "Spieler " + player.getName() + " hat " + cardToDrop.getName() + 
					" abgelegt und " + cardToTake.getName() + " aufgenommen.";
		}
		
		return str;
	}

	public String getUmbauAnswerString(EnumLanguage lang, Player player, Card cardToDrop, Card cardToTake) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "Player " + player.getName() + " has dropped " + cardToDrop.getName() + 
					" and taken " + cardToTake.getName();
		}
		else {
			str = "Spieler " + player.getName() + " hat " + cardToDrop.getName() + 
					" abgelegt und " + cardToTake.getName() + " aufgenommen.";  	
		}
		
		return str;
	}

	public String getWerkstattAnswerString(EnumLanguage lang, Player player, Card cardToTake) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "Player " + player.getName() + " has taken " + cardToTake.getName();
		}
		else {
			str = "Spieler " + player.getName() + " hat " + cardToTake.getName() + " aufgenommen.";  	
		}
		
		return str;
	}

	public String getKellerAnswerString(EnumLanguage lang, Player player, int intCardsToDrop) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "Spieler " + player.getName() + " has dropped " + intCardsToDrop + " cards and taken the same amount of cards from his deck";
		}
		else {
			str = "Spieler " + player.getName() + " hat " + intCardsToDrop + " Karten abgelegt und dieselbe Anzahl Karten auf die Hand gezogen";
		}
		
		return str;
	}

	public String getMilizAnswerString(EnumLanguage lang, Player player, int intCardsToDrop) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "Spieler " + player.getName() + " has dropped " + intCardsToDrop;
		}
		else {
			str = "Spieler " + player.getName() + " hat " + intCardsToDrop + " Karten abgelegt";
		}
		
		return str;
	}

	public String getDiebAnswerMessageString_NoCard(EnumLanguage lang, Player player, Player opponent, Card card1, Card card2) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str += "Player " + player.getName() + " has not taken any card. " + 
					"The following cards were dropped to Player " + opponent.getName() + "'s graveyard: " + 
					card1.getName() + ", " + card2.getName();
		}
		else {
			str += "Spieler " + player.getName() + " hat keine Karte zu sich genommen. " + 
					"Die folgenden Karten wurden vom Deck von Spieler " + opponent.getName() + " auf den Ablagestapel gelegt: " + 
					card1.getName() + ", " + card2.getName();
		}
		
		return str;
	}
	
	public String getDiebAnswerMessageString(EnumLanguage lang, Player player, Player opponent, Card card1, Card card2, boolean stehlen) {
		String str = "";
		if (lang == EnumLanguage.English) {
			if (stehlen) {
				str += "Player " + player.getName() + " has robbed the card " + card1.getName() + 
						". The following card was dropped to Player " + opponent.getName() + "'s graveyard: " + 
						card2.getName();
			}
			else {
				str += "Player " + player.getName() + " has chosen to liquidate the card " + card1.getName() + 
						". The following card was dropped to Player " + opponent.getName() + "'s graveyard: " + 
						card2.getName();
			}
		}
		else {
			if (stehlen) {
				str += "Spieler " + player.getName() + " hat die Karte " + card1.getName() + " gestohlen. " + 
						"Die folgende Karte wurden vom Deck von Spieler " + opponent.getName() + " auf den Ablagestapel gelegt: " +
						card2.getName();
			}
			else {
				str += "Spieler " + player.getName() + " hat die Karte " + card1.getName() + " gew�hlt und vernichtet. " + 
						"Die folgende Karte wurden vom Deck von Spieler " + opponent.getName() + " auf den Ablagestapel gelegt: " +
						card2.getName();
			}
		}
		
		return str;
	}
	
	public String getKapelleAnswerString(EnumLanguage lang, Player player, ArrayList<Card> cardsToDrop) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "Player: " + player.getName() + " has liquidated ";
			for (Card c:cardsToDrop) {
				str += c.getName() + ", ";
			}
			str = str.substring(0, str.length()-2);
		}
		else {
			str = "Spieler: " + player.getName() + " hat ";
			for (Card c:cardsToDrop) {
				str += c.getName() + ", ";
			}
			str = str.substring(0, str.length()-2);
			str += " entsorgt";
		}
		
		return str;
	}

	public String getFestmahlAnswerString(EnumLanguage lang, Player player, Card cardToTake) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "Player " + player.getName() + " has liquidated Festmahl and put " + 
					cardToTake.getName() + " to graveyard";
		}
		else {
			str = "Spieler " + player.getName() + " hat Festmahl entsorgt und daf�r " + 
					cardToTake.getName() + " aufgenommen und auf den Ablagestapel gelegt.";
		}
		
		return str;
	}
	
	
	/** @author Cyrill **/
	
	public String getLblPointsString(EnumLanguage lang, int points) {
		String str;
		if (lang == EnumLanguage.English) {
			str = "\t Points in deck: " + points;
		}
		
		else {
			str = "\t Punkte im Deck: " + points;
		}
		
		return str;

	}
	
	public String getLblAdditionalMoneyString(EnumLanguage lang, int additionalMoney) {
		String str;
		if (lang == EnumLanguage.English) {
			str = "\t Additional Money: " + additionalMoney;
		}
		
		else {
			str = "\t Zus�tzliche Geldeinheiten: " + additionalMoney;
		}
		
		return str;
		
	}
	
	public String getLblAdditionalBuysString(EnumLanguage lang, int additionalBuys) {
		String str;
		if (lang == EnumLanguage.English) {
			str = "\t Additional Buys: " + additionalBuys;
		}
		
		else {
			str = "\t Zus�tzliche Kaufeinheiten: " + additionalBuys;
		}
		
		return str;
	}
	
	public String getErrorTooManyCardsSelected(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "\nYou either have selected no or too many cards";
		}
		else {
			str = "\nDu hast keine oder zu viele Karten angew�hlt.";
		}
		
		return str;
	}
	
	public String getErrorOnlyMoneyCardsStealable(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "\nYou can only steal Money Cards";
		}
		else {
			str = "\nDu kannst nur Geldkarten stehlen.";
		}
		
		return str;
	}
	
	
	public String getErrorOnlyMoneyCardsToTrash(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "\nYou can only trash Money Cards";
		}
		else {
			str = "\nDu kannst nur Geldkarten auf den M�ll werfen.";
		}
		
		return str;
	}
	
	public String getErrorYieldHasNoMoneyCards(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "\nHard Luck: No Money Card in Yield.";
		}
		else {
			str = "\nKein Gl�ck: Die Ausbeute enth�lt keine Geldkarten.";
		}
		
		return str;
	}
	
	public String getPlayPhaseTextAction(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "Play Action Card";
		}
		else {
			str = "Aktionskarte spielen";
		}
		
		return str;
	}
	
	public String getPlayPhaseTextBuy(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "Buy card/s";
		}
		else {
			str = "Karte/n kaufen";
		}
		return str;
	}
	
	public String getPlayPhaseTextActionMine(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "Execute Card Mine";
		}
		else {
			str = "Karte Mine ausf�hren";
		}
		return str;
	}

	public String getPlayPhaseTextActionUmbau(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "Execute Card Umbau";
		}
		else {
			str = "Karte Umbau ausf�hren";
		}
		return str;
	}
	
	public String getPlayPhaseTextActionKeller(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "Drop Cards";
		}
		else {
			str = "Karten ablegen";
		}
		return str;
	}	
	
	public String getPlayPhaseTextActionWerkstatt(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "Choose Card";
		}
		else {
			str = "Karte ausw�hlen";
		}
		return str;
	}
		
	public String getPlayPhaseTextActionKapelle(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "Execute Card Kapelle";
		}
		else {
			str = "Karte Kapelle ausf�hren";
		}
		return str;
	}
	
	public String getPlayPhaseTextActionMiliz(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "Drop Cards";
		}
		else {
			str = "Karten ablegen";
		}
		return str;
	}
	
	public String getErrorNoActionCardSelected(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "\nNo Cards choosen. Please chose an Action Card to execute your turn.";
		}
		else {
			str = "\nKeine Karte angew�hlt. Bitte w�hle eine Aktionskarte aus um den Zug auszuf�hren.";
		}
		return str;
	}
	
	public String getErrorTooManyActionCardsSelected(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "\nYou have selected too many Cards. You can only play one Card at once.";
		}
		else {
			str = "\nZu viele Karten angew�hlt. Du kannst nur eine Karte auf einmal spielen.";
		}
		return str;
	}
	
	public String getErrorNoMoneyCardsSelectedOnHand(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "\nYou have not selected any Card: Please choose an object to buy"
					+ " and Money Cards from your hand. If you do not want "
					+ "to buy Cards, end the phase.";
		}
		else {
			str = "\nKeine Karte angew�hlt: Bitte w�hle Kaufobjekte"
					+ " und Geldkarten aus deiner Hand aus. Falls du keine "
					+ "Karten kaufen m�chtest, beende die Phase.";
		}
		return str;
	}
	
	public String getErrorNotEnoughBuys(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "\nWithout additional Buys you can only buy one Card per round.";
		}
		else {
			str = "\nOhne zus�tzliche Kaufeinheiten kannst du nur eine Karte pro Runde kaufen.";
		}
		return str;
	}
	
	public String getErrorNotEnoughMoney(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "\nYou don't have enough Money for the Cards you want to buy. Please adapt your cart.";
		}
		else {
			str = "\nDu hast nicht gen�gend Geld f�r die Karten, die du kaufen m�chtest. Bitte passe deinen Warenkorb an.";
		}
		return str;
	}
	
	public String getErrorEmptyCart(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "\nYour cart is empty. If you do not want to buy any Card, end the phase.";
		}
		else {
			str = "\nDein Warenkorb ist leer. Wenn du keine Karte kaufen m�chtest, beende die Phase.";
		}
		return str;
	}
	
	public String getErrorActionMineCardTooExpensive(EnumLanguage lang, int valueCardToDrop) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "\nThe Card you want to take is too expensive. Max. value: " +(valueCardToDrop+3);
		}
		else {
			str = "\nZu nehmende Karte ist zu teuer. Maximaler Wert: " +(valueCardToDrop+3);
		}
		return str;
	}
	
	public String getErrorActionMineOnlyMoneyCards(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "\nOnly Money Cards can be played with the Card Mine.";
		}
		else {
			str = "\nBei der Mine k�nnen nur Geldkarten gespielt werden.";
		}
		return str;
	}
	
	public String getErrorActionMineTooManyOrNotEnoughCards(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "\nToo many or no Cards choosen.";
		}
		else {
			str = "\nZu viele oder keine Karten ausgew�hlt.";
		}
		return str;
	}
	
	public String getErrorActionUmbauCardTooExpensive(EnumLanguage lang, int valueCardToDrop) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "\nThe Card you want to take is too expensive. Max. value: " + (valueCardToDrop+2);
		}
		else {
			str = "\nZu nehmende Karte ist zu teuer. Maximaler Wert: " + (valueCardToDrop+2);
		}
		return str;
	}
	
	public String getErrorActionUmbauTooManyOrNoCardsSelected(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "\nToo many or no Cards choosen.";
		}
		else {
			str = "\nZu viele oder keine Karten ausgew�hlt.";
		}
		return str;
	}
	
	public String getErrorActionWerkstattCardTooExpensive(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "\nSelected Card ist too expensive.";
		}
		else {
			str = "\nGew�nschte Karte ist zu teuer.";
		}
		return str;
	}
	
	public String getErrorActionWerkstattTooManyOrNoCardsSelected(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "\nToo many or no Cards choosen.";
		}
		else {
			str = "\nZu viele oder keine Karten ausgew�hlt.";
		}
		return str;
	}
	
	public String getErrorActionKellerNoCardsSelected(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "\nYou have not selected any Cards.";
		}
		else {
			str = "\nDu hast keine Karten angew�hlt.";
		}
		return str;
	}
	
	public String getErrorActionMilizCardDropMismatch(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "\nDrop Cards until 3 Cards remain on your hand.";
		}
		else {
			str = "\nLege so viele Karten ab, dass noch genau 3 Karten auf deiner Hand sind.";
		}
		return str;
	}
	
	public String getErrorActionKapelleTooManyCardsDropped(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "\nToo many Cards selected. You can only drop 4 Cards.";
		}
		else {
			str = "\nZu viele Karten ausgew�hlt. Maximal 4 Karten k�nnen abgelegt werden.";
		}
		return str;
	}
	
	public String getErrorActionFestmahlCardTooExpensive(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "\nSelected Card ist too expensive. Max. value: 5";
		}
		else {
			str = "\nZu nehmende Karte ist zu teuer. Maximaler Wert: 5";
		}
		return str;
	}
	
	public String getErrorActionFestmahlTooManyOrNoCardsSelected(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "\nToo many or no Card selected. You can only take 1 Card.";
		}
		else {
			str = "\nZu viele oder keine Karten ausgew�hlt. Du kannst maximal 1 Karte nehmen.";
		}
		return str;
	}
	
	public String getStartGameString(EnumLanguage lang) {
		String str = "";

		if (lang == EnumLanguage.English) {
			str = "\nGame started!";
		}
		else {
			str = "\nSpiel hat begonnen!";
		}
		return str;
	}
	
	public String getBurggrabenString(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "\nBurggraben can be played. Please wait until decision is set.";
		}
		else {
			str = "\nDer Burggraben kann gespielt werden. Warte auf eine Entscheidung.";
		}
		
		return str;
	}
	
	public String getYourOpponentPlayedBurggraben(EnumLanguage lang) {
		String str = "";
		if (lang == EnumLanguage.English) {
			str = "\nYour opponent played an attacking Card.\nDo you want to defend it with Burggraben?";
		}
		else {
			str = "\nDein Gegner hat eine Angriffskarte gespielt.\nM�chtest du mit dem Burggraben verteidigen?";
		}
		
		return str;
	}
	
	
}



