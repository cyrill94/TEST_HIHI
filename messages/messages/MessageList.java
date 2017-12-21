package messages;

import java.util.Observable;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MessageList {
	//diese klasse wurde nur erstellt, weil auf dem Client JAVA FX im Einsatz ist und deswegen neue Elemente auf Seiten Client mit Platform.runLater
	//hinzugefügt werden müssen. Auf Seiten Server werden die elemente mit messages.add(Message) hinzugefügt.

	public final ObservableList<Message> messages;
	
	public MessageList(){
		ObservableList<Message> messages = FXCollections.observableArrayList();
		this.messages = messages;
	}
	
	public void addMessage (Message message) {
		Platform.runLater(new Runnable(){

			@Override
			public void run() {
				messages.add(message);
			}
			});
		System.out.println("ADDED MESSAGE");
		
	}	
}
