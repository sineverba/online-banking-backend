package com.bitbank.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

import com.bitbank.constants.ERole;
import com.bitbank.entities.RolesEntity;

@DataJpaTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource("classpath:application.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RolesRepositoryTest {

	@Autowired
	private RolesRepository rolesRepository;

	/**
	 * Test can index (returns all)
	 */
	@Test
	void testCanIndexFindAll() {

		List<RolesEntity> list = new ArrayList<RolesEntity>();
		List<RolesEntity> result = new ArrayList<RolesEntity>();

		list.add(validRolesEntity(1L, ERole.valueOf("ROLE_ADMIN")));
		list.add(validRolesEntity(2L, ERole.valueOf("ROLE_CUSTOMER")));

		for (RolesEntity entity : list) {
			rolesRepository.save(entity);
			result.add(entity);
		}

		assertEquals(result, rolesRepository.findAll());
	}

	/**
	 * Test can findByRole
	 */
	@Test
	void testCanFindByRole() {

		// Create new RolesEntity
		RolesEntity rolesEntity = validRolesEntity(1L, ERole.valueOf("ROLE_ADMIN"));

		// Save the entities
		rolesRepository.save(rolesEntity);

		// Create an optional result
		Optional<RolesEntity> result = Optional.of(rolesEntity);

		assertEquals(result, rolesRepository.findByRole(ERole.ROLE_ADMIN));
	}

	/**
	 * Generate a RolesEntity
	 */
	private static RolesEntity validRolesEntity(Long id, ERole role) {
		return RolesEntity.builder().id(id).role(role).build();
	}
}
