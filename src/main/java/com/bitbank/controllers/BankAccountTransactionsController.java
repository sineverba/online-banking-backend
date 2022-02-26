package com.bitbank.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bitbank.dto.BankAccountTransactionsDTO;
import com.bitbank.entities.BankAccountTransactionsEntity;
import com.bitbank.services.BankAccountTransactionsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/bank-account-transactions")
@Tag(name = "Transactions", description = "Bank Account Transactions")
public class BankAccountTransactionsController {

	@Autowired
	private BankAccountTransactionsService bankAccountTransactionsService;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	public BankAccountTransactionsController(BankAccountTransactionsService bankAccountTransactionsService) {
		this.bankAccountTransactionsService = bankAccountTransactionsService;
	}

	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	@GetMapping
	public ResponseEntity<Page<BankAccountTransactionsEntity>> index(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer perPage,
			@RequestParam(defaultValue = "transactionDate") String orderBy,
			@RequestParam(defaultValue = "asc") String orderWay) {

		return new ResponseEntity<>(bankAccountTransactionsService.index(page, perPage, orderBy, orderWay),
				new HttpHeaders(), HttpStatus.OK);

	}

	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BankAccountTransactionsDTO post(@RequestBody BankAccountTransactionsDTO bankAccountTransactionsDTO) {
		BankAccountTransactionsEntity bankAccountTransactionsEntity = convertToEntity(bankAccountTransactionsDTO);
		BankAccountTransactionsEntity savedBankAccountTransactionsEntity = bankAccountTransactionsService
				.post(bankAccountTransactionsEntity);
		return convertToDto(savedBankAccountTransactionsEntity);
	}

	/**
	 * Convert the Entity to the DTO
	 * 
	 * @param bankAccountTransactionsEntity
	 * @return
	 */
	private BankAccountTransactionsDTO convertToDto(BankAccountTransactionsEntity bankAccountTransactionsEntity) {
		return modelMapper.map(bankAccountTransactionsEntity, BankAccountTransactionsDTO.class);
	}

	/**
	 * Convert the DTO to the entity
	 * 
	 * @param bankAccountTransactionsEntity
	 * @return
	 */
	private BankAccountTransactionsEntity convertToEntity(BankAccountTransactionsDTO bankAccountTransactionsDTO) {
		return modelMapper.map(bankAccountTransactionsDTO, BankAccountTransactionsEntity.class);
	}
}
