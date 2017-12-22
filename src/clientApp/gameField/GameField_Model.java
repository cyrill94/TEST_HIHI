package gameField;

import java.io.IOException;
import java.util.ArrayList;

import cards.Card;
import cards.MoneyCard;
import connection.ClientConn;
import messages.ActionCardMessage;
import messages.ActionFestmahlAnswerMessage;
import messages.ActionKapelleAnswerMessage;
import messages.ActionKellerAnswerMessage;
import messages.ActionMilizAnswerMessage;
import messages.ActionMineAnswerMessage;
import messages.ActionUmbauAnswerMessage;
import messages.ActionWerkstattAnswerMessage;
import messages.BurggrabenAnswerMessage;
import messages.BuyCardsMessage;
import messages.ChangeTurnMessage;
import messages.DiebAnswerMessage;
import messages.NextPhaseMessage;
import messages.NoActionCardMessage;
import messages.NoMoneyCardMessage;
import player.Player;
import templates.TemplateModel;

/** @author Sven **/

public class GameField_Model extends TemplateModel{
	
	private ArrayList<Card> cardsToBuy, cardsToDrop;
	private ArrayList<MoneyCard> moneyCards;
	private Card stolenCard;
	
	private ClientConn connection;
	private Player player;	
	
	public GameField_Model(ClientConn connection, Player player) {
		super();
		this.connection = connection;
		this.player = player;
		moneyCards = new ArrayList<MoneyCard>();
		cardsToBuy = new ArrayList<Card>();
		cardsToDrop = new ArrayList<Card>();

	}
	
	public void sendNoActionCardMessage(){
		try {
			NoActionCardMessage message = new NoActionCardMessage(connection.getClientSocketAddress());
			connection.sendToServer(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendNoMoneyCardMessage(){
		try {
			NoMoneyCardMessage message = new NoMoneyCardMessage(connection.getClientSocketAddress());
			connection.sendToServer(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendNextPhaseMessage(){
		try {
			NextPhaseMessage message = new NextPhaseMessage(connection.getClientSocketAddress());
			connection.sendToServer(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void sendActionCardMessage(Card card){
		try {
			ActionCardMessage message = new ActionCardMessage(connection.getClientSocketAddress(), card);
			connection.sendToServer(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendChangeTurnMessage() {
		try {
			ChangeTurnMessage message = new ChangeTurnMessage(connection.getClientSocketAddress());
			connection.sendToServer(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendBuyCardsMessage() {
		try {
			BuyCardsMessage buycardsMessage = new BuyCardsMessage(connection.getClientSocketAddress(),moneyCards,cardsToBuy);
			connection.sendToServer(buycardsMessage);
			moneyCards.clear();
			cardsToBuy.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendActionKellerAnswerMessage() throws IOException {
		ActionKellerAnswerMessage actionmineanswer = new ActionKellerAnswerMessage(connection.getClientSocketAddress(), this.cardsToDrop);
		connection.sendToServer(actionmineanswer);
		cardsToDrop.clear();
	}
	
	public void sendActionMineAnswerMessage(Card cardToDrop, Card cardToTake) throws IOException {
		ActionMineAnswerMessage actionmineanswer = new ActionMineAnswerMessage(connection.getClientSocketAddress(), cardToDrop, cardToTake);
		connection.sendToServer(actionmineanswer);
	}
	
	public void sendActionUmbauAnswerMessage(Card cardToDrop, Card cardToTake) throws IOException {
		ActionUmbauAnswerMessage actionmineanswer = new ActionUmbauAnswerMessage(connection.getClientSocketAddress(), cardToDrop, cardToTake);
		connection.sendToServer(actionmineanswer);
	}

	public void sendActionWerkstattAnswerMessage(Card cardToTake) throws IOException {
		ActionWerkstattAnswerMessage actionmineanswer = new ActionWerkstattAnswerMessage(connection.getClientSocketAddress(), cardToTake);
		connection.sendToServer(actionmineanswer);
	}
	
	public void sendBurggrabenAnswerMessage(boolean decision, Card burggraben){
		try {
			BurggrabenAnswerMessage actionmineanswer = new BurggrabenAnswerMessage(connection.getClientSocketAddress(), decision, burggraben);
			connection.sendToServer(actionmineanswer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendActionMilizAnswerMessage(){
		try {
			ActionMilizAnswerMessage message = new ActionMilizAnswerMessage(connection.getClientSocketAddress(), this.cardsToDrop);
			connection.sendToServer(message);
			cardsToDrop.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendActionDiebAnswerMessage(Boolean steal){
		try {
			DiebAnswerMessage message = new DiebAnswerMessage(connection.getClientSocketAddress(), this.cardsToBuy.get(0), steal);
			connection.sendToServer(message);
			cardsToBuy.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendActionDiebAnswerMessage(){
		try {
			DiebAnswerMessage message = new DiebAnswerMessage(connection.getClientSocketAddress());
			connection.sendToServer(message);
			cardsToBuy.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void sendActionKapelleAnswerMessage(ArrayList<Card> cardsToDropKapelle) {
		try {
			ActionKapelleAnswerMessage message = new ActionKapelleAnswerMessage(connection.getClientSocketAddress(), cardsToDropKapelle);
			connection.sendToServer(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Getter und Setter
	public ArrayList<MoneyCard> getMoneyCards() {
		return moneyCards;
	}

	public ArrayList<Card> getCardsToBuy() {
		return cardsToBuy;
	}
	
	public ArrayList<Card> getCardsToDrop() {
		return cardsToDrop;
	}
	
	public Card getStolenCard() {
		return stolenCard;
	}

	public void sendActionFestmahlAnswerMessage(Card cardToTake) throws IOException {
		ActionFestmahlAnswerMessage actionmineanswer = new ActionFestmahlAnswerMessage(connection.getClientSocketAddress(), cardToTake);
		connection.sendToServer(actionmineanswer);
	}
	
	
}
