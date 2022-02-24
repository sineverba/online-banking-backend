package com.bitbank.services.v1;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitbank.entities.v1.BankAccountTransactionsEntity;
import com.bitbank.repositories.v1.BankAccountTransactionsRepository;

@Service
public class BankAccountTransactionsService {

	@Autowired
	private BankAccountTransactionsRepository bankAccountTransactionsRepository;

	/**
	 * Index
	 * 
	 * @return
	 */
	public List<BankAccountTransactionsEntity> index() {
		Iterable<BankAccountTransactionsEntity> items = bankAccountTransactionsRepository.findAll();
		ArrayList<BankAccountTransactionsEntity> bankAccountTransactions = new ArrayList<>();

		for (BankAccountTransactionsEntity transaction : items) {
			bankAccountTransactions.add(transaction);
		}

		return bankAccountTransactions;
	}

	/**
	 * Post
	 */
	public BankAccountTransactionsEntity post(BankAccountTransactionsEntity bankAccountTransactionsEntity) {
		return bankAccountTransactionsRepository.save(bankAccountTransactionsEntity);
	}

	/**
	 * Return balance
	 * 
	 * @return
	 */
	public BigDecimal balance() {
		return bankAccountTransactionsRepository.balance();
	}
}
