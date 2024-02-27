package com.bitbank.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;

import com.bitbank.constants.ERole;
import com.bitbank.entities.RolesEntity;
import com.bitbank.entities.UsersEntity;

@DataJpaTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource("classpath:application.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsersRepositoryTest {

	@Autowired
	private UsersRepository usersRepository;

	/**
	 * Index
	 */
	@Test
	void testCanFindAll() {

		List<UsersEntity> list = new ArrayList<UsersEntity>();
		List<UsersEntity> result = new ArrayList<UsersEntity>();

		// ADMIN - Initialize the set
		Set<RolesEntity> adminRole = new HashSet<>();
		// ADMIN - Generate the entity
		RolesEntity adminRolesEntity = rolesEntity(1L, ERole.valueOf("ROLE_ADMIN"));
		// ADMIN - Add the entity to the set
		adminRole.add(adminRolesEntity);

		// CUSTOMER - Initialize the set
		Set<RolesEntity> customerRole = new HashSet<>();
		// CUSTOMER - Generate the entity
		RolesEntity customerRolesEntity = rolesEntity(2L, ERole.valueOf("ROLE_CUSTOMER"));
		// CUSTOMER - Add the entity to the set
		customerRole.add(customerRolesEntity);

		list.add(usersEntity(1L, "username", "password"));
		list.add(usersEntity(2L, "anotherUsername", "anotherPassword"));

		for (UsersEntity entity : list) {
			usersRepository.save(entity);
			result.add(entity);
		}

		assertEquals(result, usersRepository.findAll());
	}

	/**
	 * Find by username
	 */
	@Test
	void testCanFindByUsername() {

		List<UsersEntity> list = new ArrayList<UsersEntity>();

		// ADMIN - Initialize the set
		Set<RolesEntity> adminRole = new HashSet<>();
		// ADMIN - Generate the entity
		RolesEntity adminRolesEntity = rolesEntity(1L, ERole.valueOf("ROLE_ADMIN"));
		// ADMIN - Add the entity to the set
		adminRole.add(adminRolesEntity);

		// CUSTOMER - Initialize the set
		Set<RolesEntity> customerRole = new HashSet<>();
		// CUSTOMER - Generate the entity
		RolesEntity customerRolesEntity = rolesEntity(2L, ERole.valueOf("ROLE_CUSTOMER"));
		// CUSTOMER - Add the entity to the set
		customerRole.add(customerRolesEntity);

		UsersEntity usersEntity01 = usersEntity(1L, "username", "password");
		list.add(usersEntity01);
		list.add(usersEntity(2L, "anotherUsername", "anotherPassword"));

		for (UsersEntity entity : list) {
			usersRepository.save(entity);
		}

		Optional<UsersEntity> result = Optional.of(usersEntity01);

		assertEquals(result, usersRepository.findByUsername("username"));
	}
	
	/**
	 * Find by id
	 */
	@Test
	void testCanFindById() {

		List<UsersEntity> list = new ArrayList<UsersEntity>();

		// ADMIN - Initialize the set
		Set<RolesEntity> adminRole = new HashSet<>();
		// ADMIN - Generate the entity
		RolesEntity adminRolesEntity = rolesEntity(1L, ERole.valueOf("ROLE_ADMIN"));
		// ADMIN - Add the entity to the set
		adminRole.add(adminRolesEntity);

		// CUSTOMER - Initialize the set
		Set<RolesEntity> customerRole = new HashSet<>();
		// CUSTOMER - Generate the entity
		RolesEntity customerRolesEntity = rolesEntity(2L, ERole.valueOf("ROLE_CUSTOMER"));
		// CUSTOMER - Add the entity to the set
		customerRole.add(customerRolesEntity);

		UsersEntity usersEntity01 = usersEntity(1L, "username", "password");
		list.add(usersEntity01);
		list.add(usersEntity(2L, "anotherUsername", "anotherPassword"));

		for (UsersEntity entity : list) {
			usersRepository.save(entity);
		}

		Optional<UsersEntity> result = Optional.of(usersEntity01);

		assertEquals(result, usersRepository.findById(1L));
		assertNotEquals(result, usersRepository.findById(2L));
	}

	/**
	 * Generate a valid UsersEntity to use in tests.
	 * 
	 * @param id
	 * @param username
	 * @param password
	 * @return
	 */
	private static UsersEntity usersEntity(Long id, String username, String password) {
		return UsersEntity.builder().id(id).username(username).password(password).build();
	}

	/**
	 * Generate a RolesEntity
	 */
	private static RolesEntity rolesEntity(Long id, ERole role) {
		return RolesEntity.builder().id(id).role(role).build();
	}
}
