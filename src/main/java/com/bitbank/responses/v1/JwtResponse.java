package com.bitbank.responses.v1;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtResponse {

	@JsonProperty("access_token")
	private String accessToken;

	public JwtResponse(String token) {
		this.accessToken = token;
	}

	public String getAccessToken() {
		return this.accessToken;
	}

}
