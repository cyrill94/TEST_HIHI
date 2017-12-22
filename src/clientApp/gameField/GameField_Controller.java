/**@author Sven**/
package gameField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import cards.Card;
import cards.EnumCardType;
import cards.MoneyCard;
import game.EnumPhase;
import game.Game;
import gameElements.CardButton;
import gameElements.StackButton;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;
import mainMenu.MainMenu_View;
import messages.BurggrabenMessage;
import messages.DiebMessage;
import messages.EndGameMessage;
import messages.EnumMessageType;
import messages.GameMessage;
import messages.Message;
import messages.MessageList;
import messages.UpdateLogMessage;
import player.Player;
import server.StringUpdater;
import templates.TemplateController;

public class GameField_Controller extends TemplateController{
	
	private GameField_Model model;
	private GameField_View view;
	private Player player;
	private MessageList messageList;
	private Game game;
	private StringUpdater strUpdater;

	/** @author Sven / Thomas / Cyrill **/
	public GameField_Controller(GameField_Model model, GameField_View view, Player player, MessageList messageList, Game game, MainMenu_View mainmenuview) {
		super(model, view);
		
		this.model = model;
		this.view = view;
		this.messageList = messageList;
		this.player = player;
		this.game = game;
		this.strUpdater = new StringUpdater();
		
		this.updateView();
		this.setGuiElementsOnAction();
		
		messageList.messages.addListener((ListChangeListener) (event -> {
			Message incomingMessage = messageList.messages.get(messageList.messages.size()-1);
			
			if (incomingMessage.getMessageType() == EnumMessageType.game) {		
				GameMessage gameMessage = (GameMessage) incomingMessage;
				this.game = gameMessage.getGame();
				System.out.println(this.game.getAdditionalMoney(player));
				System.out.println(gameMessage.getGame().getAdditionalMoney(player));
				this.view.setHand(gameMessage.getGame().getHandCards(this.player));
				System.out.println("Client Game Phase bei Empfang: " + gameMessage.getGame().getPhase());
				model.getCardsToBuy().clear();
				model.getMoneyCards().clear();
				this.updateView();
				System.out.println("GAME MSG ERHALTEN mit PHASE: " + game.getPhase());
				this.runGame();
			}
			
			//Updating GameLog
			else if (incomingMessage.getMessageType() == EnumMessageType.updateLogMessage) {
				UpdateLogMessage updateLogMessage = (UpdateLogMessage) incomingMessage;
				this.view.getGameLog().appendText("\n"+updateLogMessage.getStrLog());
			}
			
			//if game is finished
			else if (incomingMessage.getMessageType() == EnumMessageType.endGameMessage) {
				EndGameMessage endgamemessage = (EndGameMessage) incomingMessage;
				
				Alert a = new Alert(AlertType.INFORMATION);
				DialogPane dialogPane = a.getDialogPane();
				dialogPane.getStylesheets().add(
				   getClass().getResource("/stylesheets/style.css").toExternalForm());
				dialogPane.getStyleClass().add("dialog-pane");
				a.setTitle(endgamemessage.getTitle());
				a.setContentText(endgamemessage.getMessage());
				a.showAndWait();				
				view.hide();
				mainmenuview.start();
			}
			
			//handles action card Burggraben
			else if (incomingMessage.getMessageType() == EnumMessageType.burggraben){
				BurggrabenMessage message = (BurggrabenMessage) incomingMessage;
				if(this.game.getTurn().getName().equals(player.getName())){
					this.view.getPlayPhase().setDisable(true);
					this.view.getPhasenEnde().setDisable(true);
					this.view.getZugEnde().setDisable(true);
				}else{
					this.view.getGameLog().appendText(strUpdater.getYourOpponentPlayedBurggraben(this.player.getLanguage()));
					this.view.playedBurggraben();
					this.setDecisionBurggrabenOnAction(message.getCard());
				}
				
			}
			
			//handles action card Dieb
			else if (incomingMessage.getMessageType() == EnumMessageType.dieb){
				DiebMessage message = (DiebMessage) incomingMessage;
				if(message.getCards().size()>0){
					for(Card c:message.getCards()){
						this.view.getStolenCards().add(new CardButton(c.getName(), c));
					}
					this.view.showStolenCards();
					this.view.getPhasenEnde().setDisable(true);
					this.view.getPlayPhase().setDisable(true);
					this.view.getZugEnde().setDisable(true);

					for(CardButton cb:this.view.getStolenCards()){
						cb.setOnAction((e) -> {
							CardButton cardButton = (CardButton) e.getSource();
							cardButton.selectCardToSteal();
						});
					}
					
					//sends stolen cards to server
					this.view.getBtnRobber().setOnAction((e) -> {
						this.model.getCardsToBuy().clear();
						for(CardButton cb:this.view.getStolenCards()){
							if(cb.isSelected()){
								this.model.getCardsToBuy().add(cb.getCard());
							}
						}
						if(this.model.getCardsToBuy().size()!=1){
							this.view.getGameLog().appendText(strUpdater.getErrorTooManyCardsSelected(player.getLanguage()));
						}
						else if(this.model.getCardsToBuy().get(0).getCardType() != EnumCardType.MoneyCard){
							this.view.getGameLog().appendText(strUpdater.getErrorOnlyMoneyCardsStealable(player.getLanguage()));
						}
						else{
							this.model.sendActionDiebAnswerMessage(true);
							this.view.removeStolenCards();
						}
					});
					
					this.view.getBtnBurnCard().setOnAction((ev) -> {
						this.model.getCardsToBuy().clear();
						for(CardButton cb:this.view.getStolenCards()){
							if(cb.isSelected()){
								this.model.getCardsToBuy().add(cb.getCard());
							}
						}
						if(this.model.getCardsToBuy().size()!=1){
							this.view.getGameLog().appendText(strUpdater.getErrorTooManyCardsSelected(player.getLanguage()));
						}
						else if(this.model.getCardsToBuy().get(0).getCardType() != EnumCardType.MoneyCard){
							this.view.getGameLog().appendText(strUpdater.getErrorOnlyMoneyCardsToTrash(player.getLanguage()));
						}
						else{
							this.model.sendActionDiebAnswerMessage(false);
							this.view.removeStolenCards();
						}
					});

					
					this.view.getBtnNoRobber().setOnAction((eve) -> {
						this.model.getCardsToBuy().clear();
						Card card = new Card();
						card = null;
						this.model.getCardsToBuy().add(card);
						this.model.sendActionDiebAnswerMessage();
						this.view.removeStolenCards();
					});
					
				}else{
					view.getGameLog().appendText(strUpdater.getErrorYieldHasNoMoneyCards(player.getLanguage()));
				}	
			}
		}));
	}
	
