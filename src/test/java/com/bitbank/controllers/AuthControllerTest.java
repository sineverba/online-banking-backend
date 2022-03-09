package com.bitbank.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
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
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthController.class)
@TestPropertySource("classpath:application.properties")
class AuthControllerTest {

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
	void canRegisterNewUser() throws Exception {

		var userToSave = validUserEntity("username", "password");
		var savedUser = validUserEntity("username", "a1.b2.c3");

		when(userDetailsServiceImpl.post(userToSave)).thenReturn(savedUser);

		mvc.perform(post("/api/v1/auth/register/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(userToSave))).andExpect(status().isCreated());
	}

	@WithMockUser("username")
	@Test
	void canLogin() throws Exception {

		// Add security context
		SecurityContextHolder.setContext(securityContext);

		// Create a new usersentity to deal with authentication
		UsersEntity usersEntity = new UsersEntity(1L, "username", "password");
		// Build an user from usersEntity
		UserDetailsImpl user = UserDetailsImpl.build(usersEntity);

		// Mock some methods
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getPrincipal()).thenReturn(user);

		String token = jwtUtils.generateJwtToken(authentication);

		Long expiryDate = jwtUtils.getExpiryDateFromJwtToken(token);

		// Create an usersEntity to pass to the login
		var userToLogin = validUserEntity("username", "password");

		mvc.perform(post("/api/v1/auth/login/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(userToLogin))).andExpect(status().isOk())
				.andExpect(jsonPath("$.access_token", is(token)))
				.andExpect(jsonPath("$.expiry_at", is(expiryDate.toString())));
	}

	/**
	 * Perform several tests, with invalid input.
	 * 
	 * @param invalidUsersEntity
	 * @throws Exception
	 */
	@ParameterizedTest
	@MethodSource("getInvalidUsers")
	void testCanCatchExceptionOnLogin(UsersEntity invalidUsersEntity) throws Exception {

		mvc.perform(post("/api/v1/auth/login/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(invalidUsersEntity))).andExpect(status().isBadRequest());

	}

	@WithMockUser("username")
	@Test
	void testCanRefreshToken() throws Exception {

		// Add the Security Context
		SecurityContextHolder.setContext(securityContext);

		// Create an usersEntity to build by userDetailsImpl
		UsersEntity usersEntity = new UsersEntity(1L, "username", "password");
		UserDetailsImpl user = UserDetailsImpl.build(usersEntity);

		// Mock some method...
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getPrincipal()).thenReturn(user);

		// Create a fake token
		String token = "a1.b2.c3";
		String refreshedToken = "re.fre.shed";
		Long expiryAt = 19999999L;
		when(jwtUtils.generateJwtToken(authentication)).thenReturn(token);
		when(jwtUtils.generateJwtToken(token)).thenReturn(refreshedToken);
		when(jwtUtils.getExpiryDateFromJwtToken(refreshedToken)).thenReturn(expiryAt);

		// Make the call
		mvc.perform(post("/api/v1/auth/refresh-token/").contentType(MediaType.APPLICATION_JSON).header("Authorization",
				token)).andExpect(status().isOk()).andExpect(jsonPath("$.access_token", is(refreshedToken)))
				.andExpect(jsonPath("$.expiry_at", is(expiryAt.toString())));
	}

	/**
	 * Perform several tests, with invalid input.
	 * 
	 * @param invalidUsersEntity
	 * @throws Exception
	 */
	@ParameterizedTest
	@MethodSource("getInvalidUsers")
	void testCanCatchExceptionOnRegistration(UsersEntity invalidUsersEntity) throws Exception {

		mvc.perform(post("/api/v1/auth/register/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(invalidUsersEntity))).andExpect(status().isBadRequest());

	}

	/**
	 * Return a series of invalid users
	 * 
	 * @return
	 */
	private static Stream<UsersEntity> getInvalidUsers() {
		// Create a valid entity
		var validUsersEntity = validUserEntity("username", "password");

		return Stream.of(validUsersEntity.toBuilder().username(null).build(),
				validUsersEntity.toBuilder().username("").build(), validUsersEntity.toBuilder().password(null).build(),
				validUsersEntity.toBuilder().password("").build());
	}

	private static UsersEntity validUserEntity(String username, String password) {
		return UsersEntity.builder().username(username).password(password).build();
	}

}