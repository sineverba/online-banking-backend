package com.bitbank.test.controllers.v1;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.bitbank.config.AuthEntryPointJwt;
import com.bitbank.config.AuthTokenFilter;
import com.bitbank.controllers.v1.AuthController;
import com.bitbank.entities.v1.UsersEntity;
import com.bitbank.services.v1.UserDetailsServiceImpl;
import com.bitbank.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthController.class)
@TestPropertySource(locations = "classpath:application.properties", properties = "app.enableRegistration=false")
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

	@Test
	void cannotRegisterUserWhenRegistrationsAreDisabled() throws Exception {

		var userToSave = validUserEntity("username", "password");
		var savedUser = validUserEntity("username", "a1.b2.c3");

		when(userDetailsServiceImpl.post(userToSave)).thenReturn(savedUser);

		mvc.perform(post("/api/v1/auth/register/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(userToSave))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.status", is("registrations are disabled")));
	}

	private static UsersEntity validUserEntity(String username, String password) {
		return UsersEntity.builder().username(username).password(password).build();
	}

}