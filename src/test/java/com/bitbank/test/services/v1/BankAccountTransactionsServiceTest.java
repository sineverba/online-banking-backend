package com.bitbank.test.services.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bitbank.entities.v1.BankAccountTransactionsEntity;
import com.bitbank.repository.v1.BankAccountTransactionsRepository;
import com.bitbank.services.v1.BankAccountTransactionsService;

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
		
		var transaction01 = BankAccountTransactionsServiceTest.validBankAccountTransactionsEntity(1L, new BigDecimal(99.99), "First Transaction");
		var transaction02 = BankAccountTransactionsServiceTest.validBankAccountTransactionsEntity(2L, new BigDecimal(150), "Second Transaction");
		
		bankAccountTransactionsRepository.save(transaction01);
		bankAccountTransactionsRepository.save(transaction02);
		
		var result = new ArrayList<BankAccountTransactionsEntity>();
		result.add(transaction01);
		result.add(transaction02);
		
		when(bankAccountTransactionsRepository.findAll()).thenReturn(result);
		
		assertEquals(result, bankAccountTransactionsService.index());
		
	}
	
	/**
	 * Post section
	 */
	
	@Test
	void post_testCanSaveItem() {
		var transactionToSave = BankAccountTransactionsServiceTest.validBankAccountTransactionsEntity(new BigDecimal(99.99), "First Transaction");
		var savedTransaction = BankAccountTransactionsServiceTest.validBankAccountTransactionsEntity(1L, new BigDecimal(99.99), "First Transaction");
		
		when(bankAccountTransactionsRepository.save(transactionToSave)).thenReturn(savedTransaction);
		
		assertEquals(savedTransaction, bankAccountTransactionsService.post(transactionToSave));
	}
	
	/**
	 * Useful methods
	 * 
	 * @param id
	 * @param amount
	 * @param purpose
	 * @return
	 */
	
	private static BankAccountTransactionsEntity validBankAccountTransactionsEntity(Long id, BigDecimal amount, String purpose) {
		return BankAccountTransactionsEntity.
				builder()
				.id(id)
				.amount(amount)
				.purpose(purpose)
				.build();
	}
	 
	private static BankAccountTransactionsEntity validBankAccountTransactionsEntity(BigDecimal amount, String purpose) {
			return BankAccountTransactionsEntity.
					builder()
					.amount(amount)
					.purpose(purpose)
					.build();
	}
}
