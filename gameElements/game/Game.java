/**@author Sven**/
package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

import cards.ActionCard;
import cards.Card;
import cards.EnumCardType;
import cards.MoneyCard;
import cards.PointCard;
import cards.SpellCard;
import messages.EndGameMessage;
import player.EnumCardSet;
import player.Player;
import server.Slot;
import stacks.CardStack;

public class Game implements Serializable {
	
	private Stack<MoneyCard> MoneyStackCopper, MoneyStackSilver, MoneyStackGold;
	private Stack<PointCard> PointStack1, PointStack3, PointStack6;
	private Stack<SpellCard> spellCardStack;
	private ArrayList<Stack> ActionStacks, StandardStacks;

	private ArrayList<ActionCard> cardSet;
	private GamePlayer gamePlayer1;
	private GamePlayer gamePlayer2;
	private EnumPhase phase;
	private ArrayList<String> gameLog;
	private Player player1;
	private Player player2;
	
	public Game(EnumCardSet cS, Player player1, Player player2){
		this.cardSet = new ArrayList<ActionCard>();
		this.ActionStacks = new ArrayList<Stack>();
		this.StandardStacks = new ArrayList<Stack>();
		this.spellCardStack = new Stack<SpellCard>();
		this.gamePlayer1 = new GamePlayer(player1, true);
		this.gamePlayer2 = new GamePlayer(player2, false);
		this.phase = EnumPhase.Action;
		this.player1 = player1;
		this.player2 = player2;
		initializeCardSets(cS);
		initializeStacks();
		initializeDeck(gamePlayer1);
		initializeDeck(gamePlayer2);
		System.out.println("deckstack initialisiert player1: " + this.gamePlayer1.getdeckStack());
		System.out.println("deckstack initialisiert player2: " + this.gamePlayer2.getdeckStack());
		for (int i = 0; i < 5; i ++) {
			this.drawCardFromDeck(player1);
		}
		for (int i = 0; i < 5; i ++) {
			this.drawCardFromDeck(player2);
		}
	}

