package com.bitbank.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bitbank.dto.BankAccountTransactionsDTO;
import com.bitbank.entities.BankAccountTransactionsEntity;
import com.bitbank.services.BankAccountTransactionsService;

@RestController
@RequestMapping("/api/v1/bank-account-transactions")
public class BankAccountTransactionsController {
	
	@Autowired
	private BankAccountTransactionsService bankAccountTransactionsService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	public BankAccountTransactionsController(BankAccountTransactionsService bankAccountTransactionsService) {
		this.bankAccountTransactionsService = bankAccountTransactionsService;
	}

	@GetMapping
	public List<BankAccountTransactionsDTO> index() {
		List<BankAccountTransactionsEntity> transactions = bankAccountTransactionsService.index();
		return transactions.stream().map(this::convertToDto).toList();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BankAccountTransactionsDTO post(@RequestBody BankAccountTransactionsDTO bankAccountTransactionsDTO) {
		BankAccountTransactionsEntity bankAccountTransactionsEntity = convertToEntity(bankAccountTransactionsDTO);
		BankAccountTransactionsEntity savedBankAccountTransactionsEntity = bankAccountTransactionsService.post(bankAccountTransactionsEntity);
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