	/** @author Sven **/
	public void runGame(){
		EnumPhase phase = this.game.getPhase();
		System.out.println("aktuelle Phase: " + phase);
		if (this.game.getTurn().getName().equals(this.player.getName())){
			
			switch(phase){
			
			case Action:
				if(this.game.getHandCards(player).checkForActionCard()==true){
					this.disableStacks();
					view.getPlayPhase().setText(strUpdater.getPlayPhaseTextAction(player.getLanguage()));
				}else{
					model.sendNoActionCardMessage();
				}
				break;
				
			case Buy:
				if(this.game.getHandCards(player).checkForMoneyCard()==true || game.getAdditionalMoney(player)>0){
					view.getPlayPhase().setText(strUpdater.getPlayPhaseTextBuy(player.getLanguage()));
				}else{
					model.sendNoMoneyCardMessage();
				}
				break;
				
			case actionMine:
				//Prüfen ob überhaupt eine MoneyCard abgelegt werden kann
				if(this.game.getHandCards(player).checkForMoneyCard()==true) {
					view.getPlayPhase().setText(strUpdater.getPlayPhaseTextActionMine(player.getLanguage()));
					}else{
					//auf servermodel wird dadurch nextphase() aufgerufen und die "Subphase" beendet
					model.sendNoMoneyCardMessage();
				}
				break;
				
			case actionUmbau:
				view.getPlayPhase().setText(strUpdater.getPlayPhaseTextActionUmbau(player.getLanguage()));
				break;
			
			case actionKeller:
				view.getPlayPhase().setText(strUpdater.getPlayPhaseTextActionKeller(player.getLanguage()));
				break;
				
			case actionWerkstatt:
				view.getPlayPhase().setText(strUpdater.getPlayPhaseTextActionWerkstatt(player.getLanguage()));
				break;
				
			case actionKapelle:
				view.getPlayPhase().setText(strUpdater.getPlayPhaseTextActionKapelle(player.getLanguage()));
				break;
				
			}
		}
		
		if (game.getPhase().equals(EnumPhase.actionMiliz)) {
			if(game.getTurn().getName().equals(player.getName())){
				this.disableStacks();
				this.disableHand();
				view.getPhasenEnde().setDisable(true);
				view.getPlayPhase().setDisable(true);
				view.getZugEnde().setDisable(true);
			}else{
				this.enableHand();
				view.getPlayPhase().setDisable(false);
				view.getPlayPhase().setText(strUpdater.getPlayPhaseTextActionMiliz(player.getLanguage()));
				view.getPhasenEnde().setDisable(true);
				view.getZugEnde().setDisable(true);
			}
		}
	}
	
