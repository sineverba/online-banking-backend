package com.bitbank.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;

import com.bitbank.config.AuthEntryPointJwt;
import com.bitbank.config.AuthTokenFilter;
import com.bitbank.entities.BankAccountTransactionsEntity;
import com.bitbank.exceptions.BalanceNotEnoughException;
import com.bitbank.services.BankAccountTransactionsService;
import com.bitbank.services.UserDetailsServiceImpl;
import com.bitbank.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
// Delete database before each test
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
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
	@WithMockUser(username = "username", authorities = { "ROLE_CUSTOMER" })
	@Test
	void testCanIndex() throws Exception {

		@SuppressWarnings("unchecked")
		Page<BankAccountTransactionsEntity> items = mock(Page.class);
		when(bankAccountTransactionsService.index(0, 1, "id", "desc")).thenReturn(items);
		mvc.perform(get("/api/v1/bank-account-transactions")).andExpect(status().isOk());
	}
	
	/**
	 * show
	 */
	@WithMockUser(username = "username", authorities = { "ROLE_CUSTOMER" })
	@Test
	void testCanShow() throws Exception {
		var id = 1L;
		var transaction = validBankAccountTransactionsEntity(new BigDecimal(100), "First Transaction");
		Optional<BankAccountTransactionsEntity> result = Optional.of(transaction);
		when(bankAccountTransactionsService.show(id)).thenReturn(result);
		mvc.perform(get("/api/v1/bank-account-transactions/"+id)).andExpect(status().isOk());
	}

	@WithMockUser(username = "username", authorities = { "ROLE_CUSTOMER" })
	@Test
	void testCanSave() throws Exception {
		var id = 1L;
		var transactionToSave = validBankAccountTransactionsEntity(new BigDecimal(99.99), "First Transaction");
		var savedTransaction = validBankAccountTransactionsEntity(id, new BigDecimal(99.99), "First Transaction");

		when(bankAccountTransactionsService.post(transactionToSave)).thenReturn(savedTransaction);

		mvc.perform(post("/api/v1/bank-account-transactions").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(transactionToSave))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is((int) id)))
				.andExpect(jsonPath("$.amount", is(transactionToSave.getAmount())));
	}

	@WithMockUser(username = "username", authorities = { "ROLE_CUSTOMER" })
	@ParameterizedTest
	@MethodSource("getInvalidBankAccountTransactions")
	void testCanCatchException(BankAccountTransactionsEntity invalidBankAccountTransactionsEntity) throws Exception {

		mvc.perform(post("/api/v1/bank-account-transactions").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(invalidBankAccountTransactionsEntity)))
				.andExpect(status().isBadRequest());

	}

	/**
	 * Method source to get a list of INVALID bank account transactions.
	 * 
	 * See test above.
	 * 
	 * @return
	 */
	private static Stream<BankAccountTransactionsEntity> getInvalidBankAccountTransactions() {
		// Create a valid entity
		var validBankAccountTransactionsEntity = validBankAccountTransactionsEntity(new BigDecimal(100), "ok");

		return Stream.of(validBankAccountTransactionsEntity.toBuilder().amount(null).build(),
				validBankAccountTransactionsEntity.toBuilder().purpose(null).build(),
				validBankAccountTransactionsEntity.toBuilder().purpose("").build());
	}

	/**
	 * Test not enough deduct throws BalanceNotEnoughException
	 * 
	 */
	@WithMockUser(username = "username", authorities = { "ROLE_CUSTOMER" })
	@Test
	void testCanThrowsBalanceNotEnoughException() throws Exception {

		// Create a valid entity to submit.
		var transactionToSave = validBankAccountTransactionsEntity(new BigDecimal(100), "test");

		when(bankAccountTransactionsService.post(transactionToSave))
				.thenThrow(new BalanceNotEnoughException("balance is not enough to deduct 100"));

		mvc.perform(post("/api/v1/bank-account-transactions").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(transactionToSave))).andExpect(status().isBadRequest())
				.andExpect(
						jsonPath("$.error", is("balance is not enough to deduct " + (new BigDecimal(100).toString()))));

	}

	/**
	 * Generate a valid transaction.
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

	/**
	 * Generate a valid transaction.
	 * 
	 * @param id
	 * @param amount
	 * @param purpose
	 * @return
	 */
	private static BankAccountTransactionsEntity validBankAccountTransactionsEntity(BigDecimal amount, String purpose) {
		return BankAccountTransactionsEntity.builder().amount(amount).purpose(purpose).build();
	}
}
