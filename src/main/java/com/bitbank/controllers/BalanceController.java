package com.bitbank.controllers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitbank.responses.BalanceResponse;
import com.bitbank.services.BankAccountTransactionsService;

@RestController
@RequestMapping("/api/v1/balance")
public class BalanceController {

	@Autowired
	private BankAccountTransactionsService bankAccountTransactionsService;

	@GetMapping
	public ResponseEntity<BalanceResponse> show() {
		BigDecimal balance = bankAccountTransactionsService.balance();
		return ResponseEntity.ok(new BalanceResponse(balance));
	}
}
