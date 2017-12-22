package registerMenu;

/** @author Cyrill **/

import connection.ClientConn;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import login.Login_Controller;
import login.Login_Model;
import login.Login_View;
import mainMenu.MainMenu_Controller;
import mainMenu.MainMenu_Model;
import mainMenu.MainMenu_View;
import messages.LoginAnswerMessage;
import messages.Message;
import messages.MessageList;
import player.EnumLanguage;
import player.Player;
import messages.CreateUserAnswerMessage;
import messages.EnumMessageType;
import javafx.scene.control.Alert.AlertType;
import templates.TemplateController;
import templates.TemplateModel;
import templates.TemplateView;

public class RegisterMenu_Controller extends TemplateController{

private RegisterMenu_Model model;
private RegisterMenu_View view;

	public RegisterMenu_Controller(RegisterMenu_Model model, RegisterMenu_View view, ClientConn connection, MessageList messageList, Login_View loginview) {
		super(model, view);
		
		this.model = model;
		this.view = view;
	
		view.getBtnRegister().setOnAction((event) ->{
			
			try {
				model.sendUserRegistration(this.view.getTxtUsername(), this.view.getTxtPassword(), connection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		
		
		view.getBtnCancel().setOnAction((event) -> {
			loginview.start();
			view.getTxtPasswordField().setText(null);
			view.getTxtUsernameField().setText(null);
			view.hide();
		});
		

/** @author Cyrill **/
		messageList.messages.addListener((ListChangeListener) (event -> {

			Message incomingMessage = messageList.messages.get(messageList.messages.size()-1);
			if (incomingMessage.getMessageType() == EnumMessageType.createUserAnswer){
				CreateUserAnswerMessage createuseranswermessage = (CreateUserAnswerMessage) incomingMessage;
				
					Alert a = new Alert(AlertType.ERROR);
					DialogPane dialogPane = a.getDialogPane();
					dialogPane.getStylesheets().add(
					   getClass().getResource("/stylesheets/style.css").toExternalForm());
					dialogPane.getStyleClass().add("dialog-pane");
					
					if (createuseranswermessage.getErrorCode() == 0) {
						a.setTitle("Username too short");
						a.setContentText("The username is too short.\nIt has to be at least 4 characters long.");
					}
					
					else if (createuseranswermessage.getErrorCode() == 1) {
						a.setTitle("User already exists");
						a.setContentText("The user already exists. \nPlease choose an other username.");					
						
					}
					
					else if (createuseranswermessage.getErrorCode() == 2) {
						a.setTitle("Password too short");
						a.setContentText("The password is too short. \nPassword's minimum length is 8 characters.");		
						
					} 
					
					else if (createuseranswermessage.getErrorCode() == 3) {
						a.setAlertType(AlertType.INFORMATION);
						DialogPane dialPane = a.getDialogPane();
						dialPane.getStylesheets().add(
						   getClass().getResource("/stylesheets/style.css").toExternalForm());
						dialPane.getStyleClass().add("dialog-pane");
						a.setTitle("User created");
						a.setContentText("The user has been successfully created.\nYou are now able to login!");
						
						loginview.start();
						view.getTxtPasswordField().setText(null);
						view.getTxtUsernameField().setText(null);
						view.hide();
						
					}
					
					else if (createuseranswermessage.getErrorCode() == 10) {
						a.setTitle("Character not allowed");
						a.setContentText("A space in the username or password isn't allowed!");	
					}
					
					this.view.getTxtPasswordField().setText(null);
					this.view.getTxtUsernameField().setText(null);
					a.showAndWait();
				
			};
		}));

							
			
			
	}
}



	

