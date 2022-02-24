package com.bitbank.repositories.v1;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bitbank.entities.v1.BankAccountTransactionsEntity;

@Repository
public interface BankAccountTransactionsRepository extends CrudRepository<BankAccountTransactionsEntity, Long> {

	@Query("SELECT SUM(b.amount) FROM BankAccountTransactionsEntity b")
	BigDecimal balance();

}
