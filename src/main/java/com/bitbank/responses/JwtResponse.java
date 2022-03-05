package com.bitbank.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtResponse {

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("expiry_at")
	private String expiryAt;

	public JwtResponse(String token, String expiryAt) {
		this.setAccessToken(token);
		this.setExpiryAt(expiryAt);
	}

	private void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	private void setExpiryAt(String expiryAt) {
		this.expiryAt = expiryAt;
	}

	public String getAccessToken() {
		return this.accessToken;
	}

	public String getExpiryAt() {
		return this.expiryAt;
	}

}
