package com.bitbank.responses;

import java.util.List;

public class MeResponse {

	private String username;

	private List<String> roles;

	public MeResponse(String username, List<String> roles) {
		this.setUsername(username);
		this.setRoles(roles);
	}

	private void setUsername(String username) {
		this.username = username;
	}

	private void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getUsername() {
		return this.username;
	}

	public List<String> getRoles() {
		return this.roles;
	}

}
