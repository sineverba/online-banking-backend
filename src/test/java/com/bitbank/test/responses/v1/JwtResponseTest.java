package com.bitbank.test.responses.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bitbank.responses.v1.JwtResponse;

@ExtendWith(MockitoExtension.class)
class JwtResponseTest {

	@Test
	void canReturnToken() {
		String token = "a1.b2.c3";
		JwtResponse jwtResponse = new JwtResponse(token);
		assertEquals(token, jwtResponse.getToken());
	}

}