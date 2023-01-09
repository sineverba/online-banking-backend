package com.bitbank.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bitbank.entities.BankAccountTransactionsEntity;

public interface BankAccountTransactionsRepository
		extends PagingAndSortingRepository<BankAccountTransactionsEntity, Long>, CrudRepository<BankAccountTransactionsEntity, Long> {

	@Query("SELECT SUM(b.amount) FROM BankAccountTransactionsEntity b")
	BigDecimal balance();

}
