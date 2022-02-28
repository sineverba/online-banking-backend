package com.bitbank.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bitbank.entities.RefreshTokensEntity;

@Repository
public interface RefreshTokensRepository extends CrudRepository<RefreshTokensEntity, Long> {

}
