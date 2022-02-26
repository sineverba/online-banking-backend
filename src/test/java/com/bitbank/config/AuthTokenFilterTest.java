package com.bitbank.config;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.bitbank.config.AuthEntryPointJwt;
import com.bitbank.config.AuthTokenFilter;
import com.bitbank.controllers.PingController;
import com.bitbank.services.PingService;
import com.bitbank.services.UserDetailsServiceImpl;
import com.bitbank.utils.JwtUtils;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PingController.class)
class AuthTokenFilterTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	AuthTokenFilter authTokenFilter;

	@MockBean
	JwtUtils jwtUtils;

	@MockBean
	UserDetailsServiceImpl userDetailsServiceImpl;

	@MockBean
	private PingService pingService;
	
	@MockBean
	AuthEntryPointJwt authEntryPointJwt;

	@Test
	void testCanReturnNullIfJwtIsMissing() throws Exception {
		mvc.perform(get("/api/v1/ping")).andExpect(status().isOk());
	}

	@Test
	void testCanValidateToken() throws Exception {
		String token = "a1.b2.c3";
		when(jwtUtils.validateJwtToken(token)).thenReturn(true);
		mvc.perform(get("/api/v1/ping").header("Authorization", "Bearer " + token)).andExpect(status().isOk());
	}

	@Test
	void testCannotValidateToken() throws Exception {
		String token = "a1.b2.c3";
		when(jwtUtils.validateJwtToken(token)).thenReturn(false);
		mvc.perform(get("/api/v1/ping").header("Authorization", "Bearer " + token)).andExpect(status().isOk());
	}

	@Test
	void testCanReturnNullIfJwtIsMissingButOtherHeaderIsInPlace() throws Exception {
		String token = "a1.b2.c3";
		mvc.perform(get("/api/v1/ping").header("Authorization", "NotStartWithBearer " + token))
				.andExpect(status().isOk());
	}

}