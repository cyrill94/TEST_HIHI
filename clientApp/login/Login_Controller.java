/**@author Sven**/
package login;

import connection.ClientConn;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import mainMenu.MainMenu_Controller;
import mainMenu.MainMenu_Model;
import mainMenu.MainMenu_View;
import messages.LoginAnswerMessage;
import messages.Message;
import messages.MessageList;
import player.EnumLanguage;
import player.Player;
import registerMenu.RegisterMenu_Controller;
import registerMenu.RegisterMenu_Model;
import registerMenu.RegisterMenu_View;
import messages.EnumMessageType;
import javafx.scene.control.Alert.AlertType;
import templates.TemplateController;
import templates.TemplateModel;
import templates.TemplateView;

public class Login_Controller extends TemplateController{
	
	private Login_Model model;
	private Login_View view;
	

	public Login_Controller(Login_Model model, Login_View view, MessageList messageList, ClientConn connection) {
		super(model, view);
		
		this.model = model;
		this.view = view;
		

		Stage registerstage = new Stage();
		RegisterMenu_Model registermodel = new RegisterMenu_Model();
		RegisterMenu_View registerview = new RegisterMenu_View(registermodel, registerstage);
		
		RegisterMenu_Controller registermmc = new RegisterMenu_Controller(registermodel, registerview, connection, messageList, view);
		
		view.getBtnLogin().setOnAction((event) -> {
			try {
				model.login(view.getUsername(), view.getPassword(), connection);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		}
		);
		
		view.getBtnCancel().setOnAction((e) -> {
			view.stop();
		});
		
		view.getBtnRegister().setOnAction((e) ->{
			view.getTfPassword().setText(null);
			view.getTfUsername().setText(null);
			registerview.start();
			view.hide();
			
		});
		

/** @author Thomas **/
		messageList.messages.addListener((ListChangeListener) (event -> {

			Message incomingMessage = messageList.messages.get(messageList.messages.size()-1);
			if (incomingMessage.getMessageType() == EnumMessageType.loginAnswer){
				if (((LoginAnswerMessage) incomingMessage).getLoginCheck() == true ) {
					Player p1 = ((LoginAnswerMessage) incomingMessage).getPlayer();
					//EnumLanguage language = p1.getLanguage();
				
					
					Stage s = new Stage();
					MainMenu_Model m = new MainMenu_Model();
					MainMenu_View v = new MainMenu_View(m, s);
					v.updateText(p1.getLanguage());
					
					
					
					MainMenu_Controller mmc = new MainMenu_Controller(m, v, p1, connection, messageList);
					v.start();
					view.stop();
				}
				else {
					Alert a = new Alert(AlertType.WARNING);
					DialogPane dialogPane = a.getDialogPane();
					dialogPane.getStylesheets().add(
					   getClass().getResource("/stylesheets/style.css").toExternalForm());
					dialogPane.getStyleClass().add("dialog-pane");
					a.setTitle("Anmeldung fehlgeschlagen");
					a.setContentText("Falscher Username / Password. Find out real usr/pwd or die tryin...");
					a.showAndWait();
					
				};
			};
		}));
	
	}
}


