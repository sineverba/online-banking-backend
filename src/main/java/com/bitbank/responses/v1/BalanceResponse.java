package com.bitbank.responses.v1;

import java.math.BigDecimal;

/**
 * ResponseEntity for balance
 * 
 * @author sineverba
 *
 */
public class BalanceResponse {

	private BigDecimal balance;

	public BalanceResponse(BigDecimal balance) {
		this.setBalance(balance);
	}

	public BigDecimal getBalance() {
		return this.balance;
	}

	private void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
