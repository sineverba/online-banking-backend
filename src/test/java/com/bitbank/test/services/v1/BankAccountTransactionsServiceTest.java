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
	
	@Test
	void get_testCanIndex() {
		
		var transaction01 = BankAccountTransactionsEntity
				.builder()
				.id(1L)
				.amount(new BigDecimal(99.99))
				.purpose("First transaction")
				.build();
		
		var transaction02 = BankAccountTransactionsEntity
				.builder()
				.id(2L)
				.amount(new BigDecimal(150))
				.purpose("Second transaction")
				.build();
		
		bankAccountTransactionsRepository.save(transaction01);
		bankAccountTransactionsRepository.save(transaction02);
		
		var result = new ArrayList<BankAccountTransactionsEntity>();
		result.add(transaction01);
		result.add(transaction02);
		
		when(bankAccountTransactionsRepository.findAll()).thenReturn(result);
		
		assertEquals(result, bankAccountTransactionsService.index());
		
	}
}
