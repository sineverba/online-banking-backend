package com.bitbank.repositories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

import com.bitbank.constants.ERole;
import com.bitbank.entities.BankAccountTransactionsEntity;
import com.bitbank.entities.RolesEntity;

@DataJpaTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource("classpath:application.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
	
	/**
	 * Show - can get single transaction
	 */
	@Test
	void testCanFindSingleTransaction() {
		// 1. Create an item
		BankAccountTransactionsEntity bankAccountTransactionsEntity = validBankAccountTransactionsEntity(1L, new BigDecimal(100), "first");
		// 2. Save the item
		bankAccountTransactionsRepository.save(bankAccountTransactionsEntity);
		// 3 - Create an optional result
		Optional<BankAccountTransactionsEntity> result = Optional.of(bankAccountTransactionsEntity);
		// 4 - Get the result
		result = bankAccountTransactionsRepository.findById(1L);
		// 5 - Test
		assertEquals(result.get().getAmount(), new BigDecimal(100));
	}

	private static BankAccountTransactionsEntity validBankAccountTransactionsEntity(Long id, BigDecimal amount,
			String purpose) {
		return BankAccountTransactionsEntity.builder().id(id).amount(amount).purpose(purpose).build();
	}
}
