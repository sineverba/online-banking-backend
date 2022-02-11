package com.bitbank.test.services.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bitbank.services.v1.JwtUserDetailService;

@ExtendWith(MockitoExtension.class)
class JwtUserDetailServiceTest {

	@Test
	void testCanLoadUserByUsername() {

		JwtUserDetailService jwtUserDetailService = new JwtUserDetailService();
		UserDetails userDetails = jwtUserDetailService.loadUserByUsername("username");
		assertEquals("username", userDetails.getUsername());
		assertNotEquals("username2", userDetails.getUsername());

	}

	@Test
	void testCanThrowException() {
		JwtUserDetailService jwtUserDetailService = new JwtUserDetailService();

		UsernameNotFoundException usernameNotFoundException = assertThrows(UsernameNotFoundException.class, () -> {
			UserDetails userDetails = jwtUserDetailService.loadUserByUsername("username2");
		});

		String expectedMessage = "User not found with username: username2";
		String actualMessage = usernameNotFoundException.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

}
