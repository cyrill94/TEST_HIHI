/**@author Sven**/
package configMenu;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import player.EnumCardSet;
import player.EnumLanguage;
import templates.TemplateView;

public class ConfigMenu_View extends TemplateView{
	
	private ConfigMenu_Model model;
	private Button btnSaveConfig, btnCancel, btnChangePassword;
	private PasswordField currentPassword, newPassword;
	private Label lblCardSet, lblLanguage;
	private ToggleGroup tgCardSet, tgLanguage;
	private RadioButton rbCardSetStandard, rbCardSetInChange, rbCardSetCustom, rbEnglish, rbGerman;
	private HBox top, center, bottom, set1, set2, language, root;
	private VBox mainConfigurations, customCardSet, center2, cardSet;
	private ArrayList<CheckBox> customCards;
	
	public ConfigMenu_View(ConfigMenu_Model model, Stage primaryStage) {
		super(model, primaryStage);
		this.model = model;
	}
	
/**This view is divided into 3 vertical parts (root = VBox). The part on top contains a horizontal box
 * with a label called cardSet in its left part and a few more boxes (vertical and horizontal) for the 
 * optional card sets 'Standard', 'In Change' and 'Custom'.
 * 
 * The central part of the root element is made of a horizontal box with a label 'Language' in its left
 * part aswell. The right part of this hbox contains another hbox with the two language options 'German'
 * and 'English'.
 * 
 * The bottom part is made of one hbox with two buttons 'save' and 'cancel'.**/

	@Override
	public Scene getScene() {
		this.mainConfigurations = new VBox();
		this.top = new HBox();
		this.center = new HBox();
		this.center2 = new VBox();
		this.bottom = new HBox();
		this.cardSet = new VBox();
		this.set1 = new HBox();
		this.set2 = new HBox();
		this.language = new HBox();
		this.customCardSet = new VBox();
		this.root = new HBox();
		this.customCards = new ArrayList<CheckBox>();
		this.fillWithCards();
		
		this.tgCardSet = new ToggleGroup();
		this.tgLanguage= new ToggleGroup();
		
		//Initiate nodes for HBox top
		lblCardSet = new Label();
		rbCardSetStandard = new RadioButton();
		rbCardSetInChange = new RadioButton();
		rbCardSetCustom = new RadioButton();
		
		rbCardSetStandard.setToggleGroup(tgCardSet);
		rbCardSetInChange.setToggleGroup(tgCardSet);
		rbCardSetCustom.setToggleGroup(tgCardSet);
		
		//Initiate nodes for HBox center
		lblLanguage = new Label();
		rbGerman = new RadioButton();
		rbEnglish = new RadioButton();
		rbGerman.setToggleGroup(tgLanguage);
		rbEnglish.setToggleGroup(tgLanguage);
		
		//Initiate nodes for HBox bottom
		this.btnSaveConfig = new Button();
		this.btnCancel = new Button();
		this.btnChangePassword = new Button();
		this.currentPassword = new PasswordField();
		this.newPassword = new PasswordField();

		//Setting IDs for Stylesheet
		top.setId("hBox");
		center.setId("hBox");
		center2.setId("hBox");
		bottom.setId("hBoxConfigMenuBottom");
		cardSet.setId("vBox");
		customCardSet.setId("vBoxCustomCard");
		mainConfigurations.setId("vBoxMainConfig");
		root.setId("ConfigMenu");
		set1.setId("hBoxConfigMenu");
		set2.setId("hBoxConfigMenu");
		language.setId("hBoxConfigMenu");
		currentPassword.setId("txtField");
		newPassword.setId("txtField");
		
				
		//Get nodes for HBox top
		top.getChildren().add(0, lblCardSet);
		top.getChildren().add(1, cardSet);
		cardSet.getChildren().add(0, set1);
		cardSet.getChildren().add(1, set2);
		set1.getChildren().add(0,rbCardSetStandard);
		set1.getChildren().add(1, rbCardSetInChange);
		set2.getChildren().add(0, rbCardSetCustom);
		
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
		
		//Get nodes for VBox root
		mainConfigurations.getChildren().add(0, top);
		mainConfigurations.getChildren().add(1, center);
		mainConfigurations.getChildren().add(2, center2);
		mainConfigurations.getChildren().add(3, bottom);
		
		root.getChildren().add(mainConfigurations);
		Scene scene = new Scene(root);

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
	
	public RadioButton getRbCardSetCustom(){
		return rbCardSetCustom;
	}
	
	public ArrayList<CheckBox> getCustomCards() {
		return customCards;
	}
	
	public void settgCardSetStandard () {
		this.tgCardSet.selectToggle(rbCardSetStandard);
	}
	
	public void settgCardSetInChange () {
		this.tgCardSet.selectToggle(rbCardSetInChange);
	}

	public void settgCardSetCustom () {
		this.tgCardSet.selectToggle(rbCardSetCustom);
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
	
	public EnumCardSet getCardSet() {
		if (tgCardSet.getSelectedToggle() == rbCardSetStandard) {
			return EnumCardSet.Standard;
		}
		else if (tgCardSet.getSelectedToggle() == rbCardSetInChange) {
			return EnumCardSet.InChange;
		}
		else{
			return EnumCardSet.custom;
		}
	}
	
	public Button getBtnChangePassword() {
		return btnChangePassword;
	}
	
	public RadioButton getRbCardSetStandard() {
		return rbCardSetStandard;
	}

	public RadioButton getRbCardSetInChange() {
		return rbCardSetInChange;
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
			rbCardSetCustom.setText("Custom");
			
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
			rbCardSetCustom.setText("Benutzerdefiniert");
			
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
	
	//fills Checkbox List with possible cards
	private void fillWithCards() {
		customCards.add(new CheckBox("Abenteurer"));
		customCards.add(new CheckBox("Burggraben"));
		customCards.add(new CheckBox("Dieb"));
		customCards.add(new CheckBox("Dorf"));
		customCards.add(new CheckBox("Festmahl"));
		customCards.add(new CheckBox("Gaerten"));
		customCards.add(new CheckBox("Hexe"));
		customCards.add(new CheckBox("Holzfaeller"));
		customCards.add(new CheckBox("Jahrmarkt"));
		customCards.add(new CheckBox("Kapelle"));
		customCards.add(new CheckBox("Keller"));
		customCards.add(new CheckBox("Laboratorium"));
		customCards.add(new CheckBox("Markt"));
		customCards.add(new CheckBox("Miliz"));
		customCards.add(new CheckBox("Mine"));
		customCards.add(new CheckBox("Ratsversammlung"));
		customCards.add(new CheckBox("Schmiede"));
		customCards.add(new CheckBox("Spion"));
		customCards.add(new CheckBox("Umbau"));
		customCards.add(new CheckBox("Werkstatt"));
	}

	//shows menu for choosing cards for custom card set
	public void showCustomCardSet() {
		for(CheckBox cb:customCards){
			cb.getStyleClass().add("check-box");
			customCardSet.getChildren().add(cb);	
		}
		root.getChildren().add(customCardSet);
	}
	
	//removes menu for choosing cards for custom card set
	public void removeCustomCardSet(){
		this.customCardSet.getChildren().clear();
		this.root.getChildren().remove(customCardSet);
	}
	

}
