package org.adligo.i.smtp.authenticators;

import org.adligo.i.smtp.I_SmtpCredentials;

public class SmtpUserPassword  implements I_SmtpCredentials {
	private String user;
	private String password;
	
	public SmtpUserPassword() {}
	
	public SmtpUserPassword(String user, String pass) {
		this.user = user;
		this.password = pass;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
