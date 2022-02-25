package com.bitbank.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bitbank.entities.UsersEntity;

@Repository
public interface UsersRepository extends CrudRepository<UsersEntity, Long> {

	Optional<UsersEntity> findByUsername(String username);

}