	/*Definiert auf Grund der Wahl des Kartensets in den Spieleinstellungen,
	 *welche Action-Karten in 10er-Stapeln geladen werden.*/
	private void initializeCardSets(EnumCardSet cS){
		
		switch(cS){		
		/*Erstes Spiel: Burggraben, Dorf, Holzfäller, Keller, Markt, Miliz, Mine,
		Schmiede, Umbau, Werkstatt.*/
			case Standard:
				this.cardSet.add(new ActionCard(2, "Burggraben"));
				this.cardSet.add(new ActionCard(3, "Dorf"));
				this.cardSet.add(new ActionCard(3, "Holzfaeller"));
				this.cardSet.add(new ActionCard(2, "Keller"));
				this.cardSet.add(new ActionCard(5, "Markt"));
				this.cardSet.add(new ActionCard(4, "Miliz"));
				this.cardSet.add(new ActionCard(5, "Mine"));
				this.cardSet.add(new ActionCard(4, "Schmiede"));
				this.cardSet.add(new ActionCard(4, "Umbau"));
				this.cardSet.add(new ActionCard(3, "Werkstatt"));		
				break;
				
		/*Großes Geld: Abenteurer, Bürokrat, Festmahl, Geldverleiher, Kanzler, Kapelle,
		Laboratorium, Markt, Mine, Thronsaal.*/
			case BigMoney:
				this.cardSet.add(new ActionCard(6, "Abenteurer"));
				this.cardSet.add(new ActionCard(4, "Buerokrat"));
				this.cardSet.add(new ActionCard(4, "Festmahl"));
				this.cardSet.add(new ActionCard(4, "Geldverleiher"));
				this.cardSet.add(new ActionCard(3, "Kanzler"));
				this.cardSet.add(new ActionCard(2, "Kapelle"));
				this.cardSet.add(new ActionCard(5, "Laboratorium"));
				this.cardSet.add(new ActionCard(5, "Markt"));
				this.cardSet.add(new ActionCard(5, "Mine"));
				this.cardSet.add(new ActionCard(4, "Thronsaal"));		
				break;
			
		/*Im Wandel: Dieb, Dorf, Festmahl, Gärten, Hexe, Holzfäller, Kapelle, Keller,
		Laboratorium, Werkstatt.*/
			case InChange:
				this.cardSet.add(new ActionCard(4, "Dieb"));
				this.cardSet.add(new ActionCard(3, "Dorf"));
				this.cardSet.add(new ActionCard(4, "Festmahl"));
				this.cardSet.add(new ActionCard(4, "Gaerten"));
				this.cardSet.add(new ActionCard(5, "Hexe"));
				this.cardSet.add(new ActionCard(3, "Holzfaeller"));
				this.cardSet.add(new ActionCard(2, "Kapelle"));
				this.cardSet.add(new ActionCard(2, "Keller"));
				this.cardSet.add(new ActionCard(5, "Laboratorium"));
				this.cardSet.add(new ActionCard(3, "Werkstatt"));	
				break;
		
		/*Dorfplatz: Bibliothek, Bürokrat, Dorf, Holzfäller, Jahrmarkt, Keller, Markt,
		Schmiede, Thronsaal, Umbau.*/		
			case VillageSquare:
				this.cardSet.add(new ActionCard(5, "Bibliothek"));
				this.cardSet.add(new ActionCard(4, "Buerokrat"));
				this.cardSet.add(new ActionCard(3, "Dorf"));
				this.cardSet.add(new ActionCard(3, "Holzfaeller"));
				this.cardSet.add(new ActionCard(5, "Jahrmarkt"));
				this.cardSet.add(new ActionCard(2, "Keller"));
				this.cardSet.add(new ActionCard(5, "Markt"));
				this.cardSet.add(new ActionCard(4, "Schmiede"));
				this.cardSet.add(new ActionCard(4, "Thronsaal"));
				this.cardSet.add(new ActionCard(4, "Umbau"));		
				break;
		
		//Als Default wird das Standardset gesetzt.
			default:
				this.cardSet.add(new ActionCard(2, "Burggraben"));
				this.cardSet.add(new ActionCard(3, "Dorf"));
				this.cardSet.add(new ActionCard(3, "Holzfaeller"));
				this.cardSet.add(new ActionCard(2, "Keller"));
				this.cardSet.add(new ActionCard(5, "Markt"));
				this.cardSet.add(new ActionCard(4, "Miliz"));
				this.cardSet.add(new ActionCard(5, "Miene"));
				this.cardSet.add(new ActionCard(4, "Schmiede"));
				this.cardSet.add(new ActionCard(4, "Umbau"));
				this.cardSet.add(new ActionCard(3, "Werkstatt"));		
				break;
		}
	}
	
	/*Initialisiert alle Kartenstapel in der Spielfeldmitte (ohne Hand bzw. Spielerdecks)*/
	private void initializeStacks(){

		int numberOfCopperCards = 60, numberOfSilverCards = 40, numberOfGoldCards = 30;
		int numberOfPointCards1 = 8, numberOfPointCards3 = 8, numberOfPointCards6 = 8;
		int numberOfActionCards = 10;
		int numberOfSpellCards = 10;

		/**MoneyCards**/
		//füllt den Kupferstapel mit 60 Kupferkarten auf
		this.MoneyStackCopper = new Stack<MoneyCard>();
		for(int i = 0; i < numberOfCopperCards; i++){
			this.MoneyStackCopper.add(new MoneyCard(0, 1, "Kupfer"));
		}
		
		//füllt den Silberstapel mit 40 Silberkarten auf 
		this.MoneyStackSilver = new Stack<MoneyCard>();
		for(int i = 0; i < numberOfSilverCards; i++){
			this.MoneyStackSilver.add(new MoneyCard(3, 2, "Silber"));
		}
		
		//füllt den Goldstapel mit 30 Goldkarten auf
		this.MoneyStackGold = new Stack<MoneyCard>();
		for(int i = 0; i < numberOfGoldCards; i++){
			this.MoneyStackGold.add(new MoneyCard(6, 3, "Gold"));
		}
		
		/**Point Cards**/
		//füllt den Anwesenstapel mit 8 Karten auf
		this.PointStack1 = new Stack<PointCard>();
		for(int i = 0; i < numberOfPointCards1; i++){
			this.PointStack1.add(new PointCard(2, 1, "Anwesen"));
		}
		
		//füllt den Herzogtumstapel mit 8 Karten auf
		this.PointStack3 = new Stack<PointCard>();
		for(int i = 0; i < numberOfPointCards3; i++){
			this.PointStack3.add(new PointCard(5, 3, "Herzogtum"));
		}
		
		//füllt den Provinzstapel mit 8 Karten auf
		this.PointStack6 = new Stack<PointCard>();
		for(int i = 0; i < numberOfPointCards6; i++){
			this.PointStack6.add(new PointCard(8, 6, "Provinz"));
		}
		
		//füllt den Spellstapel mit Karten auf
		for(int i = 0; i < numberOfSpellCards; i++){
			this.spellCardStack.add(new SpellCard(0, "Fluch"));
		}
		
		//füllt die Stapel mit Geld- und Punktekarten in eine ArrayList ab
		//(für einfachen Zugriff auf die Stapel)
		this.StandardStacks.add(MoneyStackCopper);
		this.StandardStacks.add(MoneyStackSilver);
		this.StandardStacks.add(MoneyStackGold);
		this.StandardStacks.add(PointStack1);
		this.StandardStacks.add(PointStack3);
		this.StandardStacks.add(PointStack6);
				
		//füllt alle 10 Aktionsstapel mit jeweils 10 Karten auf
		for(int i = 0; i < 10; i++){
			Stack<ActionCard> stack = new Stack<ActionCard>();
			for(int y = 0; y < numberOfActionCards; y++){
				stack.add(new ActionCard(cardSet.get(i).getIntCost(),cardSet.get(i).getName()));
			}
			this.ActionStacks.add(stack);
		}
	}
	
