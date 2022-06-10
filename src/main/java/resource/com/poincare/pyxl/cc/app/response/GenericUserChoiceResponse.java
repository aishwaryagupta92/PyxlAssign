package com.poincare.pyxl.cc.app.response;

import com.poincare.pyxl.cc.app.launcher.UserChoice;

public class GenericUserChoiceResponse {

	private Boolean success;
	private UserChoice userChoice;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public UserChoice getUserChoice() {
		return userChoice;
	}

	public void setUserChoice(UserChoice userChoice) {
		this.userChoice = userChoice;
	}

}
