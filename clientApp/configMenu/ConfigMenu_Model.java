package configMenu;

import java.io.IOException;

import connection.ClientConn;
import messages.PasswordMessage;
import messages.SettingsMessage;
import player.Player;
import templates.TemplateModel;

public class ConfigMenu_Model extends TemplateModel{

	/** @author Cyrill **/
	public void sendSettings (Player player, ClientConn connection) {
		try {
			SettingsMessage settingsMessage = new SettingsMessage(player, connection.getClientSocketAddress());
			connection.sendToServer(settingsMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendPassword (Player player, String currentpassword, String newpassword, ClientConn connection) throws IOException {
		
		PasswordMessage passwordMessage = new PasswordMessage (player, currentpassword, newpassword);
		connection.sendToServer(passwordMessage);
		
	}
	
	
}