	/**@author Thomas**/
	private void initializeDeck(GamePlayer player) {
		ArrayList<Card> list = new ArrayList<Card>();
		for(int i=0; i<7; i++){
			list.add(this.MoneyStackCopper.pop());
		}
		for(int i=0; i<3; i++){
			list.add(this.PointStack1.pop());
		}
		CardStack stack = new CardStack(list);
		player.setDeckStack(stack);
		player.mixDeckStack();
	}
	
	public ArrayList<ActionCard> getCardSet() {
		return cardSet;
	}
	
	/**@author Thomas**/
	/*ändert den ziehenden Spieler (player.setTurn()), je nachdem welcher Spieler an der Reihe war.
	 *zusätzlich wird die Phase auf Aktionsphase geändert und der Actioncount auf 1 gesetzt.*/
	public void changeTurn() {
		
			this.setPhase(EnumPhase.Action);
			this.gamePlayer1.setActionCount(1);
			this.gamePlayer2.setActionCount(1);
			this.gamePlayer1.setBuyCount(1);
			this.gamePlayer2.setBuyCount(1);
			this.gamePlayer1.setAdditionalMoney(0);
			this.gamePlayer2.setAdditionalMoney(0);
			if (this.gamePlayer1.getTurn() == true) {
				this.gamePlayer1.setTurn(false);
				this.gamePlayer1.putAllHandCardsToGraveyard();
				this.gamePlayer2.setTurn(true);
				for (int i = 0; i < 5; i++) {
					this.drawCardFromDeck(gamePlayer1.getPlayer());
				}
			}
			else {
				this.gamePlayer2.setTurn(false);
				this.gamePlayer2.putAllHandCardsToGraveyard();
				this.gamePlayer1.setTurn(true);
				for (int i = 0; i < 5; i++) {
					this.drawCardFromDeck(gamePlayer2.getPlayer());
				}
			}
		}


	/**@author Thomas**/
	/* setzt die Phase auf die angegebene EnumPhase*/
	public void setPhase(EnumPhase phase) {
		this.phase = phase;
	}
	
	/**@author Thomas**/
	public void nextPhase() {
		if (this.phase == EnumPhase.Buy) {
			this.changeTurn();
		}
		else {
			//this.setPhase(EnumPhase.Buy);
			if (this.getActionCount(this.getTurn()) > 0) {
				this.setPhase(EnumPhase.Action);
			}
			else {
				this.setPhase(EnumPhase.Buy);
			}
			
		}
		
	}

