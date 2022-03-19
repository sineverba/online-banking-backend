package com.bitbank.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bitbank.entities.RolesEntity;
import com.bitbank.exceptions.RoleOrAuthorityNotFoundException;
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
	 * 
	 * @throws RoleOrAuthorityNotFoundException
	 */
	@Test
	void testCanGetFindByRole() throws RoleOrAuthorityNotFoundException {

		// Generate a valid role
		RolesEntity rolesEntity = validRolesEntity(1L, ERole.valueOf("ROLE_ADMIN"));

		// Save the role
		rolesRepository.save(rolesEntity);

		// Mock the method
		when(rolesRepository.findByRole(ERole.valueOf("ROLE_ADMIN"))).thenReturn(Optional.of(rolesEntity));

		assertEquals(Optional.of(rolesEntity), rolesService.show(ERole.valueOf("ROLE_ADMIN")));

	}

	/**
	 * Post section. Test can throw BalanceNotEnoughException
	 * 
	 * @throws RoleOrAuthorityNotFoundException
	 */

	@Test
	void testCanThrowRoleOrAuthorityNotFoundException() {

		// Generate a valid role
		// RolesEntity rolesEntity = validRolesEntity(1L, ERole.valueOf("ROLE_ADMIN"));

		// Mock the method
		Optional<RolesEntity> optionalRolesEntity = Optional.empty();
		when(rolesRepository.findByRole(ERole.valueOf("ROLE_ADMIN"))).thenReturn(optionalRolesEntity);

		// Throw the exception
		RoleOrAuthorityNotFoundException roleOrAuthorityNotFoundException = assertThrows(
				RoleOrAuthorityNotFoundException.class, () -> {
					rolesService.show(ERole.valueOf("ROLE_ADMIN"));
				});

		// Compare string
		String expectedMessage = "cannot find role ROLE_ADMIN";
		String actualMessage = roleOrAuthorityNotFoundException.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
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
