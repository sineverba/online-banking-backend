package com.bitbank.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MfaResponse {

	@JsonProperty("castle_id")
	private String tempSecret;

	public MfaResponse(String tempSecret) {
		this.setTempSecret(tempSecret);
	}

	private void setTempSecret(String tempSecret) {
		this.tempSecret = tempSecret;
	}

	public String getTempSecret() {
		return this.tempSecret;
	}
}