	/**@author Thomas**/
	// gibt die Punkte für den angegebenen Spieler zurück
	public int getPoints(Player player) {
		if (player.getName().equals(gamePlayer1.getPlayer().getName())) {			
			return gamePlayer1.getPoints();
		}
		else {
			return gamePlayer2.getPoints();
		}
	}

	/**@author Thomas**/
	//zieht für den angegebenen Spieler eine Karte vom Deck (player.deckStack) und legt sie zu den HandKarten (player.handCards)
	//wenn keine Karte mehr auf dem Deck hat, soll der Graveyard zum Deck gelegt werden und das Deck gemischt werden, bevor eine neue Karte gezogen wird. 
	public void drawCardFromDeck(Player player) {
		if (player.equals(gamePlayer1.getPlayer())) {	
			if (gamePlayer1.getdeckStack().getSize() == 0) {
				gamePlayer1.setDeckStack(gamePlayer1.getgraveyardCards());
				gamePlayer1.mixDeckStack();
				gamePlayer1.clearGraveyard();
			}
			gamePlayer1.addCardToHand(gamePlayer1.drawCardFromDeck());
		}
		else {
			if (gamePlayer2.getdeckStack().getSize() == 0) {
				gamePlayer2.setDeckStack(gamePlayer2.getgraveyardCards());
				gamePlayer2.mixDeckStack();
				gamePlayer2.clearGraveyard();
			}
			gamePlayer2.addCardToHand(gamePlayer2.drawCardFromDeck());
		}
	}

	/**@author Thomas**/
	//gibt den Player zurück, der aktuell am Zug ist
	public Player getTurn() {
		if (gamePlayer1.getTurn() == true) {
			return gamePlayer1.getPlayer();
		}
		else {
			return gamePlayer2.getPlayer();
		}
	}


	/**@author Thomas**/
	//setzt für den angegebenen Spieler die angegebene Menge Geld für den nächsten Kauf. ist nach Change turn wieder zurückgesetzt auf 0
	public void setAdditionalMoney(Player player, int additionalMoney) {
		if (player.equals(gamePlayer1.getPlayer())) {			
			gamePlayer1.setAdditionalMoney(additionalMoney);
		}
		else {
			gamePlayer2.setAdditionalMoney(additionalMoney);
		}
	}

	/**@author Thomas**/
	public void mixDeckStack(Player player) {
		if (player.equals(gamePlayer1.getPlayer())) {			
			gamePlayer1.mixDeckStack();
		}
		else {
			gamePlayer2.mixDeckStack();
		}
	}

	/**@author Thomas**/
	public void setDeckStack(Player player, CardStack deckStack) {
		if (player.equals(gamePlayer1.getPlayer())) {			
			gamePlayer1.setdeckStack(deckStack);
		}
		else {
			gamePlayer2.setdeckStack(deckStack);
		}
	}

	/**@author Thomas**/
	public void addCardToHand(Player player, Card card) {
		if (player.equals(gamePlayer1.getPlayer())) {	
			gamePlayer1.addCardToHand(card);
		}
		else {
			gamePlayer2.addCardToHand(card);
		}
	}

	/**@author Thomas**/
	//nimmt eine Karte von der Hand und legt sie auf den Ablagestapel
	public void removeCardFromHandAndPutToGraveyard(Player player, Card card) {
		if (player.equals(gamePlayer1.getPlayer())) {	
			gamePlayer1.removeCardFromHand(card);
			gamePlayer1.addCardToGraveyard(card);
		}
		else {
			gamePlayer2.removeCardFromHand(card);
			gamePlayer2.addCardToGraveyard(card);
		}
	}


	/**@author Thomas**/
	public void addCardToGraveyard(Player player, Card card) {
		if (player.equals(gamePlayer1.getPlayer())) {	
			gamePlayer1.addCardToGraveyard(card);
		}
		else {
			gamePlayer2.addCardToGraveyard(card);
		}
	}
	
	/**@author Thomas**/
	public int getActionCount(Player player) {
		if (player.equals(gamePlayer1.getPlayer())) {	
			return gamePlayer1.getActionCount();
		}
		else {
			return gamePlayer2.getActionCount();
		}
	}

