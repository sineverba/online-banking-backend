package com.bitbank.responses;

/**
 * Generic class for ResponseEntity
 * 
 * @author sineverba
 *
 */
public class MessageResponse {

	private String status;
	private String message;

	public MessageResponse(String status, String message) {
		this.setStatus(status);
		this.setMessage(message);
	}

	public String getStatus() {
		return status;
	}

	private void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	private void setMessage(String message) {
		this.message = message;
	}

}
