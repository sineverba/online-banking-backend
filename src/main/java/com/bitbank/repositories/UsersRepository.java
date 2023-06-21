package com.bitbank.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.bitbank.entities.UsersEntity;

public interface UsersRepository extends CrudRepository<UsersEntity, Long> {

	Optional<UsersEntity> findByUsername(String username);

	Optional<UsersEntity> findByTempSecret(String tempSecret);

}
