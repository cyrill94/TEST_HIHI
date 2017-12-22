package configMenu;

/** @author Sven **/

import java.io.IOException;

import connection.ClientConn;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import mainMenu.MainMenu_View;
import messages.EnumMessageType;
import messages.Message;
import messages.MessageList;
import messages.PasswordAnswerMessage;
import player.EnumCardSet;
import player.EnumLanguage;
import player.Player;
import templates.TemplateController;

public class ConfigMenu_Controller extends TemplateController{

	private ConfigMenu_Model model;
	private ConfigMenu_View view;
	private Player player;
	
	public ConfigMenu_Controller(ConfigMenu_Model model, ConfigMenu_View view, Player player, ClientConn connection, MessageList messageList, MainMenu_View mainmenuview) {
		super(model, view);
		this.model = model;
		this.view = view;
		this.player = player;
		
		
		if (this.player.getCardSet() == EnumCardSet.Standard) {
			this.view.settgCardSetStandard();
		}
		else if (this.player.getCardSet() == EnumCardSet.InChange) {
			this.view.settgCardSetInChange();
		}
		else if (this.player.getCardSet() == EnumCardSet.custom) {
			this.view.settgCardSetCustom();
			this.view.showCustomCardSet();
		}
		
		if (this.player.getLanguage() == EnumLanguage.English) {
			this.view.settgLanguageEnglish();
		}
		else if (this.player.getLanguage() == EnumLanguage.German) {
			this.view.settgLanguageGerman();
		}
		
		
		this.view.getBtnSaveConfig().setOnAction((event) -> {
			
			this.player.setLanguage(this.view.getLanguage());
			this.player.setCardSet(this.view.getCardSet());
			this.model.getChosenCustomCards().clear();
			
			if(this.view.getRbCardSetCustom().isSelected()){
				for(CheckBox cb:this.view.getCustomCards()){
					if(cb.isSelected()){
						this.model.getChosenCustomCards().add(cb.getText());
					}
				}
				
				this.player.setCustomCardSetCards(this.model.getChosenCustomCards());
				
				if(this.model.getChosenCustomCards().size()!= 10){
					Alert a = new Alert(AlertType.ERROR);
					DialogPane dialogPane = a.getDialogPane();
					dialogPane.getStylesheets().add(
					   getClass().getResource("/stylesheets/style.css").toExternalForm());
					dialogPane.getStyleClass().add("dialog-pane");
					if(player.getLanguage()==EnumLanguage.German){
						a.setTitle("Benutzerdefiniertes Kartenset");
						a.setContentText("Das benutzerdefinierte Kartenset muss 10 Karten enhalten.");
					}
					else if(player.getLanguage()==EnumLanguage.English){
						a.setTitle("Custom Card Set");
						a.setContentText("A customized card set must contain 10 cards.");												
					}
					
					a.showAndWait();
				}else{
					this.model.sendSettings(player, connection);
					this.model.sendSettingsWithCustomCardSet(player, connection);
					//Hier werden die Einstellungen gespeichert, die aktuelle View geschlossen und die letzte View (Main Menu) wieder geöffnet
					mainmenuview.start();
					mainmenuview.updateText(this.player.getLanguage());
					mainmenuview.getRandomTipp(this.player.getLanguage());
					view.hide();
				}			
			}else{
				this.model.sendSettings(player, connection);
				//Hier werden die Einstellungen gespeichert, die aktuelle View geschlossen und die letzte View (Main Menu) wieder geöffnet
				mainmenuview.start();
				mainmenuview.updateText(this.player.getLanguage());
				mainmenuview.getRandomTipp(this.player.getLanguage());
				view.hide();
			}			
		}
		);
		
		this.view.getBtnCancel().setOnAction((event) -> {
			//Hier werden die Einstellungen verworfen, die aktuelle View geschlossen und die letzte View (Main Menu) wieder geöffnet
			mainmenuview.start();
			mainmenuview.updateText(this.player.getLanguage());
			
			this.view.getCurrentPasswordField().setText(null);
			this.view.getNewPasswordField().setText(null);
			view.hide();
		});
		
		/** @author Cyrill **/
		
		this.view.getBtnChangePassword().setOnAction((event) -> {
			
			try {
				this.model.sendPassword(player, view.getCurrentPassword() ,view.getNewPassword(), connection);
				this.view.getCurrentPasswordField().setText(null);
				this.view.getNewPasswordField().setText(null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});
		
		this.view.getRbCardSetInChange().setOnAction((event) -> {
			this.view.removeCustomCardSet();					
		});

		this.view.getRbCardSetStandard().setOnAction((event) -> {
			this.view.removeCustomCardSet();					
		});

		
		this.view.getRbCardSetCustom().setOnAction((event) -> {
			this.view.showCustomCardSet();
			this.updateChosenCustomCards();
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

	/**@author Sven**/
	public void updateConfigurations() {
		//aktualisiert den Sprach-Toggle
		if(player.getLanguage()==EnumLanguage.English){
			view.settgLanguageEnglish();
		}else{
			view.settgLanguageGerman();
		}
		
		//aktualisiert den Kartensatz-Toggle
		if(player.getCardSet()==EnumCardSet.Standard){
			view.settgCardSetStandard();
			view.removeCustomCardSet();
		}
		else if(player.getCardSet()==EnumCardSet.InChange){
			view.settgCardSetInChange();
			view.removeCustomCardSet();
		}
		else if(player.getCardSet()==EnumCardSet.custom){
			view.settgCardSetCustom();
			view.removeCustomCardSet();
			view.showCustomCardSet();
		}
		
		//aktualisiert die Auswahl des Custom Kartensatz
		this.updateChosenCustomCards();
		
	}
	
	//aktualisiert die Auswahl des Custom Kartensatz
	public void updateChosenCustomCards(){
		for(CheckBox cb:view.getCustomCards()){
			if(player.getCustomCardSetCards().toString().contains(cb.getText().toString())){
				cb.setSelected(true);
			}else{
				cb.setSelected(false);
			}
		}
	}

}
