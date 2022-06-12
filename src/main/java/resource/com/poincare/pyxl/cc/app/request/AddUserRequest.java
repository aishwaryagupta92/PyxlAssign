package com.poincare.pyxl.cc.app.request;

import com.poincare.pyxl.cc.app.launcher.Role;

public class AddUserRequest {

	private String emailId;
	private String password;
	private Role role;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
