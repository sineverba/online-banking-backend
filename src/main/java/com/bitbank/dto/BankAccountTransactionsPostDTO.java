package com.bitbank.dto;

/**
 * Class to validate BankAccountTransactions data passed
 * from Controller.
 * 
 * Valid only when submit, not other.
 * 
 */

import java.math.BigDecimal;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BankAccountTransactionsPostDTO {

	@NotNull(message = "has to be present")
	private BigDecimal amount;

	@NotNull(message = "has to be present")
	@NotEmpty(message = "has to be present")
	private String purpose;
}
