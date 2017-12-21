package login;

public class Login {
	
private String Username, Password;
	
	public Login(String username, String pw){
		this.Username = username;
		this.Password = pw;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

}
