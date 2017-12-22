package configMenu;

/** @author Sven **/

import java.io.IOException;
import java.util.ArrayList;
import cards.Card;
import connection.ClientConn;
import messages.PasswordMessage;
import messages.SettingsCustomCardSetsMessage;
import messages.SettingsMessage;
import player.Player;
import templates.TemplateModel;

public class ConfigMenu_Model extends TemplateModel{
	
	private ArrayList<String> customCards, chosenCustomCards;
	
	//Constructor
	public ConfigMenu_Model(){
		customCards = new ArrayList<String>();
		chosenCustomCards = new ArrayList<String>();
		
				
	}
	
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
	
	/** @author Thomas / Cyrill **/
	public void sendSettingsWithCustomCardSet (Player player, ClientConn connection) {
		try {
			SettingsCustomCardSetsMessage settingsMessage = new SettingsCustomCardSetsMessage(player, connection.getClientSocketAddress(), this.chosenCustomCards);
			connection.sendToServer(settingsMessage);
			chosenCustomCards.clear();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendPassword (Player player, String currentpassword, String newpassword, ClientConn connection) throws IOException {
		
		PasswordMessage passwordMessage = new PasswordMessage (player, currentpassword, newpassword);
		connection.sendToServer(passwordMessage);
		
	}
	
	//Getter and Setter
	public ArrayList<String> getCustomCards() {
		return customCards;
	}

	public void setCustomCards(ArrayList<String> customCards) {
		this.customCards = customCards;
	}

	public ArrayList<String> getChosenCustomCards() {
		return chosenCustomCards;
	}

	public void setChosenCustomCards(ArrayList<String> chosenCustomCards) {
		this.chosenCustomCards = chosenCustomCards;
	}

	
	
}