	/**@author Thomas**/
	public void setActionCount(Player player, int actionCount) {
		if (player.equals(gamePlayer1.getPlayer())) {	
			gamePlayer1.setActionCount(actionCount);
		}
		else {
			gamePlayer2.setActionCount(actionCount);
		}
	}

	/**@author Thomas**/
	public int getAdditionalMoney(Player player) {
		if (player.getName().equals(gamePlayer1.getPlayer().getName())) {	
			return gamePlayer1.getAdditionalMoney();
		}
		else {
			return gamePlayer2.getAdditionalMoney();
		}
	}
	
	/**@author Thomas**/
	public void setBuyCount(Player player, int buyCount) {
		if (player.equals(gamePlayer1.getPlayer())) {	
			gamePlayer1.setBuyCount(buyCount);
		}
		else {
			gamePlayer2.setBuyCount(buyCount);
		}
	}

	/**@author Thomas**/
	public int getBuyCount(Player player) {
		if (player.getName().equals(gamePlayer1.getPlayer().getName())) {	
			return gamePlayer1.getBuyCount();
		}
		else {
			return gamePlayer2.getBuyCount();
		}
	}

	/**@author Thomas**/
	public void putAllHandCards	(Player player) {
		if (player.equals(gamePlayer1.getPlayer())) {	
			gamePlayer1.putAllHandCardsToGraveyard();
		}
		else {
			gamePlayer2.putAllHandCardsToGraveyard();
		}
	}
	
	public Card getCardFromActionStacks(int stackID) {
		return (Card) this.ActionStacks.get(stackID).peek();
	}
	
///NEEDS TO BE TESTED
/** @author Sven und Thomas **/
	public void buyCards(Player player, ArrayList<MoneyCard> moneyCards, ArrayList<Card> cardsToBuy) {
		//lege jede moneyCard, mit der ich kaufen möchte, auf den Ablagestapel
		for (int counter = 0; counter < moneyCards.size(); counter++) { 		      
	          this.removeCardFromHandAndPutToGraveyard(player, moneyCards.get(counter));
	    }
		
		System.out.println("Handkarten nach dem Ablegen der Karten p1: " + gamePlayer1.gethandCards());
		System.out.println("Handkarten nach dem Ablegen der Karten p2: " + gamePlayer2.gethandCards());
		
		//iteriere durch alle ActionStacks, bis der Stack mit der zu kaufenden Karte gefunden wurde. 
		//Am Schluss wird diese Karte vom Stapel gezogen und auf die Hand des Spielers gelegt.
		for (Card card:cardsToBuy) {  
			if (card.getCardType() == EnumCardType.ActionCard) {
				
				int stackID = -1;
				for(int i = 0; i < 10; i++){
					Stack stack = this.ActionStacks.get(i);
					if (stack.size() > 0) {
						Card tempcard = (Card) stack.peek();
						if (tempcard.getName().equals(card.getName())){
							stackID = i;
						}
					}
				}  
				this.addCardToGraveyard(player, (Card)this.ActionStacks.get(stackID).pop());
			}
			
			else if (card.getCardType() == EnumCardType.MoneyCard||card.getCardType() == EnumCardType.PointCard) {
				int stackID = -1;
				for(int i = 0; i < 6; i++){
					Stack stack = this.StandardStacks.get(i);
					if (stack.size() > 0) {
						Card tempcard = (Card) stack.peek();
						if (tempcard.getName().equals(card.getName())){
							stackID = i;
						}
					}
					
				}   
				this.addCardToGraveyard(player, (Card)this.StandardStacks.get(stackID).pop());
			}
			
			else if (card.getCardType() == EnumCardType.SpellCard) {
				System.out.println("Fuck of with your Spell Cards");
			}
	    }
	}

	public CardStack getHandCards(Player player) {
		if (player.getName().equals(gamePlayer1.getPlayer().getName())) {	
			return gamePlayer1.gethandCards();
		}
		else {
			return gamePlayer2.gethandCards();
		}
	}

	public void takeActionCard(Player player, int stackID) {
			this.addCardToGraveyard(player, (Card)this.ActionStacks.get(stackID).pop());
	}
	
	public void takePointCard(Player player, int type) {
		if (type==1) {
			this.addCardToGraveyard(player, (Card) this.PointStack1.pop());
		}
		else if (type==3) {
			this.addCardToGraveyard(player, (Card) this.PointStack3.pop());
		}
		else {
			this.addCardToGraveyard(player, (Card) this.PointStack6.pop());
		}
	}
	
