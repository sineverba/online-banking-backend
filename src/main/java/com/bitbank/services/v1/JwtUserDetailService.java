package com.bitbank.services.v1;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailService implements UserDetailsService {
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (username.equals("username")) {
			return new User("username", "$2a$12$iJ8d5kJoWMTUrhQu70j8yu.u7V3ZPefV.qo6f0I1fUcRTdhxveSzi\n" + "",
					new ArrayList<>());
		}
		throw new UsernameNotFoundException("User not found with username: " + username);
	}
}
