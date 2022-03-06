package com.bitbank.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.bitbank.entities.BankAccountTransactionsEntity;
import com.bitbank.exceptions.BalanceNotEnoughException;
import com.bitbank.repositories.BankAccountTransactionsRepository;

@ExtendWith(MockitoExtension.class)
class BankAccountTransactionsServiceTest {
	@Mock
	private BankAccountTransactionsRepository bankAccountTransactionsRepository;

	@InjectMocks
	private BankAccountTransactionsService bankAccountTransactionsService;

	/**
	 * Index
	 */

	@Test
	void get_testCanIndex() {

		var transaction01 = BankAccountTransactionsServiceTest.validBankAccountTransactionsEntity(1L,
				new BigDecimal(99.99), "First Transaction");
		var transaction02 = BankAccountTransactionsServiceTest.validBankAccountTransactionsEntity(2L,
				new BigDecimal(150), "Second Transaction");

		bankAccountTransactionsRepository.save(transaction01);
		bankAccountTransactionsRepository.save(transaction02);

		var result = new ArrayList<BankAccountTransactionsEntity>();
		result.add(transaction01);
		result.add(transaction02);

		@SuppressWarnings("unchecked")
		Page<BankAccountTransactionsEntity> items = mock(Page.class);
		Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC, "id"));
		when(bankAccountTransactionsRepository.findAll(pageable)).thenReturn(items);
		assertEquals(0, bankAccountTransactionsService.index(0, 1, "id", "asc").getNumberOfElements());

		// Test by desc
		pageable = PageRequest.of(0, 1, Sort.by(Sort.Direction.DESC, "id"));
		when(bankAccountTransactionsRepository.findAll(pageable)).thenReturn(items);
		assertEquals(0, bankAccountTransactionsService.index(0, 1, "id", "desc").getNumberOfElements());

	}

	/**
	 * Post section. Test can add or deduct
	 * 
	 * @throws BalanceNotEnoughException
	 */

	@Test
	void post_testCanSaveItem() throws BalanceNotEnoughException {
		var transactionToSave = BankAccountTransactionsServiceTest
				.validBankAccountTransactionsEntity(new BigDecimal(99.99), "First Transaction");
		var savedTransaction = BankAccountTransactionsServiceTest.validBankAccountTransactionsEntity(1L,
				new BigDecimal(99.99), "First Transaction");

		when(bankAccountTransactionsRepository.balance()).thenReturn(new BigDecimal(1000));
		when(bankAccountTransactionsRepository.save(transactionToSave)).thenReturn(savedTransaction);

		assertEquals(savedTransaction, bankAccountTransactionsService.post(transactionToSave));
	}

	/**
	 * Balance section
	 */

	@Test
	void testCanGetBalance() {

		BigDecimal balance = new BigDecimal(15000);

		when(bankAccountTransactionsRepository.balance()).thenReturn(new BigDecimal(15000));

		assertThat(balance, Matchers.comparesEqualTo(bankAccountTransactionsService.balance()));
	}

	/**
	 * Post section. Test can throw BalanceNotEnoughException
	 * 
	 * @throws BalanceNotEnoughException
	 */

	@Test
	void testCanThrowBalanceNotEnoughException() {

		// Create a valid transaction
		var transactionToSave = BankAccountTransactionsServiceTest
				.validBankAccountTransactionsEntity(new BigDecimal(-100), "Test Transaction");

		// Mock the balance
		when(bankAccountTransactionsRepository.balance()).thenReturn(new BigDecimal(10));

		// Throw the exception
		BalanceNotEnoughException balanceNotEnoughException = assertThrows(BalanceNotEnoughException.class, () -> {
			bankAccountTransactionsService.post(transactionToSave);
		});

		// Compare string
		String expectedMessage = "balance is not enough to deduct -100";
		String actualMessage = balanceNotEnoughException.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	/**
	 * Useful methods
	 * 
	 * @param id
	 * @param amount
	 * @param purpose
	 * @return
	 */

	private static BankAccountTransactionsEntity validBankAccountTransactionsEntity(Long id, BigDecimal amount,
			String purpose) {
		return BankAccountTransactionsEntity.builder().id(id).amount(amount).purpose(purpose).build();
	}

	private static BankAccountTransactionsEntity validBankAccountTransactionsEntity(BigDecimal amount, String purpose) {
		return BankAccountTransactionsEntity.builder().amount(amount).purpose(purpose).build();
	}
}
