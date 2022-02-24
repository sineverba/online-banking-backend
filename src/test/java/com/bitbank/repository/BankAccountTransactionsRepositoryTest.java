package com.bitbank.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bitbank.entities.BankAccountTransactionsEntity;
import com.bitbank.repositories.BankAccountTransactionsRepository;

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
		var transaction01 = BankAccountTransactionsEntity.builder().amount(new BigDecimal(99.99))
				.purpose("First transaction").build();

		var transaction02 = BankAccountTransactionsEntity.builder().amount(new BigDecimal(150))
				.purpose("Second transaction").build();

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

	/**
	 * Balance
	 */
	@Test
	void testCanGetBalance() {

		List<BankAccountTransactionsEntity> list = new ArrayList<BankAccountTransactionsEntity>();
		List<BankAccountTransactionsEntity> result = new ArrayList<BankAccountTransactionsEntity>();

		list.add(validBankAccountTransactionsEntity(1L, new BigDecimal(100), "first"));
		list.add(validBankAccountTransactionsEntity(2L, new BigDecimal(200), "second"));
		list.add(validBankAccountTransactionsEntity(3L, new BigDecimal(-50), "third"));

		for (BankAccountTransactionsEntity entity : list) {
			bankAccountTransactionsRepository.save(entity);
			result.add(entity);
		}

		BigDecimal balance = new BigDecimal(250);

		assertThat(balance, Matchers.comparesEqualTo(bankAccountTransactionsRepository.balance()));

	}

	private static BankAccountTransactionsEntity validBankAccountTransactionsEntity(Long id, BigDecimal amount,
			String purpose) {
		return BankAccountTransactionsEntity.builder().id(id).amount(amount).purpose(purpose).build();
	}
}
