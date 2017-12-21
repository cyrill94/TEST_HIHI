package connection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import messages.Message;
import messages.MessageList;
import server.ServerController;
import server.ServerModel;
import server.Slot;

/** @author Thomas **/

public class ServerConn{
	
	public static void main(String[] args) throws IOException {
	
		ObservableList<Message> messageListServer = FXCollections.observableArrayList();
		
		List<Slot> slotList = new ArrayList<Slot>();
		
		ServerSocket serverSocket = new ServerSocket(2002);
		System.out.println("did not start listener, waiting for accepting a socket connection");
		

		ServerModel serverModel = new ServerModel(messageListServer, slotList);
		ServerController serverController = new ServerController(serverModel, messageListServer, slotList);
		
		while (true){
			Socket socket = serverSocket.accept();
			System.out.println(socket.getRemoteSocketAddress().toString());
			ObjectOutputStream messageSender = null;
			try {
				messageSender = new ObjectOutputStream(socket.getOutputStream());
				System.out.println("initialized message sender on server");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("initializing message sender on server FAILED");
				e1.printStackTrace();
			}
				
			slotList.add(new Slot(socket, messageSender));
			
			ServerThreadListener serverListener = new ServerThreadListener(socket, messageSender, serverModel, messageListServer);
			serverListener.start();
			System.out.println("started Server Listener. Client Address: " + socket.getInetAddress());
			
			
		}
		
	}
	
	public static void sendToClient(Message outgoingMessage, ObjectOutputStream messageSender) throws IOException {
		messageSender.writeObject(outgoingMessage);
		messageSender.reset();
	}

}
