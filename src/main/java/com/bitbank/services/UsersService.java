package com.bitbank.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitbank.entities.UsersEntity;
import com.bitbank.repositories.UsersRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UsersService {

	@Autowired
	private UsersRepository usersRepository;

	/**
	 * Return single user.
	 * 
	 * @param id
	 * @return Optional<UsersEntity>
	 */
	public Optional<UsersEntity> show(long id) {
		return usersRepository.findById(id);
	}
	
	/**
	 * Return the ID of the user from username
	 * 
	 * @param string
	 * @return Long ID the id of the user
	 */
	public Long getIdFromUsername(String username) {
		Optional<UsersEntity> user = usersRepository.findByUsername(username);
		if (user.isEmpty()) {
			String error = "no user found with username " + username;
			throw new EntityNotFoundException(error);
		}
		return user.get().getId();
	}

}
