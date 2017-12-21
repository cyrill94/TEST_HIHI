/**@author Sven**/
package gameField;

import java.util.ArrayList;
import gameElements.CardButton;
import gameElements.StackButton;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import player.EnumLanguage;
import templates.TemplateView;

public class GameField_View extends TemplateView{
	
	private ArrayList<CardButton> hand, buyCart, stolenCards;
	private ArrayList<StackButton> btnCardStacks, standardCards;
	private ArrayList<String> cardSet;
	private ArrayList<Label> lblStackSize, lblStackSizeStandardCards;

	private Button phasenEnde, zugEnde, playPhase, btnPullStack, handCard, btnYes, btnNo, btnNoRobber, btnRobber;
	private Label lblPoints, lblBuyCart, lblPlayer, lblAdditionalMoney, lblAdditionalBuys, lblBurggraben, lblStolenCards;
	private TextArea gameLog;
	private HBox handBox, chosenCards, statusBar, btnBox, btnYesNo, stolenCardsBox, btnStealBox;
	private VBox center, leftSideBorderPane, rightSideBorderPane, upperLeftSide, lowerLeftSide;

	private BorderPane root;
	private Scene scene;
	private GridPane stackPane;
	

	public GameField_View(GameField_Model model, Stage primaryStage) {
		super(model, primaryStage);
	}

