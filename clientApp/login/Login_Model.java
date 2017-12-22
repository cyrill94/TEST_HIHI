/**@author Sven**/
package login;

import templates.TemplateModel;

import java.io.IOException;
import java.net.UnknownHostException;
import connection.ClientConn;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import messages.*;

public class Login_Model extends TemplateModel{

	/** @author Thomas **/
	public void login(String username, String password, ClientConn connection) throws ClassNotFoundException {
		try {
			LoginMessage loginmessage = new LoginMessage(username, password, connection.getClientSocketAddress());
			connection.sendToServer(loginmessage);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void createPlayer(String username) {
		
	}
}



