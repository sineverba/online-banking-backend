package com.bitbank.responses.v1;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -20220212081300L;

	private final String jwtToken;

	public JwtResponse(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public String getToken() {
		return this.jwtToken;
	}

}