	@Override
	public Scene getScene() {
		this.root = new BorderPane();
		this.scene = new Scene(root);
		this.center = new VBox();
		this.stackPane = new GridPane();
		this.statusBar = new HBox();
		this.btnBox = new HBox();
		this.leftSideBorderPane = new VBox();
		this.rightSideBorderPane = new VBox();
		this.upperLeftSide = new VBox();
		this.handBox = new HBox();
		this.chosenCards = new HBox();
		this.buyCart = new ArrayList<CardButton>();
		this.btnCardStacks = new ArrayList<StackButton>();
		this.lblStackSize = new ArrayList<Label>();
		this.lblStackSizeStandardCards = new ArrayList<Label>();
		this.hand = new ArrayList<CardButton>();


		
		//Initializing Buttons and Size Labels for Action Stacks
		for(int i = 0; i < 10; i++){
			btnCardStacks.add(new StackButton("Markt"));
			btnCardStacks.get(i).setId(btnCardStacks.get(i).getName());
			lblStackSize.add(new Label("99"));
			lblStackSize.get(i).setId("lblStackSize");
			lblStackSize.get(i).setAlignment(Pos.TOP_LEFT);

		}

		int index = 0;
		for(int i = 0; i < 5; i++){
			stackPane.add(btnCardStacks.get(i), (i+3), 1);
			stackPane.add(btnCardStacks.get(i+5), (i+3), 2);
			stackPane.add(lblStackSize.get(i), (i+3), 1);
			stackPane.add(lblStackSize.get(i+5), (i+3), 2);

			index++;
		}
		
		//Initializing Buttons for Actions and Navigation
		this.phasenEnde = new Button();
		this.zugEnde = new Button();
		this.playPhase = new Button();
		
		//Initializing TextArea for Game-Log
		this.gameLog = new TextArea();
		gameLog.setEditable(false);
		gameLog.setMinHeight(500);
		gameLog.setMaxHeight(600);
		gameLog.setMinWidth(250);
		gameLog.setMaxWidth(300);
		gameLog.setWrapText(true);
		gameLog.setOpacity(0.9);
				
		//Initializing Buttons for Money and Point Stacks
		this.standardCards = new ArrayList<StackButton>();
		standardCards.add(new StackButton("Kupfer"));
		standardCards.add(new StackButton("Silber"));
		standardCards.add(new StackButton("Gold"));
		standardCards.add(new StackButton("Anwesen"));
		standardCards.add(new StackButton("Herzogtum"));
		standardCards.add(new StackButton("Provinz"));
		
		for(int i = 0; i < 6; i++){
			standardCards.get(i).setId(standardCards.get(i).getName());
			standardCards.get(i).addToolTip();
			lblStackSizeStandardCards.add(new Label("99"));
			lblStackSizeStandardCards.get(i).setId("lblStackSize");
			lblStackSizeStandardCards.get(i).setAlignment(Pos.TOP_LEFT);
		}
		
		for(int i = 0; i < 3; i++){
			stackPane.add(standardCards.get(i), i, 1);
			stackPane.add(lblStackSizeStandardCards.get(i), i, 1);
			stackPane.add(standardCards.get(i+3), i, 2);
			stackPane.add(lblStackSizeStandardCards.get(i+3), i, 2);
		}
		
		//Initializing Buttons for Hand Cards
		for(CardButton cb:hand){
			new CardButton(cb.getName());
			this.handCard = cb;
			cb.setText("10");
			cb.setId(cb.getName());
			cb.addToolTip();
			handBox.getChildren().add(cb);
		}
		
		//Initializing Labels for statusBar
		this.lblPoints = new Label();
		this.lblAdditionalMoney = new Label();
		this.lblPlayer = new Label();
		this.lblAdditionalBuys = new Label();
		this.lblPoints.setId("lblPoints");
		this.lblAdditionalMoney.setId("lblAdditionalMoney");
		this.lblPlayer.setId("lblPlayer");
		this.lblAdditionalBuys.setId("lblBuys");
		
		//Initializing Label for BuyCart
		this.lblBuyCart = new Label();
		this.lblBuyCart.setText("Warenkorb");
		this.lblBuyCart.setId("lblBuyCart");
		
		//Initializing Elements for Burggraben
		this.btnYes = new Button("Ja");
		this.btnNo = new Button("Nein");
		this.btnYesNo = new HBox();
		this.btnYes.setId("btnDecision");
		this.btnNo.setId("btnDecision");
		this.lblBurggraben = new Label("Dein Gegner hat eine Angriffskarte gespielt.\nMöchtest du mit dem Burggraben verteidigen?");
		lblBurggraben.setMinHeight(50);
		btnYesNo.setId("hBoxConfigMenuBottom");
		
		//Initializing  Elements for Dieb
		this.stolenCards = new ArrayList<CardButton>();
		this.stolenCardsBox = new HBox();
		//this.btnStealBox = new HBox();
		this.lowerLeftSide = new VBox();
		this.lblStolenCards = new Label("Ausbeute");
		this.btnNoRobber = new Button("Verwerfen");
		this.btnRobber = new Button("Stehlen");
		this.stolenCardsBox.setId("hBoxBuyCart");
		this.btnRobber.setId("btnDecision");
		this.btnNoRobber.setId("btnDecision");
		this.lblStolenCards.setId("lblBuyCart");
		//this.btnStealBox.setId("hBoxConfigMenuBottom");
		this.lowerLeftSide.setId("vBoxBuyCart");
		this.lowerLeftSide.setAlignment(Pos.TOP_CENTER);
		
		//Styling Panes and Boxes
		handBox.setId("hBoxConfigMenuBottom");
		btnBox.setId("hBoxConfigMenuBottom");
		upperLeftSide.setId("vBoxBuyCart");
		upperLeftSide.setAlignment(Pos.TOP_CENTER);
		phasenEnde.setId("GameButtons");
		playPhase.setId("GameButtons");
		zugEnde.setId("GameButtons");
		rightSideBorderPane.setId("vBox");
		statusBar.setId("hBoxStatusBar");
		chosenCards.setId("hBoxBuyCart");
		chosenCards.setId("hBoxBuyCart");
		stackPane.getStyleClass().add("GridPane");
		center.setId("vBox");
		root.setId("gameField");
		//this.getPrimaryStage().setFullScreen(true);
		this.getPrimaryStage().setMinWidth(1500);
		this.getPrimaryStage().setMinHeight(850);
		
		//Add Nodes to Parents
		btnBox.getChildren().add(phasenEnde);
		btnBox.getChildren().add(playPhase);
		btnBox.getChildren().add(zugEnde);
		upperLeftSide.getChildren().add(lblBuyCart);
		upperLeftSide.getChildren().add(chosenCards);
		leftSideBorderPane.getChildren().add(upperLeftSide);
		rightSideBorderPane.getChildren().add(gameLog);
		statusBar.getChildren().add(lblPlayer);
		statusBar.getChildren().add(lblPoints);
		statusBar.getChildren().add(lblAdditionalMoney);
		statusBar.getChildren().add(lblAdditionalBuys);
		center.getChildren().add(stackPane);
		center.getChildren().add(btnBox);
		center.getChildren().add(handBox);
		root.setTop(statusBar);
		root.setRight(rightSideBorderPane);
		root.setLeft(leftSideBorderPane);
		root.setCenter(center);
		
		return scene;
	}
	
	//implements two buttons "yes" and "no" to decide about playing Burggraben
	public void playedBurggraben(){
		this.btnYesNo.getChildren().add(btnYes);
		this.btnYesNo.getChildren().add(btnNo);
		this.center.getChildren().add(lblBurggraben);
		this.center.getChildren().add(btnYesNo);
	}
	
	//removes the two buttons "yes" and "no" to decide about playing Burggraben
	public void removePlayedBurggraben(){
		this.center.getChildren().remove(btnYesNo);
		this.center.getChildren().remove(lblBurggraben);
	}
	
