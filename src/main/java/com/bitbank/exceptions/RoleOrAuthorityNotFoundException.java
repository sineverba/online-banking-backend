package com.bitbank.exceptions;

/**
 * This exception covers the case when role or authority are not found.
 * 
 * 
 * @author sineverba
 *
 */
public class RoleOrAuthorityNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -20220319092300L;

	public RoleOrAuthorityNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
