package com.bitbank.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bitbank.entities.UsersEntity;
import com.bitbank.services.UserDetailsImpl;

@ExtendWith(SpringExtension.class)
class AuthenticationUtilsTest {

	@MockBean
	AuthenticationManager authenticationManager;

	@MockBean
	Authentication authentication;

	@MockBean
	SecurityContext securityContext;

	@Test
	void testCanReturnUsersDetailsImpl() {

		AuthenticationUtils authenticationUtils = new AuthenticationUtils();

		UsersEntity usersEntity = new UsersEntity(1L, "username", "password");
		UserDetailsImpl user = UserDetailsImpl.build(usersEntity);

		when(securityContext.getAuthentication()).thenReturn(authentication);
		when(authentication.getPrincipal()).thenReturn(user);

		assertEquals(user, authenticationUtils.getPrincipal(authentication));

	}

}
