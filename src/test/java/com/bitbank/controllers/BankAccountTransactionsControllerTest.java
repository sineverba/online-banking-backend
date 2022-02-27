package com.bitbank.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.bitbank.config.AuthEntryPointJwt;
import com.bitbank.config.AuthTokenFilter;
import com.bitbank.entities.BankAccountTransactionsEntity;
import com.bitbank.services.BankAccountTransactionsService;
import com.bitbank.services.UserDetailsServiceImpl;
import com.bitbank.utils.JwtUtils;
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

	@Autowired
	AuthTokenFilter authTokenFilter;

	@MockBean
	JwtUtils jwtUtils;

	@MockBean
	UserDetailsServiceImpl userDetailsServiceImpl;

	@MockBean
	AuthEntryPointJwt authEntryPointJwt;

	@MockBean
	Pageable pageableMock;

	/**
	 * index
	 */
	@WithMockUser("username")
	@Test
	void testCanIndex() throws Exception {

		@SuppressWarnings("unchecked")
		Page<BankAccountTransactionsEntity> items = mock(Page.class);
		when(bankAccountTransactionsService.index(0, 1, "id", "desc")).thenReturn(items);
		mvc.perform(get("/api/v1/bank-account-transactions/")).andExpect(status().isOk());
	}

	@WithMockUser("username")
	@Test
	void testCanSave() throws Exception {
		var id = 1L;
		var transactionToSave = validBankAccountTransactionsEntity(new BigDecimal(99.99), "First Transaction");
		var savedTransaction = validBankAccountTransactionsEntity(id, new BigDecimal(99.99), "First Transaction");

		when(bankAccountTransactionsService.post(transactionToSave)).thenReturn(savedTransaction);

		mvc.perform(post("/api/v1/bank-account-transactions/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(transactionToSave))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is((int) id)))
				.andExpect(jsonPath("$.amount", is(transactionToSave.getAmount())));
	}

	@WithMockUser("username")
	@ParameterizedTest
	@MethodSource("getInvalidBankAccountTransactions")
	void testCanCatchException(BankAccountTransactionsEntity invalidBankAccountTransactionsEntity) throws Exception {

		mvc.perform(post("/api/v1/bank-account-transactions/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(invalidBankAccountTransactionsEntity)))
				.andExpect(status().isBadRequest());

	}
	
	private static Stream<BankAccountTransactionsEntity> getInvalidBankAccountTransactions() {
		// Create a valid entity
		var validBankAccountTransactionsEntity = validBankAccountTransactionsEntity(new BigDecimal(100), "ok");
		
		return Stream.of(
				validBankAccountTransactionsEntity.toBuilder().amount(null).build(),
				validBankAccountTransactionsEntity.toBuilder().purpose(null).build(),
				validBankAccountTransactionsEntity.toBuilder().purpose("").build()
				);
	}

	private static BankAccountTransactionsEntity validBankAccountTransactionsEntity(Long id, BigDecimal amount,
			String purpose) {
		return BankAccountTransactionsEntity.builder().id(id).amount(amount).purpose(purpose).build();
	}

	private static BankAccountTransactionsEntity validBankAccountTransactionsEntity(BigDecimal amount, String purpose) {
		return BankAccountTransactionsEntity.builder().amount(amount).purpose(purpose).build();
	}
}
