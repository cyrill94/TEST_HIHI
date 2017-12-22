/**@author Sven Cueni**/
package login;

import java.io.IOException;
import java.net.UnknownHostException;

import connection.ClientConn;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import messages.MessageList;

public class Main extends Application{
	
	private Login_View view;
	private Login_Model model;
	private Login_Controller controller;
	

	@Override
	public void start(Stage primaryStage){
		MessageList messageList = new MessageList();
		
		ClientConn connection = null;
		try {
			connection = new ClientConn("localhost", 2002, messageList);
			this.model = new Login_Model();
			this.view = new Login_View(model, primaryStage);
			this.controller = new Login_Controller(model, view, messageList, connection);
			
			view.start();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("No server connection");
			a.setContentText("Unable to connect to the server. Make sure you are able to reach the server and try again.");	
			a.showAndWait();
			//e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	
}
