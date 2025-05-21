package com.usermanagement.dto;

public class JWTRequest {
	
	private String usermail;
	private String password;
	
	public String getUsermail() {
		return usermail;
	}
	public String getPassword() {
		return password;
	}
	
	public void setUsermail(String usermail) {
		this.usermail=usermail;
	}
	public void setPassword(String password) {
		this.password=password;
	}

}
