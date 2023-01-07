package com.bitbank.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.bitbank.config.AuthEntryPointJwt;
import com.bitbank.config.AuthTokenFilter;
import com.bitbank.services.PingService;
import com.bitbank.services.UserDetailsServiceImpl;
import com.bitbank.utils.JwtUtils;

@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource("classpath:application.properties")
class PingControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	AuthTokenFilter authTokenFilter;

	@MockBean
	AuthEntryPointJwt authEntryPointJwt;

	@MockBean
	JwtUtils jwtUtils;

	@MockBean
	UserDetailsServiceImpl userDetailsServiceImpl;

	@MockBean
	private PingService pingService;

	@Value("${app.version}")
	private String appVersion;

	@Test
	void indexShouldReturnApiVersion() throws Exception {

		when(pingService.show()).thenReturn(appVersion);

		mvc.perform(get("/api/v1/ping")).andExpect(status().isOk()).andExpect(jsonPath("$.version", is(appVersion)));
	}

}