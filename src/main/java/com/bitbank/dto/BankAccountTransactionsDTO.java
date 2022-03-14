package com.bitbank.dto;

/**
 * Class to validate BankAccountTransactions data passed
 * from Controller.
 * 
 * Manages also conversion from DTO to entity (and vice-versa)
 * 
 */

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BankAccountTransactionsDTO {

	private Long id;

	private BigDecimal amount;

	private String purpose;

	private LocalDateTime transactionDate;
}
