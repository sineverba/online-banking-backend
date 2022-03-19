package com.bitbank.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitbank.entities.RolesEntity;
import com.bitbank.repositories.ERole;
import com.bitbank.repositories.RolesRepository;

@Service
public class RolesService {

	@Autowired
	private RolesRepository rolesRepository;

	/**
	 * Return single item
	 */
	public Optional<RolesEntity> show(ERole role) {
		return rolesRepository.findByRole(role);
	}

}
