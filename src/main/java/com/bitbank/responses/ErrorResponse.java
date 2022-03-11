package com.bitbank.responses;

/**
 * Generic class for Error Response.
 * 
 * The format will be:
 * 
 * { "error": your_custom_message }
 * 
 * @author sineverba
 *
 */

public class ErrorResponse {

	private String error;

	private void setError(String error) {
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public ErrorResponse(String error) {
		this.setError(error);
	}
}
