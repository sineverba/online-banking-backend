package com.bitbank.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.bitbank.config.AuthEntryPointJwt;
import com.bitbank.config.AuthTokenFilter;
import com.bitbank.entities.UsersEntity;
import com.bitbank.services.UserDetailsImpl;
import com.bitbank.services.UserDetailsServiceImpl;
import com.bitbank.utils.JwtUtils;
import com.bitbank.utils.TimeSource;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MeController.class)
@TestPropertySource("classpath:application.properties")
class MeControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	AuthTokenFilter authTokenFilter;

	@MockBean
	AuthEntryPointJwt authEntryPointJwt;

	@MockBean
	JwtUtils jwtUtils;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	UserDetailsServiceImpl userDetailsServiceImpl;

	@MockBean
	AuthenticationManager authenticationManager;

	@MockBean
	Authentication authentication;

	@MockBean
	SecurityContext securityContext;

	@MockBean
	private TimeSource timeSource;

	@WithMockUser("testusername")
	@Test
	void testCanReturnUsername() throws Exception {

		// Add the Security Context
		SecurityContextHolder.setContext(securityContext);

		// Create an usersEntity to build by userDetailsImpl
		UsersEntity usersEntity = new UsersEntity(1L, "testusername", "password");
		UserDetailsImpl user = UserDetailsImpl.build(usersEntity);

		// Mock some method...
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getPrincipal()).thenReturn(user);

		// Create a fake token
		String token = "a1.b2.c3";

		// Mock return
		when(jwtUtils.getUserNameFromJwtToken(token)).thenReturn("testusername");

		mvc.perform(get("/api/v1/me/").contentType(MediaType.APPLICATION_JSON).header("Authorization", token))
				.andExpect(status().isOk()).andExpect(jsonPath("$.username", is("testusername")));
	}

	/**
	 * Return a valid user entity
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	private static UsersEntity validUserEntity(String username, String password) {
		return UsersEntity.builder().username(username).password(password).build();
	}

}