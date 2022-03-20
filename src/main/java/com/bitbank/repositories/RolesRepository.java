package com.bitbank.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bitbank.entities.RolesEntity;

@Repository
public interface RolesRepository extends CrudRepository<RolesEntity, Long> {

	Optional<RolesEntity> findByRole(ERole role);
}
