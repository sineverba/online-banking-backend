package com.bitbank.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtResponse {

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("expiry_at")
	private String expiryAt;

	private List<String> roles;

	public JwtResponse(String token, String expiryAt, List<String> roles) {
		this.setAccessToken(token);
		this.setExpiryAt(expiryAt);
		this.setRoles(roles);
	}

	private void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	private void setExpiryAt(String expiryAt) {
		this.expiryAt = expiryAt;
	}

	private void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getAccessToken() {
		return this.accessToken;
	}

	public String getExpiryAt() {
		return this.expiryAt;
	}

	public List<String> getRoles() {
		return this.roles;
	}

}