	public void takeMoneyCard(Player player, int type) {
		if (type==1) {
			this.addCardToGraveyard(player, (Card) this.MoneyStackCopper.pop());
		}
		else if (type==2) {
			this.addCardToGraveyard(player, (Card) this.MoneyStackSilver.pop());
		}
		else {
			this.addCardToGraveyard(player, (Card) this.MoneyStackGold.pop());
		}
	}
	
	//gibt den Stack zurück, auf den die ID passt. ID maximal 9 (10 Stacks, beginnt bei 0)
	public Stack getStack(int id) {
		return this.ActionStacks.get(id);
	}

	//legt den Graveyard auf das Deck und mischt das Deck
	public void putGraveyardToDeck(Player player) {
		if (player.equals(gamePlayer1.getPlayer())) {	
			gamePlayer1.setDeckStack(gamePlayer1.getgraveyardCards());
			gamePlayer1.clearGraveyard();
			gamePlayer1.mixDeckStack();
		}
		else {
			gamePlayer2.setDeckStack(gamePlayer1.getgraveyardCards());
			gamePlayer2.clearGraveyard();
			gamePlayer2.mixDeckStack();
		}
	}

	public EnumPhase getPhase() {
		return this.phase;
	}
	
	public void setPoints (Player player, int points) {
		if (player.equals(gamePlayer1.getPlayer())) {	
			gamePlayer1.setPoints(points);
		}
		else {
			gamePlayer2.setPoints(points);
		}
	}
	
	/** @author Cyrill **/
	
	public Stack getMoneyStackCopper() {
		return this.MoneyStackCopper;
	}
	
	public Stack getMoneyStackSilver() {
		return this.MoneyStackSilver;
	}
	
	public Stack getMoneyStackGold() {
		return this.MoneyStackGold;
	}
	
	public Stack getPointStack1() {
		return this.PointStack1;
	}
	
	public Stack getPointStack3() {
		return this.PointStack3;
	}
	
	public Stack getPointStack6() {
		return this.PointStack6;
	}	
	
	// Prüft ob Bedingungen um das Spiel zu beenden erfüllt sind
	public boolean endOfGame() {
		boolean gameEnded = false;
		int emptyStackCount = 0;
		
		// Bedingung 1 für Ende des Spiels
				if (this.PointStack6.size() == 0) {
					gameEnded = true;
				}
		
		for(int i = 0; i < 6; i++){
			Stack stack = this.StandardStacks.get(i);
			if (stack.size() == 0) {
				emptyStackCount++;
			}
		}  
		
		for(int i = 0; i < 10; i++){
			Stack stack = this.ActionStacks.get(i);
			if (stack.size() == 0) {
				emptyStackCount++;
			}
		} 
		
		if (2 < emptyStackCount) {
			gameEnded = true;
		}
		
		return gameEnded;
	}

	
	public GamePlayer calculateWinner() {
		GamePlayer winner = null;
		
		if (this.getDeckPoints(player1)<this.getDeckPoints(player2)) {
			winner = this.gamePlayer2;
		}
		
		if (this.getDeckPoints(player1)>this.getDeckPoints(player2)) {
			winner = this.gamePlayer1;
		}
		
		return winner;
	}
	
	public GamePlayer calculateLoser() {
		GamePlayer loser = null;
		
		if (this.calculateWinner() == null) {
			return loser;
		}
		else {
			if (this.calculateWinner().getPlayer().getName().equals(this.gamePlayer1.getPlayer().getName())) {
				loser = this.gamePlayer2;			
			}
			else {
				loser = this.gamePlayer1;
			}
			
			return loser;
		}
	}
	
	public ArrayList<Stack> getActionStacks() {
		return ActionStacks;
	}
	
	public ArrayList<Stack> getStandardStacks() {
		return StandardStacks;
	}

