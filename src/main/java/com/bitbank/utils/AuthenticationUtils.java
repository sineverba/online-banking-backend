package com.bitbank.utils;

import java.io.Serializable;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.bitbank.services.UserDetailsImpl;

@Component
public class AuthenticationUtils implements Serializable {

	private static final long serialVersionUID = -20220302071700L;

	public UserDetailsImpl getPrincipal(Authentication authentication) {
		return (UserDetailsImpl) authentication.getPrincipal();
	}

}
