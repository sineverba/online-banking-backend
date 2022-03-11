package com.bitbank.services;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bitbank.entities.BankAccountTransactionsEntity;
import com.bitbank.exceptions.BalanceNotEnoughException;
import com.bitbank.repositories.BankAccountTransactionsRepository;

@Service
public class BankAccountTransactionsService {

	/**
	 * Create our logger
	 */
	Logger logger = LoggerFactory.getLogger(BankAccountTransactionsService.class);

	@Autowired
	private BankAccountTransactionsRepository bankAccountTransactionsRepository;

	/**
	 * Index paginate
	 * 
	 * @return
	 */
	public Page<BankAccountTransactionsEntity> index(Integer page, Integer perPage, String sortBy, String orderWay) {

		/**
		 * Create the paging
		 */
		Pageable pageable = PageRequest.of(page, perPage,
				Sort.by(orderWay.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy));

		/**
		 * Fetch data
		 */
		return bankAccountTransactionsRepository.findAll(pageable);
	}

	/**
	 * Add or deduct balance.
	 * 
	 * @throws BalanceNotEnoughException
	 */
	public BankAccountTransactionsEntity post(BankAccountTransactionsEntity bankAccountTransactionsEntity)
			throws BalanceNotEnoughException {

		// Get current balance
		BigDecimal balance = this.balance();
		// Get amount
		BigDecimal amount = bankAccountTransactionsEntity.getAmount();
		// Arithmetical sum between balance and amount
		BigDecimal arithmeticalSum = balance.add(amount);

		// Compare data
		if (arithmeticalSum.compareTo(BigDecimal.ZERO) < 0) {
			/**
			 * "Preconditions" and logging arguments should not require evaluation
			 */
			logger.info("Cannot deduct {} from {}", amount, balance);
			throw new BalanceNotEnoughException("balance is not enough to deduct " + amount.toString());
		}

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
