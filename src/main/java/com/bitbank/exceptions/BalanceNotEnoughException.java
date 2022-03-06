package com.bitbank.exceptions;

/**
 * This exception covers the case when balance is not enough.
 * 
 * E.g. if we pass an add with an amount greater then current balance.
 * 
 * @author sineverba
 *
 */
public class BalanceNotEnoughException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6911201285134153488L;

	public BalanceNotEnoughException(String errorMessage) {
		super(errorMessage);
	}
}
