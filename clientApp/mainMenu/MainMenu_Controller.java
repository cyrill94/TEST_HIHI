/**@author Sven**/
package mainMenu;

import java.util.ArrayList;

import javax.swing.ToolTipManager;

import configMenu.ConfigMenu_Controller;
import configMenu.ConfigMenu_Model;
import configMenu.ConfigMenu_View;
import connection.ClientConn;
import game.Game;
import gameElements.CardButton;
import gameField.GameField_Controller;
import gameField.GameField_Model;
import gameField.GameField_View;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import messages.EnumMessageType;
import messages.LoginAnswerMessage;
import messages.*;
import messages.MessageList;
import player.EnumCardSet;
import player.EnumLanguage;
import player.Player;
import templates.TemplateController;
import templates.TemplateModel;
import templates.TemplateView;

public class MainMenu_Controller extends TemplateController{
	
	private MainMenu_Model model;
	private MainMenu_View view;
	private Player player;
	private MessageList messageList;
	private ConfigMenu_View configview;
	private GameField_View gamefieldview;
	private GameField_Controller gamefieldcontroller;

	public MainMenu_Controller(MainMenu_Model model, MainMenu_View view, Player player, ClientConn connection, MessageList messageList) {
		super(model, view);
		this.view = view;
		this.model = model;
		this.player = player;
		this.messageList = messageList;
		this.configview = null;
		this.gamefieldview = null;
		this.gamefieldcontroller = null;
		
		
		view.getTaTipps().setText(model.getRandomTipp(this.player.getLanguage()));
		
				
		view.getBtnConfig().setOnAction((event) -> {
			//Hier kommt der Aufruf der nächsten View (Config Menü) beim Klick auf Login
			if (this.configview == null) {
			Stage configstage = new Stage();
			ConfigMenu_Model configmodel = new ConfigMenu_Model();
			this.configview = new ConfigMenu_View(configmodel, configstage);
			configview.updateText(this.player.getLanguage());
			
			ConfigMenu_Controller configmmc = new ConfigMenu_Controller(configmodel, configview, player, connection, messageList, view);
			}
			
			configview.updateText(this.player.getLanguage());
			configview.start();
			view.hide();
			
		}
		);
		
		view.getBtnPlayGame().setOnAction((event) -> {
			model.readyGame(connection);
			view.setbtnAbortPlayGameVisible();
			view.setbtnConfigDisabled();
			view.setbtnPlayGameDisbled();
		});
		
		view.getBtnAbortPlayGame().setOnAction((event) -> {
			model.abortReadyGame(connection);
			view.setbtnAbortPlayGameNotVisible();
			view.setbtnConfigEnabled();
			view.setbtnPlayGameEnabled();
			
		});
		
		
		messageList.messages.addListener((ListChangeListener) (event -> {

			Message incomingMessage = messageList.messages.get(messageList.messages.size()-1);
			
			if (incomingMessage.getMessageType() == EnumMessageType.startGame) {	
				Game game = ((GameMessage)((StartGameMessage) incomingMessage).getGameMessage()).getGame();
				
				if (this.gamefieldview == null) {
					Stage gamefieldstage = new Stage();
					GameField_Model gamefieldmodel = new GameField_Model(connection, player);
					this.gamefieldview = new GameField_View(gamefieldmodel, gamefieldstage);
					this.gamefieldcontroller = new GameField_Controller(gamefieldmodel, gamefieldview, player, messageList, game, view);
				}
				this.gamefieldview.start();
				
				this.gamefieldcontroller.setGame(game);
				this.gamefieldcontroller.updateView();
				this.gamefieldcontroller.setGuiElementsOnAction();
				this.gamefieldview.updateText(this.player.getLanguage());
				this.gamefieldcontroller.runGame();
				view.hide();
				
			};
			
			if (incomingMessage.getMessageType() == EnumMessageType.cardsetMismatchMessage) {				
				CardsetMismatchMessage incomingCardsetMismatchMessage = (CardsetMismatchMessage) incomingMessage;
								
				//Alert für Kartensetmismatch ausgeben
				Alert a7 = new Alert(AlertType.WARNING);
				DialogPane dialogPane = a7.getDialogPane();
				dialogPane.getStylesheets().add(
				   getClass().getResource("/stylesheets/style.css").toExternalForm());
				dialogPane.getStyleClass().add("dialog-pane");
				a7.setTitle("Falsches Kartenset");
				a7.setContentText("Du hast das Kartenset " + incomingCardsetMismatchMessage.getyourCardSetName() + " gewählt. \nDer Gegner hat das Kartenset " + incomingCardsetMismatchMessage.getopponentCardSetName() + " gewählt. \nEin Spiel ist nur mit übereinstimmenden Sets möglich!");
				a7.showAndWait();
				
			};
			
		}));
		
		
	}
}