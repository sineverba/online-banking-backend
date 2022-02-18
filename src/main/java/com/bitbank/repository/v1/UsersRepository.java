package com.bitbank.repository.v1;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bitbank.entities.v1.UsersEntity;

@Repository
public interface UsersRepository extends CrudRepository<UsersEntity, Long> {

	Optional<UsersEntity> findByUsername(String username);

}
