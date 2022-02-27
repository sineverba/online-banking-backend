package com.bitbank.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BankAccountTransactionsDTO {

	private Long id;

	@NotNull(message = "has to be present")
	private BigDecimal amount;

	@NotNull(message = "has to be present")
	@NotEmpty(message = "has to be present")
	private String purpose;

	private LocalDateTime transactionDate;
}