	public void takeCardFromStackAndPutToHand(Player player, Card card) {
		if (card.getCardType() == EnumCardType.ActionCard) {
				
				int stackID = -1;
				for(int i = 0; i < 10; i++){
					Stack stack = this.ActionStacks.get(i);

					Card tempcard = (Card) stack.peek();
					if (tempcard.getName().equals(card.getName())){
						stackID = i;
					}
				}  
				this.addCardToHand(player, (Card)this.ActionStacks.get(stackID).pop());
		}
		else if (card.getCardType() == EnumCardType.MoneyCard||card.getCardType() == EnumCardType.PointCard) {
			int stackID = -1;
			for(int i = 0; i < 6; i++){
				Stack stack = this.StandardStacks.get(i);
				if (stack.size() > 0) {
					Card tempcard = (Card) stack.peek();
					if (tempcard.getName().equals(card.getName())){
						stackID = i;
					}
				}
				
			}   
			this.addCardToHand(player, (Card)this.StandardStacks.get(stackID).pop());
		}
		
	}
	
	public void takeCardFromStackAndPutToGraveyard(Player player, Card card) {
		if (card.getCardType() == EnumCardType.ActionCard) {
				
				int stackID = -1;
				for(int i = 0; i < 10; i++){
					Stack stack = this.ActionStacks.get(i);
					if (stack.size() > 0) {
						Card tempcard = (Card) stack.peek();
						if (tempcard.getName().equals(card.getName())){
							stackID = i;
						}
					}
					
				}  
				this.addCardToGraveyard(player, (Card)this.ActionStacks.get(stackID).pop());
		}
		else if (card.getCardType() == EnumCardType.MoneyCard||card.getCardType() == EnumCardType.PointCard) {
			int stackID = -1;
			for(int i = 0; i < 6; i++){
				Stack stack = this.StandardStacks.get(i);

				Card tempcard = (Card) stack.peek();
				if (tempcard.getName().equals(card.getName())){
					stackID = i;
				}
			}   
			this.addCardToGraveyard(player, (Card)this.StandardStacks.get(stackID).pop());
		}	
	}
	
	/** @author Cyrill **/
	public void removeCardFromHandAndPutToTrash (Player player, Card card) {

		if (player.equals(gamePlayer1.getPlayer())) {	
			gamePlayer1.removeCardFromHand(card);
		}
		else {
			gamePlayer2.removeCardFromHand(card);
		}		
		
	}
	
	/**@author Sven**/
	//returns total points of a players deck
	public int getDeckPoints(Player player){
		int points = 0;
		int cardscount = 0;
		int gaertencount = 0;
		
		if(player.getName().toString().equals(gamePlayer1.getPlayer().getName().toString())){
			for(Card c:gamePlayer1.getdeckStack().getArrCards()){
				cardscount ++;
				if(c.getCardType()==EnumCardType.PointCard){
					PointCard pc = (PointCard) c;
					points += pc.getValue();
				}
				if(c.getCardType()==EnumCardType.SpellCard){
					points--;
				}
				if(c.getName().equals("Gaerten")) {
					gaertencount++;
				}
			}
			for(Card c:gamePlayer1.getgraveyardCards().getArrCards()){
				cardscount++;
				if(c.getCardType()==EnumCardType.PointCard){
					PointCard pc = (PointCard) c;
					points += pc.getValue();
				}
				if(c.getCardType()==EnumCardType.SpellCard){
					points--;
				}
				if(c.getName().equals("Gaerten")) {
					gaertencount++;
				}
			}
			for(Card c:gamePlayer1.gethandCards().getArrCards()){
				cardscount++;
				if(c.getCardType()==EnumCardType.PointCard){
					PointCard pc = (PointCard) c;
					points += pc.getValue();
				}
				if(c.getCardType()==EnumCardType.SpellCard){
					points--;
				}
				if(c.getName().equals("Gaerten")) {
					gaertencount++;
				}
			}
			points += Math.floor((gaertencount * cardscount)/10);
		}
		else if(player.getName().toString().equals(gamePlayer2.getPlayer().getName().toString())){
			for(Card c:gamePlayer2.getdeckStack().getArrCards()){
				cardscount ++;
				if(c.getCardType()==EnumCardType.PointCard){
					PointCard pc = (PointCard) c;
					points += pc.getValue();
				}
				if(c.getCardType()==EnumCardType.SpellCard){
					points--;
				}
				if(c.getName().equals("Gaerten")) {
					gaertencount++;
				}
			}
			for(Card c:gamePlayer2.getgraveyardCards().getArrCards()){
				cardscount++;
				if(c.getCardType()==EnumCardType.PointCard){
					PointCard pc = (PointCard) c;
					points += pc.getValue();
				}
				if(c.getCardType()==EnumCardType.SpellCard){
					points--;
				}
				if(c.getName().equals("Gaerten")) {
					gaertencount++;
				}
			}
			for(Card c:gamePlayer2.gethandCards().getArrCards()){
				cardscount++;
				if(c.getCardType()==EnumCardType.PointCard){
					PointCard pc = (PointCard) c;
					points += pc.getValue();
				}
				if(c.getCardType()==EnumCardType.SpellCard){
					points--;
				}
				if(c.getName().equals("Gaerten")) {
					gaertencount++;
				}
			}
			points += Math.floor((gaertencount * cardscount)/10);
		}
		return points;
	}
	
