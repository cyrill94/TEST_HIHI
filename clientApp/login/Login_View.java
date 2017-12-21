/**@author Sven Cueni**/
package login;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import templates.TemplateModel;
import templates.TemplateView;

public class Login_View extends TemplateView{
	
	private Button btnLogin, btnCancel, btnRegister;
	private TextField tfUsername;
	private PasswordField tfPassword;

	public Login_View(TemplateModel model, Stage primaryStage) {
		super(model, primaryStage);
		// TODO Auto-generated constructor stub
	}

	public Scene getScene() {
		VBox root = new VBox();
		Scene scene = new Scene(root);
		TextField tfUsername = new TextField();
		PasswordField tfPassword = new PasswordField();
		//Button register = new Button("Registrieren");
		Button login = new Button("Login");
		Button cancel = new Button("Cancel");
		Button btnRegister = new Button("Register");
		
		//this.btnRegister = register;
		this.btnLogin = login;
		this.btnCancel = cancel;
		this.btnRegister = btnRegister;
		
		//this.tfUsername / tfPassword = register;
		this.tfUsername = tfUsername;
		this.tfPassword = tfPassword;
		
		login.setDefaultButton(true);
		
		login.setId("menuButton");
		cancel.setId("menuButton");
		btnRegister.setId("menuButton");
		tfUsername.setId("txtField");
		tfPassword.setId("txtField");
		root.getStyleClass().add("login");
		
		tfUsername.setPromptText("Username");
		tfPassword.setPromptText("Password");
	
		root.getChildren().add(tfUsername);
		root.getChildren().add(tfPassword);
		root.getChildren().add(login);
		root.getChildren().add(cancel);
		root.getChildren().add(btnRegister);
		
		return scene;
	}

	public Button getBtnRegister() {
		return btnRegister;
	}

	public void setBtnRegister(Button btnRegister) {
		this.btnRegister = btnRegister;
	}

	public TextField getTfUsername() {
		return tfUsername;
	}

	public void setTfUsername(TextField tfUsername) {
		this.tfUsername = tfUsername;
	}

	public TextField getTfPassword() {
		return tfPassword;
	}

	public void setTfPassword(PasswordField tfPassword) {
		this.tfPassword = tfPassword;
	}

	public Button getBtnLogin() {
		return btnLogin;
	}

	public void setBtnLogin(Button btnLogin) {
		this.btnLogin = btnLogin;
	}

	public Button getBtnCancel() {
		return btnCancel;
	}

	public void setBtnCancel(Button btnCancel) {
		this.btnCancel = btnCancel;
	}
	
	public String getUsername() {
		return tfUsername.getText();
	}
	
	public String getPassword() {
		return tfPassword.getText();
	} 

}
