package com.bitbank.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bitbank.entities.UsersEntity;
import com.bitbank.repositories.UsersRepository;

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

	/**
	 * Return an user by ID
	 * @param id
	 * @return UserDetails user
	 */
	@Transactional
	public UserDetails loadUserById(int id) {
		UsersEntity userEntity = usersRepository.findById(Long.valueOf(id))
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return UserDetailsImpl.build(userEntity);
	}

}
