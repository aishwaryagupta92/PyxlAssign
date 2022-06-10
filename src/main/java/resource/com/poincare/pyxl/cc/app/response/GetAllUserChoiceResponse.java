package com.poincare.pyxl.cc.app.response;

import java.util.List;

import com.poincare.pyxl.cc.app.launcher.UserChoice;

public class GetAllUserChoiceResponse {

	private Boolean success;
	private List<UserChoice> userChoices;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public List<UserChoice> getUserChoices() {
		return userChoices;
	}

	public void setUserChoices(List<UserChoice> userChoices) {
		this.userChoices = userChoices;
	}

}