	/** @author Sven **/
	//führt die aktuelle Phase aus sobald der Button playPhase gedrückt wird
	public void playDraw() throws IOException{
		EnumPhase phase = this.game.getPhase();
		switch(phase){
		
		/**-----AKTIONSPHASE-----**/
		case Action:
			ArrayList<CardButton> list = new ArrayList<CardButton>();
			for(CardButton c:view.getHand()){
				if(c.isSelected()){
					list.add(c);
				}
			}
			if(list.size()==0){
				view.getGameLog().appendText(strUpdater.getErrorNoActionCardSelected(player.getLanguage()));
			}
			else if(list.size() == 1){
				model.sendActionCardMessage(this.game.getHandCards(player).getCard(view.getHand().indexOf(list.get(0))));
			}
			else{
				view.getGameLog().appendText(strUpdater.getErrorTooManyActionCardsSelected(player.getLanguage()));
			}
			break;
			
		/**-----KAUFPHASE-----**/
		case Buy:
			for(CardButton c:view.getHand()){
				if(c.isSelected()&&c.getCard().getCardType() == EnumCardType.MoneyCard){
					model.getMoneyCards().add((MoneyCard) c.getCard());
				}
			}
			
			//calculates costs of buy cart and the value of all selected money cards
			int cost = 0;
			int value = 0;
			for(MoneyCard card:model.getMoneyCards()){
				value += card.getIntValue();
			}
			for(Card card:model.getCardsToBuy()){
				cost += card.getIntCost();
			}
			value += game.getAdditionalMoney(player); //if action cards delivers additional money
			
			//shows an alert in game log if player wants to buy more than one card without additional buys
			if(model.getCardsToBuy().size()>(game.getBuyCount(player))){
				view.getGameLog().appendText(strUpdater.getErrorNotEnoughBuys(player.getLanguage()));
			}
			//shows a hint in the game log if costs of the desired cards are higher than the selected money cards
			else if(cost > value){
				view.getGameLog().appendText(strUpdater.getErrorNotEnoughMoney(player.getLanguage()));
			}
			//sends the desired and selected money cards to the server
			else if(cost <= value){
				if(model.getCardsToBuy().size() == 0){
					view.getGameLog().appendText(strUpdater.getErrorEmptyCart(player.getLanguage()));
				}else{
					model.sendBuyCardsMessage();
					view.getChosenCards().getChildren().clear();
					view.getHandBox().getChildren().clear();
				}
			}
			model.getMoneyCards().clear();
			break;
		
		
		/**-----AKTIONEN-----**/
		/** @author Cyrill **/
		case actionMine:
			int handSelectedCount = 0;
			int buyCardCount = 0;

			//Hand prüfen was ausgewählt ist
			for (CardButton c : view.getHand()) {
				if (c.isSelected() == true) {
					handSelectedCount++;
				}
			}
			for(Card card:model.getCardsToBuy()){
				buyCardCount++;
			}
			
			// Prüfung ob Aktion legitim ist
			if (handSelectedCount == 1 && buyCardCount == 1) {
				
				//Ausgewählte Karten zwischenspeichern für Nachricht an Server
				Card cardToDrop = new Card();
				Card cardToTake = new Card();
				
				for (CardButton c : view.getHand()) {
					if (c.isSelected() == true) {
						cardToDrop = c.getCard();
					}
				}
							
				cardToTake = model.getCardsToBuy().get(0);
				if(cardToTake.getCardType() == EnumCardType.MoneyCard && cardToDrop.getCardType() == EnumCardType.MoneyCard){
					//Zwischenspeichern & Casting
					MoneyCard moneyCardToDrop = (MoneyCard) cardToDrop;
					MoneyCard moneyCardToTake = (MoneyCard) cardToTake;
					
					int valueCardToDrop = moneyCardToDrop.getIntCost();
					int valueCardToTake = moneyCardToTake.getIntCost();
					
					if ((valueCardToDrop+3) >= valueCardToTake) {
						model.sendActionMineAnswerMessage(cardToDrop, cardToTake);
						view.getChosenCards().getChildren().clear();
						model.getCardsToBuy().clear();
					}else{
						view.getGameLog().appendText(strUpdater.getErrorActionMineCardTooExpensive(player.getLanguage(), valueCardToDrop));
					}
				}else{
					view.getGameLog().appendText(strUpdater.getErrorActionMineOnlyMoneyCards(player.getLanguage()));
				}
			}else{
				view.getGameLog().appendText(strUpdater.getErrorActionMineTooManyOrNotEnoughCards(player.getLanguage()));
			}
			break;
		
		/**@author Sven**/
		case actionUmbau:
			int handUmbauSelectedCount = 0;
			int buyUmbauCardCount = 0;

			//Hand prüfen was ausgewählt ist
			for (CardButton c : view.getHand()) {
				if (c.isSelected() == true) {
					handUmbauSelectedCount++;
				}
			}
			for(Card card:model.getCardsToBuy()){
				buyUmbauCardCount++;
			}
			
			// Prüfung ob Aktion legitim ist
			if (handUmbauSelectedCount == 1 && buyUmbauCardCount == 1) {
				
				//Ausgewählte Karten zwischenspeichern für Nachricht an Server
				Card cardToDrop = new Card();
				Card cardToTake = new Card();
				
				for (CardButton c : view.getHand()) {
					if (c.isSelected() == true) {
						cardToDrop = c.getCard();
					}
				}
							
				cardToTake = model.getCardsToBuy().get(0);
					
					int valueCardToDrop = cardToDrop.getIntCost();
					int valueCardToTake = cardToTake.getIntCost();
					
					if ((valueCardToDrop+2) >= valueCardToTake) {
						model.sendActionUmbauAnswerMessage(cardToDrop, cardToTake);
						view.getChosenCards().getChildren().clear();
						model.getCardsToBuy().clear();
					}else{
						view.getGameLog().appendText(strUpdater.getErrorActionUmbauCardTooExpensive(player.getLanguage(), valueCardToDrop));
					}
			}else{
				view.getGameLog().appendText(strUpdater.getErrorActionUmbauTooManyOrNoCardsSelected(player.getLanguage()));
			}			
			break;
		
		/**@author Sven**/
		case actionWerkstatt:
			int buyWerkstattCardCount = 0;
			for(Card card:model.getCardsToBuy()){
				buyWerkstattCardCount++;
			}
			
			// Prüfung ob Aktion legitim ist
			if (buyWerkstattCardCount == 1) {
				Card cardToTake = model.getCardsToBuy().get(0);
				if(cardToTake.getIntCost()<= 4){
					int valueCardToTake = cardToTake.getIntCost();
					model.sendActionWerkstattAnswerMessage(cardToTake);
					view.getChosenCards().getChildren().clear();
					model.getCardsToBuy().clear();
				}else{
					view.getGameLog().appendText(strUpdater.getErrorActionWerkstattCardTooExpensive(player.getLanguage()));
				}
			}else{
				view.getGameLog().appendText(strUpdater.getErrorActionWerkstattTooManyOrNoCardsSelected(player.getLanguage()));
			}
		break;
			
		/**@author Sven**/
		case actionKeller:
			for (CardButton c : view.getHand()) {
				if (c.isSelected() == true) {
					model.getCardsToDrop().add(c.getCard());
				}
			}
			
			//sends card which are supposed to be dropped to the server
			if(model.getCardsToDrop().isEmpty()){
				view.getGameLog().appendText(strUpdater.getErrorActionKellerNoCardsSelected(player.getLanguage()));
			}else{
				model.sendActionKellerAnswerMessage();
			}
			break;
			
		/**@author Thomas**/
		case actionMiliz:
			model.getCardsToDrop().clear();
			for (CardButton c : view.getHand()) {
				if (c.isSelected() == true) {
					model.getCardsToDrop().add(c.getCard());
				}
			}
			if((view.getHand().size()-model.getCardsToDrop().size())==3){
				model.sendActionMilizAnswerMessage();
			}else{
				view.getGameLog().appendText(strUpdater.getErrorActionMilizCardDropMismatch(player.getLanguage()));
			}
			break;
		
			/**@author Thomas**/
		case actionKapelle:
			int handKapelleSelectedCount = 0;
			
			//Hand prüfen was ausgewählt ist
			for (CardButton c : view.getHand()) {
				if (c.isSelected() == true) {
					handKapelleSelectedCount++;
				}
			}
			
			// Prüfung ob Aktion legitim ist
			if (handKapelleSelectedCount < 5) {
				
				ArrayList<Card> cardsToDropKapelle = new ArrayList<Card>();

				for (CardButton c : view.getHand()) {
					if (c.isSelected() == true) {
						cardsToDropKapelle.add(c.getCard());
					}
				}
				model.sendActionKapelleAnswerMessage(cardsToDropKapelle);	
				view.getChosenCards().getChildren().clear();
				model.getCardsToBuy().clear();
			}else{
				view.getGameLog().appendText(strUpdater.getErrorActionKapelleTooManyCardsDropped(player.getLanguage()));
			}			
			break;	
			
			/**@author Thomas**/
		case actionFestmahl:
			int buyFestmahlCardCount = 0;

			
			for(Card card:model.getCardsToBuy()){
				buyFestmahlCardCount++;
			}
			
			// Prüfung ob Aktion legitim ist
			if (buyFestmahlCardCount == 1) {
				
				//Ausgewählte Karten zwischenspeichern für Nachricht an Server
				Card cardToTake = new Card();
				
							
				cardToTake = model.getCardsToBuy().get(0);
					
					int valueCardToTake = cardToTake.getIntCost();
					
					if (5 >= valueCardToTake) {
						model.sendActionFestmahlAnswerMessage(cardToTake);
						view.getChosenCards().getChildren().clear();
						model.getCardsToBuy().clear();
					}else{
						view.getGameLog().appendText(strUpdater.getErrorActionFestmahlCardTooExpensive(player.getLanguage()));
						
					}
			}else{
				view.getGameLog().appendText(strUpdater.getErrorActionFestmahlTooManyOrNoCardsSelected(player.getLanguage()));
			}			
			break;
			
		}
	}
	
