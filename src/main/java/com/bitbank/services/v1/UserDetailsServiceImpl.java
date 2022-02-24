package com.bitbank.services.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bitbank.entities.v1.UsersEntity;
import com.bitbank.repositories.v1.UsersRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UsersRepository usersRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) {
		UsersEntity userEntity = usersRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return UserDetailsImpl.build(userEntity);
	}

	/**
	 * Post
	 */
	public UsersEntity post(UsersEntity usersEntity) {
		return usersRepository.save(usersEntity);
	}

}
