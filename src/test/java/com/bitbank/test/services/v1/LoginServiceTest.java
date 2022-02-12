package com.bitbank.test.services.v1;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bitbank.services.v1.LoginService;
import com.bitbank.utils.JwtAuthentication;

@SpringBootTest
class LoginServiceTest {
	
	@Autowired
	private LoginService loginService;

	@Test
	void canReturnTrueIfAuthenticated() {
		JwtAuthentication jwtAuthentication = new JwtAuthentication();
		JwtAuthentication spy = Mockito.spy(jwtAuthentication);
		Mockito.doNothing().when(spy).authenticate("username", "password");
		assertTrue(loginService.authenticate("username", "password"));
	}

}
