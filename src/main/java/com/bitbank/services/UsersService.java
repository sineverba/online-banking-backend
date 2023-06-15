package com.bitbank.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitbank.entities.UsersEntity;
import com.bitbank.repositories.UsersRepository;

@Service
public class UsersService {

	@Autowired
	private UsersRepository usersRepository;

	/**
	 * Return single transaction.
	 * 
	 * @param id
	 * @return Optional<UsersEntity>
	 */
	public Optional<UsersEntity> show(long id) {
		return usersRepository.findById(id);
	}
}