	//Method only used for Dieb Action Card, do not use this otherwise
	public Card popDeckCard_dieb(Player player) {
		if (player.equals(gamePlayer1.getPlayer())) {	
			
			return gamePlayer1.getdeckStack().popCard();
			
		}
		else {
			
			return gamePlayer2.getdeckStack().popCard();
		}
	}
	
	//Method only used for Dieb Action Card, do not use this otherwise
		public void addCardToDeck_dieb(Player player, Card card) {
			if (player.equals(gamePlayer1.getPlayer())) {	
				
				gamePlayer1.getdeckStack().add(card);
				
			}
			else {

				gamePlayer2.getdeckStack().add(card);
			}
		}
	
	/** @author Cyrill **/
	public void opponentDrawSpellCard(Player player) {
		if (player.getName().equals(this.gamePlayer1.getPlayer().getName())) {
			if (!(this.spellCardStack.isEmpty())) {
				gamePlayer2.addCardToGraveyard(this.spellCardStack.pop());
			}
		}
		
		else if (player.getName().equals(this.gamePlayer2.getPlayer().getName())) {
			if (!(this.spellCardStack.isEmpty())) {
				gamePlayer1.addCardToGraveyard(this.spellCardStack.pop());
			}
		}
	}
	
	public void checkDeckForLowCards(Player player) {
		System.out.println("CHECKED FOR LOW CARDS");
		System.out.println(gamePlayer1.getdeckStack());
		System.out.println(gamePlayer2.getdeckStack());
		if (player.getName().equals(this.gamePlayer1.getPlayer().getName())) {
			if (gamePlayer1.getdeckStack().getSize() == 0) {
				gamePlayer1.setDeckStack(gamePlayer1.getgraveyardCards());
				gamePlayer1.mixDeckStack();
				gamePlayer1.clearGraveyard();
			}
		}
		
		else if (player.getName().equals(this.gamePlayer2.getPlayer().getName())) {
			if (gamePlayer2.getdeckStack().getSize() == 0) {
				gamePlayer2.setDeckStack(gamePlayer2.getgraveyardCards());
				gamePlayer2.mixDeckStack();
				gamePlayer2.clearGraveyard();
			}
		}
		
		else if (player.getName().equals(this.gamePlayer1.getPlayer().getName())) {
			if (gamePlayer1.getdeckStack().getSize() == 1) {
				Card tempCard = gamePlayer1.drawCardFromDeck();
				gamePlayer1.setDeckStack(gamePlayer1.getgraveyardCards());
				gamePlayer1.mixDeckStack();
				gamePlayer1.getdeckStack().add(tempCard);
				gamePlayer1.clearGraveyard();
			}
		}
		
		else if (player.getName().equals(this.gamePlayer2.getPlayer().getName())) {
			if (gamePlayer2.getdeckStack().getSize() == 1) {
				Card tempCard = gamePlayer2.drawCardFromDeck();
				gamePlayer2.setDeckStack(gamePlayer2.getgraveyardCards());
				gamePlayer2.mixDeckStack();
				gamePlayer2.getdeckStack().add(tempCard);
				gamePlayer2.clearGraveyard();
			}
		}

		System.out.println(gamePlayer1.getdeckStack());
		System.out.println(gamePlayer2.getdeckStack());
	}
	
}
