package server;

/** @author Thomas / Cyrill **/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cards.ActionCard;
import cards.Card;
import connection.ServerConn;
import javafx.collections.ObservableList;
import game.EnumPhase;
import game.Game;
import game.GamePlayer;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import mainMenu.MainMenu_Controller;
import mainMenu.MainMenu_Model;
import mainMenu.MainMenu_View;
import messages.ActionCardMessage;
import messages.ActionFestmahlAnswerMessage;
import messages.ActionKapelleAnswerMessage;
import messages.ActionKellerAnswerMessage;
import messages.ActionMilizAnswerMessage;
import messages.ActionMineAnswerMessage;
import messages.ActionUmbauAnswerMessage;
import messages.ActionWerkstattAnswerMessage;
import messages.BurggrabenAnswerMessage;
import messages.BurggrabenMessage;
import messages.BuyCardsMessage;
import messages.CardMessage;
import messages.CardsetMismatchMessage;
import messages.CreateUserAnswerMessage;
import messages.CreateUserMessage;
import messages.CustomCardSetMismatchMessage;
import messages.DiebAnswerMessage;
import messages.DiebMessage;
import messages.DropCardDrawCardAnswerMessage;
import messages.DropCardDrawCardMessage;
import messages.EndGameMessage;
import messages.EnumMessageType;
import messages.GameMessage;
import messages.LoginAnswerMessage;
import messages.LoginMessage;
import messages.Message;
import messages.MessageList;
import messages.NextPhaseMessage;
import messages.PasswordAnswerMessage;
import messages.StartGameMessage;
import messages.UpdateLogMessage;
import player.EnumCardSet;
import player.EnumLanguage;
import player.Player;

public class ServerModel {
	private ObservableList<Message> messageListServer;
	private List<Slot> slotList;
	private boolean messagereceived = false;
	private Game game;
	private Slot slot1;
	private Slot slot2;
	private boolean gameRunning;
	private StringUpdater strUpdater;
	
	public boolean isGameRunning() {
		return gameRunning;
	}

	public void setGameRunning(boolean gameRunning) {
		this.gameRunning = gameRunning;
	}

	public void gameNextPhase() throws IOException {
		if (game.endOfGame()) {
			this.endOfGame();
		}
		else {
			game.nextPhase();
		}
	}
	
	/**@author Thomas**/
	public ServerModel (ObservableList<Message> messageListServer,  List<Slot> slotList) {
		this.messageListServer = messageListServer;
		this.slotList = slotList;
		this.strUpdater = new StringUpdater();
	}
	