	//implements two buttons "steal" or "dont steel" when player plays action card "dieb"
	public void showStolenCards(){
		for(CardButton cb:stolenCards){
			cb.getStyleClass().clear();
			cb.getStyleClass().add("buyCards");
			cb.addToolTip();
			cb.setId(cb.getCard().getName());
			stolenCardsBox.getChildren().add(cb);
		}
		//this.btnStealBox.getChildren().add(btnRubber);
		//this.btnStealBox.getChildren().add(btnNoRubber);
		this.lowerLeftSide.getChildren().add(lblStolenCards);
		this.lowerLeftSide.getChildren().add(stolenCardsBox);
		this.lowerLeftSide.getChildren().add(btnRobber);
		this.lowerLeftSide.getChildren().add(btnNoRobber);
		this.leftSideBorderPane.getChildren().add(lowerLeftSide);
	}
	
	//removes GUI Elements after Dieb has been played
	public void removeStolenCards(){
		stolenCards.clear();
		stolenCardsBox.getChildren().clear();
		//btnStealBox.getChildren().clear();
		//this.lowerLeftSide.getChildren().remove(btnStealBox);
		//this.lowerLeftSide.getChildren().remove(stolenCardsBox);
		//this.lowerLeftSide.getChildren().remove(lblStolenCards);
		this.lowerLeftSide.getChildren().clear();
		this.leftSideBorderPane.getChildren().remove(lowerLeftSide);
	}
	
	//Getters and Setters
	public Button getBtnNo() {
		return btnNo;
	}

	public ArrayList<CardButton> getStolenCards() {
		return stolenCards;
	}

	public Button getBtnYes() {
		return btnYes;
	}

	public VBox getCenter() {
		return center;
	}
	
	public HBox getHandBox() {
		return handBox;
	}
	
	public Label getLblPlayer() {
		return lblPlayer;
	}

	public Label getLblAdditionalMoney() {
		return lblAdditionalMoney;
	}

	public Label getLblAdditionalBuys() {
		return lblAdditionalBuys;
	}
	
	public Label getLblPoints() {
		return lblPoints;
	}
	
	public ArrayList<Label> getLblStackSizeStandardCards() {
		return lblStackSizeStandardCards;
	}
	
	public ArrayList<Label> getLblStackSize() {
		return lblStackSize;
	}
	
	public ArrayList<StackButton> getBtnCardStacks() {
		return btnCardStacks;
	}

	public void setBtnCardStacks(ArrayList<StackButton> btnCardStacks) {
		this.btnCardStacks = btnCardStacks;
	}

	public ArrayList<CardButton> getHand() {
		return hand;
	}

	public void setHand(ArrayList<CardButton> hand) {
		this.hand = hand;
	}

	public ArrayList<StackButton> getStandardCards() {
		return standardCards;
	}

	public void setStandardCards(ArrayList<StackButton> standardCards) {
		this.standardCards = standardCards;
	}

	public Button getPhasenEnde() {
		return phasenEnde;
	}
	
	public void setPhasenEnde(Button phasenEnde) {
		this.phasenEnde = phasenEnde;
	}

	public Button getZugEnde() {
		return zugEnde;
	}

	public void setZugEnde(Button zugEnde) {
		this.zugEnde = zugEnde;
	}

	public Button getPlayPhase() {
		return playPhase;
	}

	public void setPlayPhase(Button playPhase) {
		this.playPhase = playPhase;
	}

	public Button getBtnPullStack() {
		return btnPullStack;
	}

	public void setBtnPullStack(Button btnPullStack) {
		this.btnPullStack = btnPullStack;
	}

	public ArrayList<CardButton> getBuyCart() {
		return buyCart;
	}

	public Button getHandCard() {
		return handCard;
	}

	public void setHandCard(Button handCard) {
		this.handCard = handCard;
	}

	public TextArea getGameLog() {
		return gameLog;
	}

	public void setGameLog(TextArea gameLog) {
		this.gameLog = gameLog;
	}
	
	public ArrayList<String> getCardSet() {
		return cardSet;
	}
	
	public HBox getChosenCards() {
		return chosenCards;
	}
	
	public Button getBtnNoRobber() {
		return btnNoRobber;
	}
	
	public Button getBtnRobber() {
		return btnRobber;
	}
	
	public void updateText(EnumLanguage lang) {
		if (lang == EnumLanguage.English) {
			phasenEnde.setText("End Phase");
			zugEnde.setText("End Turn");
			playPhase.setText("Execute Phase");			
		}
		else {
			phasenEnde.setText("Phase beenden");
			zugEnde.setText("Zug beenden");
			playPhase.setText("Phase ausführen");
		}
		
	}




}
