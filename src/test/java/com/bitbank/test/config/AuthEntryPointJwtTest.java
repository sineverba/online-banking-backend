package com.bitbank.test.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import com.bitbank.config.AuthEntryPointJwt;

class AuthEntryPointJwtTest {

	@Test
	void testCanReturn401() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		AuthEntryPointJwt authEntryPointJwt = new AuthEntryPointJwt();
		authEntryPointJwt.commence(request, response, null);
		assertThat(response.getStatus()).isEqualTo(401);
	}
}
