package com.bitbank.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bitbank.entities.RolesEntity;
import com.bitbank.repositories.ERole;
import com.bitbank.repositories.RolesRepository;

@ExtendWith(MockitoExtension.class)
class RolesServiceTest {
	@Mock
	private RolesRepository rolesRepository;

	@InjectMocks
	private RolesService rolesService;

	/**
	 * Test can get a Role by findByRole
	 */
	@Test
	void testCanGetFindByRole() {

		// Generate a valid role
		RolesEntity rolesEntity = validRolesEntity(1L, ERole.valueOf("ROLE_ADMIN"));

		// Save the role
		rolesRepository.save(rolesEntity);

		// Mock the method
		when(rolesRepository.findByRole(ERole.valueOf("ROLE_ADMIN"))).thenReturn(Optional.of(rolesEntity));

		assertEquals(Optional.of(rolesEntity), rolesService.show(ERole.valueOf("ROLE_ADMIN")));

	}

	/**
	 * Test can handle empty role
	 */
	@Test
	void testCanHandleEmptyRole() {

		when(rolesRepository.findByRole(ERole.valueOf("ROLE_ADMIN"))).thenReturn(Optional.empty());

		assertTrue(rolesService.show(ERole.valueOf("ROLE_ADMIN")).isEmpty());

	}

	/**
	 * Useful methods
	 * 
	 * @param id
	 * @param amount
	 * @param purpose
	 * @return
	 */

	private static RolesEntity validRolesEntity(Long id, ERole role) {
		return RolesEntity.builder().id(id).role(role).build();
	}
}
