package com.bitbank.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MfaResponse {

	@JsonProperty("id")
	private String id;

	public MfaResponse(String id) {
		this.setId(id);
	}

	private void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}
}
