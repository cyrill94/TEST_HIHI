/**@author Sven**/
package configMenu;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import player.EnumCardSet;
import player.EnumLanguage;
import templates.TemplateModel;
import templates.TemplateView;

public class ConfigMenu_View extends TemplateView{
	
	private Button btnSaveConfig;
	private Button btnCancel;
	private Button btnChangePassword;
	private PasswordField currentPassword, newPassword;
	private Label lblCardSet, lblLanguage, lblCurrentPassword, lblNewPassword;
	private ToggleGroup tgCardSet, tgLanguage;
	private RadioButton rbCardSetStandard;
	private RadioButton rbCardSetInChange;
	private RadioButton rbCardSetBigMoney;
	private RadioButton rbCardSetVillageSquare;
	private RadioButton rbEnglish;
	private RadioButton rbGerman;

	public ConfigMenu_View(ConfigMenu_Model model, Stage primaryStage) {
		super(model, primaryStage);
		// TODO Auto-generated constructor stub
	}
	
/**This view is divided into 3 vertical parts (root = VBox). The part on top contains a horizontal box
 * with a label called cardSet in its left part and a few more boxes (vertical and horizontal) for the 
 * optional card sets 'Standard', 'In Change', 'Big Game' and 'Village Square'.
 * 
 * The central part of the root element is made of a horizontal box with a label 'Language' in its left
 * part aswell. The right part of this hbox contains another hbox with the two language options 'German'
 * and 'English'.
 * 
 * The bottom part is made of one hbox with two buttons 'save' and 'cancel'.**/

	@Override
	public Scene getScene() {
		VBox root = new VBox();
		HBox top = new HBox();
		HBox center = new HBox();
		VBox center2 = new VBox();
		HBox bottom = new HBox();
		VBox cardSet = new VBox();
		HBox set1 = new HBox();
		HBox set2 = new HBox();
		HBox language = new HBox();
		
		ToggleGroup tgCardSet = new ToggleGroup();
		ToggleGroup tgLanguage= new ToggleGroup();
		
		//Initiate nodes for HBox top
		Label lblCardSet = new Label();
		rbCardSetStandard = new RadioButton();
		rbCardSetInChange = new RadioButton();
		rbCardSetBigMoney = new RadioButton();
		rbCardSetVillageSquare = new RadioButton();
		
		rbCardSetStandard.setToggleGroup(tgCardSet);
		rbCardSetInChange.setToggleGroup(tgCardSet);
		rbCardSetBigMoney.setToggleGroup(tgCardSet);
		rbCardSetVillageSquare.setToggleGroup(tgCardSet);
		
		//Initiate nodes for HBox center
		Label lblLanguage = new Label();
		rbGerman = new RadioButton();
		rbEnglish = new RadioButton();
		rbGerman.setToggleGroup(tgLanguage);
		rbEnglish.setToggleGroup(tgLanguage);
		
		//Initiate nodes for HBox bottom
		Button btnSaveConfig = new Button();
		Button btnCancel = new Button();
		Button btnChangePassword = new Button();
		PasswordField currentPassword = new PasswordField();
		PasswordField newPassword = new PasswordField();
				
		Scene scene = new Scene(root);
		
		this.btnSaveConfig = btnSaveConfig;
		this.btnCancel = btnCancel;
		this.btnChangePassword = btnChangePassword;
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
		this.lblCardSet = lblCardSet;
		this.lblLanguage = lblLanguage;
		this.tgCardSet = tgCardSet;
		this.tgLanguage = tgLanguage;

		
		
		//Setting IDs for Stylesheet
		top.setId("hBox");
		center.setId("hBox");
		center2.setId("hBox");
		bottom.setId("hBoxConfigMenuBottom");
		cardSet.setId("vBox");
		set1.setId("hBoxConfigMenu");
		set2.setId("hBoxConfigMenu");
		language.setId("hBoxConfigMenu");
		currentPassword.setId("txtField");
		newPassword.setId("txtField");
		
		//Get nodes for VBox root
		root.getChildren().add(0, top);
		root.getChildren().add(1, center);
		root.getChildren().add(2, center2);
		root.getChildren().add(3, bottom);
		
		//Get nodes for HBox top
		top.getChildren().add(0, lblCardSet);
		top.getChildren().add(1, cardSet);
		cardSet.getChildren().add(0, set1);
		cardSet.getChildren().add(1, set2);
		set1.getChildren().add(0,rbCardSetStandard);
		set1.getChildren().add(1, rbCardSetInChange);
		set2.getChildren().add(0, rbCardSetBigMoney);
		set2.getChildren().add(1, rbCardSetVillageSquare);
		
		//Get nodes for HBox center
		center.getChildren().add(0, lblLanguage);
		center.getChildren().add(1, language);
		
		center2.getChildren().add(0, currentPassword);
		center2.getChildren().add(1, newPassword);
		
		center2.getChildren().add(2, btnChangePassword);
		
		language.getChildren().add(0, rbGerman);
		language.getChildren().add(1, rbEnglish);
		
		//Get nodes for HBox bottom
		bottom.getChildren().add(0, btnSaveConfig);
		bottom.getChildren().add(1, btnCancel);
		
		return scene;
	}
	
