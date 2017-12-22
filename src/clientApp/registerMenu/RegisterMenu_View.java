package registerMenu;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import templates.TemplateModel;
import templates.TemplateView;

/** @author Cyrill / Sven **/

public class RegisterMenu_View extends TemplateView {
	
	private Button btnRegister, btnCancel;
	private TextField txtUsername;
	private PasswordField txtPassword;
	
	public RegisterMenu_View(RegisterMenu_Model model, Stage primaryStage) {
		super(model, primaryStage);
		// TODO Auto-generated constructor stub
		}
	
	public Scene getScene() {
		VBox root = new VBox();
		Scene scene = new Scene(root);
		this.txtUsername = new TextField();
		this.txtPassword = new PasswordField();

		this.btnRegister = new Button("Register");
		this.btnCancel = new Button("Cancel");
		
		this.btnCancel.setId("menuButton");
		this.btnRegister.setId("menuButton");
		
		this.btnRegister.setDefaultButton(true);
		
		txtUsername.setId("txtField");
		txtPassword.setId("txtField");
		root.getStyleClass().add("login");
		
		txtUsername.setPromptText("Username");
		txtPassword.setPromptText("New Password");
	
		
		root.getChildren().add(txtUsername);
		root.getChildren().add(txtPassword);
		
		root.getChildren().add(btnRegister);
		root.getChildren().add(btnCancel);
		
		return scene;
	}

	
	public Button getBtnRegister() {
		return btnRegister;
	}

	public void setBtnRegister(Button btnRegister) {
		this.btnRegister = btnRegister;
	}

	public Button getBtnCancel() {
		return btnCancel;
	}

	public void setBtnCancel(Button btnCancel) {
		this.btnCancel = btnCancel;
	}

	public String getTxtUsername() {
		return this.txtUsername.getText();
	}
	
	public TextField getTxtUsernameField() {
		return this.txtUsername;
	}

	public void setTxtUsername(TextField txtUsername) {
		this.txtUsername = txtUsername;
	}

	public String getTxtPassword() {
		return this.txtPassword.getText();
	}
	
	public PasswordField getTxtPasswordField() {
		return this.txtPassword;
	}

	public void setTxtPassword(PasswordField txtPassword) {
		this.txtPassword = txtPassword;
	}

	
}


