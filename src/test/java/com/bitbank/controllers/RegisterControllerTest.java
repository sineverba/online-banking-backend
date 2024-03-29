package com.bitbank.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.bitbank.config.AuthEntryPointJwt;
import com.bitbank.config.AuthTokenFilter;
import com.bitbank.entities.UsersEntity;
import com.bitbank.services.RolesService;
import com.bitbank.services.UserDetailsServiceImpl;
import com.bitbank.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties", properties = "app.enableSubscription=false")
class RegisterControllerTest {

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
	private RolesService rolesService;

	@Test
	@WithMockUser(username = "username", authorities = { "ROLE_ADMIN" })
	void cannotRegisterUserWhenSubscriptionsAreDisabled() throws Exception {

		var userToSave = validUserEntity("username", "password");
		var savedUser = validUserEntity("username", "a1.b2.c3");

		when(userDetailsServiceImpl.post(userToSave)).thenReturn(savedUser);

		mvc.perform(post("/api/v1/auth/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(userToSave))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status", is("error")))
				.andExpect(jsonPath("$.message", is("subscriptions disabled")));
	}

	private static UsersEntity validUserEntity(String username, String password) {
		return UsersEntity.builder().username(username).password(password).build();
	}

}