package com.bitbank.dto;

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