	public ToggleGroup getTgCardSet() {
	return tgCardSet;
}

public void setTgCardSet(ToggleGroup tgCardSet) {
	this.tgCardSet = tgCardSet;
}

public ToggleGroup getTgLanguage() {
	return tgLanguage;
}

public void setTgLanguage(ToggleGroup tgLanguage) {
	this.tgLanguage = tgLanguage;
}

	public Button getBtnSaveConfig() {
		return btnSaveConfig;
	}

	public void setBtnSaveConfig(Button btnSaveConfig) {
		this.btnSaveConfig = btnSaveConfig;
	}

	public Button getBtnCancel() {
		return btnCancel;
	}

	public void setBtnCancel(Button btnCancel) {
		this.btnCancel = btnCancel;
	}

	public Label getLblCardSet() {
		return lblCardSet;
	}

	public void setLblCardSet(Label lblCardSet) {
		this.lblCardSet = lblCardSet;
	}

	public Label getLblLanguage() {
		return lblLanguage;
	}

	public void setLblLanguage(Label lblLanguage) {
		this.lblLanguage = lblLanguage;
	}
	
	public void settgCardSetStandard () {
		this.tgCardSet.selectToggle(rbCardSetStandard);
	}
	
	public void settgCardSetInChange () {
		this.tgCardSet.selectToggle(rbCardSetInChange);
	}

	public void settgCardSetBigMoney () {
		this.tgCardSet.selectToggle(rbCardSetBigMoney);
	}

	public void settgCardSetVillageSquare () {
		this.tgCardSet.selectToggle(rbCardSetVillageSquare);
	}
	
	public void settgLanguageGerman () {
		this.tgLanguage.selectToggle(rbGerman);
	}
	
	public void settgLanguageEnglish () {
		this.tgLanguage.selectToggle(rbEnglish);
	}
	
	public EnumLanguage getLanguage () {
		if (tgLanguage.getSelectedToggle() == rbEnglish) {
			return EnumLanguage.English;
		}
		else {
			return EnumLanguage.German;
		}
	}
	
	public EnumCardSet getCardSet () {
		if (tgCardSet.getSelectedToggle() == rbCardSetStandard) {
			return EnumCardSet.Standard;
		}
		else if (tgCardSet.getSelectedToggle() == rbCardSetInChange) {
			return EnumCardSet.InChange;
		}
		else if (tgCardSet.getSelectedToggle() == rbCardSetBigMoney) {
			return EnumCardSet.BigMoney;
		}
		else {
			return EnumCardSet.VillageSquare;
		}
	}
	
	public Button getBtnChangePassword() {
		return btnChangePassword;
	}
	
	public String getCurrentPassword() {
		return currentPassword.getText();
	}
	
	public String getNewPassword() {
		return newPassword.getText();
	}
	
	public PasswordField getCurrentPasswordField() {
		return this.currentPassword;
	}
	
	public PasswordField getNewPasswordField() {
		return this.newPassword;
	}
	
	public void updateText(EnumLanguage lang) {
		if (lang == EnumLanguage.English) {
			lblCardSet.setText("Cardset");
			rbCardSetStandard.setText("Standard");
			rbCardSetInChange.setText("In Change");
			rbCardSetBigMoney.setText("Big Money");
			rbCardSetVillageSquare.setText("Village Square");
			
			currentPassword.setPromptText("Current Password");
			newPassword.setPromptText("New Password");

			lblLanguage.setText("Language");
			rbGerman.setText("German");
			rbEnglish.setText("English");
			
			btnSaveConfig.setText("Save Settings");
			btnCancel.setText("Cancel");
			btnChangePassword.setText("Change password");
		}
		
		else if (lang == EnumLanguage.German){
			lblCardSet.setText("Kartensatz");
			rbCardSetStandard.setText("Standard");
			rbCardSetInChange.setText("Im Wandel");
			rbCardSetBigMoney.setText("Grosses Spiel");
			rbCardSetVillageSquare.setText("Dorfplatz");
			
			currentPassword.setPromptText("Aktuelles Passwort");
			newPassword.setPromptText("Neues Passwort");

			lblLanguage.setText("Sprache");
			rbGerman.setText("Deutsch");
			rbEnglish.setText("Englisch");

			btnSaveConfig.setText("Einstellungen speichern");
			btnCancel.setText("Abbrechen");
			btnChangePassword.setText("Passwort ändern");
		}
	}

}
