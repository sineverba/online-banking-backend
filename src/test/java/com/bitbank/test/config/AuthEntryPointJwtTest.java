package com.bitbank.test.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

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
