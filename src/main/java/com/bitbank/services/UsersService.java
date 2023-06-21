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
	 * Return single user.
	 * 
	 * @param String tempSecret
	 * @return Optional<UsersEntity>
	 */
	public Optional<UsersEntity> findByTempCode(String tempSecret) {
		return usersRepository.findByTempSecret(tempSecret);
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

	/**
	 * Store the temp secret into user
	 */
	public boolean setTempSecret(Long id, String tempSecret) {
		Optional<UsersEntity> usersEntity = usersRepository.findById(id);
		if (usersEntity.isEmpty()) {
			String error = "no user found with id " + id.toString();
			throw new EntityNotFoundException(error);
		}
		usersEntity.get().setTempSecret(tempSecret);
		UsersEntity updateUsersEntity = usersRepository.save(usersEntity.get());
		return updateUsersEntity.getTempSecret() == null
				|| updateUsersEntity.getTempSecret().equals(usersEntity.get().getTempSecret());
	}

}
