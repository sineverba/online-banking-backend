package com.bitbank.exceptions;

/**
 * This exception covers the case when MFA code is not valid
 * 
 * @author sineverba
 *
 */
public class InvalidMfaException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -20230615121200L;

	public InvalidMfaException(String errorMessage) {
		super(errorMessage);
	}
}
