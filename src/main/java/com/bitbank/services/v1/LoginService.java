package com.bitbank.services.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitbank.utils.JwtAuthentication;

@Service
public class LoginService {

	@Autowired
	private JwtAuthentication jwtAuthentication;

	public Boolean authenticate(String username, String password) {
		jwtAuthentication.authenticate(username, password);
		return true;
	}

}