	//Defines what happens if buttons are clicked
	public void setGuiElementsOnAction(){
		
		//beendet den Zug und übergibt an den nächsten Spieler
		view.getZugEnde().setOnAction((event) -> {
			model.sendChangeTurnMessage();
		}
		);
		
		//beendet die aktuelle Phase und geht in die nächste Phase
		view.getPhasenEnde().setOnAction((event) -> {
			view.getChosenCards().getChildren().clear();
			model.sendNextPhaseMessage();
		}
		);
			
		//Führt die aktuelle Phase aus und geht in die nächste Phase
		view.getPlayPhase().setOnAction((event) -> {
			try {
				this.playDraw();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		);
		
		/**@author Sven**/
		//wählt eine Standardkarte an (Geld, Punkte), verschiebt sie in den Warenkorb und aktualisiert die entsprechenden Label
		for(int i=0; i<6; i++){
			view.getStandardCards().get(i).setOnAction((event) -> {
				StackButton stackButton = (StackButton) event.getSource();
				if(stackButton.getCardStack().size()==1){
					stackButton.setId("");
					stackButton.setDisable(true);
				}
				CardButton buyCard = new CardButton(stackButton.getCardStack().peek().getName(), stackButton.getCardStack().pop());
				buyCard.getStyleClass().clear();
				buyCard.getStyleClass().add("buyCards");
				buyCard.setId(buyCard.getName());
				view.getBuyCart().add(buyCard);
				view.getChosenCards().getChildren().add(buyCard);
				model.getCardsToBuy().add((Card)buyCard.getCard());
				view.getLblStackSizeStandardCards().get(view.getStandardCards().indexOf(stackButton)).setText(Integer.toString(stackButton.getCardStack().size()));
				this.setBuyCartCardsOnAction();
			});
		}

		/**@author Sven**/
		//wählt eine Aktionskarte an, verschiebt sie in den Warenkorb und aktualisiert die entsprechenden Label
		for(StackButton stackButton:view.getBtnCardStacks()){
			stackButton.setOnAction((event) -> {
				StackButton sB = (StackButton) event.getSource();
				if(stackButton.getCardStack().size()==1){
					stackButton.setId("");
					stackButton.setDisable(true);
				}
				CardButton buyCard = new CardButton(sB.getCardStack().peek().getName(), sB.getCardStack().pop());
				buyCard.getStyleClass().clear();
				buyCard.getStyleClass().add("buyCards");
				buyCard.setId(buyCard.getName());
				view.getBuyCart().add(buyCard);
				view.getChosenCards().getChildren().add(buyCard);
				Card card = (Card)buyCard.getCard();
				model.getCardsToBuy().add(card);
				view.getLblStackSize().get(view.getBtnCardStacks().indexOf(sB)).setText(Integer.toString(sB.getCardStack().size()));
				this.setBuyCartCardsOnAction();
			});
		}
		
	}
	
	/**@author Sven**/
	//Sets Buttons in BuyCart on Action
	public void setBuyCartCardsOnAction(){
		
		if(view.getBuyCart().isEmpty()){

		}
		else{
			for(CardButton cardButton:view.getBuyCart()){
				cardButton.setOnAction((e) -> {
					//verschiebt eine Karte vom Warenkorb zurück auf den ursprünglichen Stapel 
					//und aktualisiert das entsprechende Label
					CardButton cB = (CardButton) e.getSource();
					model.getCardsToBuy().remove(model.getCardsToBuy().indexOf(cB.getCard()));
					view.getChosenCards().getChildren().remove(cB);
					if(cB.getCard().getCardType()==EnumCardType.ActionCard){
						int stackID = -1;
						for(int i = 0; i < 10; i++){
							Stack<Card> stack = view.getBtnCardStacks().get(i).getCardStack();
							if (view.getBtnCardStacks().get(i).getName().equals(cB.getCard().getName())){
								stackID = i;
							}
						}
						Stack <Card> tempStack = view.getBtnCardStacks().get(stackID).getCardStack();
						tempStack.push(cB.getCard());
						view.getLblStackSize().get(stackID).setText(Integer.toString(tempStack.size()));
						view.getBtnCardStacks().get(stackID).setId(view.getBtnCardStacks().get(stackID).getName());
						view.getBtnCardStacks().get(stackID).setDisable(false);
					}
					else if(cB.getCard().getCardType()==EnumCardType.MoneyCard || cB.getCard().getCardType()==EnumCardType.PointCard){
						int stackID = -1;
						for(int i = 0; i < 6; i++){
							Stack<Card> stack = view.getStandardCards().get(i).getCardStack();
							if (view.getStandardCards().get(i).getName().equals(cB.getCard().getName())){
								stackID = i;
							}
						}
						Stack <Card> tempStack = view.getStandardCards().get(stackID).getCardStack();
						tempStack.push(cB.getCard());
						view.getLblStackSizeStandardCards().get(stackID).setText(Integer.toString(tempStack.size()));
						view.getStandardCards().get(stackID).setId(view.getStandardCards().get(stackID).getName());
						view.getStandardCards().get(stackID).setDisable(false);
					}

				});
			}
		}
	}
	
	/**@author Sven**/
	//markiert eine Handkarte als angewählt
	public void setHandCardsOnAction(){
		for(CardButton cB:view.getHand()){
			cB.setOnAction((event) -> {
				CardButton cardButton = (CardButton) event.getSource();
				cardButton.selectCard();
			});
		}
	}
	
	/**@author Sven**/
	//Defines what happens if buttons are clicked
	public void setDecisionBurggrabenOnAction(Card card){
		
		//sendet BurggrabenKarte an server
		view.getBtnYes().setOnAction((event) -> {
			model.sendBurggrabenAnswerMessage(true, card);
			view.removePlayedBurggraben();
		});
		
		//sendet BurggrabenKarte nicht an server
		view.getBtnNo().setOnAction((event) -> {
			model.sendBurggrabenAnswerMessage(false, card);
			view.removePlayedBurggraben();
		});
	}
	

	/**@author Sven**/
	//Updating View with new Cards
	public void updateHand(){
		view.getHandBox().getChildren().clear();
		view.getHand().clear();
		for(int i = 0; i<this.game.getHandCards(player).getSize(); i++){
			Card card = this.game.getHandCards(player).getCard(i);
			view.getHand().add(new CardButton(card.getName(), card));
			view.getHand().get(i).setId(view.getHand().get(i).getName());
			view.getHand().get(i).addToolTip();
			view.getHandBox().getChildren().add(view.getHand().get(i));
			System.out.println(view.getHandBox().getChildren().get(i));
		}
		this.setHandCardsOnAction();
	}
	
	/**@author Sven**/
	//Updates StackButtons and Stack Size Labels
	public void updateStacks(){
		for(int i = 0; i<10; i++){
			view.getBtnCardStacks().get(i).setCardStack(game.getActionStacks().get(i));
			view.getLblStackSize().get(i).setText(Integer.toString(game.getActionStacks().get(i).size()));
			if(game.getActionStacks().get(i).size()<1){
				view.getBtnCardStacks().get(i).setId("");
				view.getBtnCardStacks().get(i).removeToolTip();
				//view.getBtnCardStacks().get(i).setDisable(true);
			}else{
				Card card = (Card) game.getActionStacks().get(i).get(0);
				view.getBtnCardStacks().get(i).setName(card.getName());
				view.getBtnCardStacks().get(i).setId(card.getName());
				view.getBtnCardStacks().get(i).addToolTip();
			}
		}
		
		for(int i = 0; i<6; i++){
			view.getStandardCards().get(i).setCardStack(game.getStandardStacks().get(i));
			view.getLblStackSizeStandardCards().get(i).setText(Integer.toString(game.getStandardStacks().get(i).size()));
			if(game.getStandardStacks().get(i).size()<1){
				view.getStandardCards().get(i).setId("");
				view.getStandardCards().get(i).removeToolTip();
				//view.getStandardCards().get(i).setDisable(true);
			}else{
				Card card = (Card) game.getStandardStacks().get(i).get(0);
				view.getStandardCards().get(i).setName(card.getName());
				view.getStandardCards().get(i).setId(card.getName());
				view.getStandardCards().get(i).addToolTip();
			}
		}
	}
	
	/**@author Sven**/
	//sofern der Spieler nicht am Zug ist, werden alle Steuerelemente disabled
	public void updateView(){
		this.updateHand();
		this.updateStacks();
		view.getBuyCart().clear();
		view.getChosenCards().getChildren().clear();
		view.getLblPoints().setText(strUpdater.getLblPointsString(player.getLanguage(), game.getDeckPoints(player)));
		view.getLblAdditionalMoney().setText(strUpdater.getLblAdditionalMoneyString(player.getLanguage(), game.getAdditionalMoney(player)));
		view.getLblPlayer().setText("\t " + player.getName());
		view.getLblAdditionalBuys().setText(strUpdater.getLblAdditionalBuysString(player.getLanguage(), (game.getBuyCount(player)-1)));
		view.getLblCardDeck().setText(Integer.toString(game.getDeckSize(player)));
		view.getLblGraveyard().setText(Integer.toString(game.getGraveyardSize(player)));
		if(game.getGraveyardSize(player)==0){
			view.getLblGraveyard().setId("");
		}else{
			view.getLblGraveyard().setId(game.getTopGraveyardCard(player).getName());
		}
		
		if (game.getTurn().getName().toString().equals(player.getName().toString())){			
			this.enableHand();
			this.enableStack();
			view.getPhasenEnde().setDisable(false);
			view.getZugEnde().setDisable(false);
			view.getPlayPhase().setDisable(false);
		}else{
			this.disableHand();
			this.disableStacks();
			view.getPhasenEnde().setDisable(true);
			view.getZugEnde().setDisable(true);
			view.getPlayPhase().setDisable(true);
		}
	}
	
	
	/**@author Sven**/
	//disables Buttons for Action, Money and Point Stacks
	public void disableStacks(){
		for(StackButton c:view.getBtnCardStacks()){
			c.setDisable(true);
		}
		for(StackButton c:view.getStandardCards()){
			c.setDisable(true);
		}
	}
	
	/**@author Sven**/
	//activates StackButtons for Action, Money and Point Stacks
	public void enableStack(){
		for(StackButton c:view.getBtnCardStacks()){
			if(c.getCardStack().size()>0){
				c.setDisable(false);
			}else{
				c.setDisable(true);
			}
		}
		for(StackButton c:view.getStandardCards()){
			if(c.getCardStack().size()>0){
				c.setDisable(false);
			}else{
				c.setDisable(true);
			}
		}
	}
	
	/**@author Sven**/
	//disables Buttons for Hand Cards
	public void disableHand(){
		for(CardButton c:view.getHand()){
			c.setDisable(true);
		}
	}
	
	/**@author Sven**/
	//activates Buttons for Hand Cards
	public void enableHand(){
		for(CardButton c:view.getHand()){
			c.setDisable(false);
		}
	}
	
	//Getter and Setter
	public void setGame(Game game) {
		this.game = game;
	}
	
	

	
}

