package com.bitbank.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.bitbank.config.AuthEntryPointJwt;
import com.bitbank.config.AuthTokenFilter;
import com.bitbank.services.BankAccountTransactionsService;
import com.bitbank.services.UserDetailsServiceImpl;
import com.bitbank.utils.JwtUtils;

@AutoConfigureMockMvc
@SpringBootTest
class BalanceControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private BankAccountTransactionsService bankAccountTransactionsService;

	@Autowired
	AuthTokenFilter authTokenFilter;

	@MockBean
	JwtUtils jwtUtils;

	@MockBean
	UserDetailsServiceImpl userDetailsServiceImpl;

	@MockBean
	AuthEntryPointJwt authEntryPointJwt;

	/**
	 * Can get Balance
	 */
	@WithMockUser(username = "username", authorities = { "ROLE_CUSTOMER" })
	@Test
	void testCanGetBalance() throws Exception {

		BigDecimal balance = new BigDecimal(1000);

		when(bankAccountTransactionsService.balance()).thenReturn(balance);

		mvc.perform(get("/api/v1/balance")).andExpect(status().isOk())
				.andExpect(jsonPath("$.balance", is(balance.intValue())));
	}

	/**
	 * Test access denied exception.
	 * 
	 * Access with role admin to a route created for customer
	 */
	@WithMockUser(username = "username", authorities = { "ROLE_ADMIN" })
	@Test
	void testCanThrowCustomException() throws Exception {

		mvc.perform(get("/api/v1/balance")).andExpect(status().isForbidden());
	}
}