	/*** @author Cyrill ***/
	public boolean checkPassword (String user, String password) {
		File database = new File("serverApp\\resources\\database.txt");
		String line;
		String linepart;
		boolean passwordCheck = false;
		try {
			Scanner scan = new Scanner (database);
			Scanner linescan;
			
			do {
				line = scan.nextLine();
				linescan = new Scanner(line);
				linescan.useDelimiter(";");

				linepart = linescan.next();
				if (linepart.equals(user)) {
					linepart = linescan.next();
					if (linepart.equals(password)) {
						passwordCheck = true;
					} 
				} 
												
			} while (scan.hasNextLine());
			
			scan.close();
			linescan.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return passwordCheck;
	}
	
	/*** @author Thomas ***/
	public void authenticateUser(LoginMessage loginMessage, ObjectOutputStream messageSender) {
		//send Answer, if true also send a player object and write player in Slot
		if  (this.checkPassword(loginMessage.getUsername(), loginMessage.getPassword())) {
			try {
				boolean playerExists = false;
				
				for (Slot slot : slotList) {
					if (slot.getPlayer() != null) {
						System.out.println(slot.getPlayer().getName());
						System.out.println(loginMessage.getUsername());
						if (slot.getPlayer().getName().equals(loginMessage.getUsername())) {
							playerExists = true;
						}
					}
				}
				
				if (!playerExists) {
					Player player = new Player(loginMessage.getUsername(), loginMessage.getPassword()); 
					ServerConn.sendToClient(new LoginAnswerMessage(true, player), messageSender);
					for (Slot slot : slotList) {
						if (loginMessage.getSocketAddress().equals(slot.getClientSocketAddress())) {
							slot.setPlayer(player);
					    }
					}
				}
				else {
					try {
						// wenn der Spieler existiert, f�ge den Spieler in den neuen Slot ein und l�sche ihn aus dem alten slot.
						Player player = new Player(loginMessage.getUsername(), loginMessage.getPassword()); 
						ServerConn.sendToClient(new LoginAnswerMessage(true, player), messageSender);
						for (Slot slot : slotList) {
							if (loginMessage.getSocketAddress().equals(slot.getClientSocketAddress())) {
								slot.setPlayer(player);
						    }
						}
						for (Slot slot : slotList) {
							if (!(loginMessage.getSocketAddress().equals(slot.getClientSocketAddress()))) {
								if (loginMessage.getUsername().equals(slot.getPlayer().getName())) {
									slot.setPlayer(null);
									slot.getSocket().close();
								}
						    }
						}

						
					} catch (IOException e) {
						// TODO Auto-generated catch block
					}
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}
		else {
			try {
				ServerConn.sendToClient(new LoginAnswerMessage(false), messageSender);
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}
	}
	
	/*** @author Cyrill ***/
	public void setSettings(Player player){
		
		try {
			
			//Scanner f�r Inhalt der zu ersetzenden Zeile finden (Variable lineToReplace setzen)
			File file = new File("serverApp\\resources\\database.txt");
			
			Scanner scan = new Scanner(file);
			Scanner linescan;
			
			String line;
			String linepart;
			
			String lineToReplace=null;
			
			do {
			line = scan.nextLine();
			linescan = new Scanner (line);
			linescan.useDelimiter(";");
				
				linepart = linescan.next();
				if (linepart.equals(player.getName())){
						lineToReplace = line;
				}
			
			} while (scan.hasNextLine());
			
			scan.close();
			linescan.close();
			
			
			///BufferedReader f�r File auslesen
			BufferedReader reader = new BufferedReader(new FileReader("serverApp\\resources\\database.txt"));
			
			///ArrayList f�r Zwischenspeicherung des Database Files (Alle Zeilen werden in den Array geschrieben)
			ArrayList <String> database = new ArrayList<String>();
			
			String str;
			String lineReplacer;
			
			//Counter startet mit 1 damit in database.txt mit dem Counter 1 die Zeile 1 geschrieben wird, mit Counter 2 die Zeile 2 geschrieben wird etc...
			int bufferedWriterCount=1;
			
			/// Diese Zeile wird f�r die lineToReplace eingef�gt
			lineReplacer = player.getName()+";"+this.getPassword(player.getName())+";"+player.getCardSet()+";"+player.getLanguage();
			
			///database.txt auslesen und in Array speichern
			while ((str = reader.readLine()) != null) {
				if (str.equals(lineToReplace)){
					database.add(lineReplacer);
				}
				else database.add(str);
			}
			
			reader.close();
			
			/// Writer f�r File neu schreiben
			BufferedWriter writer = new BufferedWriter(new FileWriter("serverApp\\resources\\database.txt"));
			
			
			int lineCount = database.size();
			
			/// database.txt wird komplett neu geschrieben 
			for (String y : database) {
				if (bufferedWriterCount<(lineCount)) {
				writer.write(y+"\n");
				bufferedWriterCount++;
				}
				else {	
						writer.write(y);
						bufferedWriterCount++;
					}
			}

			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/** @author Thomas / teils Cyrill**/
	public void setCustomCardSet(Player player, ArrayList<String> cardStrings) {
		System.out.println("settings custom cardset");
		System.out.println(cardStrings);
		System.out.println(player);
		try {
			//Scanner f�r Inhalt der zu ersetzenden Zeile finden (Variable lineToReplace setzen)
			File file = new File("serverApp\\resources\\customCardSets.txt");
			
			Scanner scan = new Scanner(file);
			Scanner linescan;
			
			String line;
			String linepart;
			
			String lineToReplace=null;
			
			do {
			line = scan.nextLine();
			linescan = new Scanner (line);
			linescan.useDelimiter(";");
				
				linepart = linescan.next();
				if (linepart.equals(player.getName())){
						lineToReplace = line;
				}
			
			} while (scan.hasNextLine());
			
			scan.close();
			linescan.close();
			
			
			///BufferedReader f�r File auslesen
			BufferedReader reader = new BufferedReader(new FileReader("serverApp\\resources\\customCardSets.txt"));
			
			///ArrayList f�r Zwischenspeicherung des Database Files (Alle Zeilen werden in den Array geschrieben)
			ArrayList <String> database = new ArrayList<String>();
			
			String str;
			String lineReplacer;
			
			//Counter startet mit 1 damit in database.txt mit dem Counter 1 die Zeile 1 geschrieben wird, mit Counter 2 die Zeile 2 geschrieben wird etc...
			int bufferedWriterCount=1;
			
			/// Diese Zeile wird f�r die lineToReplace eingef�gt
			lineReplacer = player.getName();
			for (String cardString:cardStrings) {
				lineReplacer+=";"+cardString;
			}
			
			///database.txt auslesen und in Array speichern
			while ((str = reader.readLine()) != null) {
				if (str.equals(lineToReplace)){
					database.add(lineReplacer);
				}
				else database.add(str);
			}
			
			reader.close();
			
			/// Writer f�r File neu schreiben
			BufferedWriter writer = new BufferedWriter(new FileWriter("serverApp\\resources\\customCardSets.txt"));
			
			
			int lineCount = database.size();
			
			/// database.txt wird komplett neu geschrieben 
			for (String y : database) {
				if (bufferedWriterCount<(lineCount)) {
				writer.write(y+"\n");
				bufferedWriterCount++;
				}
				else {	
						writer.write(y);
						bufferedWriterCount++;
					}
			}

			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** @author Thomas **/
	public static ArrayList<String> getCustomCardSet(String playername) throws FileNotFoundException{
		File database = new File("serverApp\\resources\\customCardSets.txt");
		String line;
		String linepart;
		String user = playername;
		ArrayList<String> arrayListCards = new ArrayList<String>();
			Scanner scan = new Scanner (database);
			Scanner linescan;
			
			do {
				line = scan.nextLine();
				linescan = new Scanner(line);
				linescan.useDelimiter(";");

				linepart = linescan.next();
				
				if (linepart.equals(user)) {
					
					if (linescan.hasNext()) {
						
						do{
				        arrayListCards.add(linescan.next());
				    }while(linescan.hasNext()); 
						
					}
					
				} 
											
			} while (scan.hasNextLine());
			
			scan.close();
			linescan.close();
						
			return arrayListCards;
	}
		
	/** @author Cyrill **/
	public void setPassword(Player player, String currentpassword, String newpassword) throws IOException {
		
		boolean passwordMatch = false;
		boolean passwordLength = false;
		boolean passwordSpace = false;
			
		for (Slot slot : slotList) {
			if (player.getName().equals(slot.getPlayer().getName())) {
				
				if (this.getPassword(player.getName()).equals(currentpassword)) {			
					passwordMatch = true;
				}
				
				else {
					PasswordAnswerMessage passwordanswermessage = new PasswordAnswerMessage(1);
					ServerConn.sendToClient(passwordanswermessage, slot.getmessageSender());
				}
				
				
				if (newpassword.length() > 7 && passwordMatch == true) {
					passwordLength = true;
				}
				
				else if (passwordMatch == true){
					PasswordAnswerMessage passwordanswermessage = new PasswordAnswerMessage(2);
					ServerConn.sendToClient(passwordanswermessage, slot.getmessageSender());
				}
				
				
				if (newpassword.contains(" ") == false && passwordMatch == true && passwordLength == true) {
					passwordSpace = true;
				}
				
				else if (passwordLength == true && passwordMatch == true){
					PasswordAnswerMessage passwordanswermessage = new PasswordAnswerMessage(10);
					ServerConn.sendToClient(passwordanswermessage, slot.getmessageSender());
				}
				
				
				if (passwordMatch == true && passwordLength == true && passwordSpace == true) {
										
					try {
						
						//Scanner f�r Inhalt der zu ersetzenden Zeile finden (Variable lineToReplace setzen)
						File file = new File("serverApp\\resources\\database.txt");
						
						Scanner scan = new Scanner(file);
						Scanner linescan;
						
						String line;
						String linepart;
						
						String lineToReplace=null;
						
						do {
						line = scan.nextLine();
						linescan = new Scanner (line);
						linescan.useDelimiter(";");
							
							linepart = linescan.next();
							if (linepart.equals(player.getName())){
									lineToReplace = line;
							}
						
						} while (scan.hasNextLine());
						
						scan.close();
						linescan.close();
						
						///BufferedReader f�r File auslesen
						BufferedReader reader = new BufferedReader(new FileReader("serverApp\\resources\\database.txt"));
						
						///ArrayList f�r Zwischenspeicherung des Database Files (Alle Zeilen werden in den Array geschrieben)
						ArrayList <String> database = new ArrayList<String>();
						
						String str;
						String lineReplacer;
						
						//Counter startet mit 1 damit in database.txt mit dem Counter 1 die Zeile 1 geschrieben wird, mit Counter 2 die Zeile 2 geschrieben wird etc...
						int bufferedWriterCount=1;
						
						
						/// Diese Zeile wird f�r die lineToReplace eingef�gt
						lineReplacer = player.getName()+";"+newpassword+";"+player.getCardSet()+";"+player.getLanguage();
						
						///database.txt auslesen und in Array speichern
						while ((str = reader.readLine()) != null) {
							if (str.equals(lineToReplace)){
								database.add(lineReplacer);
							}
							else database.add(str);
						}
						
						reader.close();
						
						/// Writer f�r File neu schreiben
						BufferedWriter writer = new BufferedWriter(new FileWriter("serverApp\\resources\\database.txt"));
						
						
						int lineCount = database.size();
						
						/// database.txt wird komplett neu geschrieben 
						for (String y : database) {
							if (bufferedWriterCount<(lineCount)) {
							writer.write(y+"\n");
							bufferedWriterCount++;
							}
							else {	
									writer.write(y);
									bufferedWriterCount++;
								}
						}

						writer.close();
						
						
						PasswordAnswerMessage passwordanswermessage = new PasswordAnswerMessage(3);
						ServerConn.sendToClient(passwordanswermessage, slot.getmessageSender());
						
					} 
					catch (Exception e) {
						e.printStackTrace();
					}	
								
			}
		}
		
		
			
			
		}
		
	}
	
	/** @author Cyrill **/
	public static String getPassword(String username) {
		
		File database = new File("serverApp\\resources\\database.txt");
		String line;
		String linepart;
		String password = null;
		
		EnumLanguage language = null;
		
		try {
			Scanner scan = new Scanner (database);
			Scanner linescan;
			
			do {
				line = scan.nextLine();
				linescan = new Scanner(line);
				linescan.useDelimiter(";");

				linepart = linescan.next();
				if (linepart.equals(username)) {
					linepart = linescan.next();
					
					password = linepart;
					
				} 
												
			} while (scan.hasNextLine());
			
			scan.close();
			linescan.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return password;
		
	}	
	
	/*** @author Cyrill ***/
	public static EnumCardSet getCardSet(String username) {
		File database = new File("serverApp\\resources\\database.txt");
		String line;
		String linepart;
		EnumCardSet cardSet = null;
		
		try {
			Scanner scan = new Scanner (database);
			Scanner linescan;
			
			do {
				line = scan.nextLine();
				linescan = new Scanner(line);
				linescan.useDelimiter(";");

				linepart = linescan.next();
				if (linepart.equals(username)) {
					linepart = linescan.next();
					linepart = linescan.next();
					
					if (linepart.equals("Standard")) {
						cardSet = EnumCardSet.Standard;
					} 
					
					if (linepart.equals("BigMoney")) {
						cardSet = EnumCardSet.BigMoney;
					} 
					
					if (linepart.equals("InChange")) {
						cardSet = EnumCardSet.InChange;
					} 
					
					if (linepart.equals("VillageSquare")) {
						cardSet = EnumCardSet.VillageSquare;
					} 
					
					if (linepart.equals("custom")) {
						cardSet = EnumCardSet.custom;
					} 
					
				} 
												
			} while (scan.hasNextLine());
			
			scan.close();
			linescan.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return cardSet;
		
	}

	/*** @author Cyrill ***/
	public static EnumLanguage getLanguage(String username) {
		
		File database = new File("serverApp\\resources\\database.txt");
		String line;
		String linepart;
		EnumLanguage language = null;
		
		try {
			Scanner scan = new Scanner (database);
			Scanner linescan;
			
			do {
				line = scan.nextLine();
				linescan = new Scanner(line);
				linescan.useDelimiter(";");

				linepart = linescan.next();
				if (linepart.equals(username)) {
					linepart = linescan.next();
					linepart = linescan.next();
					linepart = linescan.next();
					
					if (linepart.equals("English")) {
						language = EnumLanguage.English;
					} 
					
					if (linepart.equals("German")) {
						language = EnumLanguage.German;
					} 
					
				} 
												
			} while (scan.hasNextLine());
			
			scan.close();
			linescan.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return language;
		
	}
	
	/** @author Cyrill **/
	public void createUser(CreateUserMessage createUserMessage) throws IOException {
		
		for (Slot slot : slotList) {
			if (createUserMessage.getSocketAddress().equals(slot.getClientSocketAddress())) {

				if (createUserMessage.getUsername().length()<4) {
					CreateUserAnswerMessage createuserfailmessage = new CreateUserAnswerMessage(0);
					ServerConn.sendToClient(createuserfailmessage, slot.getmessageSender());
				}
				
				else if (createUserMessage.getUsername().contains(" ") || createUserMessage.getPassword().contains(" ")) {
					CreateUserAnswerMessage createuserfailmessage = new CreateUserAnswerMessage(10);
					ServerConn.sendToClient(createuserfailmessage, slot.getmessageSender());
				}
				
				
				else if (this.checkIfUserAlreadyExists(createUserMessage.getUsername())==true){
					
					CreateUserAnswerMessage createuserfailmessage = new CreateUserAnswerMessage(1);
					ServerConn.sendToClient(createuserfailmessage, slot.getmessageSender());

				}
				
				
				else if (createUserMessage.getPassword().length()<8) {
					
					CreateUserAnswerMessage createuserfailmessage = new CreateUserAnswerMessage(2);
					ServerConn.sendToClient(createuserfailmessage, slot.getmessageSender());
				}
				
				else if (this.checkIfUserAlreadyExists(createUserMessage.getUsername())==false) {
							
					try {
					
						//Database auslesen
						BufferedReader reader = new BufferedReader(new FileReader("serverApp\\resources\\database.txt"));
						
						///Zwischenspeichern der database
						ArrayList <String> database = new ArrayList<String>();
						
						String str;
									
						while ((str = reader.readLine()) != null) {
							database.add(str);
						}
						
						reader.close();
						
						/// database neu schreiben
						BufferedWriter writer = new BufferedWriter(new FileWriter("serverApp\\resources\\database.txt"));
						
						String newUser = createUserMessage.getUsername() + ";" + createUserMessage.getPassword() + ";Standard;English";
						
						/// database.txt wird komplett neu geschrieben 
						for (String y : database) {
							writer.write(y+"\n");
							}
						
						writer.write(newUser);
						
						writer.close();
						
						
						//Database auslesen
						BufferedReader reader2 = new BufferedReader(new FileReader("serverApp\\resources\\customCardSets.txt"));
						
						
						///Zwischenspeichern der database
						ArrayList <String> database2 = new ArrayList<String>();
						
						String str2;
									
						while ((str2 = reader2.readLine()) != null) {
							database2.add(str2);
						}
						
						reader2.close();
						
						/// database neu schreiben
						BufferedWriter writer2 = new BufferedWriter(new FileWriter("serverApp\\resources\\customCardSets.txt"));
						
						String newUser2 = createUserMessage.getUsername() + ";";
						
						/// database.txt wird komplett neu geschrieben 
						for (String y2 : database2) {
							writer2.write(y2+"\n");
							}
						
						writer2.write(newUser2);
						
						writer2.close();
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
						
					} catch (Exception e) {
						e.printStackTrace();
					}

					
					CreateUserAnswerMessage createusersuccessmessage = new CreateUserAnswerMessage(3);
					ServerConn.sendToClient(createusersuccessmessage, slot.getmessageSender());
					
				
				}
		    }
		}
		
		
	}
	
	/** @author Thomas **/
	public boolean checkIfUserAlreadyExists(String username) {
		
		boolean userExists = false;
		
		try {		
			File file = new File("serverApp\\resources\\database.txt");
			
			Scanner scan = new Scanner(file);
			Scanner userscan;
			
			String line;
			String userpart;
			
			do {
			
			if (scan.hasNextLine()) {
				
				line = scan.nextLine();
				userscan = new Scanner (line);
				userscan.useDelimiter(";");
					
					userpart = userscan.next();
					if (userpart.equals(username)){
							userExists = true;
					}
					userscan.close();
			}
			
			
			} while (scan.hasNextLine());
			
			scan.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return userExists;
	}
	
	/**@author Thomas**/
	public void playerReady (SocketAddress socketAddress) throws IOException {
		int counter = 0;
		Slot slot1 = null;
		Slot slot2 = null;
		for (Slot slot : slotList) {
			if (socketAddress.equals(slot.getClientSocketAddress())) {
				slot.setReady();
		    }
			if (slot.getReadyStatus() == true) {
				counter ++;
				if (slot1 == null) {
					slot1 = slot;
				}
				else {
					slot2 = slot;
				}
			}
			if (counter == 2) {
				//check if selected cardsets match
				if (slot1.getPlayer().getCardSet().equals(slot2.getPlayer().getCardSet())) {
					if (slot1.getPlayer().getCardSet() == EnumCardSet.custom) {
						if (slot1.getPlayer().getCustomCardSetCards().toString().equals(slot2.getPlayer().getCustomCardSetCards().toString())) {
							this.startGame(slot1, slot2);
							String strp1 = strUpdater.getStartGameString(slot1.getPlayer().getLanguage());
							String strp2 = strUpdater.getStartGameString(slot2.getPlayer().getLanguage());
							
							UpdateLogMessage updateLogMessage = new UpdateLogMessage(strp1);
							UpdateLogMessage updateLogMessage2 = new UpdateLogMessage(strp2);
							
							
							ServerConn.sendToClient(updateLogMessage, slot1.getmessageSender());
							ServerConn.sendToClient(updateLogMessage2, slot2.getmessageSender());
						}
						else {
							CustomCardSetMismatchMessage slot1CustomCardSetMismatchMessage = new CustomCardSetMismatchMessage(slot1.getPlayer().getCustomCardSetCards(), slot2.getPlayer().getCustomCardSetCards());
							CustomCardSetMismatchMessage slot2CustomCardSetMismatchMessage = new CustomCardSetMismatchMessage(slot2.getPlayer().getCustomCardSetCards(), slot1.getPlayer().getCustomCardSetCards());
							
							ServerConn.sendToClient(slot1CustomCardSetMismatchMessage, slot1.getmessageSender());
							ServerConn.sendToClient(slot2CustomCardSetMismatchMessage, slot2.getmessageSender());
						}
					}
					else {
						this.startGame(slot1, slot2);
						String strp1 = strUpdater.getStartGameString(slot1.getPlayer().getLanguage());
						String strp2 = strUpdater.getStartGameString(slot2.getPlayer().getLanguage());
						
						UpdateLogMessage updateLogMessage = new UpdateLogMessage(strp1);
						UpdateLogMessage updateLogMessage2 = new UpdateLogMessage(strp2);
						
						
						ServerConn.sendToClient(updateLogMessage, slot1.getmessageSender());
						ServerConn.sendToClient(updateLogMessage2, slot2.getmessageSender());
					}
					
				}
				else {
					CardsetMismatchMessage slot1CardsetMismatchMessage = new CardsetMismatchMessage(slot1.getPlayer().getCardSet(), slot2.getPlayer().getCardSet());
					CardsetMismatchMessage slot2CardsetMismatchMessage = new CardsetMismatchMessage(slot2.getPlayer().getCardSet(), slot1.getPlayer().getCardSet());

					ServerConn.sendToClient(slot1CardsetMismatchMessage, slot1.getmessageSender());
					ServerConn.sendToClient(slot2CardsetMismatchMessage, slot2.getmessageSender());
					
				}
				slot1.setNotReady();
				slot2.setNotReady();
			}
		}
	}
	
	/**@author Thomas**/
	public void playerNotReady (SocketAddress socketAddress) {
		for (Slot slot : slotList) {
			if (socketAddress.equals(slot.getClientSocketAddress())) {
				slot.setNotReady();
		    }
		}
	}
	
	/**@author Thomas**/
	public void startGame (Slot slot1, Slot slot2) throws IOException {
		System.out.println("Player in Slot1 is ready. Username: " + slot1.getPlayer().getName());
		System.out.println("Player in Slot2 is ready. Username: " + slot2.getPlayer().getName());
		
		this.slot1 = slot1;
		this.slot2 = slot2;
		
		GameMessage gameMessage = null;
		
		if (slot1.getPlayer().getCardSet() == EnumCardSet.custom) {
			gameMessage = new GameMessage (game = new Game (this.getCustomCardSet(slot1.getPlayer().getName()), slot1.getPlayer(), slot2.getPlayer()));
		}
		else {
			gameMessage = new GameMessage (game = new Game (slot1.getPlayer().getCardSet(), slot1.getPlayer(), slot2.getPlayer()));
		}
		
		
		StartGameMessage startGameMessage = new StartGameMessage(gameMessage);
		
		this.setGameRunning(true);
		
		ServerConn.sendToClient(startGameMessage, slot1.getmessageSender());
		ServerConn.sendToClient(startGameMessage, slot2.getmessageSender());
		
	}
	
	/** @author Thomas / Cyrill **/
	public void nextPhase(Message message) throws IOException {
		
		UpdateLogMessage updatelogmessage1 = null;
		UpdateLogMessage updatelogmessage2 = null;
		
		if (message.getMessageType() == EnumMessageType.noMoneyCard) {
			String str1 = strUpdater.getNextPhaseString(slot1.getPlayer().getLanguage(), game.getTurn(), EnumMessageType.noMoneyCard);
			String str2 = strUpdater.getNextPhaseString(slot2.getPlayer().getLanguage(), game.getTurn(), EnumMessageType.noMoneyCard);
			
			updatelogmessage1 = new UpdateLogMessage(str1);
			updatelogmessage2 = new UpdateLogMessage(str2);
			
		}
		
		else if (message.getMessageType() == EnumMessageType.noActionCard) {
			//wenn es eine NoActionCardMessage ist, muss der Actioncount auf 0 gesetzt werden, damit nachher die nextPhase Methode nicht in einen Loop kommt.
			this.game.setActionCount(slot1.getPlayer(), 0);
			this.game.setActionCount(slot2.getPlayer(), 0);

			String str1 = strUpdater.getNextPhaseString(slot1.getPlayer().getLanguage(), game.getTurn(), EnumMessageType.noActionCard);
			String str2 = strUpdater.getNextPhaseString(slot2.getPlayer().getLanguage(), game.getTurn(), EnumMessageType.noActionCard);
			updatelogmessage1 = new UpdateLogMessage(str1);
			updatelogmessage2 = new UpdateLogMessage(str2);
			
			
		}
		
		else if (message.getMessageType() == EnumMessageType.nextPhase) {
			//wenn es eine NextPhaseMessage ist, muss der Actioncount auf 0 gesetzt werden, damit nachher die nextPhase Methode nicht in einen Loop kommt.
			this.game.setActionCount(slot1.getPlayer(), 0);
			this.game.setActionCount(slot2.getPlayer(), 0);
			
			String str1 = strUpdater.getNextPhaseString(slot1.getPlayer().getLanguage(), game.getTurn(), EnumMessageType.nextPhase);
			String str2 = strUpdater.getNextPhaseString(slot2.getPlayer().getLanguage(), game.getTurn(), EnumMessageType.nextPhase);
			
			updatelogmessage1 = new UpdateLogMessage(str1);
			updatelogmessage2 = new UpdateLogMessage(str2);
			
		}
		
		this.gameNextPhase();

		
		GameMessage gameMessage = new GameMessage (this.game);
		System.out.println("aktuelle Phase auf Server: "+ gameMessage.getGame().getPhase());
		
		
		ServerConn.sendToClient(updatelogmessage1, slot1.getmessageSender());
		ServerConn.sendToClient(updatelogmessage2, slot2.getmessageSender());
		ServerConn.sendToClient(gameMessage, slot1.getmessageSender());
		ServerConn.sendToClient(gameMessage, slot2.getmessageSender());
	}
	
	/** @author Cyrill**/
	public void nextTurn(Message message) throws IOException {
		this.game.changeTurn();

		GameMessage gameMessage = new GameMessage (this.game);
		
		String str1 = strUpdater.getNextTurnString(slot1.getPlayer().getLanguage(), game.getTurn());
		String str2 = strUpdater.getNextTurnString(slot2.getPlayer().getLanguage(), game.getTurn());
		
		UpdateLogMessage updatelogmessage1 = new UpdateLogMessage(str1);
		UpdateLogMessage updatelogmessage2 = new UpdateLogMessage(str2);
		
		System.out.println("N�chster Spieler am Zug: " + game.getTurn().getName().toString());
		
		ServerConn.sendToClient(updatelogmessage1, slot1.getmessageSender());
		ServerConn.sendToClient(updatelogmessage2, slot2.getmessageSender());
		ServerConn.sendToClient(gameMessage, slot1.getmessageSender());
		ServerConn.sendToClient(gameMessage, slot2.getmessageSender());
	}
	
	/** @author Cyrill / Thomas **/
	public void buyCards(BuyCardsMessage buycardsMessage) throws IOException {
		String str1 = "";
		String str2 = "";
		for (Slot slot : slotList) {
			if (buycardsMessage.getSocketAddress().equals(slot.getClientSocketAddress())) {
				
				str1 = strUpdater.getBuyCardsString(slot1.getPlayer().getLanguage(), game.getTurn(), buycardsMessage.getCardsToBuy());
				str2 = strUpdater.getBuyCardsString(slot2.getPlayer().getLanguage(), game.getTurn(), buycardsMessage.getCardsToBuy());
				
				game.buyCards(slot.getPlayer(), buycardsMessage.getMoneyCards(), buycardsMessage.getCardsToBuy());
				
								
		    }
		}
		
		
		UpdateLogMessage updatelogmessage1 = new UpdateLogMessage(str1);
		UpdateLogMessage updatelogmessage2 = new UpdateLogMessage(str2);

		ServerConn.sendToClient(updatelogmessage1, slot1.getmessageSender());
		ServerConn.sendToClient(updatelogmessage2, slot2.getmessageSender());
		
		//NextPhaseMessage wird nicht geschickt, nur um in this.nextPhase zu pr�fen, warum die n�chste Phase kommt.
		NextPhaseMessage nextPhaseMessage = new NextPhaseMessage();
		this.nextPhase(nextPhaseMessage);
	}
	
	/** @author Cyrill **/
	public void endOfGame() throws IOException {
		EndGameMessage endgamemessage1 = null;
		EndGameMessage endgamemessage2 = null;
		if (game.endOfGame() == true) {
			GamePlayer winner = game.calculateWinner();
			GamePlayer loser = game.calculateLoser();
			
			if (winner==null) {
				
				String title1 = strUpdater.getEndOfGameTitleString(slot1.getPlayer().getLanguage());
				String title2 = strUpdater.getEndOfGameTitleString(slot2.getPlayer().getLanguage());
				
				String str1 = strUpdater.getEndOfGameDrawString(slot1.getPlayer().getLanguage(), game.getPoints(slot1.getPlayer()));
				String str2 = strUpdater.getEndOfGameDrawString(slot2.getPlayer().getLanguage(), game.getPoints(slot2.getPlayer()));
				
				endgamemessage1 = new EndGameMessage(title1, str1);
				endgamemessage2 = new EndGameMessage(title2, str2);
			}
			
			else {
				
				String title1 = strUpdater.getEndOfGameTitleString(slot1.getPlayer().getLanguage());
				String title2 = strUpdater.getEndOfGameTitleString(slot2.getPlayer().getLanguage());
				
				String str1 = strUpdater.getEndOfGameWinnerString(slot1.getPlayer().getLanguage(), game.getDeckPoints(winner.getPlayer()), game.getDeckPoints(loser.getPlayer()), winner.getPlayer(), loser.getPlayer());
				String str2 = strUpdater.getEndOfGameWinnerString(slot2.getPlayer().getLanguage(), game.getDeckPoints(winner.getPlayer()), game.getDeckPoints(loser.getPlayer()), winner.getPlayer(), loser.getPlayer());
				
				endgamemessage1 = new EndGameMessage(title1, str1);
				endgamemessage2 = new EndGameMessage(title2, str2);
				
			}
			
		}
		this.setGameRunning(false);
		ServerConn.sendToClient(endgamemessage1, slot1.getmessageSender());
		ServerConn.sendToClient(endgamemessage2, slot2.getmessageSender());
	}
	
	/**@author Thomas**/
	public void playActionCardAfterBurggraben(BurggrabenAnswerMessage burggrabenanswer) throws IOException {
		String str1 = "";
		String str2 = "";
		for (Slot slot : slotList) {
			if (!(burggrabenanswer.getSocketAddress().equals(slot.getClientSocketAddress()))) {
				ActionCard actionCard = (ActionCard) burggrabenanswer.getCard();
			
				if (actionCard.getName().equals("Dieb")  &&burggrabenanswer.isBurggrabenPlayed()) {
					this.game.setActionCount(slot.getPlayer(), this.game.getActionCount(slot.getPlayer())-1);					
					this.gameNextPhase();
					
					str1 = strUpdater.getPlayActionCardString(slot1.getPlayer().getLanguage(), game.getTurn(), burggrabenanswer.getCard(), burggrabenanswer.isBurggrabenPlayed());
					str2 = strUpdater.getPlayActionCardString(slot2.getPlayer().getLanguage(), game.getTurn(), burggrabenanswer.getCard(), burggrabenanswer.isBurggrabenPlayed());
							
										
					UpdateLogMessage updatelogmessage1 = new UpdateLogMessage(str1);
					UpdateLogMessage updatelogmessage2 = new UpdateLogMessage(str2);
					
					ServerConn.sendToClient(updatelogmessage1, slot1.getmessageSender());
					ServerConn.sendToClient(updatelogmessage2, slot2.getmessageSender());
					
					GameMessage gameMessage = new GameMessage (this.game);
					
					ServerConn.sendToClient(gameMessage, slot1.getmessageSender());
					ServerConn.sendToClient(gameMessage, slot2.getmessageSender());
				}
				else if (actionCard.getName().equals("Dieb") && (!burggrabenanswer.isBurggrabenPlayed())) {
					for (Slot s : slotList) {
						if (!(slot.getPlayer().getName().equals(s.getPlayer().getName()))) {
							game.checkDeckForLowCards(s.getPlayer());
						}
					}
					ArrayList<Card> top2CardsFromEnemyDeck = new ArrayList<Card>();
					
					//nimm die obersten beiden Karten vom gegnerischen Deck (nur vom temporaryGame, damit das richtige Game nicht ver�ndert wird)
					//und schicke sie dem Client, damit er ausw�hlen kann, ob er eine davon nehmen m�chte.
					for (Slot s : slotList) {
						if (!(slot.getPlayer().getName().equals(s.getPlayer().getName()))) {
							top2CardsFromEnemyDeck.add(game.popDeckCard_dieb(s.getPlayer()));
							top2CardsFromEnemyDeck.add(game.popDeckCard_dieb(s.getPlayer()));
							game.addCardToDeck_dieb(s.getPlayer(), top2CardsFromEnemyDeck.get(1));
							game.addCardToDeck_dieb(s.getPlayer(), top2CardsFromEnemyDeck.get(0));
						}
					}

					game.removeCardFromHandAndPutToGraveyard(slot.getPlayer(), actionCard);
					DiebMessage diebmessage = new DiebMessage(top2CardsFromEnemyDeck);
					ServerConn.sendToClient(diebmessage, slot.getmessageSender());
				}				
				else {
					this.game = actionCard.run(this.game, slot.getPlayer(), this, slot.getClientSocketAddress(),burggrabenanswer.isBurggrabenPlayed());
					this.game.setActionCount(slot.getPlayer(), this.game.getActionCount(slot.getPlayer())-1);
					
					System.out.println("PHASE NACH Aktionskarte: " + this.game.getPhase());
					
					//wenn es actionCard Phasen sind, dann darf nicht nextPhase aufgerufen werden.
					if (this.game.getPhase() == EnumPhase.Action) {
						this.gameNextPhase();
					}
					
					str1 = strUpdater.getPlayActionCardString(slot1.getPlayer().getLanguage(), game.getTurn(), burggrabenanswer.getCard(), burggrabenanswer.isBurggrabenPlayed());
					str2 = strUpdater.getPlayActionCardString(slot2.getPlayer().getLanguage(), game.getTurn(), burggrabenanswer.getCard(), burggrabenanswer.isBurggrabenPlayed());
	
					game.removeCardFromHandAndPutToGraveyard(slot.getPlayer(), burggrabenanswer.getCard());
					
					UpdateLogMessage updatelogmessage1 = new UpdateLogMessage(str1);
					UpdateLogMessage updatelogmessage2 = new UpdateLogMessage(str2);
					ServerConn.sendToClient(updatelogmessage1, slot1.getmessageSender());
					ServerConn.sendToClient(updatelogmessage2, slot2.getmessageSender());
					
					GameMessage gameMessage = new GameMessage (this.game);
										
					ServerConn.sendToClient(gameMessage, slot1.getmessageSender());
					ServerConn.sendToClient(gameMessage, slot2.getmessageSender());
				}
		    }
		}
	}
	
	/** @author Thomas **/
	public void playActionCard(ActionCardMessage actionCardMessage) throws IOException {

		for (Slot slot : slotList) {
			if (actionCardMessage.getSocketAddress().equals(slot.getClientSocketAddress())) {
				ActionCard actionCard = (ActionCard) actionCardMessage.getCard();
				
				//check if enemy Hand contains Burggraben
				boolean enemyHandContainsBurggraben = false;
				Slot enemySlot = null;
				for (Slot s : slotList) {
					if (!(slot.getPlayer().getName().equals(s.getPlayer().getName()))) {
						enemyHandContainsBurggraben = game.getHandCards(s.getPlayer()).containsBurggraben();
						enemySlot = s;
					}
				}
				
				//Wenn der Gegner einen Burggraben auf der Hand h�lt, soll er diesen spielen. Ansonsten wird die Aktionskarte einfach gespielt.
				if (actionCard.isAttackCard() && enemyHandContainsBurggraben) {
				
					BurggrabenMessage burggrabenmessage = new BurggrabenMessage(actionCard);
					
					UpdateLogMessage logmessage = new UpdateLogMessage(strUpdater.getBurggrabenString(slot1.getPlayer().getLanguage()));
					UpdateLogMessage logmessage2 = new UpdateLogMessage(strUpdater.getBurggrabenString(slot2.getPlayer().getLanguage()));
					
					ServerConn.sendToClient(logmessage, slot1.getmessageSender());
					ServerConn.sendToClient(logmessage2, slot2.getmessageSender());
					ServerConn.sendToClient(burggrabenmessage, slot.getmessageSender());
					ServerConn.sendToClient(burggrabenmessage, enemySlot.getmessageSender());
					
				}
				
				//Wenn die Aktionskarte Dieb gespielt wird, soll der Sondermechanismus Dieb gestartet werden.
				else if (actionCard.getName().equals("Dieb")) {
					for (Slot s : slotList) {
						if (!(slot.getPlayer().getName().equals(s.getPlayer().getName()))) {
							game.checkDeckForLowCards(s.getPlayer());
						}
					}
					ArrayList<Card> top2CardsFromEnemyDeck = new ArrayList<Card>();
					
					//nimm die obersten beiden Karten vom gegnerischen Deck (nur vom temporaryGame, damit das richtige Game nicht ver�ndert wird)
					//und schicke sie dem Client, damit er ausw�hlen kann, ob er eine davon nehmen m�chte.
					for (Slot s : slotList) {
						if (!(slot.getPlayer().getName().equals(s.getPlayer().getName()))) {
							top2CardsFromEnemyDeck.add(game.popDeckCard_dieb(s.getPlayer()));
							top2CardsFromEnemyDeck.add(game.popDeckCard_dieb(s.getPlayer()));
							game.addCardToDeck_dieb(s.getPlayer(), top2CardsFromEnemyDeck.get(1));
							game.addCardToDeck_dieb(s.getPlayer(), top2CardsFromEnemyDeck.get(0));
							
						}
					}
					game.removeCardFromHandAndPutToGraveyard(slot.getPlayer(), actionCard);
					DiebMessage diebmessage = new DiebMessage(top2CardsFromEnemyDeck);
					ServerConn.sendToClient(diebmessage, slot.getmessageSender());
				}
				
				//ausf�hren der Aktionskarte
				else {
					this.game = actionCard.run(this.game, slot.getPlayer(), this, slot.getClientSocketAddress(), false);
					
					
					
					this.game.setActionCount(slot.getPlayer(), this.game.getActionCount(slot.getPlayer())-1);
					
										
					//wenn es actionCard Phasen sind, dann darf nicht nextPhase aufgerufen werden.
					if (this.game.getPhase() == EnumPhase.Action) {
						this.gameNextPhase();
					}
										
					//alle karten ausser das festmahl sollen nach dem spielen auf den ablagestapel gelegt werden.
					if (!actionCard.getName().equals("Festmahl")) {
						game.removeCardFromHandAndPutToGraveyard(slot.getPlayer(), actionCardMessage.getCard());
					}
					//die karte festmahl soll entfernt werden, wenn sie gespielt wurde. 
					if (actionCard.getName().equals("Festmahl")) {
						game.removeCardFromHandAndPutToTrash(slot.getPlayer(), actionCard);
					}
					
					String str1 = strUpdater.getPlayActionCardString(slot1.getPlayer().getLanguage(), game.getTurn(), actionCardMessage.getCard(), false);
					String str2 = strUpdater.getPlayActionCardString(slot2.getPlayer().getLanguage(), game.getTurn(), actionCardMessage.getCard(), false);
					
					UpdateLogMessage updatelogmessage1 = new UpdateLogMessage(str1);
					UpdateLogMessage updatelogmessage2 = new UpdateLogMessage(str2);
					
					ServerConn.sendToClient(updatelogmessage1, slot1.getmessageSender());
					ServerConn.sendToClient(updatelogmessage2, slot2.getmessageSender());
					
					GameMessage gameMessage = new GameMessage (this.game);

					ServerConn.sendToClient(gameMessage, slot1.getmessageSender());
					ServerConn.sendToClient(gameMessage, slot2.getmessageSender());
				}
		    }
		
		}
	}
	
	/** @author Thomas **/
	public void mineAnswer(ActionMineAnswerMessage mineanswermessage) throws IOException {

		for (Slot slot : slotList) {
			if (mineanswermessage.getSocketAddress().equals(slot.getClientSocketAddress())) {
				
				game.removeCardFromHandAndPutToGraveyard(slot.getPlayer(), mineanswermessage.getCardToDrop());
				game.takeCardFromStackAndPutToHand(slot.getPlayer(), mineanswermessage.getCardToTake());
				
		    }
		
		}
		
		this.gameNextPhase();
		
		String str1 = strUpdater.getMineAnswerString(slot1.getPlayer().getLanguage(), game.getTurn(), mineanswermessage.getCardToDrop(), mineanswermessage.getCardToTake());
		String str2 = strUpdater.getMineAnswerString(slot2.getPlayer().getLanguage(), game.getTurn(), mineanswermessage.getCardToDrop(), mineanswermessage.getCardToTake());
		
		UpdateLogMessage updatelogmessage1 = new UpdateLogMessage(str1);
		UpdateLogMessage updatelogmessage2 = new UpdateLogMessage(str2);
		ServerConn.sendToClient(updatelogmessage1, slot1.getmessageSender());
		ServerConn.sendToClient(updatelogmessage2, slot2.getmessageSender());
		
		GameMessage gameMessage = new GameMessage (this.game);
		ServerConn.sendToClient(gameMessage, slot1.getmessageSender());
		ServerConn.sendToClient(gameMessage, slot2.getmessageSender());
	}
	
	/** @author Thomas **/
	public void umbauAnswer(ActionUmbauAnswerMessage umbauanswermessage) throws IOException {

		for (Slot slot : slotList) {
			if (umbauanswermessage.getSocketAddress().equals(slot.getClientSocketAddress())) {
				
				game.removeCardFromHandAndPutToTrash(slot.getPlayer(), umbauanswermessage.getCardToDrop());
				game.takeCardFromStackAndPutToGraveyard(slot.getPlayer(), umbauanswermessage.getCardToTake());
					
			}
		
		}
		
		this.gameNextPhase();
		
		String str1 = strUpdater.getUmbauAnswerString(slot1.getPlayer().getLanguage(), game.getTurn(), umbauanswermessage.getCardToDrop(), umbauanswermessage.getCardToTake());
		String str2 = strUpdater.getUmbauAnswerString(slot2.getPlayer().getLanguage(), game.getTurn(), umbauanswermessage.getCardToDrop(), umbauanswermessage.getCardToTake());
		
		UpdateLogMessage updatelogmessage1 = new UpdateLogMessage(str1);
		UpdateLogMessage updatelogmessage2 = new UpdateLogMessage(str2);
		
		ServerConn.sendToClient(updatelogmessage1, slot1.getmessageSender());
		ServerConn.sendToClient(updatelogmessage2, slot2.getmessageSender());
		
		GameMessage gameMessage = new GameMessage (this.game);
		ServerConn.sendToClient(gameMessage, slot1.getmessageSender());
		ServerConn.sendToClient(gameMessage, slot2.getmessageSender());
	}
	
	/** @author Cyrill **/
	public void werkstattAnswer(ActionWerkstattAnswerMessage werkstattanswermessage) throws IOException {
		for (Slot slot : slotList) {
			if (werkstattanswermessage.getSocketAddress().equals(slot.getClientSocketAddress())) {
				
				game.takeCardFromStackAndPutToGraveyard(slot.getPlayer(), werkstattanswermessage.getCardToTake());

		    }
		
		}
		
		this.gameNextPhase();
		
		String str1 = strUpdater.getWerkstattAnswerString(slot1.getPlayer().getLanguage(), game.getTurn(), werkstattanswermessage.getCardToTake());
		String str2 = strUpdater.getWerkstattAnswerString(slot2.getPlayer().getLanguage(), game.getTurn(), werkstattanswermessage.getCardToTake());
		
		UpdateLogMessage updatelogmessage1 = new UpdateLogMessage(str1);
		UpdateLogMessage updatelogmessage2 = new UpdateLogMessage(str2);
		
		ServerConn.sendToClient(updatelogmessage1, slot1.getmessageSender());
		ServerConn.sendToClient(updatelogmessage2, slot2.getmessageSender());
		
		GameMessage gameMessage = new GameMessage (this.game);
		ServerConn.sendToClient(gameMessage, slot1.getmessageSender());
		ServerConn.sendToClient(gameMessage, slot2.getmessageSender());
	}

	/** @author Cyrill **/
	public void kellerAnswer(ActionKellerAnswerMessage kelleranswermessage) throws IOException {
		
		for (Slot slot : slotList) {
			if (kelleranswermessage.getSocketAddress().equals(slot.getClientSocketAddress())) {
				for (Card card : kelleranswermessage.getCardsToDrop()){
					game.removeCardFromHandAndPutToGraveyard(slot.getPlayer(), card);
					game.drawCardFromDeck(slot.getPlayer());
				}
				
		    }
		}
		
		this.gameNextPhase();
		
		String str1 = strUpdater.getKellerAnswerString(slot1.getPlayer().getLanguage(), game.getTurn(), kelleranswermessage.getCardsToDrop().size());
		String str2 = strUpdater.getKellerAnswerString(slot2.getPlayer().getLanguage(), game.getTurn(), kelleranswermessage.getCardsToDrop().size());
		
		UpdateLogMessage updatelogmessage1 = new UpdateLogMessage(str1);
		UpdateLogMessage updatelogmessage2 = new UpdateLogMessage(str2);
		
		ServerConn.sendToClient(updatelogmessage1, slot1.getmessageSender());
		ServerConn.sendToClient(updatelogmessage2, slot2.getmessageSender());
		
		GameMessage gameMessage = new GameMessage (this.game);
		ServerConn.sendToClient(gameMessage, slot1.getmessageSender());
		ServerConn.sendToClient(gameMessage, slot2.getmessageSender());
	}
	
	/** @author Thomas **/
	public void milizAnswer(ActionMilizAnswerMessage milizanswermessage) throws IOException {

		for (Slot slot : slotList) {
			if (milizanswermessage.getSocketAddress().equals(slot.getClientSocketAddress())) {
				for (Card card : milizanswermessage.getCardsToDrop()){
					game.removeCardFromHandAndPutToGraveyard(slot.getPlayer(), card);
				}
		    }
		}
		
		this.gameNextPhase();
		
		String str1 = strUpdater.getMilizAnswerString(slot1.getPlayer().getLanguage(), game.getTurn(), milizanswermessage.getCardsToDrop().size());
		String str2 = strUpdater.getMilizAnswerString(slot2.getPlayer().getLanguage(), game.getTurn(), milizanswermessage.getCardsToDrop().size());
		
		UpdateLogMessage updatelogmessage1 = new UpdateLogMessage(str1);
		UpdateLogMessage updatelogmessage2 = new UpdateLogMessage(str2);
		
		ServerConn.sendToClient(updatelogmessage1, slot1.getmessageSender());
		ServerConn.sendToClient(updatelogmessage2, slot2.getmessageSender());
		
		GameMessage gameMessage = new GameMessage (this.game);
		ServerConn.sendToClient(gameMessage, slot1.getmessageSender());
		ServerConn.sendToClient(gameMessage, slot2.getmessageSender());
	}
	
	/** @author Thomas **/
	public void diebAnswer(DiebAnswerMessage diebanswermessage) throws IOException {
		String str1 ="";
		String str2 = "";
		for (Slot slot : slotList) {
			if (diebanswermessage.getSocketAddress().equals(slot.getClientSocketAddress())) {
				
				if (diebanswermessage.getCard() == null) {
					for (Slot s : slotList) {
						if (!(slot.getPlayer().getName().equals(s.getPlayer().getName()))) {
							Card card1 = game.popDeckCard_dieb(s.getPlayer());
							Card card2 = game.popDeckCard_dieb(s.getPlayer());
							game.addCardToGraveyard(s.getPlayer(), card1);
							game.addCardToGraveyard(s.getPlayer(), card2);
							
							str1 = strUpdater.getDiebAnswerMessageString_NoCard(slot1.getPlayer().getLanguage(), slot1.getPlayer(), slot2.getPlayer(), card1, card2);
							str2 = strUpdater.getDiebAnswerMessageString_NoCard(slot2.getPlayer().getLanguage(), slot2.getPlayer(), slot1.getPlayer(), card1, card2);
							
							this.gameNextPhase();
						}
					}
				}
				else {
					for (Slot s : slotList) {
						if (!(slot.getPlayer().getName().equals(s.getPlayer().getName()))) {
							Card card1 = game.popDeckCard_dieb(s.getPlayer());
							Card card2 = game.popDeckCard_dieb(s.getPlayer());
							if (card1.getName().equals(diebanswermessage.getCard().getName())) {
								if (diebanswermessage.getStehlen()) {
									game.addCardToGraveyard(slot.getPlayer(), card1);
									str1 = strUpdater.getDiebAnswerMessageString(slot1.getPlayer().getLanguage(), slot1.getPlayer(), slot2.getPlayer(), card1, card2, diebanswermessage.getStehlen());
									str2 = strUpdater.getDiebAnswerMessageString(slot2.getPlayer().getLanguage(), slot2.getPlayer(), slot1.getPlayer(), card1, card2, diebanswermessage.getStehlen());
									
								}
								else {
									str1 = strUpdater.getDiebAnswerMessageString(slot1.getPlayer().getLanguage(), slot1.getPlayer(), slot2.getPlayer(), card1, card2, diebanswermessage.getStehlen());
									str2 = strUpdater.getDiebAnswerMessageString(slot2.getPlayer().getLanguage(), slot2.getPlayer(), slot1.getPlayer(), card1, card2, diebanswermessage.getStehlen());
								}
								game.addCardToGraveyard(s.getPlayer(), card2);
							}
							else {
								if (diebanswermessage.getStehlen()) {
									game.addCardToGraveyard(slot.getPlayer(), card2);
									str1 = strUpdater.getDiebAnswerMessageString(slot1.getPlayer().getLanguage(), slot1.getPlayer(), slot2.getPlayer(), card2, card1, diebanswermessage.getStehlen());
									str2 = strUpdater.getDiebAnswerMessageString(slot2.getPlayer().getLanguage(), slot2.getPlayer(), slot1.getPlayer(), card2, card1, diebanswermessage.getStehlen());
								}
								else {
									str1 = strUpdater.getDiebAnswerMessageString(slot1.getPlayer().getLanguage(), slot1.getPlayer(), slot2.getPlayer(), card2, card1, diebanswermessage.getStehlen());
									str2 = strUpdater.getDiebAnswerMessageString(slot2.getPlayer().getLanguage(), slot2.getPlayer(), slot1.getPlayer(), card2, card1, diebanswermessage.getStehlen());
								}
								game.addCardToGraveyard(s.getPlayer(), card1);
							}
							
							this.gameNextPhase();

						}
					}
				}
				this.game.setActionCount(slot.getPlayer(), this.game.getActionCount(slot.getPlayer())-1);
		    }
		}
		
		this.gameNextPhase();
		
		UpdateLogMessage updatelogmessage1 = new UpdateLogMessage(str1);
		UpdateLogMessage updatelogmessage2 = new UpdateLogMessage(str2);
		
		ServerConn.sendToClient(updatelogmessage1, slot1.getmessageSender());
		ServerConn.sendToClient(updatelogmessage2, slot2.getmessageSender());
		
		GameMessage gameMessage = new GameMessage (this.game);
		ServerConn.sendToClient(gameMessage, slot1.getmessageSender());
		ServerConn.sendToClient(gameMessage, slot2.getmessageSender());
	}
	
	/** @author Cyrill **/
	public void kapelleAnswer(ActionKapelleAnswerMessage kapelleanswermessage) throws IOException {
		for (Slot slot : slotList) {
			if (kapelleanswermessage.getSocketAddress().equals(slot.getClientSocketAddress())) {
				for (Card c:kapelleanswermessage.getCardsToDrop()) {
					game.removeCardFromHandAndPutToTrash(slot.getPlayer(), c);
				}
			}
		
		}
		
		this.gameNextPhase();
		
		String str1 = strUpdater.getKapelleAnswerString(slot1.getPlayer().getLanguage(), game.getTurn(), kapelleanswermessage.getCardsToDrop());
		String str2 = strUpdater.getKapelleAnswerString(slot2.getPlayer().getLanguage(), game.getTurn(), kapelleanswermessage.getCardsToDrop());
		
		UpdateLogMessage updatelogmessage1 = new UpdateLogMessage(str1);
		UpdateLogMessage updatelogmessage2 = new UpdateLogMessage(str2);
		
		ServerConn.sendToClient(updatelogmessage1, slot1.getmessageSender());
		ServerConn.sendToClient(updatelogmessage2, slot2.getmessageSender());
		
		GameMessage gameMessage = new GameMessage (this.game);
		ServerConn.sendToClient(gameMessage, slot1.getmessageSender());
		ServerConn.sendToClient(gameMessage, slot2.getmessageSender());
	}
	
	
	/** @author Cyrill 
	 * @throws IOException **/
	public void festmahlAnswer(ActionFestmahlAnswerMessage festmahlanswermessage) throws IOException {
		
		for (Slot slot : slotList) {
			if (festmahlanswermessage.getSocketAddress().equals(slot.getClientSocketAddress())) {
				game.takeCardFromStackAndPutToGraveyard(slot.getPlayer(), festmahlanswermessage.getCardToTake());

		    }
		
		}
		
		this.gameNextPhase();
		
		String str1 = strUpdater.getFestmahlAnswerString(slot1.getPlayer().getLanguage(), game.getTurn(), festmahlanswermessage.getCardToTake());
		String str2 = strUpdater.getFestmahlAnswerString(slot2.getPlayer().getLanguage(), game.getTurn(), festmahlanswermessage.getCardToTake());
		
		UpdateLogMessage updatelogmessage1 = new UpdateLogMessage(str1);
		UpdateLogMessage updatelogmessage2 = new UpdateLogMessage(str2);
		ServerConn.sendToClient(updatelogmessage1, slot1.getmessageSender());
		ServerConn.sendToClient(updatelogmessage2, slot2.getmessageSender());
		
		GameMessage gameMessage = new GameMessage (this.game);
		ServerConn.sendToClient(gameMessage, slot1.getmessageSender());
		ServerConn.sendToClient(gameMessage, slot2.getmessageSender());
		
	}
}
