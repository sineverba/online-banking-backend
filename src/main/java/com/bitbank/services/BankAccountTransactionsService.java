package com.bitbank.services;

import java.math.BigDecimal;

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
		BigDecimal balance = this.balance();

		if (balance.compareTo(bankAccountTransactionsEntity.getAmount()) < 0) {
			throw new BalanceNotEnoughException("balance is not enough");
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
