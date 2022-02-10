package com.bitbank.test.repository.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bitbank.entities.v1.BankAccountTransactionsEntity;
import com.bitbank.repository.v1.BankAccountTransactionsRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class BankAccountTransactionsRepositoryTest {
	
	@Autowired
	private BankAccountTransactionsRepository bankAccountTransactionsRepository;
	
	/**
	 * Index
	 */
	@Test
	void testCanFindAll() {
		var transaction01 = BankAccountTransactionsEntity
				.builder()
				.amount(new BigDecimal(99.99))
				.purpose("First transaction")
				.build();
		
		var transaction02 = BankAccountTransactionsEntity
				.builder()
				.amount(new BigDecimal(150))
				.purpose("Second transaction")
				.build();
		
		bankAccountTransactionsRepository.save(transaction01);
		bankAccountTransactionsRepository.save(transaction02);
		
		var result = new ArrayList<BankAccountTransactionsEntity>();
		result.add(transaction01);
		result.add(transaction02);
		
		assertEquals(result, bankAccountTransactionsRepository.findAll());
	}
	
	@Test
	void testCanFindAllWithoutItems() {
		var result = new ArrayList<BankAccountTransactionsEntity>();
		assertEquals(result, bankAccountTransactionsRepository.findAll());
	}
}
