package configMenu;

import java.io.IOException;

import connection.ClientConn;
import gameField.GameField_Controller;
import gameField.GameField_Model;
import gameField.GameField_View;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import mainMenu.MainMenu_Controller;
import mainMenu.MainMenu_Model;
import mainMenu.MainMenu_View;
import messages.CreateUserAnswerMessage;
import messages.EnumMessageType;
import messages.Message;
import messages.MessageList;
import messages.PasswordAnswerMessage;
import player.EnumCardSet;
import player.EnumLanguage;
import player.Player;
import templates.TemplateController;
import templates.TemplateModel;
import templates.TemplateView;

public class ConfigMenu_Controller extends TemplateController{

	private ConfigMenu_Model model;
	private ConfigMenu_View view;
	private Player player;
	
	public ConfigMenu_Controller(ConfigMenu_Model model, ConfigMenu_View view, Player player, ClientConn connection, MessageList messageList, MainMenu_View mainmenuview) {
		super(model, view);
		this.model = model;
		this.view = view;
		this.player = player;
		
		if (player.getCardSet() == EnumCardSet.Standard) {
			view.settgCardSetStandard();
		}
		else if (player.getCardSet() == EnumCardSet.InChange) {
			view.settgCardSetInChange();
		}
		else if (player.getCardSet() == EnumCardSet.BigMoney) {
			view.settgCardSetBigMoney();
		}
		else if (player.getCardSet() == EnumCardSet.VillageSquare) {
			view.settgCardSetVillageSquare();
		}
		
		if (player.getLanguage() == EnumLanguage.English) {
			view.settgLanguageEnglish();
		}
		else if (player.getLanguage() == EnumLanguage.German) {
			view.settgLanguageGerman();
		}
		
		
		view.getBtnSaveConfig().setOnAction((event) -> {
			
			this.player.setLanguage(view.getLanguage());
			this.player.setCardSet(view.getCardSet());
			
			model.sendSettings(player, connection);
			
			//Hier werden die Einstellungen gespeichert, die aktuelle View geschlossen und die letzte View (Main Menu) wieder geöffnet
			mainmenuview.start();
			mainmenuview.updateText(this.player.getLanguage());
			mainmenuview.getRandomTipp(this.player.getLanguage());
			view.hide();
			
		}
		);
		
		view.getBtnCancel().setOnAction((event) -> {
			//Hier werden die Einstellungen verworfen, die aktuelle View geschlossen und die letzte View (Main Menu) wieder geöffnet
			mainmenuview.start();
			mainmenuview.updateText(this.player.getLanguage());
			this.view.getCurrentPasswordField().setText(null);
			this.view.getNewPasswordField().setText(null);
			view.hide();
		});
		
		/** @author Cyrill **/
		
		view.getBtnChangePassword().setOnAction((event) -> {
			
			try {
				model.sendPassword(player, view.getCurrentPassword() ,view.getNewPassword(), connection);
				this.view.getCurrentPasswordField().setText(null);
				this.view.getNewPasswordField().setText(null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});
		
		
		/** @author Cyrill **/
		messageList.messages.addListener((ListChangeListener) (event -> {

			Message incomingMessage = messageList.messages.get(messageList.messages.size()-1);
			if (incomingMessage.getMessageType() == EnumMessageType.passwordAnswerMessage){
				PasswordAnswerMessage passwordanswermessage = (PasswordAnswerMessage) incomingMessage;

				Alert a = new Alert(AlertType.ERROR);
				DialogPane dialogPane = a.getDialogPane();
				dialogPane.getStylesheets().add(
				   getClass().getResource("/stylesheets/style.css").toExternalForm());
				dialogPane.getStyleClass().add("dialog-pane");
				
				if (passwordanswermessage.getErrorCode() == 1) {
					if (player.getLanguage()==EnumLanguage.English) {
						a.setTitle("Current password wrong");
						a.setContentText("The current password you entered is wrong.\nPassword hasn't changed.");	
					}
					
					else if (player.getLanguage()==EnumLanguage.German) {
						a.setTitle("Falsches Passwort");
						a.setContentText("Das jetzige Passwort ist nicht korrekt.\nDas Passwort wurde nicht geändert.");	
					}	
					
				}
				
				else if (passwordanswermessage.getErrorCode() == 2) {
					if (player.getLanguage()==EnumLanguage.English) {
						a.setTitle("Password too short");
						a.setContentText("The new password is too short.\nPassword's minimum length is 8 characters.");	
					}
					
					else if (player.getLanguage()==EnumLanguage.German) {
						a.setTitle("Passwort zu kurz");
						a.setContentText("Das neue Passwort ist zu kurz.\nDie Mindestlänge beträgt 8 Zeichen.");	
					}
					
				} 
				
				else if (passwordanswermessage.getErrorCode() == 3) {
					a.setAlertType(AlertType.INFORMATION);
					
					if (player.getLanguage()==EnumLanguage.English) {
						a.setTitle("Password changed");
						a.setContentText("The password has been changed.");	
					}
					
					else if (player.getLanguage()==EnumLanguage.German){
						a.setTitle("Passwort geändert");
						a.setContentText("Der Passwort wurde gewechselt.");	
					}
				}
				
				else if (passwordanswermessage.getErrorCode() == 10) {
					if (player.getLanguage()==EnumLanguage.English) {
						a.setTitle("Character not allowed");
						a.setContentText("The space character is not allowed.\nPlease choose a password without it.");	
					}
					
					else if (player.getLanguage()==EnumLanguage.German) {
						a.setTitle("Zeichen nicht erlaubt");
						a.setContentText("Ein Abstand im Passwort ist nicht erlaubt.\nBitte wähle ein Passwort ohne dieses Zeichen.");	
					}
					
				} 
				
				a.showAndWait();
				
			};
		}));
		
		
	}

}
