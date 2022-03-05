package com.bitbank.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtResponse {

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("expiry_at")
	private String expiryAt;

	public JwtResponse(String token) {
		this.accessToken = token;
	}

	public JwtResponse(String token, String expiryAt) {
		this.accessToken = token;
		this.expiryAt = expiryAt;
	}

	public String getAccessToken() {
		return this.accessToken;
	}

	public String getExpiryAt() {
		return this.expiryAt;
	}

}
