package connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import login.Login_Model;
import messages.LoginAnswerMessage;
import messages.LoginMessage;
import messages.Message;
import messages.MessageList;
import messages.SettingsMessage;

/** @author Thomas **/

public class ClientConn {
	private Socket socket;
	private ObjectOutputStream messageSender;
	
	public ClientConn(String ipaddress, int port, MessageList messageList) throws UnknownHostException, IOException {
		Socket socket = new Socket(ipaddress,port);
		System.out.println(socket.getLocalSocketAddress().toString());
		ClientThreadListener clientListener = new ClientThreadListener(socket, messageList);
		clientListener.start(); 
		
		System.out.println("started Client Listener. Server Address: " + socket.getInetAddress());
		
		ObjectOutputStream messageSender = null;
		try {
			messageSender = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("initialized message sender on client");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println("initializing message sender on client FAILED");
			e1.printStackTrace();
		}
		
		this.socket = socket;
		this.messageSender = messageSender;
		
	}
	
	public void sendToServer (Message outgoingMessage) throws IOException {		
		this.messageSender.writeObject(outgoingMessage);
		this.messageSender.reset();
	}
	
	public SocketAddress getClientSocketAddress (){
		return this.socket.getLocalSocketAddress();
	}
}
