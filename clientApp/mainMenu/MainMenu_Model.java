package mainMenu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import connection.ClientConn;
import messages.AbortReadyMessage;
import messages.SettingsMessage;
import player.EnumLanguage;
import messages.ReadyMessage;
import templates.TemplateModel;

public class MainMenu_Model extends TemplateModel{

private String randomTipp;
	
	/** @author Cyrill **/
	public String getRandomTipp(EnumLanguage language) {
		
		File tipps;
		
		if (language == EnumLanguage.English){
			tipps = new File("clientApp\\resources\\tippsEnglish.txt");
			}
		
		else tipps = new File("clientApp\\resources\\tippsGerman.txt");
		
		try {
			Scanner scanLineCount = new Scanner(tipps);
			Scanner scanRandomTipp = new Scanner(tipps);
			int lineCount = 0;
			
			while (scanLineCount.hasNextLine()) {
				scanLineCount.nextLine();
				lineCount++;
			}
			
			Random rand = new Random();
			int random = rand.nextInt(lineCount) + 1;
			
			for (int y = 0; y < random; y++) {
				randomTipp = scanRandomTipp.nextLine();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return randomTipp;
		
	}
	public void readyGame(ClientConn connection) {
		try {
			ReadyMessage startReadyMessage = new ReadyMessage(connection.getClientSocketAddress());
			connection.sendToServer(startReadyMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void abortReadyGame (ClientConn connection) {
		try {
			AbortReadyMessage abortReadyGameMessage = new AbortReadyMessage(connection.getClientSocketAddress());
			connection.sendToServer(abortReadyGameMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	}
