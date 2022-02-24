package com.bitbank.responses;

public class PingResponse {

	private String version;

	public PingResponse(String version) {
		this.version = version;
	}

	public String getVersion() {
		return this.version;
	}

}
