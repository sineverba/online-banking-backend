package com.bitbank.test.controllers.v1;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.bitbank.controllers.v1.BankAccountTransactionsController;
import com.bitbank.entities.v1.BankAccountTransactionsEntity;
import com.bitbank.services.v1.BankAccountTransactionsService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BankAccountTransactionsController.class)
class BankAccountTransactionsControllerTest {
	
	@Autowired
    private MockMvc mvc;

    @MockBean
    private BankAccountTransactionsService bankAccountTransactionsService;
    
    /**
     * index
     */
    @Test
    void testCanIndex() throws Exception {
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
		
		var result = new ArrayList<BankAccountTransactionsEntity>();
		result.add(transaction01);
		result.add(transaction02);
		
		when(bankAccountTransactionsService.index()).thenReturn(result);
		
		mvc.perform(get("/api/v1/bank-account-transactions/"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.length()", is(2)))
		.andExpect(jsonPath("$[0].id", is(1)))
		.andExpect(jsonPath("$[1].id", is(2)))
		.andExpect(jsonPath("$[0].amount", is(99.99)))
		.andExpect(jsonPath("$[1].amount", is(150.0)))
		.andExpect(jsonPath("$[0].purpose", is("First transaction")))
		.andExpect(jsonPath("$[1].purpose", is("Second transaction")))
		.andExpect(jsonPath("$[0]", Matchers.hasKey("transactionDate")))
		.andExpect(jsonPath("$[1]", Matchers.hasKey("transactionDate")));
    }
    
}
