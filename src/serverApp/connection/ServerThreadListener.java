package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import messages.*;
import server.ServerModel;

/** @author Thomas **/

public class ServerThreadListener extends Thread {
	private Socket socket;
	private ObjectOutputStream messageSender;
	private ServerModel serverModel;
	private ObservableList<Message> messageListServer;
	
	
	public ServerThreadListener (Socket socket, ObjectOutputStream messageSender, ServerModel serverModel, ObservableList<Message> messageListServer){
		this.socket = socket; 
		this.messageSender = messageSender;
		this.serverModel = serverModel;
		this.messageListServer = messageListServer;
	}
	
	@Override
	public void run() {
		

		Message incomingMessage;
		LoginMessage incomingLoginMessage;
		SettingsMessage incomingSettingsMessage;
		LoginAnswerMessage outgoingLoginAnswerMessage;

		//Initialize listener and sender. Needed to surround with because of IOException. Read here:https://stackoverflow.com/questions/2305966/why-do-i-get-the-unhandled-exception-type-ioexception
		
		
		
		ObjectInputStream messageListener = null;
		try {
			messageListener = new ObjectInputStream(this.socket.getInputStream());
			System.out.println("initialized message listener on server");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("initializing message listener on server FAILED");
			e1.printStackTrace();
		}
		
		
		
		
		// Listener is always active and listening from the client ----- Messages!
		try {
			while ((incomingMessage = (Message) messageListener.readObject()) != null) {
				messageListServer.add(incomingMessage);
				
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			System.out.println("listener on server FAILED while listening for messages");
			System.out.println("THREAD ABGEKACKT");
			//e.printStackTrace();
		}

		
		
	}
	
}

