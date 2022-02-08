package com.bitbank.test.controllers.v1;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.bitbank.controllers.v1.BankAccountTransactionsController;
import com.bitbank.entities.v1.BankAccountTransactionsEntity;
import com.bitbank.services.v1.BankAccountTransactionsService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BankAccountTransactionsController.class)
class BankAccountTransactionsControllerTest {
	
	@Autowired
    private MockMvc mvc;
	
	@MockBean
    private BankAccountTransactionsService bankAccountTransactionsService;
	
	@Autowired
    private ObjectMapper objectMapper;
    
    /**
     * index
     */
    @Test
    void testCanIndex() throws Exception {
    	var transaction01 = BankAccountTransactionsControllerTest.validBankAccountTransactionsEntity(1L, new BigDecimal(99.99), "First Transaction");
    	var transaction02 = BankAccountTransactionsControllerTest.validBankAccountTransactionsEntity(2L, new BigDecimal(150), "Second Transaction");
		
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
		.andExpect(jsonPath("$[0].purpose", is("First Transaction")))
		.andExpect(jsonPath("$[1].purpose", is("Second Transaction")))
		.andExpect(jsonPath("$[0]", Matchers.hasKey("transactionDate")))
		.andExpect(jsonPath("$[1]", Matchers.hasKey("transactionDate")));
    }
    
    @Test
    void testCanSave() throws Exception {
    	var id = 1L;
    	var transactionToSave = validBankAccountTransactionsEntity(new BigDecimal(99.99), "First Transaction");
    	var savedTransaction = validBankAccountTransactionsEntity(id, new BigDecimal(99.99), "First Transaction");
    	
    	when(bankAccountTransactionsService.post(transactionToSave))
    	.thenReturn(savedTransaction);
    	
    	mvc.perform(
    			post("/api/v1/bank-account-transactions/")
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(objectMapper.writeValueAsBytes(transactionToSave))
    			)
    	.andExpect(status().isCreated())
    	.andExpect(jsonPath("$.id", is((int) id)))
    	.andExpect(jsonPath("$.amount", is(transactionToSave.getAmount())));
    }
    
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
