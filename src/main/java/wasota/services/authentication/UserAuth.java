package wasota.services.authentication;

public class UserAuth {
	
	private String user;
	
	private String pass;
	
	private String email;
	

	public UserAuth(String user, String pass, String email) {
		this.user = user;
		this.pass = pass;
		this.email = email;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}
