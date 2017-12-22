/**@author Sven**/
package mainMenu;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import player.EnumLanguage;
import templates.TemplateView;

public class MainMenu_View extends TemplateView{
	
	private Button btnPlayGame, btnConfig, btnAbortPlayGame;
	private Label taTipps;
	private EnumLanguage language;
	private MainMenu_Model model;

	public MainMenu_View(MainMenu_Model model, Stage primaryStage) {
		super(model, primaryStage);
		this.model = model;
		this.language = language;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Scene getScene() {
		HBox root = new HBox();
		VBox leftSide = new VBox();
		Scene scene = new Scene(root);
		
		btnPlayGame = new Button(); 
		btnAbortPlayGame = new Button();
		btnConfig = new Button();
		taTipps = new Label();
				
		root.setId("mainMenu");
		leftSide.setId("vBox");
		taTipps.setId("lblTips");
		taTipps.setWrapText(true);
		
		root.getChildren().add(0,leftSide);
		root.getChildren().add(1, taTipps);
		leftSide.getChildren().add(btnPlayGame);
		leftSide.getChildren().add(btnAbortPlayGame);
		leftSide.getChildren().add(btnConfig);	

		btnPlayGame.setDefaultButton(true);
		btnAbortPlayGame.setVisible(false);
		
		return scene;
	}

	public Button getBtnPlayGame() {
		return btnPlayGame;
	}

	public void setBtnPlayGame(Button btnPlayGame) {
		this.btnPlayGame = btnPlayGame;
	}

	public Button getBtnConfig() {
		return btnConfig;
	}

	public void setBtnConfig(Button btnConfig) {
		this.btnConfig = btnConfig;
	}

	public Label getTaTipps() {
		return taTipps;
	}

	public void setTaTipps(String string) {
		this.taTipps.setText(string);
	}
	
	public Button getBtnAbortPlayGame() {
		return btnAbortPlayGame;
	}
	
	public void setbtnAbortPlayGameVisible() {
		this.btnAbortPlayGame.setVisible(true);
	}
	
	public void setbtnAbortPlayGameNotVisible() {
		this.btnAbortPlayGame.setVisible(false);
	}
	
	public void setbtnPlayGameDisbled() {
		this.btnPlayGame.setDisable(true);
	}
	
	public void setbtnPlayGameEnabled() {
		this.btnPlayGame.setDisable(false);
	}
	
	public void setbtnConfigDisabled() {
		this.btnConfig.setDisable(true);
	}
	
	public void setbtnConfigEnabled() {
		this.btnConfig.setDisable(false);
	}
	
	public EnumLanguage getLanguage() {
		return this.language;
	}
	
	public void updateText(EnumLanguage lang) {
		if (lang == EnumLanguage.English) {
			btnPlayGame.setText("Start game");
			btnAbortPlayGame.setText("Wait for opponent... (Cancel)");
			btnConfig.setText("Settings");
		}
		
		else if (lang == EnumLanguage.German) {
			btnPlayGame.setText("Spiel starten");
			btnAbortPlayGame.setText("Warten auf Spieler... (Abbrechen)");
			btnConfig.setText("Einstellungen");
		}
	}
	
	public void getRandomTipp(EnumLanguage lang) {
		this.taTipps.setText(this.model.getRandomTipp(lang));
	}
}
