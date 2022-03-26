package com.bitbank.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitbank.constants.ERole;
import com.bitbank.entities.RolesEntity;
import com.bitbank.exceptions.RoleOrAuthorityNotFoundException;
import com.bitbank.repositories.RolesRepository;

@Service
public class RolesService {

	@Autowired
	private RolesRepository rolesRepository;

	/**
	 * Return single item
	 * 
	 * @throws RoleOrAuthorityNotFoundException
	 */
	public RolesEntity show(ERole role) throws RoleOrAuthorityNotFoundException {

		Optional<RolesEntity> optionalRolesEntity = rolesRepository.findByRole(role);
		if (optionalRolesEntity.isEmpty()) {
			throw new RoleOrAuthorityNotFoundException("cannot find role " + role);
		}
		return optionalRolesEntity.get();
	}

}
