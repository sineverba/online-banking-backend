package com.bitbank.test.repository.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bitbank.entities.v1.UsersEntity;
import com.bitbank.repository.v1.UsersRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class UsersRepositoryTest {

	@Autowired
	private UsersRepository usersRepository;

	private static UsersEntity usersEntity(Long id, String username, String password) {
		return UsersEntity.builder().id(id).username(username).password(password).build();
	}

	/**
	 * Index
	 */
	@Test
	void testCanFindAll() {

		List<UsersEntity> list = new ArrayList<UsersEntity>();
		List<UsersEntity> result = new ArrayList<UsersEntity>();

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

		UsersEntity usersEntity01 = usersEntity(1L, "username", "password");
		list.add(usersEntity01);
		list.add(usersEntity(2L, "anotherUsername", "anotherPassword"));

		for (UsersEntity entity : list) {
			usersRepository.save(entity);
		}

		Optional<UsersEntity> result = Optional.of(usersEntity01);

		assertEquals(result, usersRepository.findByUsername("username"));
	}
}
