package com.bitbank.controllers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitbank.responses.BalanceResponse;
import com.bitbank.services.BankAccountTransactionsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/balance")
@Tag(name = "Balance", description = "Balance")
public class BalanceController {

	@Autowired
	private BankAccountTransactionsService bankAccountTransactionsService;

	@GetMapping
	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	public ResponseEntity<BalanceResponse> show() {
		BigDecimal balance = bankAccountTransactionsService.balance();
		return ResponseEntity.ok(new BalanceResponse(balance));
	}
}
