package com.bitbank.responses.v1;

public class PingResponse {

	private String version;

	public PingResponse(String version) {
		this.version = version;
	}

	public String getVersion() {
		return this.version;
	}

}
