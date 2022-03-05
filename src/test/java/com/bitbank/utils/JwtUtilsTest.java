package com.bitbank.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;

import com.bitbank.entities.UsersEntity;
import com.bitbank.services.UserDetailsImpl;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
class JwtUtilsTest {

	@MockBean
	private TimeSource timeSource;

	@Autowired
	private JwtUtils jwtUtils;

	@MockBean
	AuthenticationManager authenticationManager;

	@MockBean
	Authentication authentication;

	@MockBean
	SecurityContext securityContext;

	@Value("${app.jwtExpirationMs}")
	private String jwtExpirationMs;

	@Test
	void canGenerateToken() {

		UsersEntity usersEntity = new UsersEntity(1L, "username", "password");
		UserDetailsImpl user = UserDetailsImpl.build(usersEntity);

		when(timeSource.getCurrentTimeMillis()).thenReturn(System.currentTimeMillis());
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getPrincipal()).thenReturn(user);
		SecurityContextHolder.setContext(securityContext);
		String token = jwtUtils.generateJwtToken(authentication);
		assertFalse(token.isEmpty());
	}

	@Test
	void canGetUserNameFromJwtToken() {
		UsersEntity usersEntity = new UsersEntity(1L, "username", "password");
		UserDetailsImpl user = UserDetailsImpl.build(usersEntity);

		when(timeSource.getCurrentTimeMillis()).thenReturn(System.currentTimeMillis());
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getPrincipal()).thenReturn(user);
		SecurityContextHolder.setContext(securityContext);
		String token = jwtUtils.generateJwtToken(authentication);

		String username = jwtUtils.getUserNameFromJwtToken(token);
		assertEquals("username", username);
	}

	@Test
	void canValidateToken() {
		UsersEntity usersEntity = new UsersEntity(1L, "username", "password");
		UserDetailsImpl user = UserDetailsImpl.build(usersEntity);

		when(timeSource.getCurrentTimeMillis()).thenReturn(System.currentTimeMillis());
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getPrincipal()).thenReturn(user);
		SecurityContextHolder.setContext(securityContext);
		String token = jwtUtils.generateJwtToken(authentication);

		assertTrue(jwtUtils.validateJwtToken(token));
	}

	@Test
	void canCathMalformedException() {
		when(timeSource.getCurrentTimeMillis()).thenReturn(System.currentTimeMillis());
		String token = "jwt.fake.notvalid";
		assertFalse(jwtUtils.validateJwtToken(token));
	}

	@Test
	void canCheckTokenIsExpired() {
		UsersEntity usersEntity = new UsersEntity(1L, "username", "password");
		UserDetailsImpl user = UserDetailsImpl.build(usersEntity);

		// 1262344822000 == 01 Jan 2010 11:20:22 UTC
		when(timeSource.getCurrentTimeMillis()).thenReturn(1262344822000L);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getPrincipal()).thenReturn(user);
		SecurityContextHolder.setContext(securityContext);
		String token = jwtUtils.generateJwtToken(authentication);

		assertFalse(jwtUtils.validateJwtToken(token));
	}

	@Test
	void canGenerateTokenFromTokenItself() {

		UsersEntity usersEntity = new UsersEntity(1L, "username", "password");
		UserDetailsImpl user = UserDetailsImpl.build(usersEntity);

		when(timeSource.getCurrentTimeMillis()).thenReturn(System.currentTimeMillis());
		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getPrincipal()).thenReturn(user);
		SecurityContextHolder.setContext(securityContext);
		String token = jwtUtils.generateJwtToken(authentication);
		assertFalse(token.isEmpty());
		String newToken = jwtUtils.generateJwtToken(token);
		assertNotNull(newToken);
	}

	@Test
	void canGetExpiryDateFromJwtToken() {

		// Set the security context
		SecurityContextHolder.setContext(securityContext);

		// Mock the time at.. now
		Long currentTimeMillis = System.currentTimeMillis();

		// Mock the methods
		when(timeSource.getCurrentTimeMillis()).thenReturn(currentTimeMillis);
		when(securityContext.getAuthentication()).thenReturn(authentication);

		// Create an usersEntitiy
		UsersEntity usersEntity = new UsersEntity(1L, "username", "password");
		// Build the entity to return from getPrincipal
		UserDetailsImpl user = UserDetailsImpl.build(usersEntity);
		when(authentication.getPrincipal()).thenReturn(user);

		// Finally, generate a token
		String token = jwtUtils.generateJwtToken(authentication);

		// Get the expiry date (our method under test)
		Long expiryDate = jwtUtils.getExpiryDateFromJwtToken(token);

		// Finally, assert equals. Accept a small clock shift
		Long expectedExpiryDate = currentTimeMillis + Long.parseLong(jwtExpirationMs);
		assertEquals(expectedExpiryDate / 10000, expiryDate / 10000);
	}
}
