package registerMenu;

/** @author Cyrill **/

import java.io.IOException;
import connection.ClientConn;
import messages.CreateUserMessage;
import messages.SettingsMessage;
import player.Player;
import templates.TemplateModel;

public class RegisterMenu_Model extends TemplateModel{

	public void sendUserRegistration(String username, String password, ClientConn connection) throws Exception {
		
		CreateUserMessage createusermessage = new CreateUserMessage(username, password, connection.getClientSocketAddress());
		connection.sendToServer(createusermessage);
		
	}
	
}
