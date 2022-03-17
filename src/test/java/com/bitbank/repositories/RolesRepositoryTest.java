package com.bitbank.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bitbank.entities.RolesEntity;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
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

		list.add(rolesEntity(1L, ERole.valueOf("ADMIN")));
		list.add(rolesEntity(2L, ERole.valueOf("CUSTOMER")));

		for (RolesEntity entity : list) {
			rolesRepository.save(entity);
			result.add(entity);
		}

		assertEquals(result, rolesRepository.findAll());
	}

	/**
	 * Generate a RolesEntity
	 */
	private static RolesEntity rolesEntity(Long id, ERole role) {
		return RolesEntity.builder().id(id).role(role).build();
	}
}