package com.poincare.pyxl.cc.app.response;

import com.poincare.pyxl.cc.app.launcher.PyxlTimeZone;

public class ToggleTZResponse {

	private Boolean success;
	private PyxlTimeZone pyxlTimeZone;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public PyxlTimeZone getPyxlTimeZone() {
		return pyxlTimeZone;
	}

	public void setPyxlTimeZone(PyxlTimeZone pyxlTimeZone) {
		this.pyxlTimeZone = pyxlTimeZone;
	}

}
