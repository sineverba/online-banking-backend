package com.bitbank.test.config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;

import com.bitbank.config.JwtTokenUtil;
import com.bitbank.utils.TimeSource;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
class JwtTokenUtilTest {
	
	@MockBean
	private TimeSource timeSource;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Test
	void canGenerateToken() {
		
		when(timeSource.getCurrentTimeMillis()).thenReturn(System.currentTimeMillis());
		UserDetails userDetails = new User("username", "password", new ArrayList<>());
		String token = jwtTokenUtil.generateToken(userDetails);
		assertTrue(!token.isEmpty());
		
	}
	
	@Test
	void canGetUsername() {
		
		when(timeSource.getCurrentTimeMillis()).thenReturn(System.currentTimeMillis());
		UserDetails userDetails = new User("username", "password", new ArrayList<>());
		String token = jwtTokenUtil.generateToken(userDetails);
		assert("username").equals(jwtTokenUtil.getUsername(token));
		assertNotEquals("NotRealUsername", jwtTokenUtil.getUsername(token));
		
	}
	
	@Test
	void catGetExpirationDate() {
		
		when(timeSource.getCurrentTimeMillis()).thenReturn(System.currentTimeMillis());
		UserDetails userDetails = new User("username", "password", new ArrayList<>());
		String token = jwtTokenUtil.generateToken(userDetails);
		assertTrue(!jwtTokenUtil.getExpirationDate(token).before(new Date(System.currentTimeMillis())));
		
	}
	
	@Test
	void canValidateToken() {
		when(timeSource.getCurrentTimeMillis()).thenReturn(System.currentTimeMillis());
		UserDetails userDetails = new User("username", "password", new ArrayList<>());
		UserDetails userDetails2 = new User("username2", "password2", new ArrayList<>());
		String token = jwtTokenUtil.generateToken(userDetails);
		assertTrue(jwtTokenUtil.isValidToken(token, userDetails));
		// Validate token against another user
		assertFalse(jwtTokenUtil.isValidToken(token, userDetails2));
		
	}
	
	@Test
	void canCheckThatTokenIsExpired() {
		
		when(timeSource.getCurrentTimeMillis()).thenReturn(1262344822000L);
		
		UserDetails userDetails = new User("username", "password", new ArrayList<>());
		String token = jwtTokenUtil.generateToken(userDetails);
		assertFalse(jwtTokenUtil.isValidToken(token, userDetails));
	}
	
}
