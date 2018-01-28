package DTO;

import java.io.Serializable;

public class UserIdentifier implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String email;
	private String password;
	
	public UserIdentifier() {}
	
	public UserIdentifier(String email, String password) {
		
		this.email = email;
		this.password = password;
		
	}

	public String getUserEmail() {
		return email;
	}

	public void setUserEmail(String userName) {
		this.email = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
