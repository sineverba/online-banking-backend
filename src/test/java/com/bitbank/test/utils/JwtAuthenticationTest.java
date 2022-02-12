package com.bitbank.test.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bitbank.utils.JwtAuthentication;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class JwtAuthenticationTest {

	@Autowired
	private JwtAuthentication jwtAuthentication;

	@Mock
	private UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken;

	@Test
	void testCanAuthenticate() throws Exception {
		jwtAuthentication.authenticate("username", "password");
		verify(usernamePasswordAuthenticationToken, times(0)).setAuthenticated(false);
	}

	@Test
	void testCanThrowBadCredentialsException() throws Exception {
		Exception exception = assertThrows(Exception.class, () -> {
			jwtAuthentication.authenticate("anotherUsername", "anotherPassword");
		});

		String expectedMessage = "INVALID_CREDENTIALS";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}
}
