package mainMenu;

import java.util.ArrayList;

import game.Game;
import gameField.GameField_Controller;
import gameField.GameField_Model;
import gameField.GameField_View;
import javafx.application.Application;
import javafx.stage.Stage;
import messages.MessageList;
import player.EnumCardSet;
import player.Player;
import server.Slot;

public class TEST_View extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		Player player1 = new Player("p1", "p1");
		Player player2 = new Player("p2", "p2");		
		
		//Slot hat aktuell nur einen Player und keine Sender und Sockets etc. -- Senden daher nicht möglich!
		Slot slot1 = new Slot(player1);
		Slot slot2 = new Slot(player2);
		
		Game game = new Game(EnumCardSet.VillageSquare, slot1.getPlayer(), slot2.getPlayer());
				
		Stage s = new Stage();
		//GameField_Model m = new GameField_Model();
		//GameField_View v = new GameField_View(m, s);
		Player player = new Player("dummy");
		MessageList messageList = new MessageList();
		
		//GameField_Controller mmc = new GameField_Controller(m, v, player, messageList, game);
		//v.start();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}


}
