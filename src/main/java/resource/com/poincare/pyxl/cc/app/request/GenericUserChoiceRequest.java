package com.poincare.pyxl.cc.app.request;

import com.poincare.pyxl.cc.app.launcher.Status;

public class GenericUserChoiceRequest {

	private Long userId;
	private Long tzId;
	private Status status;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTzId() {
		return tzId;
	}

	public void setTzId(Long tzId) {
		this.tzId = tzId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
