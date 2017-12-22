package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import connection.ServerConn;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import mainMenu.MainMenu_Controller;
import mainMenu.MainMenu_Model;
import mainMenu.MainMenu_View;
import messages.AbortReadyMessage;
import messages.ActionCardMessage;
import messages.ActionFestmahlAnswerMessage;
import messages.ActionKapelleAnswerMessage;
import messages.ActionKellerAnswerMessage;
import messages.ActionMilizAnswerMessage;
import messages.ActionMineAnswerMessage;
import messages.ActionUmbauAnswerMessage;
import messages.ActionWerkstattAnswerMessage;
import messages.BurggrabenAnswerMessage;
import messages.BuyCardsMessage;
import messages.CardMessage;
import messages.CreateUserMessage;
import messages.DiebAnswerMessage;
import messages.EnumMessageType;
import messages.LoginAnswerMessage;
import messages.LoginMessage;
import messages.Message;
import messages.PasswordMessage;
import messages.SettingsMessage;
import messages.ReadyMessage;
import player.Player;
import templates.TemplateController;
import templates.TemplateModel;
import templates.TemplateView;

/** @author Thomas **/
public class ServerController{

	private ServerModel serverModel;
	private ObservableList<Message> messageListServer;
	private InetAddress localIP;
	
	public ServerController(ServerModel serverModel, ObservableList<Message> messageListServer, List<Slot> slotList) throws UnknownHostException, IOException {
		this.serverModel = serverModel;
		this.messageListServer = messageListServer;
	
	
	/** @author Thomas **/
	messageListServer.addListener((ListChangeListener) (event -> {
		
		Message incomingMessage = messageListServer.get(messageListServer.size()-1);
		if (incomingMessage.getMessageType() == EnumMessageType.login){
			
			//find messageSender to which we want to send the message
			ObjectOutputStream messageSender = null;
			for (Slot slot : slotList) {
				if (incomingMessage.getSocketAddress().equals(slot.getClientSocketAddress())) {
					messageSender = slot.getmessageSender();
			    }
			}
			serverModel.authenticateUser((LoginMessage) incomingMessage, messageSender);
		}
		
		/** @author Cyrill **/
		
		if (incomingMessage.getMessageType() == EnumMessageType.createUser) {
			try {
				serverModel.createUser((CreateUserMessage) incomingMessage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		/** @author Thomas **/		
		//receive Settingsmessage, then update Settings in Database and update Slot with new Player Object
		else if (incomingMessage.getMessageType() == EnumMessageType.settings){
			SettingsMessage incomingSettingsMessage = (SettingsMessage) incomingMessage;
			serverModel.setSettings(incomingSettingsMessage.getPlayer());
			
			for (Slot slot : slotList) {
				if (incomingMessage.getSocketAddress().equals(slot.getClientSocketAddress())) {
					slot.setPlayer(incomingSettingsMessage.getPlayer());
			    }
			}
			
		}
		
		else if (incomingMessage.getMessageType() == EnumMessageType.passwordMessage) {
			PasswordMessage passwordMessage = (PasswordMessage) incomingMessage;
			try {
				serverModel.setPassword(passwordMessage.getPlayer(), passwordMessage.getCurrentPassword(), passwordMessage.getNewPassword());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if (incomingMessage.getMessageType() == EnumMessageType.readyGame) {
			try {
				ReadyMessage incomingReadyMessage = (ReadyMessage) incomingMessage;
				serverModel.playerReady(incomingReadyMessage.getSocketAddress());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if (incomingMessage.getMessageType() == EnumMessageType.abortReadyGame) {
			AbortReadyMessage incomingAbortReadyMessage = (AbortReadyMessage) incomingMessage;
			serverModel.playerNotReady(incomingAbortReadyMessage.getSocketAddress());
		}
		
		if (serverModel.isGameRunning()) {
				
			if (incomingMessage.getMessageType() == EnumMessageType.noActionCard) {
				try {
					serverModel.nextPhase(incomingMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			else if (incomingMessage.getMessageType() == EnumMessageType.noMoneyCard) {
				try {
					serverModel.nextPhase(incomingMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			else if (incomingMessage.getMessageType() == EnumMessageType.nextPhase) {
				try {
					serverModel.nextPhase(incomingMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			else if (incomingMessage.getMessageType() == EnumMessageType.nextTurn) {
				try {
					serverModel.nextTurn(incomingMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			else if (incomingMessage.getMessageType() == EnumMessageType.buyCardsMessage) {
				try {
					serverModel.buyCards((BuyCardsMessage)incomingMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			else if (incomingMessage.getMessageType() == EnumMessageType.actionCard) {
				try {
					serverModel.playActionCard((ActionCardMessage)incomingMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			else if (incomingMessage.getMessageType() == EnumMessageType.mineAnswer) {
				try {
					serverModel.mineAnswer((ActionMineAnswerMessage) incomingMessage);;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if (incomingMessage.getMessageType() == EnumMessageType.umbauAnswer) {
				try {
					serverModel.umbauAnswer((ActionUmbauAnswerMessage) incomingMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			else if (incomingMessage.getMessageType() == EnumMessageType.werkstattAnswer) {
				try {
					serverModel.werkstattAnswer((ActionWerkstattAnswerMessage) incomingMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			else if (incomingMessage.getMessageType() == EnumMessageType.kellerAnswer) {
				try {
					serverModel.kellerAnswer((ActionKellerAnswerMessage) incomingMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			else if (incomingMessage.getMessageType() == EnumMessageType.burggrabenAnswer) {
				try {
					serverModel.playActionCardAfterBurggraben((BurggrabenAnswerMessage) incomingMessage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			else if (incomingMessage.getMessageType() == EnumMessageType.milizAnswer) {
				try {
					serverModel.milizAnswer((ActionMilizAnswerMessage)incomingMessage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			else if (incomingMessage.getMessageType() == EnumMessageType.festmahlAnswer) {
				try {
					serverModel.festmahlAnswer((ActionFestmahlAnswerMessage) incomingMessage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			else if (incomingMessage.getMessageType() == EnumMessageType.diebanswer) {
				try {
					serverModel.diebAnswer((DiebAnswerMessage)incomingMessage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
			else if (incomingMessage.getMessageType() == EnumMessageType.kapelleAnswer) {
				try {
					serverModel.kapelleAnswer((ActionKapelleAnswerMessage) incomingMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
		}
		
		
		
		
	}));
	
	
	}
	
	

}
