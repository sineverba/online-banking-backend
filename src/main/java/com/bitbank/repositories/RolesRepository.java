package com.bitbank.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.bitbank.constants.ERole;
import com.bitbank.entities.RolesEntity;

public interface RolesRepository extends CrudRepository<RolesEntity, Long> {

	Optional<RolesEntity> findByRole(ERole role);
}
