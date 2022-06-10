package com.poincare.pyxl.cc.app.request;

import java.util.List;

import com.poincare.pyxl.cc.app.launcher.UserChoice;

public class ToggleTZRequest {

	private String zoneId;
	private String city;
	private List<UserChoice> userChoices;

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<UserChoice> getUserChoices() {
		return userChoices;
	}

	public void setUserChoices(List<UserChoice> userChoices) {
		this.userChoices = userChoices;
	}

}
