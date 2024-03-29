package com.bitbank.controllers;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bitbank.dto.BankAccountTransactionsDTO;
import com.bitbank.dto.BankAccountTransactionsPostDTO;
import com.bitbank.entities.BankAccountTransactionsEntity;
import com.bitbank.exceptions.BalanceNotEnoughException;
import com.bitbank.services.BankAccountTransactionsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/bank-account-transactions")
@Tag(name = "Transactions", description = "Bank Account Transactions")
public class BankAccountTransactionsController {

	@Autowired
	private BankAccountTransactionsService bankAccountTransactionsService;

	@Autowired
	private ModelMapper modelMapper;

	public BankAccountTransactionsController(BankAccountTransactionsService bankAccountTransactionsService) {
		this.bankAccountTransactionsService = bankAccountTransactionsService;
	}

	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	@GetMapping
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<Page<BankAccountTransactionsEntity>> index(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer perPage,
			@RequestParam(defaultValue = "transactionDate") String orderBy,
			@RequestParam(defaultValue = "asc") String orderWay) {

		return new ResponseEntity<>(bankAccountTransactionsService.index(page, perPage, orderBy, orderWay),
				new HttpHeaders(), HttpStatus.OK);

	}

	/**
	 * Show. Single transaction
	 */
	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<Optional<BankAccountTransactionsEntity>> show(@PathVariable Long id) {
		return new ResponseEntity<>(bankAccountTransactionsService.show(id), new HttpHeaders(), HttpStatus.OK);
	}

	/**
	 * Add or deduct an amount.
	 * 
	 * If amount is positive, we ADD to the balance. If amount is negative, we
	 * DEDUCT from the balance.
	 * 
	 * @param bankAccountTransactionsDTO
	 * @return
	 * @throws BalanceNotEnoughException
	 */
	@Operation(security = { @SecurityRequirement(name = "bearer-key") })
	@PostMapping
	@PreAuthorize("hasRole('CUSTOMER')")
	@ResponseStatus(HttpStatus.CREATED)
	public BankAccountTransactionsDTO post(
			@Valid @RequestBody BankAccountTransactionsPostDTO bankAccountTransactionsDTO)
			throws BalanceNotEnoughException {
		BankAccountTransactionsEntity bankAccountTransactionsEntity = convertToEntity(bankAccountTransactionsDTO);
		BankAccountTransactionsEntity savedBankAccountTransactionsEntity = bankAccountTransactionsService
				.post(bankAccountTransactionsEntity);
		return convertToDto(savedBankAccountTransactionsEntity);
	}

	/**
	 * Convert the Entity to the DTO
	 * 
	 * Why is this necessary?
	 * 
	 * See Sonar S4684
	 * 
	 * "Replace this persistent entity with a simple POJO or DTO object."
	 * "Persistent entities should not be used as arguments of "@RequestMapping"
	 * methods"
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
	private BankAccountTransactionsEntity convertToEntity(BankAccountTransactionsPostDTO bankAccountTransactionsDTO) {
		return modelMapper.map(bankAccountTransactionsDTO, BankAccountTransactionsEntity.class);
	}
}
