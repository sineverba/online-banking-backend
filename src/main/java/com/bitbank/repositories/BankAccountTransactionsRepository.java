package com.bitbank.repositories;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.bitbank.entities.BankAccountTransactionsEntity;

@Repository
public interface BankAccountTransactionsRepository
		extends PagingAndSortingRepository<BankAccountTransactionsEntity, Long> {

	@Query("SELECT SUM(b.amount) FROM BankAccountTransactionsEntity b")
	BigDecimal balance();

}
