package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import javafx.application.Platform;
import login.Login_Model;
import messages.*;

/** @author Thomas **/

public class ClientThreadListener extends Thread {
	private Socket socket;
	private ObjectOutputStream messageSender;
	private MessageList messageList;
	
	
	public ClientThreadListener (Socket socket, MessageList messageList){
		this.socket = socket; 
		this.messageList = messageList;
	}
	
	
	@Override
	public void run() {
		
		Message incomingMessage;
		LoginMessage incomingLoginMessage;
		SettingsMessage incomingSettingsMessage;
		LoginAnswerMessage incomingBooleanLoginMessage;
		
		ObjectInputStream messageListener = null;
		try {
			messageListener = new ObjectInputStream(this.socket.getInputStream());
			System.out.println("initialized message listener on client");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("exception1");
			e1.printStackTrace();
		}
		

		// Listener is always active and listening from the client ----- Messages!
		try {
			while ((incomingMessage = (Message) messageListener.readObject()) != null) {
				messageList.addMessage(incomingMessage);
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			System.out.println("listener on client FAILED while listening for messages");
			e.printStackTrace();
		}			
	}
}
